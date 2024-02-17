package com.apina.api.dtos;

import com.apina.api.models.CompanyEntity;

import java.util.HashMap;
import java.util.Map;
public record CompanyDTO(
                         String homePage,
                         String name,
                         Map<String, String> prices) {

    public CompanyDTO(CompanyEntity c) {
        this(c.getHomePage(), c.getName(), c.getPrices());
    }

    public CompanyEntity toCompanyEntity() {
        return new CompanyEntity(homePage, name, prices);
    }
}
