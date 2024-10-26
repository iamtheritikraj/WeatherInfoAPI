package com.weather.info.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.weather.info.models.GeocodingResponse;
import com.weather.info.models.Pincode;
import com.weather.info.models.WeatherApiResponse;
import com.weather.info.models.WeatherInfo;
import com.weather.info.repos.PincodeRepository;
import com.weather.info.repos.WeatherInfoRepository;

@Service
public class WeatherService {
    private final PincodeRepository pincodeRepository;
    private final WeatherInfoRepository weatherInfoRepository;
    private final RestTemplate restTemplate;

    @Value("${openweather.api.key}")
    private String apiKey;

    public WeatherService(PincodeRepository pincodeRepository, WeatherInfoRepository weatherInfoRepository, RestTemplate restTemplate) {
        this.pincodeRepository = pincodeRepository;
        this.weatherInfoRepository = weatherInfoRepository;
        this.restTemplate = restTemplate;
    }

    public WeatherInfo getWeatherInfo(String pincode, LocalDate date) {
        Optional<Pincode> pincodeOptional = pincodeRepository.findByPincode(pincode);
        Pincode pincodeEntity = pincodeOptional.orElseGet(() -> fetchAndSavePincodeData(pincode));

        return weatherInfoRepository.findByPincodeAndDate(pincodeEntity, date)
            .orElseGet(() -> fetchAndSaveWeatherData(pincodeEntity, date));
    }

    private Pincode fetchAndSavePincodeData(String pincode) {
        String geocodingUrl = String.format("https://api.openweathermap.org/geo/1.0/zip?zip=%s,IN&appid=%s", pincode, apiKey);
        GeocodingResponse response = restTemplate.getForObject(geocodingUrl, GeocodingResponse.class);

        Pincode newPincode = new Pincode();
        newPincode.setPincode(pincode);
        newPincode.setLatitude(response.getLat());
        newPincode.setLongitude(response.getLon());

        return pincodeRepository.save(newPincode);
    }

    private WeatherInfo fetchAndSaveWeatherData(Pincode pincode, LocalDate date) {
        String weatherUrl = String.format("https://api.openweathermap.org/data/2.5/weather?lat=%s&lon=%s&appid=%s", 
            pincode.getLatitude(), pincode.getLongitude(), apiKey);

        WeatherApiResponse response = restTemplate.getForObject(weatherUrl, WeatherApiResponse.class);

        WeatherInfo weatherInfo = new WeatherInfo();
        weatherInfo.setPincode(pincode);
        weatherInfo.setDate(date);
        weatherInfo.setTemperature(response.getMain().getTemp());
        weatherInfo.setHumidity(response.getMain().getHumidity());
        weatherInfo.setPressure(response.getMain().getPressure());
        weatherInfo.setWeatherDescription(response.getWeather().get(0).getDescription());

        return weatherInfoRepository.save(weatherInfo);
    }
}
