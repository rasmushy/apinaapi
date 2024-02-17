package com.apina.api.models;

import java.util.Objects;

public class AddressEntity {
    private int number;
    private String street;
    private String postcode;
    private String city;

    public AddressEntity() {
    }

    public AddressEntity(int number, String street, String postcode, String city) {
        this.number = number;
        this.street = street;
        this.postcode = postcode;
        this.city = city;
    }

    public int getNumber() {
        return number;
    }

    public AddressEntity setNumber(int number) {
        this.number = number;
        return this;
    }

    public String getStreet() {
        return street;
    }

    public AddressEntity setStreet(String street) {
        this.street = street;
        return this;
    }

    public String getPostcode() {
        return postcode;
    }

    public AddressEntity setPostcode(String postcode) {
        this.postcode = postcode;
        return this;
    }

    public String getCity() {
        return city;
    }

    public AddressEntity setCity(String city) {
        this.city = city;
        return this;
    }

    @Override
    public String toString() {
        return "Address{" + "number=" + number + ", street='" + street + '\'' + ", postcode='" + postcode + '\'' + ", city='" + city + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AddressEntity addressEntity = (AddressEntity) o;
        return number == addressEntity.number && Objects.equals(street, addressEntity.street) && Objects.equals(
                postcode, addressEntity.postcode) && Objects.equals(city, addressEntity.city)
                ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, street, postcode, city);
    }
}
