package ru.iusupov.trafficjams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.iusupov.trafficjams.service.TrafficJamService;

@Component
public class HealthChecker {

    final private TrafficJamService service;

    @Autowired
    public HealthChecker(TrafficJamService service) {
        this.service = service;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkHealth() {
        service.systemCheck();
    }

}
