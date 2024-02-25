package com.apina.api.util;

import com.apina.api.dtos.AddressDTO;
import com.apina.api.dtos.CompanyDTO;
import com.apina.api.dtos.GymDTO;
import com.apina.api.services.GymService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

// Atm @Profile doesn't seem to work, so you have to comment out the @Profile("populator") annotation in order to run the app to populate the database
// mvn spring-boot:run -Ppopulator
// mvn spring-boot:run -Papp
//DatabasePopulator.java


@Profile("populator")
public class DatabasePopulator implements CommandLineRunner {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(DatabasePopulator.class);

    private final String[] gymTypes = {"Kiipeilyareena", "Boulderkeskus", "Kiipeilykeskus"};
    private final String[] weekdays = {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};

    private final GymService gymService;

    public DatabasePopulator(GymService gymService) {
        this.gymService = gymService;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(DatabasePopulator.class, args);
        SpringApplication.exit(ctx);
    }

    @Override
    public void run(String... args) throws Exception {
        gymService.deleteAll();
        List<GymDTO> gyms = new ArrayList<>();
        gyms.addAll(createGymsForType(gymTypes[0]));
        gyms.addAll(createGymsForType(gymTypes[1]));
        gyms.addAll(createGymsForType(gymTypes[2]));
        gyms = gymService.saveAll(gyms);
        LOGGER.info("Database populated with {} gyms", gyms.size());
    }

    private List<GymDTO> createGymsForType(String gymType) {
        List<GymDTO> gyms = new ArrayList<>();
        List<Map<String, String>> gymDetails = getGymDetails(gymType);
        for (Map<String, String> details : gymDetails) {
            gyms.add(new GymDTO(null, new CompanyDTO(details.get("url"), gymType, getPrices(gymType)), new AddressDTO(details.get("number"), details.get("address"), details.get("postcode"), details.get("city")), new Date(), getOpeningHours(gymType), getClosingHours(gymType), details.get("additionalInfo")));
        }
        return gyms;
    }

    private List<Map<String, String>> getGymDetails(String gymType) {
        for (String gym : List.of(gymTypes)) {
            if (gymType.equals(gym)) {
                switch (gymType) {
                    case "Kiipeilyareena" -> {
                        return List.of(Map.of("url", "https://kiipeilyareena.com/venue/kiipeilyareena-redi/", "address", "Hermannin rantatie", "number", "5", "city", "Helsinki", "postcode", "00580", "additionalInfo", "Redi"), Map.of("url", "https://kiipeilyareena.com/venue/kiipeilyareena-salmisaari/", "address", "Energiakatu", "number", "3", "city", "Helsinki", "postcode", "00180", "additionalInfo", "Salmisaari"), Map.of("url", "https://kiipeilyareena.com/venue/kiipeilyareena-ristikko/", "address", "Ajomiehentie", "number", "1", "city", "Helsinki", "postcode", "00390", "additionalInfo", "Ristikko"), Map.of("url", "https://kiipeilyareena.com/venue/kiipeilyareena-tammisto/", "address", "Tammiston kauppatie", "number", "10", "city", "Vantaa", "postcode", "01510", "additionalInfo", "Tammisto"));
                    }
                    case "Boulderkeskus" -> {
                        return List.of(Map.of("url", "https://www.boulderkeskus.com/en/content/konala", "address", "Ruosilantie", "number", "1", "city", "Helsinki", "postcode", "00390", "additionalInfo", "Konala"), Map.of("url", "https://www.boulderkeskus.com/en/content/espoo", "address", "Niittyrinne", "number", "2B", "city", "Espoo", "postcode", "02270", "additionalInfo", "Iso ku mikä"), Map.of("url", "https://www.boulderkeskus.com/en/content/pasila", "address", "Veturitallinkuja", "number", "4", "city", "Helsinki", "postcode", "00520", "additionalInfo", "Pasila"), Map.of("url", "https://www.boilderkeskus.com/en/content/herttoniemi", "address", "Mekaanikonkatu", "number", "15A", "city", "Helsinki", "postcode", "00880", "additionalInfo", "Herttoniemi"));
                    }
                    case "Kiipeilykeskus" -> {
                        return List.of(Map.of("url", "https://kiipeilykeskus.com/", "address", "Erätie", "number", "3", "city", "Helsinki", "postcode", "00730", "additionalInfo", "Tapanilan erä"));
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + gymType);
                }
            }
        }
        return List.of();
    }

    private Map<String, String> getPrices(String gymType) {
        String monthly = "Monthly pass";
        String timesPass = "10 times pass";
        String dayPass = "Day Pass";
        String shoeRental = "Shoe Rental";
        for (String gym : List.of(gymTypes)) {
            if (gymType.equals(gym)) {
                switch (gymType) {
                    case "Kiipeilyareena" -> {
                        return Map.of(dayPass, "16€", shoeRental, "5€", timesPass, "99€", monthly, "99€");
                    }
                    case "Boulderkeskus" -> {
                        return Map.of(dayPass, "13€", shoeRental, "4€", timesPass, "99€", monthly, "75€");
                    }
                    case "Kiipeilykeskus" -> {
                        return Map.of(dayPass, "6-12€", shoeRental, "4€", timesPass, "90€", monthly, "100€");
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + gymType);
                }
            }
        }
        return Map.of();
    }

    private Map<String, String> getOpeningHours(String gymType) {
        String nineAm = "09:00";
        String tenAm = "10:00";
        String twelveAm = "12:00";
        for (String gym : List.of(gymTypes)) {
            if (gymType.equals(gym)) {
                switch (gymType) {
                    case "Kiipeilyareena" -> {
                        return Map.of(weekdays[0], tenAm, weekdays[1], tenAm, weekdays[2], tenAm, weekdays[3], tenAm, weekdays[4], tenAm, weekdays[5], tenAm, weekdays[6], tenAm);
                    }
                    case "Boulderkeskus" -> {
                        return Map.of(weekdays[0], tenAm, weekdays[1], tenAm, weekdays[2], tenAm, weekdays[3], tenAm, weekdays[4], tenAm, weekdays[5], twelveAm, weekdays[6], twelveAm);
                    }
                    case "Kiipeilykeskus" -> {
                        return Map.of(weekdays[0], nineAm, weekdays[1], nineAm, weekdays[2], nineAm, weekdays[3], nineAm, weekdays[4], nineAm, weekdays[5], tenAm, weekdays[6], tenAm);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + gymType);
                }
            }
        }
        return Map.of();
    }

    private Map<String, String> getClosingHours(String gymType) {
        String sixPm = "18:00";
        String eightPm = "20:00";
        String ninePm = "21:00";
        String tenPm = "22:00";
        for (String gym : List.of(gymTypes)) {
            if (gymType.equals(gym))
                switch (gymType) {
                    case "Kiipeilyareena", "Kiipeilykeskus" -> {
                        return Map.of(weekdays[0], tenPm, weekdays[1], tenPm, weekdays[2], tenPm, weekdays[3], tenPm, weekdays[4], tenPm, weekdays[5], eightPm, weekdays[6], eightPm);
                    }
                    case "Boulderkeskus" -> {
                        return Map.of(weekdays[0], ninePm, weekdays[1], ninePm, weekdays[2], ninePm, weekdays[3], ninePm, weekdays[4], ninePm, weekdays[5], sixPm, weekdays[6], sixPm);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + gymType);
                }
        }
        return Map.of();
    }
}
/*
    Boulderkeskus aukioloajat:
    Pasila
    ma - pe 10.00 - 21.00
    la 12.00 - 18.00
    su 12.00 - 18.00

    Konala
    ma - pe 10.00 - 21.00
    la 12.00 - 18.00
    su 12.00 - 18.00

    Herttoniemi
    ma - pe 10.00 - 21.00
    la 12.00 - 18.00
    su 12.00 - 18.00

    Espoo
    ma - pe 10.00 - 21.00
    la 10.00 - 18.00
    su 10.00 - 18.00

    Kiipeilyareena aukioloajat:
    MON–FRI 10–22, SAT–SUN 10–20

    Kiipeilykeskus aukioloajat:
    ma-pe klo 9-22
    la-su klo 10-20
*/
