package com.labor.webeng.service;

import com.labor.webeng.client.GeocodingClient;
import com.labor.webeng.model.Location;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class WandernService {

    private final GeocodingClient geocodingClient;

    public WandernService(GeocodingClient geocodingClient) {
        this.geocodingClient = geocodingClient;
    }

    public Location getCoordinates(String location) {
        return geocodingClient.findCoordinates(location)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ort nicht gefunden: " + location));
    }
}
