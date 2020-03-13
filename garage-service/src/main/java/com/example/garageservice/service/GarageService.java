package com.example.garageservice.service;

import com.example.garageservice.model.Garage;
import com.example.garageservice.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GarageService {
    private final GarageRepository garageRepository;

    @Autowired
    public GarageService(GarageRepository garageRepository) {
        this.garageRepository = garageRepository;
    }

    public ResponseEntity createGarage(Garage garage) {
    Optional<Garage> newGarage = garageRepository.findGarageByGarageNumber(garage.getGarageNumber());
    if (newGarage.isPresent()) {
        return ResponseEntity.badRequest().body("Garage already exist");

    }
    return new ResponseEntity<>(garageRepository.save(garage), HttpStatus.CREATED);
}

    public List<Garage> listGarages() {
        return garageRepository.findAll();
    }

    public Optional<Garage> getById(String id) {
        return garageRepository.findById(id);
    }

    public List<Garage> getByOwnerSurName(String surname) {
        return garageRepository.findGarageByOwnerSurname(surname);
    }

    public List<Garage> getByOwnerPhoneNumber(String phoneNumber) {
        return garageRepository.findGarageByOwnerPhoneNumber(phoneNumber);
    }

    public List<Garage> getByCarNumber(String carNumber) {
        return garageRepository.findGarageByCarNumber(carNumber);
    }

    public Optional<Garage> getByGarageNumber(Integer garageNumber) {
        return garageRepository.findGarageByGarageNumber(garageNumber);
    }

    public Garage updateGarage(String id, Garage garage) {
        garage.setId(id);
        garageRepository.save(garage);
        return garage;
    }

    public String deleteGarage(String id) {
        garageRepository.deleteById(id);
        return "Contact record for employee-id= " + id + " deleted.";
    }
}
