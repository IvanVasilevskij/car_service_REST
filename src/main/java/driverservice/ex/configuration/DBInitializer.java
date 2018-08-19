package driverservice.ex.configuration;

import driverservice.ex.model.Car;
import driverservice.ex.repository.CarRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import static driverservice.ex.configuration.FinalStaticContentForApp.*;

@Component
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DBInitializer implements CommandLineRunner {


    private final CarRepository carRepository;
    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    public DBInitializer(CarRepository carRepository){
        this.carRepository = carRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        if (carRepository.findAll().size() == 0) {
            Car lexus = Car.builder()
                    .withCarModel(CAR_MODEL_LEXUS)
                    .withYearOfIssue(RELEASE_DATE_LEXUS)
                    .build();
            carRepository.save(lexus);
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
            logger.info("Database has been initialized");
        }
    }
}
