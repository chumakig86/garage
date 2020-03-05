package com.example.garageservice.controller;

import com.example.garageservice.model.Garage;
import com.example.garageservice.repository.GarageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class GarageController {
    @Autowired
    private GarageRepository garageRepository;

    @Value("${garage.greeting: Hello from local config}")
    private String garageGreeting;

    @RequestMapping("/configServerHealthCheck")
    @ResponseBody
    public String configServerHealthCheck() {
        return garageGreeting;
    }

    @PostMapping(value= "createGarage")
    public String createGarage(@RequestBody Garage garage) {
        garageRepository.save(garage);
        return "ok";
    }

    @GetMapping("getGarages")
    public List listGarages(){
        return garageRepository.findAll();
    }

    @PutMapping("/updateGarage/{id}")
    public Garage updateGarage(@RequestBody Garage garage, @PathVariable String id) {
        garage.setId(id);
        garageRepository.save(garage);
        return garage;
    }

    @DeleteMapping("/deleteGarage/{id}")
    public String deleteGarage(@PathVariable String id) {
        garageRepository.deleteById(id);
        return "Contact record for employee-id= " + id + " deleted.";
    }
}
