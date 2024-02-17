package com.apina.api.dtos;

import com.apina.api.models.GymEntity;
import org.bson.types.ObjectId;

import java.util.Date;
public record GymDTO(
        String id,
        CompanyDTO company,
        AddressDTO address,
        Date createdAt,
        String openingTime,
        String closingTime
) {

    public GymDTO(GymEntity g) {
        this(g.getId().toString(), new CompanyDTO(g.getCompany()), new AddressDTO(g.getAddress()), g.getCreatedAt(), g.getOpeningTime(), g.getClosingTime());
    }

    public GymEntity toGymEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new GymEntity(_id, company.toCompanyEntity(), address.toAddressEntity(), createdAt, openingTime, closingTime);
    }
}
