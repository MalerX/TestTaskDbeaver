package com.malerx.WeatherHistory.controllers;

import com.malerx.WeatherHistory.dto.TodayWeatherDTO;
import com.malerx.WeatherHistory.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public TodayWeatherDTO getTodayWeather() {
        return weatherService.getTodayWeather();
    }
}
