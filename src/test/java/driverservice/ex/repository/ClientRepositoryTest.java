package driverservice.ex.repository;

import driverservice.ex.model.Car;
import driverservice.ex.model.Client;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static driverservice.ex.configuration.FinalStaticContentForApp.*;

import java.util.Optional;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class ClientRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarRepository carRepository;

    private final static Logger logger = LoggerFactory.getLogger(ClientRepositoryTest.class);

    @Before
    public void setUp(){
        carRepository.deleteAll();
        clientRepository.deleteAll();
        Car toyota = Car.builder()
                .withCarModel(CAR_MODEL_TOYOTA)
                .withYearOfIssue(RELEASE_DATE_TOYOTA)
                .build();
        carRepository.save(toyota);
        Car ford = Car.builder()
                .withCarModel(CAR_MODEL_FORD)
                .withYearOfIssue(RELEASE_DATE_FORD)
                .build();
        carRepository.save(ford);
        Car lexus = Car.builder()
                .withCarModel(CAR_MODEL_LEXUS)
                .withYearOfIssue(RELEASE_DATE_LEXUS)
                .build();
        carRepository.save(lexus);
        Car mazda = Car.builder()
                .withCarModel(CAR_MODEL_MAZDA)
                .withYearOfIssue(RELEASE_DATE_MAZDA)
                .build();
        carRepository.save(mazda);
    }

    @Test
    public void createClient() {
        logger.info("Start create client test");
        Optional<Car> ourCars = carRepository.findAllByClientIsNull().stream()
                .filter(car -> car.getCarModel().equals(CAR_MODEL_TOYOTA) &&
                        car.getReleaseDate().equals(RELEASE_DATE_TOYOTA)).findAny();
        Car carForClient = ourCars.orElseThrow(
                () -> new IllegalArgumentException("Car does not exist or all car busy"));

        logger.info("Find car for client with model - " + carForClient.getCarModel());

        Client client = Client.builder()
                .withName(CLIENT_NAME_PETER)
                .withBirthday(CLIENT_BIRTHDAY_PETER)
                .withCar(carForClient)
                .build();
        clientRepository.save(client);
        logger.info("Created client with name - " + client.getName());

        assertEquals("Total client should have 1 position", 1, clientRepository.findAll().size());
        assertEquals("Client should drive the Toyota", CAR_MODEL_TOYOTA,
                clientRepository.findAll().get(0).getCar().getCarModel());
        logger.info("End create client test");
    }

    @Test
    public void deleteClient() {
        logger.info("Start delete client test");
        Optional<Car> ourCars = carRepository.findAllByClientIsNull().stream()
                .filter(car -> car.getCarModel().equals(CAR_MODEL_TOYOTA) &&
                        car.getReleaseDate().equals(RELEASE_DATE_TOYOTA)).findAny();
        Car carForClient = ourCars.orElseThrow(
                () -> new IllegalArgumentException("Car does not exist or all car busy"));
        logger.info("Find car for client with model - " + carForClient.getCarModel());

        Client client = Client.builder()
                .withName(CLIENT_NAME_MIKE)
                .withBirthday(CLIENT_BIRTHDAY_MIKE)
                .withCar(carForClient)
                .build();
        clientRepository.save(client);
        logger.info("Created client with name - " + client.getName());

        logger.info("Total client - " + clientRepository.findAll().size());
        assertEquals("Total client - 1 position", 1, clientRepository.findAll().size());

        clientRepository.deleteByNameAndCar(CLIENT_NAME_MIKE, carForClient);
        logger.info("Total client now - 0");

        assertEquals("Total client should have 0 positions", 0, clientRepository.findAll().size());
        logger.info("End delete client test");
    }

    @Test
    public void findClientByNameAndBirthday() {
        logger.info("Start find client by name and birthday test");
        Optional<Car> ourCars = carRepository.findAllByClientIsNull().stream()
                .filter(car -> car.getCarModel().equals(CAR_MODEL_TOYOTA) &&
                        car.getReleaseDate().equals(RELEASE_DATE_TOYOTA)).findAny();
        Car carForClient = ourCars.orElseThrow(
                () -> new IllegalArgumentException("Car does not exist or all car busy"));
        logger.info("Find car for client with model - " + carForClient.getCarModel());

        Client client = Client.builder()
                .withName(CLIENT_NAME_MIKE)
                .withBirthday(CLIENT_BIRTHDAY_MIKE)
                .withCar(carForClient)
                .build();
        clientRepository.save(client);
        logger.info("Created client with name - " + client.getName());

        assertEquals("One client should found", client.toString(),
                clientRepository.findByNameAndBirthday(CLIENT_NAME_MIKE, CLIENT_BIRTHDAY_MIKE).toString());

        logger.info("End find client by name and birthday test");
    }

    @Test
    public void findByName() {
        logger.info("Start find client by name test");

        Optional<Car> ourCars = carRepository.findAllByClientIsNull().stream()
                .filter(car -> car.getCarModel().equals(CAR_MODEL_TOYOTA) &&
                        car.getReleaseDate().equals(RELEASE_DATE_TOYOTA)).findAny();
        Car carForClient = ourCars.orElseThrow(
                () -> new IllegalArgumentException("Car does not exist or all car busy"));
        logger.info("Find car for client with model - " + carForClient.getCarModel());

        Client client = Client.builder()
                .withName(CLIENT_NAME_MIKE)
                .withBirthday(CLIENT_BIRTHDAY_MIKE)
                .withCar(carForClient)
                .build();
        clientRepository.save(client);
        logger.info("Created client with name - " + client.getName());

        assertEquals("Client with name (Mike) only one", 1,
                clientRepository.findClientByName(CLIENT_NAME_MIKE).size());
        assertEquals("Client with name (Mike) found", client,
                clientRepository.findClientByName(CLIENT_NAME_MIKE).get(0));

        Optional<Car> ourCarsT = carRepository.findAllByClientIsNull().stream()
                .filter(car -> car.getCarModel().equals(CAR_MODEL_LEXUS) &&
                        car.getReleaseDate().equals(RELEASE_DATE_LEXUS)).findAny();
        Car carForClientSecond = ourCarsT.orElseThrow(
                () -> new IllegalArgumentException("Car does not exist or all car busy"));
        logger.info("Find car for client with model - " + carForClient.getCarModel());

        Client clientSecond = Client.builder()
                .withName(CLIENT_NAME_MIKE)
                .withBirthday(CLIENT_BIRTHDAY_MIKE)
                .withCar(carForClientSecond)
                .build();
        clientRepository.save(clientSecond);
        logger.info("Created client with name - " + clientSecond.getName());

        assertEquals("Client with name (Mike) = two", 2, clientRepository.findClientByName(CLIENT_NAME_MIKE).size());

        logger.info("End find client by name test");
    }
}
