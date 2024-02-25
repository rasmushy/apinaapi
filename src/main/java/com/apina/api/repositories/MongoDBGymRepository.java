package com.apina.api.repositories;

import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.TransactionOptions;
import com.mongodb.WriteConcern;
import com.mongodb.client.ClientSession;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReplaceOneModel;
import com.apina.api.models.GymEntity;
import jakarta.annotation.PostConstruct;
import org.bson.BsonDocument;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.in;
import static com.mongodb.client.model.ReturnDocument.AFTER;

@Repository
public class MongoDBGymRepository implements GymRepository {

    private static final TransactionOptions txnOptions = TransactionOptions.builder()
            .readPreference(ReadPreference.primary())
            .readConcern(ReadConcern.MAJORITY)
            .writeConcern(WriteConcern.MAJORITY)
            .build();

    private final MongoClient client;

    private MongoCollection<GymEntity> gymCollection;

    public MongoDBGymRepository(MongoClient mongoClient) {
        this.client = mongoClient;
    }

    @PostConstruct
    void init() {
        gymCollection = client.getDatabase("ApinaData").getCollection("gyms", GymEntity.class);
    }

    @Override
    public GymEntity save(GymEntity gymEntity) {
        gymEntity.setId(new ObjectId());
        gymCollection.insertOne(gymEntity);
        return gymEntity;
    }

    @Override
    public List<GymEntity> saveAll(List<GymEntity> gymEntities) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(() -> {
                gymEntities.forEach(p -> p.setId(new ObjectId()));
                gymCollection.insertMany(clientSession, gymEntities);
                return gymEntities;
            }, txnOptions);
        }
    }

    @Override
    public List<GymEntity> findAll() {
        return gymCollection.find().into(new ArrayList<>());
    }

    @Override
    public List<GymEntity> findAll(List<String> ids) {
        return gymCollection.find(in("_id", mapToObjectIds(ids))).into(new ArrayList<>());
    }
    @Override
    public List<GymEntity> findAllByCity(String city) {
        return gymCollection.find(eq("address.city", city)).into(new ArrayList<>());
    }
    @Override
    public List<GymEntity> findAllByOpeningTime(String openingTime) {
        return gymCollection.find(eq("openingTime", openingTime)).into(new ArrayList<>());
    }
    @Override
    public List<GymEntity> findAllByClosingTime(String closingTime) {
        return gymCollection.find(eq("closingTime", closingTime)).into(new ArrayList<>());
    }
    @Override
    public List<GymEntity> findAllByCompany(String companyName) {
        return gymCollection.find(eq("company.name", companyName)).into(new ArrayList<>());
    }

    @Override
    public GymEntity findOne(String id) {
        return gymCollection.find(eq("_id", new ObjectId(id))).first();
    }
    @Override
    public GymEntity findByAddress(String streetName) {
        return gymCollection.find(eq("address.street", streetName)).first();
    }

    @Override
    public long count() {
        return gymCollection.countDocuments();
    }

    @Override
    public long delete(String id) {
        return gymCollection.deleteOne(eq("_id", new ObjectId(id))).getDeletedCount();
    }

    @Override
    public long delete(List<String> ids) {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> gymCollection.deleteMany(clientSession, in("_id", mapToObjectIds(ids))).getDeletedCount(),
                    txnOptions);
        }
    }

    @Override
    public long deleteAll() {
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> gymCollection.deleteMany(clientSession, new BsonDocument()).getDeletedCount(), txnOptions);
        }
    }

    @Override
    public GymEntity update(GymEntity gymEntity) {
        FindOneAndReplaceOptions options = new FindOneAndReplaceOptions().returnDocument(AFTER);
        return gymCollection.findOneAndReplace(eq("_id", gymEntity.getId()), gymEntity, options);
    }

    @Override
    public long update(List<GymEntity> gymEntities) {
        List<ReplaceOneModel<GymEntity>> writes = gymEntities.stream()
                .map(p -> new ReplaceOneModel<>(eq("_id", p.getId()),
                        p))
                .toList();
        try (ClientSession clientSession = client.startSession()) {
            return clientSession.withTransaction(
                    () -> gymCollection.bulkWrite(clientSession, writes).getModifiedCount(), txnOptions);
        }
    }

    private List<ObjectId> mapToObjectIds(List<String> ids) {
        return ids.stream().map(ObjectId::new).toList();
    }
}

