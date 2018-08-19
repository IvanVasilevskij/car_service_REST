package driverservice.ex.repository;

import driverservice.ex.model.Car;
import driverservice.ex.model.Client;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface ClientRepository extends CrudRepository<Client, Long> {
    List<Client> findClientByName(String name);
    List<Client> findAll();
    void deleteByNameAndCar(String name, Car car);
    Client findByNameAndBirthday(String name, LocalDate birthday);
}
