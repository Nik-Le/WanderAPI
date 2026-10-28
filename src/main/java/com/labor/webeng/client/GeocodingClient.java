package com.labor.webeng.client;

import com.labor.webeng.model.Location;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;

@Component
public class GeocodingClient {

    private final RestClient restClient = RestClient.create("https://geocoding-api.open-meteo.com");

    public Optional<Location> findCoordinates(String name) {
        GeocodingResponse response = restClient.get()
                .uri("/v1/search?name={name}&count=1&language=de&format=json", name)
                .retrieve()
                .body(GeocodingResponse.class);

        // Bei unbekanntem Ort fehlt "results" in der Antwort komplett
        if (response == null || response.results() == null || response.results().isEmpty()) {
            return Optional.empty();
        }

        GeocodingResult first = response.results().get(0);
        Location location = new Location();
        location.setLatitude(first.latitude());
        location.setLongitude(first.longitude());
        return Optional.of(location);
    }

    private record GeocodingResponse(List<GeocodingResult> results) {}

    private record GeocodingResult(double latitude, double longitude) {}
}
