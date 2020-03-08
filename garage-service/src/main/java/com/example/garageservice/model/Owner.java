package com.example.garageservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Owner {
    @NotNull(message = "Please provide owners name")
    private String name;
    @NotNull(message = "Please provide owners surname")
    private String surname;
}
