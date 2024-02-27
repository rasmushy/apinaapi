package com.apina.api.controllers;

import com.apina.api.dtos.GymDTO;
import com.apina.api.services.GymService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.apina.api.util.ControllerUtils.isAdmin;
import static com.apina.api.util.ControllerUtils.isUser;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("postGym: {}", authentication);
        if (isAdmin(authentication) || isUser(authentication)) {
            return gymService.save(gymDTO);
        }
        return null;
    }

    @PostMapping("gyms")
    @ResponseStatus(HttpStatus.CREATED)
    public List<GymDTO> postGyms(@RequestBody List<GymDTO> gymEntities) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("postGyms: {}", authentication);
        if (isAdmin(authentication)) {
            return gymService.saveAll(gymEntities);

        }
        return Collections.emptyList();
    }

    @GetMapping("gyms")
    @ResponseStatus(HttpStatus.OK)
    public List<GymDTO> getGyms() {
        return gymService.findAll();
    }

    @GetMapping("gym/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<GymDTO> getGym(@PathVariable String id) {
        GymDTO gymDTO = gymService.findOne(id);
        if (gymDTO == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        return ResponseEntity.ok(gymDTO);
    }

    @GetMapping("gyms/{ids}")
    @ResponseStatus(HttpStatus.OK)
    public List<GymDTO> getGyms(@PathVariable String ids) {
        List<String> listIds = List.of(ids.split(","));
        return gymService.findAll(listIds);
    }

    @GetMapping("gyms/city/{city}")
    @ResponseStatus(HttpStatus.OK)
    public List<GymDTO> getGymsByCity(@PathVariable String city) {
        return gymService.findAllByCity(city);
    }

    @GetMapping("gyms/company/{company}")
    @ResponseStatus(HttpStatus.OK)
    public List<GymDTO> getGymsByCompany(@PathVariable String company) {
        return gymService.findAllByCompany(company);
    }

    @GetMapping("gyms/openingTime/{openingTime}")
    @ResponseStatus(HttpStatus.OK)
    public List<GymDTO> getGymsByOpeningTime(@PathVariable String openingTime) {
        return gymService.findAllByOpeningTime(openingTime);
    }

    @GetMapping("gyms/closingTime/{closingTime}")
    @ResponseStatus(HttpStatus.OK)
    public List<GymDTO> getGymsByClosingTime(@PathVariable String closingTime) {
        return gymService.findAllByClosingTime(closingTime);
    }

    @GetMapping("gyms/address/{streetName}")
    @ResponseStatus(HttpStatus.OK)
    public GymDTO getGymByAddress(@PathVariable String streetName) {
        return gymService.findByAddress(streetName);
    }

    @PutMapping("gym/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GymDTO putGym(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("putGym: {}", authentication);
        if (isAdmin(authentication)) {
            GymDTO gymDTO = gymService.findOne(id);
            return gymService.update(gymDTO);
        }
        return null;
    }

    @PutMapping("gyms/{ids}")
    @ResponseStatus(HttpStatus.OK)
    public Long putGyms(@PathVariable String ids) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("putGyms: {}", authentication);
        if (isAdmin(authentication)) {
            List<String> listIds = List.of(ids.split(","));
            List<GymDTO> gymEntities = gymService.findAll(listIds);
            return gymService.update(gymEntities);
        }
        return 0L;
    }

    @DeleteMapping("gyms")
    @ResponseStatus(HttpStatus.OK)
    public Long deleteGyms() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("deleteGyms: {}", authentication);
        if (isAdmin(authentication)) {
            return gymService.deleteAll();
        }
        return 0L;

    }

    @DeleteMapping("gym/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteGym(@PathVariable String id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("deleteGym(String id): {}", authentication);
        if (isAdmin(authentication)) {
            return ResponseEntity.ok("Deleted: " + gymService.delete(id));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @DeleteMapping("gyms/{ids}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> deleteGyms(@PathVariable String ids) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LOGGER.info("deleteGyms: {}", authentication);
        if (isAdmin(authentication)) {
            List<String> listIds = List.of(ids.split(","));
            return ResponseEntity.ok("Deleted: " + gymService.delete(listIds) + " gyms");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("gyms/count")
    public Long getCount() {
        return gymService.count();
    }
}
