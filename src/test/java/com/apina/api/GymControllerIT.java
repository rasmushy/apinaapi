package com.apina.api;

import com.apina.api.dtos.GymDTO;
import com.apina.api.models.GymEntity;
import com.apina.api.repositories.GymRepository;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import jakarta.annotation.PostConstruct;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
/*
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
*/
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
//import static org.assertj.core.api.Assertions.assertThat;

/*
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GymControllerIT {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate rest;
    @Autowired
    private GymRepository gymRepository;
    @Autowired
    private TestHelper testHelper;
    private String URL;

    @Autowired
    GymControllerIT(MongoClient mongoClient) {
        createGymCollectionIfNotPresent(mongoClient);
    }

    @PostConstruct
    @BeforeEach
    void setUp() {
        URL = "http://localhost:" + port + "/api";
    }

    @AfterEach
    void tearDown() {
        gymRepository.deleteAll();
    }

    @DisplayName("POST /gym with 1 gym")
    @Test
    void postGym() {
        // GIVEN
        // WHEN
        ResponseEntity<GymDTO> result = rest.postForEntity(URL + "/gym", testHelper.getBkDTO(), GymDTO.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        GymDTO gymDTO = result.getBody();
        assertThat(gymDTO).isNotNull();
        assertThat(gymDTO.id()).isNotNull();
        assertThat(gymDTO).usingRecursiveComparison()
                .ignoringFields("id", "createdAt")
                .isEqualTo(testHelper.getBkDTO());
    }

    @DisplayName("POST /gyms with 2 gyms")
    @Test
    void postGyms() {
        // GIVEN
        // WHEN
        HttpEntity<List<GymDTO>> body = new HttpEntity<>(testHelper.getListBkKaDTO());
        ResponseEntity<List<GymDTO>> response = rest.exchange(URL + "/gyms", HttpMethod.POST, body,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).usingElementComparatorIgnoringFields("id", "createdAt")
                .containsExactlyInAnyOrderElementsOf(testHelper.getListBkKaDTO());
    }

    @DisplayName("GET /gyms with 2 gyms")
    @Test
    void getGyms() {
        // GIVEN
        List<GymEntity> gymEntities = gymRepository.saveAll(testHelper.getListBkKaEntity());
        // WHEN
        ResponseEntity<List<GymDTO>> result = rest.exchange(URL + "/gyms", HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<GymDTO> expected = List.of(testHelper.getBkDTOWithId(gymEntities.get(0).getId()),
                testHelper.getKaDTOWithId(gymEntities.get(1).getId()));
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt")
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @DisplayName("GET /gym/{id}")
    @Test
    void getGymById() {
        // GIVEN
        GymEntity gymInserted = gymRepository.save(testHelper.getKaEntity());
        ObjectId idInserted = gymInserted.getId();
        // WHEN
        ResponseEntity<GymDTO> result = rest.getForEntity(URL + "/gym/" + idInserted, GymDTO.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).usingRecursiveComparison()
                .ignoringFields("createdAt")
                .isEqualTo(testHelper.getKaDTOWithId(idInserted));
    }

    @DisplayName("GET /gyms/{ids}")
    @Test
    void getGymsByIds() {
        // GIVEN
        List<GymEntity> gymsInserted = gymRepository.saveAll(testHelper.getListBkKaEntity());
        List<String> idsInserted = gymsInserted.stream().map(GymEntity::getId).map(ObjectId::toString).toList();
        // WHEN
        String url = URL + "/gyms/" + String.join(",", idsInserted);
        ResponseEntity<List<GymDTO>> result = rest.exchange(url, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id", "createdAt")
                .containsExactlyInAnyOrderElementsOf(testHelper.getListBkKaDTO());
    }

    @DisplayName("GET /gyms/count")
    @Test
    void getCount() {
        // GIVEN
        gymRepository.saveAll(testHelper.getListBkKaEntity());
        // WHEN
        ResponseEntity<Long> result = rest.getForEntity(URL + "/gyms/count", Long.class);
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
    }

    @DisplayName("DELETE /gym/{id}")
    @Test
    void deleteGymById() {
        // GIVEN
        GymEntity GymInserted = gymRepository.save(testHelper.getBkEntity());
        String idInserted = GymInserted.getId().toHexString();
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/gym/" + idInserted, HttpMethod.DELETE, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(1L);
        assertThat(gymRepository.count()).isZero();
    }

    @DisplayName("DELETE /gyms/{ids}")
    @Test
    void deleteGymsByIds() {
        // GIVEN
        List<GymEntity> gymInserted = gymRepository.saveAll(testHelper.getListBkKaEntity());
        List<String> idsInserted = gymInserted.stream().map(GymEntity::getId).map(ObjectId::toString).toList();
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/gyms/" + String.join(",", idsInserted),
                HttpMethod.DELETE, null, new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        assertThat(gymRepository.count()).isZero();
    }

    @DisplayName("DELETE /gyms")
    @Test
    void deleteGyms() {
        // GIVEN
        gymRepository.saveAll(testHelper.getListBkKaEntity());
        // WHEN
        ResponseEntity<Long> result = rest.exchange(URL + "/gyms", HttpMethod.DELETE, null,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        assertThat(gymRepository.count()).isZero();
    }

    @DisplayName("PUT /gym")
    @Test
    void putGym() {
        // GIVEN
        GymEntity gymInserted = gymRepository.save(testHelper.getBkEntity());
        // WHEN
        gymInserted.setClosingTime(Map.of("monday", "20:00"));
        gymInserted.setOpeningTime(Map.of("monday", "08:00"));
        HttpEntity<GymDTO> body = new HttpEntity<>(new GymDTO(gymInserted));
        ResponseEntity<GymDTO> result = rest.exchange(URL + "/gym", HttpMethod.PUT, body,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(
                new GymDTO(gymRepository.findOne(gymInserted.getId().toString())));
        String openingTime = result.getBody().openingTime().get("monday");
        String closingTime = result.getBody().closingTime().get("monday");
        assertThat(openingTime).isEqualTo("08:00");
        assertThat(closingTime).isEqualTo("20:00");
        assertThat(gymRepository.count()).isEqualTo(1L);
    }

    @DisplayName("PUT /gyms with 2 gyms")
    @Test
    void putGyms() {
        // GIVEN
        List<GymEntity> gymsInserted = gymRepository.saveAll(testHelper.getListBkKaEntity());
        // WHEN
        gymsInserted.get(0).setClosingTime(Map.of("monday", "20:00"));
        gymsInserted.get(0).setOpeningTime(Map.of("monday", "08:00"));
        gymsInserted.get(1).setClosingTime(Map.of("monday", "21:00"));
        gymsInserted.get(1).setOpeningTime(Map.of("monday", "07:00"));
        HttpEntity<List<GymDTO>> body = new HttpEntity<>(gymsInserted.stream().map(GymDTO::new).toList());
        ResponseEntity<Long> result = rest.exchange(URL + "/gyms", HttpMethod.PUT, body,
                new ParameterizedTypeReference<>() {
                });
        // THEN
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(2L);
        GymEntity bk = gymRepository.findOne(gymsInserted.get(0).getId().toString());
        GymEntity ka = gymRepository.findOne(gymsInserted.get(1).getId().toString());
        assertThat(bk.getOpeningTime().get("monday")).isEqualTo("08:00");
        assertThat(bk.getClosingTime().get("monday")).isEqualTo("20:00");
        assertThat(ka.getClosingTime().get("monday")).isEqualTo("21:00");
        assertThat(ka.getOpeningTime().get("monday")).isEqualTo("07:00");
        assertThat(gymRepository.count()).isEqualTo(2L);
    }

    private void createGymCollectionIfNotPresent(MongoClient mongoClient) {
        // This is required because it is not possible to create a new collection within a multi-documents transaction.
        // Some tests start by inserting 2 documents with a transaction.
        MongoDatabase db = mongoClient.getDatabase("test");
        if (!db.listCollectionNames().into(new ArrayList<>()).contains("gyms")) {
            db.createCollection("gyms");
        }
    }
}
*/
