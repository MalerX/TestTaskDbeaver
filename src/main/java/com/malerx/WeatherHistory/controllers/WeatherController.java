package com.malerx.WeatherHistory.controllers;

import com.malerx.WeatherHistory.repositories.WeatherRepositories;
import com.malerx.WeatherHistory.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;
}
