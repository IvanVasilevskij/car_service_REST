package driverservice.ex.integrationtesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import driverservice.ex.ExApplication;
import driverservice.ex.dto.CarDTO;
import driverservice.ex.dto.ClientDTO;
import driverservice.ex.model.Car;
import driverservice.ex.model.Client;
import driverservice.ex.repository.CarRepository;
import driverservice.ex.repository.ClientRepository;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static driverservice.ex.configuration.FinalStaticContentForApp.*;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateAndDeleteClientControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CarRepository carRepository;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        Car lexus = Car.builder()
                .withCarModel(CAR_MODEL_LEXUS)
                .withYearOfIssue(RELEASE_DATE_LEXUS)
                .build();
        carRepository.save(lexus);
    }

    @Test
    public void createClientTest() throws JsonProcessingException, JSONException {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(CLIENT_NAME_MIKE);
        clientDTO.setBirthday(CLIENT_BIRTHDAY_MIKE);
        clientDTO.setCarModel(CAR_MODEL_LEXUS);
        clientDTO.setCarReleaseDate(RELEASE_DATE_LEXUS);

        String jsonString = mapper.writeValueAsString(clientDTO);

        HttpEntity<ClientDTO> entity = new HttpEntity<>(clientDTO, headers);

        ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/clients"),
                HttpMethod.POST, entity, String.class);

        System.out.println(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(jsonString,
                response.getBody(), false);
    }

    @Test
    public void getAllFreeCars() throws JSONException, JsonProcessingException {
        Client client = Client.builder()
                .withName(CLIENT_NAME_PETER)
                .withBirthday(CLIENT_BIRTHDAY_PETER)
                .withCar(carRepository.findByCarModel(CAR_MODEL_FORD).get(0))
                .build();
        clientRepository.save(client);

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        assertEquals(1, clientRepository.findAll().size());

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/clients/" + CLIENT_NAME_PETER + "/" + CAR_MODEL_FORD),
                HttpMethod.DELETE, entity, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(0, clientRepository.findAll().size());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}