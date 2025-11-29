package ru.kpfu.itis.snetkov.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.kpfu.itis.snetkov.http.OpenCurrencyClient;
import ru.kpfu.itis.snetkov.http.OpenWeatherClient;
import ru.kpfu.itis.snetkov.model.ComandList;

import java.io.IOException;
import java.util.Arrays;


public class CommandsService {
    private ComandList comand;
    private static final OpenWeatherClient clientWeather = new OpenWeatherClient();
    private static final OpenCurrencyClient clientCurrency = new OpenCurrencyClient();
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String list() {
        return "The commands format : \n" + "{comand_name} {param} (пробел)\n" + "commands in every case : " + Arrays.toString(ComandList.values());
    }

    public static String weather(String city) throws IOException {
        return "Погода в " + city + " " + parseWeather(clientWeather.getResponse(city));
    }

    public static String parseWeather(String jsonString) {
        try {
            JsonNode root = objectMapper.readTree(jsonString);


            String description = root.path("weather")
                    .get(0)
                    .path("description")
                    .asText();

            double temperature = root.path("main")
                    .path("temp")
                    .asDouble();

            double feelsLike = root.path("main")
                    .path("feels_like")
                    .asDouble();

            return String.format("%s, температура: %.1f°C, ощущается как: %.1f°C",
                    description, temperature, feelsLike);

        } catch (Exception e) {
            return "Ошибка при парсинге данных о погоде";
        }
    }

    public static String exchange(String currency) throws IOException {
        String ans = parseExchangeRate(clientCurrency.getResponse(currency.toUpperCase()), currency.toUpperCase());
        return ans;
    }

    public static String parseExchangeRate(String jsonString, String targetCurrency) {
        try {
            JsonNode root = objectMapper.readTree(jsonString);

            String result = root.path("result").asText();
            if (!"success".equals(result)) {
                return "Ошибка при получении курсов валют";
            }

            JsonNode rates = root.path("rates");

            if (rates.has(targetCurrency)) {
                double rate = rates.path(targetCurrency).asDouble();

                return String.format("Курс %s к RUB: %.4f ₽",
                        targetCurrency, 1.0 / rate);
            } else {
                return "Валюта '" + targetCurrency + "' не найдена в списке курсов";
            }

        } catch (Exception e) {
            return "Ошибка при парсинге данных о курсе валют";
        }
    }
}
