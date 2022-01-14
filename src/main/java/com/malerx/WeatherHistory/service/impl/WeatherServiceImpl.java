package com.malerx.WeatherHistory.service.impl;

import com.malerx.WeatherHistory.dto.TodayWeatherDTO;
import com.malerx.WeatherHistory.models.TodayWeatherEntity;
import com.malerx.WeatherHistory.repositories.WeatherRepository;
import com.malerx.WeatherHistory.service.RemoteWeatherService;
import com.malerx.WeatherHistory.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private final WeatherRepository repository;
    private final RemoteWeatherService weatherService;

    @Override
    public TodayWeatherDTO getTodayWeather() {
        String todayDate = formatter.format(new Date());
        Optional<TodayWeatherEntity> todayWeather = repository.findById(todayDate);
        return todayWeather
                .map(todayWeatherEntity -> new TodayWeatherDTO(todayDate, todayWeatherEntity.getTemperature()))
                .orElseGet(() -> saveAndReturnCurrentWeather(todayDate, weatherService.getTodayWeather()));
    }

    private TodayWeatherDTO saveAndReturnCurrentWeather(String todayDate, int temperature) {
        TodayWeatherEntity weatherEntity = new TodayWeatherEntity();
        weatherEntity.setToday(todayDate);
        weatherEntity.setTemperature(temperature);
        repository.save(weatherEntity);
        return new TodayWeatherDTO(todayDate, temperature);
    }
}
