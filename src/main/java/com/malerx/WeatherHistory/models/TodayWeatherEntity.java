package com.malerx.WeatherHistory.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "history")
@Getter
@Setter
public class TodayWeatherEntity {
    @Id
    private String today;
    private Integer temperature;
}
