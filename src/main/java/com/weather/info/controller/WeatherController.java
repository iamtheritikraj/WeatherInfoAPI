package com.weather.info.controller;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.info.Service.WeatherService;
import com.weather.info.models.WeatherInfo;
import com.weather.info.models.WeatherRequest; 

@RestController
@RequestMapping("/api")
public class WeatherController {
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @PostMapping("/weather")
    public ResponseEntity<WeatherInfo> getWeather(@RequestBody WeatherRequest request) {
        LocalDate date = LocalDate.parse(request.getForDate());
        WeatherInfo weatherInfo = weatherService.getWeatherInfo(request.getPincode(), date);
        return ResponseEntity.ok(weatherInfo);
    }
}
