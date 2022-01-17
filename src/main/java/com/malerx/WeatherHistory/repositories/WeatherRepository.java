package com.malerx.WeatherHistory.repositories;

import com.malerx.WeatherHistory.models.TodayWeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRepository extends JpaRepository<TodayWeatherEntity, String> {
}
