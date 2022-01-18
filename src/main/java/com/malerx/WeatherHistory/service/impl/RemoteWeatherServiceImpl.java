package com.malerx.WeatherHistory.service.impl;

import com.malerx.WeatherHistory.annotation.RemoteWeatherServiceBean;
import com.malerx.WeatherHistory.dto.TodayWeatherDTO;
import com.malerx.WeatherHistory.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RemoteWeatherServiceBean
@Slf4j
public class RemoteWeatherServiceImpl implements WeatherService {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    private static final int TIMEOUT = 3000;
    private static final String URL = "https://yandex.ru/";

    @Override
    public TodayWeatherDTO getTodayWeather() {
        HttpURLConnection connection;
        try {
            connection = getConnection();
            if (connection != null) {
                String content = getContent(connection);
                int temperature = parseTemperature(content);
                return new TodayWeatherDTO(formatter.format(new Date()), temperature);
            }
        } catch (IOException e) {
            log.error("ERR in getTodayWeather: ", e);
        }
        log.warn("Fail received weather.");
        throw new RuntimeException("Fail received weather from " + URL);
    }

    private int parseTemperature(String content) {
        Matcher matcher = Pattern.compile("(−?\\d{1,2}°)").matcher(content);
        String result = "0";
        while (matcher.find()) {
            String rawTemp;
            if (matcher.group().contains("−")) {
                rawTemp = matcher.group().replace("−", "-");
            } else {
                rawTemp = matcher.group();
            }
            result = rawTemp.substring(0, rawTemp.length() - 1);
        }
        return Integer.parseInt(result);
    }

    private String getContent(HttpURLConnection connection) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("weather__temp")) {
                    sb.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            log.warn("ERR in getContent: ", e);
        }
        return sb.toString();
    }

    private HttpURLConnection getConnection() throws IOException {
        URL url = new URL(URL);
        HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-length", "0");
        connection.setUseCaches(false);
        connection.setAllowUserInteraction(false);
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);
        connection.connect();

        if (connection.getResponseCode() == 200) {
            return connection;
        }
        return null;
    }
}
