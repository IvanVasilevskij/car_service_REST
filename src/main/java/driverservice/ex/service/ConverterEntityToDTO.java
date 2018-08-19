package driverservice.ex.service;

import driverservice.ex.dto.CarDTO;
import driverservice.ex.dto.ClientDTO;
import driverservice.ex.model.Car;
import driverservice.ex.model.Client;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ConverterEntityToDTO {
    private ModelMapper modelMapper = new ModelMapper();

    public ClientDTO convertToDTO(Client client) {
        return modelMapper.map(client, ClientDTO.class);
    }
    public CarDTO convertToDTO(Car car) {
        return modelMapper.map(car, CarDTO.class);
    }
}
