package ru.kpfu.itis.snetkov.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenWeatherClient {
    private final String apiKey = System.getenv("API_KEY_WEATHER");

    private HttpURLConnection getConnection(String url) throws IOException {
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return (HttpURLConnection) url1.openConnection();
    }

    public String getResponse(String city) throws IOException {
        String url = "https://api.openweathermap.org/data/2.5/weather";
        String newUrl = url + "?q="+city+"&appid=" + apiKey + "&units=metric&lang=ru";
        HttpURLConnection connection = getConnection(newUrl);
        connection.setRequestMethod("GET");

        return readResponse(connection);
    }

    private static String readResponse(HttpURLConnection connection) {
        StringBuilder content = new StringBuilder();
        String input;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            while ((input = reader.readLine()) != null) {
                content.append(input);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content.toString();
    }
}
