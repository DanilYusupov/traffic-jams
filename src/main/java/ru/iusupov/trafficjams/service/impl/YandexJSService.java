package ru.iusupov.trafficjams.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import ru.iusupov.trafficjams.service.TrafficJamService;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class YandexJSService implements TrafficJamService {

    @Value("classpath:scripts/main.js")
    Resource script;

    @Override
    public int getValue() {
        return 0;
    }

    int extractValue() throws IOException, ScriptException, NoSuchMethodException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
        engine.eval(Files.newBufferedReader(Paths.get(script.getURI()), StandardCharsets.UTF_8));

        Invocable inv = (Invocable) engine;
        Object result = inv.invokeFunction("getTrafficValue");
        return (int) result;
    }
}
