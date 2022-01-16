package com.malerx.WeatherHistory.service.impl;

import com.malerx.WeatherHistory.service.RemoteWeatherService;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RemoteWeatherServiceImpl implements RemoteWeatherService {
    private static final int TIMEOUT = 3000;
    private static final String URL = "https://yandex.ru/";

    @Override
    public int getTodayWeather() {
        HttpURLConnection connection = null;
        try {
            connection = getConnection();
            if (connection != null) {
                String content = getContent(connection);
                return parseTemperature(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int parseTemperature(String content) {
        Matcher matcher = Pattern.compile("(−?\\d{1,2}°)").matcher(content);
        String result = "0";
        while (matcher.find()) {
            String rawTemp = matcher.group();
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
            e.printStackTrace();
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
