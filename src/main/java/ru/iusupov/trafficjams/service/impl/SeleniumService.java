package ru.iusupov.trafficjams.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import ru.iusupov.trafficjams.configuration.Properties;
import ru.iusupov.trafficjams.service.TrafficJamService;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.function.Function;

@Slf4j
@Service
public class SeleniumService implements TrafficJamService {

    private final Properties properties;
    private final Environment env;

    @Autowired
    public SeleniumService(Properties properties, Environment env) {
        this.properties = properties;
        this.env = env;
    }

    @Override
    public int getValue() {
        try {
        WebDriver driver = initializeDriverFromClasspath();
        try {
            driver.get(properties.getUrl());
            WebElement trafficJamElement = new WebDriverWait(driver, 15).until(new Function<WebDriver, WebElement>() {
                @Override
                public WebElement apply(WebDriver webDriver) {
                    return driver.findElement(By.className(properties.getCssClass()));
                }
            });
            if (trafficJamElement.isDisplayed()) {
                String value = trafficJamElement.getText();
                log.info("Received traffic jam value {}", value);
                return convertValue(value);
            }
            log.error("Cannot resolve web element: {}", trafficJamElement);
            return -1;
        } finally {
            driver.quit();
        }
        } catch (IOException e) {
            log.error(e.getMessage());
            return -1;
        }
    }

    WebDriver initializeDriverFromClasspath() throws IOException {
        if (Arrays.asList(env.getActiveProfiles()).contains("prod")) {
            String pathToDriver = "/usr/src/trafficjam/chromedriver";
            log.info("PROD env, switching to path: {}", pathToDriver);
            return new RemoteWebDriver(new URL("http://chrome:4444/wd/hub"), new ChromeOptions());
        } else {
            String osName = System.getProperty("os.name");
            log.info("Using web driver for Linux OS");
            String path = "/driver/linux/chromedriver";
            if (osName.contains("Mac")) {
                path = "/driver/macos/chromedriver";
                log.warn("Changed to MacOS web driver");
            }
            String pathToDriver = new ClassPathResource(path).getFile().getPath();
            log.info("Creating path to driver: {}", pathToDriver);
            System.setProperty("webdriver.chrome.driver", pathToDriver);
            return new ChromeDriver();
        }
    }

    int convertValue (String value) {
        try {
            int number = Integer.parseInt(value);
            if (number < 10) {
                return number;
            } else {
                return 9;
            }
        } catch (NumberFormatException e) {
            log.error("Number value wasn't a digit: {}", value);
            return -1;
        }
    }
}
