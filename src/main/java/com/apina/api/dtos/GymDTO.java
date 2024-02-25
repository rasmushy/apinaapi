package com.apina.api.dtos;

import com.apina.api.models.GymEntity;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.Map;
public record GymDTO(
        String id,
        CompanyDTO company,
        AddressDTO address,
        Date createdAt,
        Map<String, String> openingTime,
        Map<String, String> closingTime,
        String additionalInfo
) {

    public GymDTO(GymEntity g) {
        this(g.getId().toString(), new CompanyDTO(g.getCompany()), new AddressDTO(g.getAddress()), g.getCreatedAt(), g.getOpeningTime(), g.getClosingTime(), g.getAdditionalInfo());
    }

    public GymEntity toGymEntity() {
        ObjectId _id = id == null ? new ObjectId() : new ObjectId(id);
        return new GymEntity(_id, company.toCompanyEntity(), address.toAddressEntity(), createdAt, openingTime, closingTime, additionalInfo);
    }
}
