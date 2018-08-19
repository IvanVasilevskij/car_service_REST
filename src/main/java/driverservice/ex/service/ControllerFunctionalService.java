package driverservice.ex.service;

import driverservice.ex.dto.CarDTO;
import driverservice.ex.dto.ClientDTO;
import driverservice.ex.model.Car;
import driverservice.ex.model.Client;
import driverservice.ex.repository.CarRepository;
import driverservice.ex.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ControllerFunctionalService {
    private final CarRepository carRepository;
    private final ClientRepository clientRepository;
    private final ConverterEntityToDTO converterEntityToDTO;

    private static final Logger logger = LoggerFactory.getLogger(ControllerFunctionalService.class);

    @Autowired
    public ControllerFunctionalService(CarRepository carRepository, ClientRepository clientRepository, ConverterEntityToDTO converterEntityToDTO) {
        this.carRepository = carRepository;
        this.clientRepository = clientRepository;
        this.converterEntityToDTO = converterEntityToDTO;
    }

    public List<CarDTO> getAllFreeCarService() {
        List<Car> carList = carRepository.findAllByClientIsNull();
        return carList.stream()
                .map(converterEntityToDTO::convertToDTO).collect(Collectors.toList());
    }

    @Transactional
    public ClientDTO createClient(ClientDTO clientDTO) {
//        if (!clientDTO.getBirthday().matches("\\d{2}\\.\\d{2}\\.\\d{4}")) throw new IllegalArgumentException();
        Optional<Car> ourCars = carRepository.findAllByClientIsNull().stream()
                .filter(car -> car.getCarModel().equals(clientDTO.getCarModel()) &&
                car.getReleaseDate().equals(clientDTO.getCarReleaseDate())).findAny();
        Car car = ourCars.orElseThrow(IllegalArgumentException::new);
        Client client = Client.builder()
                .withName(clientDTO.getName())
                .withBirthday(clientDTO.getBirthday())
                .withCar(car)
                .build();
        Car refreshedCar = Car.mutator(car)
                .withClient(client)
                .mutate();
        clientRepository.save(client);
        return converterEntityToDTO.convertToDTO(client);
    }

    @Transactional
    public void deleteClient(String name,
                             String carModel) {
        List<Client> clientList = clientRepository.findClientByName(name);
        Optional<Client> client = clientList.stream()
                .filter(client1 -> client1.getCar().getCarModel().equals(carModel)).findAny();

        Car car = client.orElseThrow(
                () -> new IllegalArgumentException("No client found with username " + name)).getCar();
        clientRepository.deleteByNameAndCar(name, car);
        Car carRefreshed = Car.mutator(car)
                .withClient(null)
                .mutate();
        carRepository.save(carRefreshed);
        logger.info("Client deleted, car refreshed");
    }
}
