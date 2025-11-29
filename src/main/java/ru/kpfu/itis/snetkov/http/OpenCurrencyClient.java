package ru.kpfu.itis.snetkov.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OpenCurrencyClient {
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
        String url = "https://open.er-api.com/v6/latest/RUB";
        HttpURLConnection connection = getConnection(url);
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
