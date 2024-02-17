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

@Component
class TestHelper {

    GymEntity getBkEntity() {
        return new GymEntity().setOpeningTime("08:00").setClosingTime("20:00")
                .setAddress(new AddressEntity().setCity("Helsinki")
                        .setNumber(1)
                        .setPostcode("00370")
                        .setStreet("Ruosilantie"))
                .setCompany(new CompanyEntity().setName("Boulderkeskus")
                        .setHomePage("https://www.boulderkeskus.com")
                        .setPrices(Map.of("Day Pass", "15€", "Shoe Rental", "5€")));
    }

    GymEntity getKaEntity() {
        return new GymEntity().setOpeningTime("07:00").setClosingTime("21:00")
                .setAddress(new AddressEntity().setCity("Espoo")
                        .setNumber(2)
                        .setPostcode("02150")
                        .setStreet("Keilaniementie"))
                .setCompany(new CompanyEntity().setName("Kiipeilyareena")
                        .setHomePage("https://www.kiipeilyareena.com")
                        .setPrices(Map.of("Day Pass", "20€", "Shoe Rental", "6€")));
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
