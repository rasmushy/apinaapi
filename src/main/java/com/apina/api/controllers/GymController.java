package com.apina.api.controllers;

import com.apina.api.dtos.GymDTO;
import com.apina.api.services.GymService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api")
public class GymController {
    private static final Logger LOGGER = LoggerFactory.getLogger(GymController.class);
    private final GymService gymService;

    public GymController(GymService gymService) {
        this.gymService = gymService;
    }

    @PostMapping("gym")
    @ResponseStatus(HttpStatus.CREATED)
    public GymDTO postGym(@RequestBody GymDTO gymDTO) {
        return gymService.save(gymDTO);
    }

    @PostMapping("gyms")
    @ResponseStatus(HttpStatus.CREATED)
    public List<GymDTO> postGyms(@RequestBody List<GymDTO> gymEntities) {
        return gymService.saveAll(gymEntities);
    }

    @GetMapping("gyms")
    public List<GymDTO> getGyms() {
        return gymService.findAll();
    }

    @GetMapping("gym/{id}")
    public ResponseEntity<GymDTO> getGym(@PathVariable String id) {
        GymDTO gymDTO = gymService.findOne(id);
        if (gymDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(gymDTO);
    }

    @GetMapping("gyms/{ids}")
    public List<GymDTO> getGyms(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return gymService.findAll(listIds);
    }

    @GetMapping("gyms/city/{city}")
    public List<GymDTO> getGymsByCity(@PathVariable String city) {
        return gymService.findAllByCity(city);
    }

    @GetMapping("gyms/company/{company}")
    public List<GymDTO> getGymsByCompany(@PathVariable String company) {
        return gymService.findAllByCompany(company);
    }

    @GetMapping("gyms/openingTime/{openingTime}")
    public List<GymDTO> getGymsByOpeningTime(@PathVariable String openingTime) {
        return gymService.findAllByOpeningTime(openingTime);
    }

    @GetMapping("gyms/closingTime/{closingTime}")
    public List<GymDTO> getGymsByClosingTime(@PathVariable String closingTime) {
        return gymService.findAllByClosingTime(closingTime);
    }

    @GetMapping("gyms/address/{streetName}")
    public GymDTO getGymByAddress(@PathVariable String streetName) {
        return gymService.findByAddress(streetName);
    }

    @PutMapping("gym")
    public GymDTO putGym(@RequestBody GymDTO gymDTO) {
        return gymService.update(gymDTO);
    }

    @PutMapping("gyms")
    public Long putGyms(@RequestBody List<GymDTO> gymEntities) {
        return gymService.update(gymEntities);
    }

    @DeleteMapping("gyms")
    public Long deleteGyms() {
        return gymService.deleteAll();
    }

    @DeleteMapping("gym/{id}")
    public Long deleteGym(@PathVariable String id) {
        return gymService.delete(id);
    }

    @DeleteMapping("gyms/{ids}")
    public Long deleteGyms(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return gymService.delete(listIds);
    }

    @GetMapping("gyms/count")
    public Long getCount() {
        return gymService.count();
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public final Exception handleAllExceptions(RuntimeException e) {
        LOGGER.error("Internal server error.", e);
        return e;
    }

}
