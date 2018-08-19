package driverservice.ex.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@EqualsAndHashCode
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @NotBlank
    private String name;
    @NotNull
    private LocalDate birthday;
    @OneToOne
    private Car car;

    public Client() {}

    public static Builder builder() {
        return new Builder();
    }

    Client(final Builder builder) {
        this.name = builder.name;
        this.birthday = builder.birthday;
        this.car = builder.car;
    }

    public static class Builder{
        private String name;
        private LocalDate birthday;
        private Car car;

        Builder() {}

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withBirthday(LocalDate birthday) {
            this.birthday = birthday;
            return this;
        }

        public Builder withCar(Car car) {
            this.car = car;
            return this;
        }

        public Client build() {
            return new Client(this);
        }
    }

    @Override
    public String toString() {
        return "{" +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", car=" + car +
                '}';
    }
}
