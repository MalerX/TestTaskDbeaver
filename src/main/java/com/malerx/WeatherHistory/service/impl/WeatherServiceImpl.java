package com.malerx.WeatherHistory.service.impl;

import com.malerx.WeatherHistory.annotation.LocalWeatherServiceBean;
import com.malerx.WeatherHistory.annotation.RemoteWeatherServiceBean;
import com.malerx.WeatherHistory.dto.TodayWeatherDTO;
import com.malerx.WeatherHistory.models.TodayWeatherEntity;
import com.malerx.WeatherHistory.repositories.WeatherRepository;
import com.malerx.WeatherHistory.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Component
@LocalWeatherServiceBean
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private final WeatherRepository repository;
    @RemoteWeatherServiceBean
    private final WeatherService remoteService;

    @Override
    public TodayWeatherDTO getTodayWeather() {
        String todayDate = formatter.format(new Date());
        Optional<TodayWeatherEntity> todayWeather = repository.findById(todayDate);
        return todayWeather
                .map(todayWeatherEntity -> new TodayWeatherDTO(todayDate, todayWeatherEntity.getTemperature()))
                .orElseGet(this::getSaveAndReturnCurrentWeather);
    }

    private TodayWeatherDTO getSaveAndReturnCurrentWeather() {
        TodayWeatherDTO weatherDTO = remoteService.getTodayWeather();
        TodayWeatherEntity weatherEntity = new TodayWeatherEntity();
        weatherEntity.setToday(weatherDTO.date());
        weatherEntity.setTemperature(weatherDTO.temperature());
        repository.save(weatherEntity);
        return weatherDTO;
    }
}
