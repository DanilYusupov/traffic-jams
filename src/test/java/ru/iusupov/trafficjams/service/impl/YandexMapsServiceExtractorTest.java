package ru.iusupov.trafficjams.service.impl;

import org.junit.jupiter.api.Test;
import ru.iusupov.trafficjams.configuration.Properties;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class YandexMapsServiceExtractorTest {

//    @Test
    public void loadHtmlTest() throws IOException, InterruptedException {
        YandexMapsServiceExtractor service = getService();
        System.out.println(service.loadHtmlPage());
    }

//    @Test
    public void noDomElementTest() {
        YandexMapsServiceExtractor service = getService();
        assertEquals(-1, service.extractTrafficValue("Hello daddy ;)"));
    }

//    @Test
    public void invalidHtmlPageTest() {
        YandexMapsServiceExtractor service = getService();
        String cssClass = getProperties().getCssClass();
        assertEquals(-1, service.extractTrafficValue(String.format(
                "%s\">this is traffic jam<div>2",
                cssClass
        )));
        assertEquals(-1, service.extractTrafficValue(String.format(
                "%sblablabla>fewfew<dewfwefwf>%s>4",
                cssClass,
                cssClass
        )));
    }

//    @Test
    public void maxValueTest() {
        YandexMapsServiceExtractor service = getService();
        String cssClass = getProperties().getCssClass();
        assertEquals(9, service.extractTrafficValue(String.format(
                "<\"%s\">10</div>",
                cssClass
        )));
    }

//    @Test
    public void normalValuesTest() {
        YandexMapsServiceExtractor service = getService();
        String cssClass = getProperties().getCssClass();
        for (int i = 0; i < 10; i++) {
            assertEquals(i, service.extractTrafficValue(String.format(
                    "<%s>%d</div>",
                    cssClass,
                    i
            )));
        }
    }

    private YandexMapsServiceExtractor getService() {
        return new YandexMapsServiceExtractor(getProperties());
    }

    private Properties getProperties() {
        Properties props = new Properties();
        props.setUrl("https://yandex.ru/maps/43/kazan/?l=trf%2Ctrfe&ll=49.108795%2C55.796289&utm_source=main_stripe_big&z=12");
        props.setCssClass("traffic-raw-icon__text");
        return props;
    }

}
