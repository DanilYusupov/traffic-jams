package ru.iusupov.trafficjams.service.impl;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class SeleniumServiceTest {

    @Autowired
    SeleniumService service;

    @Test
    @Disabled
    public void getValueTest() {
        int value = service.getValue();
        assertTrue(value >= 0 && value < 10);
    }

}
