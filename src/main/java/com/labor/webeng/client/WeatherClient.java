package com.labor.webeng.client;

import com.labor.webeng.model.Location;
import com.labor.webeng.model.Weather;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class WeatherClient {

    private final RestClient restClient = RestClient.create("https://api.open-meteo.com");

    // Holt die Tageswerte für heute
    public Weather getWeather(Location location) {
        ForecastResponse response = restClient.get()
                .uri("/v1/forecast?latitude={lat}&longitude={lon}"
                                + "&daily=temperature_2m_max,temperature_2m_min,precipitation_probability_max,wind_speed_10m_max"
                                + "&timezone=auto&forecast_days=1",
                        location.getLatitude(), location.getLongitude())
                .retrieve()
                .body(ForecastResponse.class);

        Daily daily = response.daily();
        return new Weather(
                daily.temperature_2m_min().get(0),
                daily.temperature_2m_max().get(0),
                daily.wind_speed_10m_max().get(0),
                daily.precipitation_probability_max().get(0));
    }

    private record ForecastResponse(Daily daily) {}

    // Feldnamen entsprechen 1:1 den JSON-Feldern der Open-Meteo-API
    private record Daily(
            List<Double> temperature_2m_min,
            List<Double> temperature_2m_max,
            List<Double> wind_speed_10m_max,
            List<Double> precipitation_probability_max) {}
}
