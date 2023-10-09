package com.example.geocare.Home;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Weather implements Serializable {
    private double humidity;
    private int temperature;
    private double uvi;
    private String description;
    private double pm25;

    public Weather(JSONObject weatherJsonObject, JSONObject airQualityJsonObject) throws JSONException {
        // Parse weather data
        JSONArray jsonArray = weatherJsonObject.getJSONArray("hourly");
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        this.uvi = jsonObject.getDouble("uvi");
        this.humidity = jsonObject.getDouble("humidity");
        this.temperature = (int)(jsonObject.getDouble("temp") - 273.15);

        JSONArray jsonArray1 = jsonObject.getJSONArray("weather");
        JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
        this.description = jsonObject1.getString("description");


//        JSONArray weatherArray = weatherJsonObject.getJSONArray("weather");
//        JSONObject weather = weatherArray.getJSONObject(0);
//        this.description = weather.getString("description");
//
//        JSONObject main = weatherJsonObject.getJSONObject("main");
//        this.temperature = (int) (main.getDouble("temp") - 273.15);
//        this.humidity = main.getDouble("humidity");
//
//        JSONObject hourly = weatherJsonObject.getJSONArray("hourly").getJSONObject(0);
//        this.uvi = hourly.getDouble("uvi");

        // Parse air quality data

        JSONArray jsonArray2 = airQualityJsonObject.getJSONArray("list");
        JSONObject jsonObject2 = jsonArray2.getJSONObject(0);
        JSONObject jsonObject3 = jsonObject2.getJSONObject("components");
        this.pm25 = jsonObject3.getDouble("pm2_5");

//        JSONObject components = airQualityJsonObject.getJSONArray("list").getJSONObject(0).getJSONObject("components");
//        this.pm25 = components.getDouble("pm2_5");
    }

    public double getHumidity() {
        return humidity;
    }

    public int getTemperature() {
        return temperature;
    }

    public double getUvi() {
        return uvi;
    }

    public String getDescription() {
        return description;
    }

    public double getPm25() {
        return pm25;
    }
}
