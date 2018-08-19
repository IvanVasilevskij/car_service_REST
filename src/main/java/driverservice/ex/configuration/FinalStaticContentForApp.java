package driverservice.ex.configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FinalStaticContentForApp {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static final String CAR_MODEL_FORD = "Ford";
    public static final LocalDate RELEASE_DATE_FORD = LocalDate.parse("01.02.2017", formatter);

    public static final String CAR_MODEL_LEXUS = "Lexus";
    public static final LocalDate RELEASE_DATE_LEXUS = LocalDate.parse("02.03.2015", formatter);

    public static final String CAR_MODEL_TOYOTA = "Toyota";
    public static final LocalDate RELEASE_DATE_TOYOTA = LocalDate.parse("05.06.2013", formatter);

    public static final String CAR_MODEL_MAZDA = "Mazda";
    public static final LocalDate RELEASE_DATE_MAZDA = LocalDate.parse("02.08.2011",formatter);

    public static final String CLIENT_NAME_BOB = "Bob";
    public static final LocalDate CLIENT_BIRTHDAY_BOB = LocalDate.parse("01.02.1974", formatter);

    public static final String CLIENT_NAME_MIKE = "Mike";
    public static final LocalDate CLIENT_BIRTHDAY_MIKE = LocalDate.parse("04.07.1995", formatter);

    public static final String CLIENT_NAME_PETER = "Peter";
    public static final LocalDate CLIENT_BIRTHDAY_PETER = LocalDate.parse("22.12.1995", formatter);

    public static final String CLIENT_NAME_GEORGE = "Gorge";
    public static final LocalDate CLIENT_BIRTHDAY_GEORGE = LocalDate.parse("04.07.1995", formatter);
}
