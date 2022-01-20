package ru.iusupov.trafficjams.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties("traffic")
@Component
@Getter
@Setter
public class Properties {
    private String url;
    private String cssClass;
}
