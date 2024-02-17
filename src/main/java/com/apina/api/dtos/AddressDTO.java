package com.apina.api.dtos;

import com.apina.api.models.AddressEntity;

public record AddressDTO(int number, String street, String postcode, String city) {

    public AddressDTO(AddressEntity a) {
        this(a.getNumber(), a.getStreet(), a.getPostcode(), a.getCity());
    }

    public AddressEntity toAddressEntity() {
        return new AddressEntity(number, street, postcode, city);
    }
}
