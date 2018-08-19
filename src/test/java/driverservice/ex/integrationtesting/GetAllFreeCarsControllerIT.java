package driverservice.ex.integrationtesting;

import com.fasterxml.jackson.core.JsonProcessingException;
import driverservice.ex.ExApplication;
import driverservice.ex.dto.CarDTO;
import driverservice.ex.repository.CarRepository;
import driverservice.ex.service.ConverterEntityToDTO;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ExApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GetAllFreeCarsControllerIT {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private CarRepository repository;
    @Autowired
    private ConverterEntityToDTO converterEntityToDTO;

    private HttpHeaders headers = new HttpHeaders();

    @Test
    public void getAllFreeCars() throws JSONException, JsonProcessingException {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                createURLWithPort("/cars"),
                HttpMethod.GET, entity, String.class);

        List<CarDTO> cars = repository.findAllByClientIsNull().stream()
                .map(converterEntityToDTO::convertToDTO).collect(Collectors.toList());

        JSONAssert.assertEquals(cars.toString(), response.getBody(), false);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

