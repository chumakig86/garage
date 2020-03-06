package com.example.garageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;


@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Garage {
    @Id
    private String id;
    @NotNull(message = "Please provide an owner information")
    private Owner owner;
    @NotNull(message = "Please provide a contact information")
    private Contact contact;
    @NotNull(message = "Please provide a car information")
    private Car car;
}
