package com.malerx.WeatherHistory.controllers;

import com.malerx.WeatherHistory.annotation.LocalWeatherServiceBean;
import com.malerx.WeatherHistory.dto.TodayWeatherDTO;
import com.malerx.WeatherHistory.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {
    private final WeatherService weatherService;

    @Autowired
    public WeatherController(@LocalWeatherServiceBean WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public TodayWeatherDTO getTodayWeather() {
        return weatherService.getTodayWeather();
    }
}
