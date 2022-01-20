package ru.iusupov.trafficjams.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.iusupov.trafficjams.configuration.Properties;
import ru.iusupov.trafficjams.service.TrafficJamService;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class YandexMapsServiceExtractor implements TrafficJamService {

    private final Properties properties;

    @Autowired
    public YandexMapsServiceExtractor(Properties properties) {
        this.properties = properties;
    }

    @Override
    public int getValue() {
        try {
            return extractTrafficValue(loadHtmlPage());
        } catch (IOException | InterruptedException e) {
            log.error("Wasn't able to load html page by URL: {}", properties.getUrl());
            return -1;
        }
    }

    int extractTrafficValue(String htmlPage) {
        String cssClass = properties.getCssClass();
        int beginIndex = htmlPage.indexOf(cssClass);
        if (!htmlPage.contains(cssClass)) {
            log.error("Wasn't able to find DOM element by class {}", cssClass);
            return -1;
        }
        String searchField = htmlPage.substring(beginIndex + cssClass.length());
        if (searchField.length() > 0) {
            int gtIndex = searchField.indexOf(">");
            if (gtIndex < 4 && Character.isDigit(searchField.charAt(gtIndex + 1))) {
                int value = Integer.parseInt(searchField.substring(gtIndex + 1, gtIndex + 2));
                if (value == 1 && searchField.charAt(gtIndex + 2) == '0') {
                    return 9;
                }
                return value;
            } else {
                log.error("Invalid HTML page. Page segment: {}", htmlPage.substring(
                        beginIndex,
                        (htmlPage.length() - beginIndex > 100) ? beginIndex + 100 : htmlPage.length()
                ));
                return -1;
            }
        } else {
            log.error("Wasn't able to find DOM element by class {}", cssClass);
            return -1;
        }
    }

    String loadHtmlPage() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(properties.getUrl()))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

}
