package com.example.garageservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class GarageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GarageServiceApplication.class, args);
    }

}
