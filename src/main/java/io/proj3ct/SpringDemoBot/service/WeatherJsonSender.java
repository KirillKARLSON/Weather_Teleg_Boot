package io.proj3ct.SpringDemoBot.service;

import org.springframework.beans.factory.annotation.Value;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WeatherJsonSender {

    public String cityInput(String city) throws IOException {
        WeatherJsonReader jsonReader = new WeatherJsonReader();
        return jsonReader.getTemprFromJson(jsonReader.getJson(city)).toString();
    }
}
