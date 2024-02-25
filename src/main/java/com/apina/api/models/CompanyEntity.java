package com.apina.api.models;

import java.util.Map;
import java.util.Objects;
public class CompanyEntity {

    private String homePage;
    private String name;
    private Map<String, String> prices;

    public CompanyEntity() {
    }

    public CompanyEntity(
            String homePage,
            String name,
            Map<String, String> prices
    ) {
        this.homePage = homePage;
        this.name = name;
        this.prices = prices;
    }

    public String getHomePage() {
        return homePage;
    }

    public CompanyEntity setHomePage(String homePage) {
        this.homePage = homePage;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Map<String, String> getPrices() {
        return prices;
    }

    public CompanyEntity setPrices(Map<String, String> prices) {
        this.prices = prices;
        return this;
    }

    public CompanyEntity addPrice(String key, String value) {
        this.prices.put(key, value);
        return this;
    }

    public CompanyEntity removePrice(String key) {
        this.prices.remove(key);
        return this;
    }

    @Override
    public String toString() {
        return "CompanyEntity{" +
               ", homePage='" + homePage + '\'' +
               ", name='" + name + '\'' +
               ", prices=" + prices +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        CompanyEntity companyEntity = (CompanyEntity) o;
        return Objects.equals(homePage, companyEntity.homePage) && Objects.equals(name, companyEntity.name) && Objects.equals(prices, companyEntity.prices);

    }

    @Override
    public int hashCode() {
        return Objects.hash(homePage, name, prices);
    }
}
