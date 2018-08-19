package driverservice.ex.model;

import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String carModel;
    @NotNull
    private LocalDate releaseDate;
    @OneToOne
    private Client client;

    public Car() {}

    private Car(final Builder builder) {
        this.carModel = builder.carModel;
        this.releaseDate = builder.releaseDate;
    }

    public static Mutator mutator(Car car) {
        return new Mutator(car);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder{
        private String carModel;
        private LocalDate releaseDate;

        Builder() {}

        public Builder withCarModel(String carModel) {
            this.carModel = carModel;
            return this;
        }

        public Builder withYearOfIssue(LocalDate releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public Car build() {
            return new Car(this);
        }

    }

    public static class Mutator{
        private Car car;

        public Mutator(Car car) {
            this.car = car;
        }

        public Mutator withClient(Client client){
            this.car.client = client;
            return this;
        }

        public Car mutate() {
            return car;
        }
    }

    @Override
    public String toString() {
        if (getClient() == null)
        { return "{" +
                "carModel='" + carModel + '\'' +
                ", releaseDate=" + releaseDate +
                ", client=" + null +
                '}'; }
        else {
            return "{" +
                    "carModel='" + carModel + '\'' +
                    ", releaseDate=" + releaseDate +
                    ", client=" + client.getName() +
                    '}';
        }
    }
}
