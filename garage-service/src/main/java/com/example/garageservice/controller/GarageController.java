package com.example.garageservice.controller;

import com.example.garageservice.model.Garage;
import com.example.garageservice.repository.GarageRepository;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/")
@Api(value="Garage Management System", description="Operations pertaining to garage in Garage Management System")
public class GarageController {
    @Autowired
    private GarageRepository garageRepository;

    @ApiOperation(value = "Add a garage")
    @PostMapping(value= "createGarage")
    public String createGarage(
            @ApiParam(value = "Garage object store in database", required = true)
            @Valid @RequestBody Garage garage) {
        garageRepository.save(garage);
        return "ok";
    }

    @ApiOperation(value = "View a list of available garages", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    @GetMapping("getGarages")
    public List<Garage> listGarages(){
        return garageRepository.findAll();
    }

    @ApiOperation(value = "Update a garage")
    @PutMapping("/updateGarage/{id}")
    public Garage updateGarage(@ApiParam(value = "Employee Id to update employee object", required = true)
                                    @PathVariable(value = "id") String id,
                                    @ApiParam(value = "Update garage object", required = true)
                                    @Valid @RequestBody Garage garage) {
        garage.setId(id);
        garageRepository.save(garage);
        return garage;
    }

    @ApiOperation(value = "Delete a garage")
    @DeleteMapping("/deleteGarage/{id}")
    public String deleteGarage(@ApiParam(value = "Garage Id from which garage object will delete from database", required = true)
                               @PathVariable(value = "id") String id) {
        garageRepository.deleteById(id);
        return "Contact record for employee-id= " + id + " deleted.";
    }
}
