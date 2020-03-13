package com.example.garageservice.controller;

import com.example.garageservice.model.Garage;
import com.example.garageservice.repository.GarageRepository;
import com.example.garageservice.service.GarageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import java.util.Optional;

@RestController
@RequestMapping("/")
@Api(value="Garage Management System", description="Operations pertaining to garage in Garage Management System")
public class GarageController {
    private final GarageService garageService;

    @Autowired
    public GarageController(GarageService garageService) {
        this.garageService = garageService;
    }

    @ApiOperation(value = "Add a garage")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request")})
    @PostMapping(value= "createGarage")
    public ResponseEntity createGarage(
            @ApiParam(value = "Garage object store in database", required = true)
            @Valid @RequestBody Garage garage) {
        return garageService.createGarage(garage);
    }

    @ApiOperation(value = "View a list of available garages", response = List.class)
    @ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
    @GetMapping("getGarages")
    public List<Garage> getGarages(){
        return garageService.listGarages();
    }

    @GetMapping(value= "/getbyid/{garage-id}")
    public Optional<Garage> getById(@PathVariable(value= "garage-id") String id) {
        return garageService.getById(id);
    }

    @ApiOperation(value = "Get garage by owners surname")
    @GetMapping(value= "/getbyOwnerSurname/{owner-surname}")
    public List<Garage> getByOwnerSurName(@PathVariable(value= "owner-surname") String surname) {
        return garageService.getByOwnerSurName(surname);
    }

    @ApiOperation(value = "Get garage by owners phone number")
    @GetMapping(value= "/getbyOwnerPhoneNumber/{owner-phonenumber}")
    public List<Garage> getByOwnerPhoneNumber(@PathVariable(value= "owner-phonenumber") String phoneNumber) {
        return garageService.getByOwnerPhoneNumber(phoneNumber);
    }

    @ApiOperation(value = "Get garage by car number")
    @GetMapping(value= "/getbyCarNumber/{carnumber}")
    public List<Garage> getByCarNumber(@PathVariable(value= "carnumber") String carNumber) {
        return garageService.getByCarNumber(carNumber);
    }

    @ApiOperation(value = "Get garage by garage number")
    @ApiResponses(value = { @ApiResponse(code = 400, message = "Bad request")})
    @GetMapping(value= "/getbyGarageNumber/{garagenumber}")
    public Optional<Garage> getByGarageNumber(@PathVariable(value= "garagenumber") Integer garageNumber) {
        return garageService.getByGarageNumber(garageNumber);
    }

    @ApiOperation(value = "Update a garage")
    @PutMapping("/updateGarage/{id}")
    public Garage updateGarage(@ApiParam(value = "Employee Id to update employee object", required = true)
                                    @PathVariable(value = "id") String id,
                                    @ApiParam(value = "Update garage object", required = true)
                                    @Valid @RequestBody Garage garage) {
        return garageService.updateGarage(id, garage);
    }

    @ApiOperation(value = "Delete a garage")
    @DeleteMapping("/deleteGarage/{id}")
    public String deleteGarage(@ApiParam(value = "Garage Id from which garage object will delete from database", required = true)
                               @PathVariable(value = "id") String id) {
        return garageService.deleteGarage(id);
    }
}
