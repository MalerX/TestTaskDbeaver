package com.malerx.WeatherHistory.repositories;

import com.malerx.WeatherHistory.models.TodayWeatherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface WeatherRepositories extends JpaRepository<TodayWeatherEntity, Date> {
}
