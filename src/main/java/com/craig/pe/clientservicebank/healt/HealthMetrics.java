package com.craig.pe.clientservicebank.healt;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

@Component
public class HealthMetrics implements HealthIndicator {

    @Override
    public Health health() {
        return isUp() ? Health.up().withDetail("success code","Get all client connection").build() : Health.down().withDetail("error code","Not connection").build();
    }

    private boolean isUp() {
        boolean isUp = false;
        try {
            URL url = new URL("http://localhost:8080/clients");
            URLConnection connection = url.openConnection();
            connection.connect();
            isUp = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return isUp;
    }
}
