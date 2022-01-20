package ru.iusupov.trafficjams;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.iusupov.trafficjams.service.TrafficJamService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TrafficJamsApplicationTests {

    final List<Integer> possibleValues = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    @Autowired
    TrafficJamService service;

    @Test
    void contextLoads() {
    }

    @Test
    public void getTrafficJamValue() {
        int result = service.getValue();
        assertTrue(possibleValues.contains(result));
    }

}
