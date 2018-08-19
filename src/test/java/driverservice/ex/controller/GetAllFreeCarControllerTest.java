package driverservice.ex.controller;

import driverservice.ex.dto.CarDTO;
import driverservice.ex.model.Car;
import driverservice.ex.repository.CarRepository;
import driverservice.ex.service.ConverterEntityToDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static driverservice.ex.configuration.FinalStaticContentForApp.*;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class GetAllFreeCarControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ConverterEntityToDTO converterEntityToDTO;

    private final static Logger logger = LoggerFactory.getLogger(GetAllFreeCarControllerTest.class);

    private List<CarDTO> carDTOList = new ArrayList<>();

    @Before
    public void setUp() {
        carRepository.deleteAll();
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

        carDTOList.add(converterEntityToDTO.convertToDTO(lexus));
        carDTOList.add(converterEntityToDTO.convertToDTO(mazda));
    }

    @Test
    public void getAllFreeCar() throws Exception {
        logger.info("Start get all free car test");
        mvc.perform(MockMvcRequestBuilders.get("/cars"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON_UTF8));
        logger.info("End get all free car test");
    }
}
