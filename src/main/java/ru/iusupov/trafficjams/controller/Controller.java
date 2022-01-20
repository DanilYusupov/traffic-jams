package ru.iusupov.trafficjams.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.iusupov.trafficjams.service.TrafficJamService;

@RestController
public class Controller {

    private final TrafficJamService service;

    @Autowired
    public Controller(TrafficJamService service) {
        this.service = service;
    }

    @GetMapping
    public int getTrafficJamValue() {
        return service.getValue();
    }
}
