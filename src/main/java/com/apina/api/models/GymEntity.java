package com.apina.api.models;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Objects;
public class GymEntity {

    private ObjectId id;
    private CompanyEntity companyEntity;
    private AddressEntity addressEntity;
    private Date createdAt = new Date();

    private String openingTime;

    private String closingTime;

    public GymEntity() {
    }

    public GymEntity(ObjectId id,
            CompanyEntity companyEntity,
            AddressEntity addressEntity,
            Date createdAt,
            String openingTime,
            String closingTime) {
        this.id = id;
        this.companyEntity = companyEntity;
        this.addressEntity = addressEntity;
        this.createdAt = createdAt;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public ObjectId getId() {
        return id;
    }

    public GymEntity setId(ObjectId id) {
        this.id = id;
        return this;
    }

    public CompanyEntity getCompany() {
        return companyEntity;
    }

    public GymEntity setCompany(CompanyEntity companyEntity) {
        this.companyEntity = companyEntity;
        return this;
    }

    public AddressEntity getAddress() {
        return addressEntity;
    }

    public GymEntity setAddress(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public GymEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public GymEntity setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public GymEntity setClosingTime(String closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    @Override
    public String toString() {
        return "GymEntity{" +
               "id=" + id +
               ", companyEntity=" + companyEntity +
               ", addressEntity=" + addressEntity +
               ", createdAt=" + createdAt +
               ", openingTime=" + openingTime +
               ", closingTime=" + closingTime +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GymEntity gymEntity = (GymEntity) o;
        return Objects.equals(id, gymEntity.id) &&
               Objects.equals(companyEntity, gymEntity.companyEntity) &&
               Objects.equals(addressEntity, gymEntity.addressEntity) &&
               Objects.equals(createdAt, gymEntity.createdAt) &&
               Objects.equals(openingTime, gymEntity.openingTime) &&
               Objects.equals(closingTime, gymEntity.closingTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyEntity, addressEntity, createdAt, openingTime, closingTime);
    }

}
