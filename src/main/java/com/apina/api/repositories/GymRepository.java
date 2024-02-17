package com.apina.api.repositories;

import com.apina.api.models.GymEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface GymRepository {

    GymEntity save(GymEntity gymEntity);

    List<GymEntity> saveAll(List<GymEntity> gymEntities);

    GymEntity findOne(String id);

    GymEntity findByAddress(String streetName);

    List<GymEntity> findAll();

    List<GymEntity> findAll(List<String> ids);

    List<GymEntity> findAllByCity(String city);

    List<GymEntity> findAllByOpeningTime(String openingTime);

    List<GymEntity> findAllByClosingTime(String closingTime);

    List<GymEntity> findAllByCompany(String companyName);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    GymEntity update(GymEntity gymEntity);

    long update(List<GymEntity> gymEntities);

}
