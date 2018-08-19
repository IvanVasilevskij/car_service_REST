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

import java.util.List;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CarRepositoryTest {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarRepository carRepository;

    private final static Logger logger = LoggerFactory.getLogger(CarRepositoryTest.class);

    @Before
    public void setUp() {
        carRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    public void createCar() {
        logger.info("Start create car test");
        Car toyota = Car.builder()
                .withCarModel(CAR_MODEL_TOYOTA)
                .withYearOfIssue(RELEASE_DATE_TOYOTA)
                .build();
        carRepository.save(toyota);
        logger.info("Create a car with model - " + toyota.getCarModel());

        assertEquals("List of total car should have 1 position", 1, carRepository.findAll().size());
        assertEquals("Found one car with car with request param", toyota.toString(),
                toyota.toString());
        logger.info("Found one car with car model (Toyota) and year of issue (01.01.2010)");
        logger.info("End create car test");
    }

    @Test
    public void findFreeCar() {
        logger.info("Start find free car test");
        Car toyota = Car.builder()
                .withCarModel(CAR_MODEL_TOYOTA)
                .withYearOfIssue(RELEASE_DATE_TOYOTA)
                .build();
        carRepository.save(toyota);
        logger.info("Create a car with model - " + toyota.getCarModel());

        Car ford = Car.builder()
                .withCarModel(CAR_MODEL_FORD)
                .withYearOfIssue(RELEASE_DATE_FORD)
                .build();
        carRepository.save(ford);
        logger.info(ford.toString());
        logger.info("Create a car with model - " + ford.getCarModel());
        logger.info("Total free car is " + carRepository.findAll().size());

        Client client = Client.builder()
                .withName(CLIENT_NAME_MIKE)
                .withBirthday(CLIENT_BIRTHDAY_MIKE)
                .withCar(ford)
                .build();
        clientRepository.save(client);
        Car busyCarFord = Car.mutator(ford)
                .withClient(client)
                .mutate();
        carRepository.save(busyCarFord);
        logger.info("Created client with name - " + client.getName() + "and car with model - " + client.getCar().getCarModel());

        logger.info("Now total free car should be 1");
        assertEquals("List of total free car should have 1 position", 1, carRepository.findAllByClientIsNull().size());

        logger.info("End find free car test");
    }

    @Test
    public void findCarByCarModel() {
        logger.info("Start find car by car model test");
        Car toyota = Car.builder()
                .withCarModel(CAR_MODEL_TOYOTA)
                .withYearOfIssue(RELEASE_DATE_TOYOTA)
                .build();
        carRepository.save(toyota);
        logger.info("Create a car with model - " + toyota.getCarModel());

        List<Car> carList = carRepository.findByCarModel("Toyota");
        assertEquals("Only one car with model is Toyota", 1, carList.size());
        logger.info("Car with model Toyota found(1) = " + carList.size());

        Car toyotaSecond = Car.builder()
                .withCarModel(CAR_MODEL_TOYOTA)
                .withYearOfIssue(RELEASE_DATE_TOYOTA)
                .build();
        carRepository.save(toyotaSecond);
        logger.info("Create a car with model - " + toyota.getCarModel());

        List<Car> carListNew = carRepository.findByCarModel("Toyota");
        assertEquals("Only two car with model is Toyota", 2, carListNew.size());
        logger.info("Car with model Toyota found(2) = " + carList.size());

        logger.info("End find car by car model test");
    }
}
