package driverservice.ex.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(value = "Car")
@Getter @Setter
public class CarDTO {
    @ApiModelProperty(value = "carModel", required = true, position = 1, example = "Ford")
    @NotBlank
    private String carModel;
    @ApiModelProperty(value = "releaseDate",name = "date of car release", required = true, position = 2, example = "2017-02-01")
    @NotNull
    private LocalDate releaseDate;

    @Override
    public String toString() {
        return "{" +
                "carModel='" + carModel + '\'' +
                ", releaseDate='" + releaseDate + '\'' +
                '}';
    }
}
