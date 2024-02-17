package com.apina.api.services;

import com.apina.api.dtos.GymDTO;
import com.apina.api.repositories.GymRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GymServiceImpl implements GymService {

    private final GymRepository gymRepository;

    public GymServiceImpl(GymRepository gymRepository) {
        this.gymRepository = gymRepository;
    }

    @Override
    public GymDTO save(GymDTO gymDTO) {
        return new GymDTO(gymRepository.save(gymDTO.toGymEntity()));
    }

    @Override
    public List<GymDTO> saveAll(List<GymDTO> gymEntities) {
        return gymEntities.stream()
                .map(GymDTO::toGymEntity)
                .peek(gymRepository::save)
                .map(GymDTO::new)
                .toList();
    }

    @Override
    public GymDTO findOne(String id) {
        return new GymDTO(gymRepository.findOne(id));
    }

    @Override
    public GymDTO findByAddress(String streetName) {
        return new GymDTO(gymRepository.findByAddress(streetName));
    }

    @Override
    public List<GymDTO> findAll() {
        return gymRepository.findAll().stream().map(GymDTO::new).toList();
    }

    @Override
    public List<GymDTO> findAll(List<String> ids) {
        return gymRepository.findAll(ids).stream().map(GymDTO::new).toList();
    }

    @Override
    public List<GymDTO> findAllByCity(String city) {
        return gymRepository.findAllByCity(city).stream().map(GymDTO::new).toList();
    }

    @Override
    public List<GymDTO> findAllByOpeningTime(String openingTime) {
        return gymRepository.findAllByOpeningTime(openingTime).stream().map(GymDTO::new).toList();
    }

    @Override
    public List<GymDTO> findAllByClosingTime(String closingTime) {
        return gymRepository.findAllByClosingTime(closingTime).stream().map(GymDTO::new).toList();
    }

    @Override
    public List<GymDTO> findAllByCompany(String companyName) {
        return gymRepository.findAllByCompany(companyName).stream().map(GymDTO::new).toList();
    }

    @Override
    public long count() {
        return gymRepository.count();
    }

    @Override
    public long delete(String id) {
        return gymRepository.delete(id);
    }

    @Override
    public long delete(List<String> ids) {
        return gymRepository.delete(ids);
    }

    @Override
    public long deleteAll() {
        return gymRepository.deleteAll();
    }

    @Override
    public GymDTO update(GymDTO gymDTO) {
        return new GymDTO(gymRepository.update(gymDTO.toGymEntity()));
    }

    @Override
    public long update(List<GymDTO> gymEntities) {
        return gymRepository.update(gymEntities.stream().map(GymDTO::toGymEntity).toList());
    }

}
