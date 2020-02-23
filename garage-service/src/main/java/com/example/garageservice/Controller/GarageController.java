package com.example.garageservice.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Component
@Controller
@RequestMapping("/")
public class GarageController {
    @Value("${garage.greeting: Hello from local config}")
    private String garageGreeting;

    @RequestMapping("garage")
    @ResponseBody
    public String garageGet() {
        return garageGreeting;
    }
}
