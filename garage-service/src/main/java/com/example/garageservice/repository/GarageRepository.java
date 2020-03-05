package com.example.garageservice.repository;

import com.example.garageservice.model.Garage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GarageRepository extends MongoRepository<Garage, String> {
}
