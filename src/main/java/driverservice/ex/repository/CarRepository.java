package driverservice.ex.repository;

import driverservice.ex.model.Car;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CarRepository extends CrudRepository<Car, Long> {
    List<Car> findByCarModel(String carModel);
    List<Car> findAll();
//    Car findByCarModelAndYearOfIssue(String carModel, String yearOfIssue);
    List<Car> findAllByClientIsNull();
}
