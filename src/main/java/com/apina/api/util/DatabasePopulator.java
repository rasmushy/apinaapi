package com.apina.api.util;

import com.apina.api.dtos.AddressDTO;
import com.apina.api.dtos.CompanyDTO;
import com.apina.api.dtos.GymDTO;
import com.apina.api.services.GymService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

// mvn spring-boot:run -Ppopulator

@Profile("populator")
@Component
public class DatabasePopulator implements CommandLineRunner {

    public static final String KA = "Kiipeilyareena";
    public static final String BK = "Boulderkeskus";
    public static final String TIMES_PASS = "10 times pass";
    public static final String DAY_PASS = "Day Pass";
    public static final String SHOE_RENTAL = "Shoe Rental";
    public static final String HELSINKI = "Helsinki";
    public static final String TEN_AM = "10:00";
    public static final String NINE_PM = "21:00";
    public static final String EIGHT_AM = "08:00";
    public static final String KA_URL = "https://kiipeilyareena.com/venue/kiipeilyareena";
    public static final String BK_URL = "https://www.boulderkeskus.com/en/content/";

    public static final String KA_DAY_PASS = "16€";

    public static final String KA_SHOE_RENTAL = "5€";

    public static final String BK_DAY_PASS = "13€";

    public static final String BK_SHOE_RENTAL = "4€";

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
        Date now = new Date();
        gymService.deleteAll();
        List<GymDTO> gyms = new ArrayList<>();
        gyms.add(new GymDTO(null, new CompanyDTO(KA_URL + "-redi/", KA, Map.of(DAY_PASS, KA_DAY_PASS, SHOE_RENTAL, KA_SHOE_RENTAL)), new AddressDTO(5, "Hermannin rantatie", "00580", HELSINKI), now, EIGHT_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(KA_URL + "-salmisaari/", KA, Map.of(DAY_PASS, KA_DAY_PASS, SHOE_RENTAL, KA_SHOE_RENTAL)), new AddressDTO(3, "Energiakatu", "00180", HELSINKI), now, EIGHT_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(KA_URL + "-ristikko/", KA, Map.of(DAY_PASS, KA_DAY_PASS, SHOE_RENTAL, KA_SHOE_RENTAL)), new AddressDTO(1, "Ajomiehentie", "00390", HELSINKI), now, EIGHT_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(KA_URL + "-tammisto/", KA, Map.of(DAY_PASS, KA_DAY_PASS, SHOE_RENTAL, KA_SHOE_RENTAL)), new AddressDTO(10, "Tammiston kauppatie", "01510", "Vantaa"), now, EIGHT_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(BK_URL + "11-bk-konala", BK, Map.of(DAY_PASS, BK_DAY_PASS, SHOE_RENTAL, BK_SHOE_RENTAL, TIMES_PASS, "99€")), new AddressDTO(1, "Ruosilantie", "00390", HELSINKI), now, TEN_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(BK_URL + "13-bk-espoo", BK, Map.of(DAY_PASS, BK_DAY_PASS, SHOE_RENTAL, BK_SHOE_RENTAL, TIMES_PASS, "99€")), new AddressDTO(2, "Niittyrinne 2B", "02270", "Espoo"), now, TEN_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(BK_URL + "10-bk-pasila", BK, Map.of(DAY_PASS, BK_DAY_PASS, SHOE_RENTAL, BK_SHOE_RENTAL, TIMES_PASS, "99€")), new AddressDTO(4, "Veturitallinkuja", "00520", HELSINKI), now, TEN_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(BK_URL + "12-bk-herttoniemi", BK, Map.of(DAY_PASS, BK_DAY_PASS, SHOE_RENTAL, BK_SHOE_RENTAL, TIMES_PASS, "99€")), new AddressDTO(15, "Mekaanikonkatu 15A", "00880", HELSINKI), now, TEN_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO(BK_URL + "14-bk-kino", BK, Map.of(DAY_PASS, "Premium only", SHOE_RENTAL, "Wear your own")), new AddressDTO(10, "Lauttasaarentie", "00200", HELSINKI), now, TEN_AM, NINE_PM));
        gyms.add(new GymDTO(null, new CompanyDTO("https://kiipeilykeskus.com/", "Kiipeilykeskus", Map.of(DAY_PASS, "6-12€", SHOE_RENTAL, "4€", TIMES_PASS, "90€")), new AddressDTO(3, "Erätie", "00730", HELSINKI), now, "09:00", "22:00"));
        gymService.saveAll(gyms);
        System.out.println("Database has been populated with pre-made data.");
    }
}
