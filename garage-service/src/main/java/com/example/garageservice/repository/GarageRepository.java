package com.example.garageservice.repository;

import com.example.garageservice.model.Garage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarageRepository extends MongoRepository<Garage, String> {

    @Query(value = "{'owner.surname': ?0}")
    List<Garage> findGarageByOwnerSurname(String ownerSurname);
    @Query(value = "{'contact.phoneNumber': ?0}")
    List<Garage> findGarageByOwnerPhoneNumber(String phoneNumber);
    @Query(value = "{'car.carNumber': ?0}")
    List<Garage> findGarageByCarNumber(String carNumber);
}
