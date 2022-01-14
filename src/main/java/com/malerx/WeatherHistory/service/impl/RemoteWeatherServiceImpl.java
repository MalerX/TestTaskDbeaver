package com.malerx.WeatherHistory.service.impl;

import com.malerx.WeatherHistory.service.RemoteWeatherService;
import org.springframework.stereotype.Component;

@Component
public class RemoteWeatherServiceImpl implements RemoteWeatherService {
    @Override
    public int getTodayWeather() {
        return 0;
    }
}
