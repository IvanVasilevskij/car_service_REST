package driverservice.ex.controller;

import driverservice.ex.dto.ClientDTO;
import driverservice.ex.service.ControllerFunctionalService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@Api
public class CreateAndDeleteClientController {
    private final ControllerFunctionalService controllerFunctionalService;

    @Autowired
    public CreateAndDeleteClientController(ControllerFunctionalService controllerFunctionalService) {
        this.controllerFunctionalService = controllerFunctionalService;
    }

    @ApiOperation(value = "Create client", response = ClientDTO.class)
    @PostMapping(value = "/clients",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClientDTO.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Some error on server")
    })
        public ClientDTO createClient(@Valid @RequestBody ClientDTO clientDTO) {
        return controllerFunctionalService.createClient(clientDTO);
    }

    @ApiOperation(value = "Delete client", response = ClientDTO.class)
    @DeleteMapping(value = "/clients/{name}/{carModel}")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Success", response = ClientDTO.class),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Some error on server")
    })
    public void deleteClient(@PathVariable String name,
                                       @PathVariable String carModel) {
        controllerFunctionalService.deleteClient(name, carModel);
    }
}
