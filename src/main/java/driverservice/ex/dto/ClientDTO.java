package driverservice.ex.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@ApiModel(value = "Client")
@Getter @Setter
public class ClientDTO {
    @ApiModelProperty(example = "Mike", required = true, position = 1)
    @NotBlank
    private String name;
    @ApiModelProperty(example = "1990-01-01", required = true, position = 2)
    @NotNull
    private LocalDate birthday;
    @ApiModelProperty(example = "Ford", position = 3)
    @NotBlank
    private String carModel;
    @ApiModelProperty(example = "2017-02-01", position = 4)
    @NotNull
    private LocalDate carReleaseDate;

    @Override
    public String toString() {
        return "{" +
                "name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", carModel='" + carModel + '\'' +
                ", carCarReleaseDate='" + carReleaseDate + '\'' +
                '}';
    }
}
