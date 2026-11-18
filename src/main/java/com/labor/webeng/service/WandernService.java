package com.labor.webeng.service;

import com.labor.webeng.client.GeocodingClient;
import com.labor.webeng.client.WeatherClient;
import com.labor.webeng.model.Location;
import com.labor.webeng.model.Wandern;
import com.labor.webeng.model.Weather;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class WandernService {

    private final GeocodingClient geocodingClient;
    private final WeatherClient weatherClient;

    public WandernService(GeocodingClient geocodingClient, WeatherClient weatherClient) {
        this.geocodingClient = geocodingClient;
        this.weatherClient = weatherClient;
    }

    public Wandern getRecommendation(String city) {
        Weather weather = weatherClient.getWeather(getCoordinates(city));

        Wandern wandern = new Wandern();
        wandern.setCity(city);
        wandern.setLowestTemp((int) Math.round(weather.minTemp()));
        wandern.setHighestTemp((int) Math.round(weather.maxTemp()));
        wandern.setWindSpeed((int) Math.round(weather.windSpeed()));
        wandern.setRainProbability((int) Math.round(weather.rainProbability()));
        wandern.setDanger(isDangerous(wandern));
        wandern.setRecommendation(recommendHike(wandern));
        wandern.setClothing(recommendClothing(wandern));
        return wandern;
    }

    private Location getCoordinates(String city) {
        return geocodingClient.findCoordinates(city)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Ort nicht gefunden: " + city));
    }

    // Sturm, Dauerregen, Hitze oder starker Frost
    private boolean isDangerous(Wandern w) {
        return w.getWindSpeed() > 60
                || w.getRainProbability() > 80
                || w.getHighestTemp() > 35
                || w.getLowestTemp() < -10;
    }

    private String recommendHike(Wandern w) {
        if (w.isDanger()) {
            return "Heute lieber nicht wandern gehen.";
        }
        if (w.getRainProbability() > 50) {
            return "Nur eine kurze Runde (ca. 1-2 Stunden), es wird wahrscheinlich regnen.";
        }
        if (w.getHighestTemp() >= 10 && w.getHighestTemp() <= 25 && w.getWindSpeed() < 30) {
            return "Perfektes Wanderwetter - ideal für eine lange Tagestour.";
        }
        return "Gutes Wetter für eine mittellange Wanderung (ca. 2-4 Stunden).";
    }

    private List<String> recommendClothing(Wandern w) {
        List<String> clothing = new ArrayList<>();
        clothing.add("Wanderschuhe");

        int temp = w.getHighestTemp();
        if (temp < 5) {
            clothing.add("Winterjacke");
            clothing.add("Mütze und Handschuhe");
        } else if (temp < 15) {
            clothing.add("Pullover oder Fleecejacke");
            clothing.add("Lange Hose");
        } else if (temp < 25) {
            clothing.add("T-Shirt");
            clothing.add("Leichte Jacke");
        } else {
            clothing.add("T-Shirt und kurze Hose");
            clothing.add("Sonnenhut und Sonnencreme");
            clothing.add("Viel Wasser");
        }

        if (w.getRainProbability() > 40) {
            clothing.add("Regenjacke");
        }
        if (w.getWindSpeed() > 30) {
            clothing.add("Windjacke");
        }
        return clothing;
    }
}
