package com.apina.api.services;

import com.apina.api.dtos.GymDTO;

import java.util.List;
public interface GymService {

    GymDTO save(GymDTO gymDTO);
    List<GymDTO> saveAll(List<GymDTO> gymEntities);

    GymDTO findOne(String id);

    GymDTO findByAddress(String streetName);

    List<GymDTO> findAll();

    List<GymDTO> findAll(List<String> ids);

    List<GymDTO> findAllByCity(String city);

    List<GymDTO> findAllByOpeningTime(String openingTime);

    List<GymDTO> findAllByClosingTime(String closingTime);

    List<GymDTO> findAllByCompany(String companyName);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    GymDTO update(GymDTO gymDTO);

    long update(List<GymDTO> gymEntities);

}
