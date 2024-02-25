package com.apina.api.models;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Map;
import java.util.Objects;
public class GymEntity {

    private ObjectId id;
    private CompanyEntity companyEntity;
    private AddressEntity addressEntity;
    private Date createdAt = new Date();

    private Map<String, String> openingHours;
    private Map<String, String> closingHours;

    private String additionalInfo;

    public GymEntity() {
    }

    public GymEntity(ObjectId id,
            CompanyEntity companyEntity,
            AddressEntity addressEntity,
            Date createdAt,
            Map<String, String> openingTime,
            Map<String, String> closingTime,
            String additionalInfo
    ) {
        this.id = id;
        this.companyEntity = companyEntity;
        this.addressEntity = addressEntity;
        this.createdAt = createdAt;
        this.openingHours = openingTime;
        this.closingHours = closingTime;
        this.additionalInfo = additionalInfo;
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

    public Map<String, String> getOpeningTime() {
        return openingHours;
    }

    public GymEntity setOpeningTime(Map<String, String> openingTime) {
        this.openingHours = openingTime;
        return this;
    }

    public Map<String, String> getClosingTime() {
        return closingHours;
    }

    public GymEntity setClosingTime(Map<String, String> closingTime) {
        this.closingHours = closingTime;
        return this;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
    public GymEntity setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    @Override
    public String toString() {
        return "GymEntity{" +
               "id=" + id +
               ", companyEntity=" + companyEntity +
               ", addressEntity=" + addressEntity +
               ", createdAt=" + createdAt +
               ", openingHours=" + openingHours +
               ", closingHours=" + closingHours +
               ", additionalInfo='" + additionalInfo + '\'' +
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
               Objects.equals(openingHours, gymEntity.openingHours) &&
               Objects.equals(closingHours, gymEntity.closingHours);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyEntity, addressEntity, createdAt, openingHours, closingHours);
    }

}
