package com.apina.api;

import com.apina.api.dtos.GymDTO;
import com.apina.api.models.AddressEntity;
import com.apina.api.models.CompanyEntity;
import com.apina.api.models.GymEntity;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
@Component
class TestHelper {

    private final Map<String, String> openingHours = new HashMap<>();

    private final Map<String, String> closingHours = new HashMap<>();

    private final Map<String, String> prices = new HashMap<>();

    TestHelper() {
        openingHours.put("monday", "10:00");
        openingHours.put("tuesday", "10:00");
        openingHours.put("wednesday", "10:00");
        openingHours.put("thursday", "10:00");
        openingHours.put("friday", "10:00");
        openingHours.put("saturday", "10:00");
        openingHours.put("sunday", "10:00");
        closingHours.put("monday", "21:00");
        closingHours.put("tuesday", "21:00");
        closingHours.put("wednesday", "21:00");
        closingHours.put("thursday", "21:00");
        closingHours.put("friday", "21:00");
        closingHours.put("saturday", "21:00");
        closingHours.put("sunday", "21:00");
        prices.put("Day Pass", "11€");
        prices.put("Shoe Rental", "4€");
        prices.put("Chalk", "2€");
    }

    GymEntity getBkEntity() {
        return new GymEntity().setOpeningTime(openingHours).setClosingTime(closingHours)
                .setAddress(new AddressEntity().setCity("Helsinki")
                        .setNumber("1")
                        .setPostcode("00370")
                        .setStreet("Ruosilantie"))
                .setCompany(new CompanyEntity().setName("Boulderkeskus")
                        .setHomePage("https://www.boulderkeskus.com")
                        .setPrices(prices));
    }

    GymEntity getKaEntity() {
        return new GymEntity().setOpeningTime(openingHours).setClosingTime(closingHours)
                .setAddress(new AddressEntity().setCity("Espoo")
                        .setNumber("2")
                        .setPostcode("02150")
                        .setStreet("Keilaniementie"))
                .setCompany(new CompanyEntity().setName("Kiipeilyareena")
                        .setHomePage("https://www.kiipeilyareena.com")
                        .setPrices(Map.of("Day Pass", "15€", "Shoe Rental", "5€")));
    }

    GymDTO getBkDTO() {
        return new GymDTO(getBkEntity());
    }

    public GymDTO getBkDTOWithId(ObjectId id) {
        return new GymDTO(getBkEntity().setId(id));
    }

    GymDTO getKaDTO() {
        return new GymDTO(getKaEntity());
    }

    GymDTO getKaDTOWithId(ObjectId id) {
        return new GymDTO(getKaEntity().setId(id));
    }

    List<GymEntity> getListBkKaEntity() {
        return List.of(getBkEntity(), getKaEntity());
    }

    List<GymDTO> getListBkKaDTO() {
        return List.of(getBkDTO(), getKaDTO());
    }
}
*/
