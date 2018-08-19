package driverservice.ex.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import driverservice.ex.dto.ClientDTO;
import driverservice.ex.model.Car;
import driverservice.ex.model.Client;
import driverservice.ex.repository.CarRepository;
import driverservice.ex.repository.ClientRepository;
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
import org.springframework.transaction.annotation.Transactional;

import static driverservice.ex.configuration.FinalStaticContentForApp.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CreateClientControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ConverterEntityToDTO converterEntityToDTO;
    @Autowired
    private ObjectMapper mapper;

    private final static Logger logger = LoggerFactory.getLogger(CreateAndDeleteClientController.class);


    @Before
    public void setUp() {
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

        Client mike = Client.builder()
                .withName(CLIENT_NAME_MIKE)
                .withBirthday(CLIENT_BIRTHDAY_MIKE)
                .withCar(lexus)
                .build();
        Car lexusRefreshed = Car.mutator(lexus)
                .withClient(mike)
                .mutate();
        clientRepository.save(mike);
    }


    @Test
    public void createClientController() throws Exception {
        logger.info("Start create client controller test");
        ClientDTO clientDTO = converterEntityToDTO.convertToDTO(clientRepository.findByNameAndBirthday(CLIENT_NAME_MIKE,
                CLIENT_BIRTHDAY_MIKE));
        mvc.perform(MockMvcRequestBuilders
                .post("/clients")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
        .content(mapper.writeValueAsString(clientDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .json(mapper.writeValueAsString(clientDTO))
                );
        logger.info("End create client controller test");
    }
}
