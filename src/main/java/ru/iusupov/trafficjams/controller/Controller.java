package ru.iusupov.trafficjams.controller;

import com.google.common.net.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.iusupov.trafficjams.configuration.Properties;
import ru.iusupov.trafficjams.service.TrafficJamService;

import java.util.Map;

@RestController
public class Controller {

    private final TrafficJamService service;
    private final Properties properties;

    @Autowired
    public Controller(TrafficJamService service, Properties properties) {
        this.service = service;
        this.properties = properties;
    }

    @GetMapping
    public int getTrafficJamValue(@RequestHeader Map<String, String> headers) {
        if (isAuthorized(headers)) {
            return service.getValue();
        } else {
            return 401;
        }
    }

    private boolean isAuthorized(Map<String, String> headers) {
        try {
            String header = headers.get(HttpHeaders.AUTHORIZATION);
            if (header == null) header = headers.get(HttpHeaders.AUTHORIZATION.toLowerCase());
            String[] arr = header.split("\\s+");
            if (arr.length != 2) return false;
            String token = arr[1];
            return token != null && token.equals(properties.getToken());
        } catch (NullPointerException e) {
            return false;
        }
    }
}
