package driverservice.ex.controller;

import driverservice.ex.dto.CarDTO;
import driverservice.ex.service.ControllerFunctionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api
public class GetAllFreeCarsController {
    private final ControllerFunctionalService controllerFunctionalService;

    @Autowired
    public GetAllFreeCarsController(ControllerFunctionalService controllerFunctionalService) {
        this.controllerFunctionalService = controllerFunctionalService;
    }

    @ApiOperation(value = "Список всех свободных машин", response = CarDTO.class)
    @GetMapping(value = "/cars",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CarDTO> getAllFreeCar() {
        return controllerFunctionalService.getAllFreeCarService();
    }
}
