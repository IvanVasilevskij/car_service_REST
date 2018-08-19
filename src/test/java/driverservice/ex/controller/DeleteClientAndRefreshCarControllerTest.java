package driverservice.ex.controller;


import driverservice.ex.model.Car;
import driverservice.ex.model.Client;
import driverservice.ex.repository.CarRepository;
import driverservice.ex.repository.ClientRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static driverservice.ex.configuration.FinalStaticContentForApp.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class DeleteClientAndRefreshCarControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ClientRepository clientRepository;

    private final static Logger logger = LoggerFactory.getLogger(DeleteClientAndRefreshCarControllerTest.class);

    @Before
    public void setUp() {
        Car lexus = Car.builder()
                .withCarModel(CAR_MODEL_LEXUS)
                .withYearOfIssue(RELEASE_DATE_LEXUS)
                .build();
        carRepository.save(lexus);
        Client gorge = Client.builder()
                .withName(CLIENT_NAME_GEORGE)
                .withBirthday(CLIENT_BIRTHDAY_GEORGE)
                .withCar(lexus)
                .build();
        Car lexusRefreshed = Car.mutator(lexus)
                .withClient(gorge)
                .mutate();
        clientRepository.save(gorge);
        carRepository.save(lexusRefreshed);
    }

    @Test
    public void deleteClientControllerTest() throws Exception {
        logger.info("Start delete client controller test");

        mvc.perform(MockMvcRequestBuilders
                .delete("/clients/"+CLIENT_NAME_GEORGE+"/"+CAR_MODEL_LEXUS))
                .andExpect(MockMvcResultMatchers.status().isOk());
        assertEquals("Client table should have nothing", 0, clientRepository.findAll().size());
        logger.info("End delete client controller test");
    }
}

