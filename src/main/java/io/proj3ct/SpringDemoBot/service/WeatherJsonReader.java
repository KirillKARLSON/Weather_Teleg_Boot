package io.proj3ct.SpringDemoBot.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;

public class WeatherJsonReader {
    public JsonObject getJson(String linkWithCity) throws IOException {
        URL jsonURL = new URL(linkWithCity);
        URLConnection request = jsonURL.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject objJson = root.getAsJsonObject();

        return objJson;

    }

    public HashMap<String, String> getTemprFromJson(JsonObject objJson){

        HashMap<String,String> weatherMap= new HashMap<>();

        String temprature = objJson.getAsJsonObject("main")
                .get("temp").toString();
        String pressure = objJson.getAsJsonObject("main")
                .get("pressure").toString();
        String humidity = objJson.getAsJsonObject("main")
                .get("humidity").toString();

        weatherMap.put("Температура воздуха ", temprature);
        weatherMap.put("Давление", pressure);
        weatherMap.put("Влажность", humidity);


        return weatherMap;
    }
}
