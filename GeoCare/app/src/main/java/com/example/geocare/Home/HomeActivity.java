package com.example.geocare.Home;

import static android.view.WindowManager.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geocare.Product.Item;
import com.example.geocare.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity implements LocationListener{
    LottieAnimationView lottieAnimationView;
    TextView title;
    ShapeableImageView heart;

    TextView temperatureText;
    TextView humidityText;
    TextView pm25Text;
    private View bottomSheet;
    LocationManager locationManager;
    DecimalFormat df = new DecimalFormat("#.##");
    private boolean responseListener1Completed = false;
    private boolean secondRequestCompleted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//
        fetch_UI();
        init_UI();

//
//
//

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Make the TextView visible and animate it
                title.animate()
                        .alpha(1f).translationY(0);
                heart.animate()
                        .alpha(1f).translationY(0);

            }
        }, 3000);

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.homeBottomSheet));
        bottomSheetBehavior.setPeekHeight(500);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        getLocation();

    }

    private void init_UI() {
        lottieAnimationView.animate().setDuration(3000);
        title.setTranslationY(100); title.setAlpha(0);
        heart.setTranslationY(100); // deprecate
    }

    private void fetch_UI() {
        lottieAnimationView = findViewById(R.id.lottie);
        title = findViewById(R.id.HomeScreenTitleText);
        heart = findViewById(R.id.HomeScreenAvatarTitle);
        bottomSheet = findViewById(R.id.homeBottomSheet);
        temperatureText = findViewById(R.id.HomeScreenTemperature);
        humidityText = findViewById(R.id.MoistureIndex);
        pm25Text = findViewById(R.id.DustIndex);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, HomeActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, ""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(HomeActivity.this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
            String address = addresses.get(0).getAddressLine(0);

            //textViewLocation.setText(address);
            getWeatherDetails(location.getLatitude(), location.getLongitude());

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getWeatherDetails(double latitude, double longitude) {

        String tempUrl1 = "https://api.openweathermap.org/data/3.0/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=current" +  "&appid=cda3653189073bccea02deb614b1b762";
      //  https://api.openweathermap.org/data/3.0/onecall?lat=33.44&lon=-94.04&exclude=current&appid=cda3653189073bccea02deb614b1b762
        String tempUrl2 = "https://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=dcfabdd5c7fc896819d09133733b7eea";


//            String tempUrl1 = "https://api.openweathermap.org/data/2.5/weather?lat=10.059304617638727&lon=105.7555741960867&appid=dcfabdd5c7fc896819d09133733b7eea";
//            String tempUrl2 = "https://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=10.059304617638727&lon=105.7555741960867&appid=dcfabdd5c7fc896819d09133733b7eea";

        // Create a RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        StringBuilder combinedResult = new StringBuilder();

        // Define a method to handle the response from the first request
        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // ... (your JSON parsing code for the first request)
//                    JSONObject jsonResponse = new JSONObject(response);
//                    JSONArray jsonArray = jsonResponse.getJSONArray("weather");
//                    JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
//                    String description = jsonObjectWeather.getString("description");
//                    JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
//                    double temp = jsonObjectMain.getDouble("temp") - 273.15;
//                    double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
//                    float pressure = jsonObjectMain.getInt("pressure");
//                    int humidity = jsonObjectMain.getInt("humidity");
//                    JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
//                    String wind = jsonObjectWind.getString("speed");
//                    JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
//                    String clouds = jsonObjectClouds.getString("all");
//                    JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
//                    String countryName = jsonObjectSys.getString("country");
//                    String cityName = jsonResponse.getString("name");

                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("hourly");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    // UV, Humidity, Temperature
                    double uvi = jsonObject.getDouble("uvi");
                    double humidity = jsonObject.getDouble("humidity");
                    double temp = jsonObject.getDouble("temp") - 273.15;
                    // Description
                    JSONArray jsonArray1 = jsonObject.getJSONArray("weather");
                    JSONObject jsonObject1 = jsonArray1.getJSONObject(0);
                    String description = jsonObject1.getString("description");



                    // Append the result of the first request to the combinedResult
//                    combinedResult.append("Current weather of ").append(cityName).append(" (").append(countryName).append(")")
//                            .append("\n Temp: ").append(df.format(temp)).append(" °C")
//                            .append("\n Feels Like: ").append(df.format(feelsLike)).append(" °C")
//                            .append("\n Humidity: ").append(humidity).append("%")
//                            .append("\n Description: ").append(description)
//                            .append("\n Wind Speed: ").append(wind).append("m/s (meters per second)")
//                            .append("\n Cloudiness: ").append(clouds).append("%")
//                            .append("\n Pressure: ").append(pressure).append(" hPa")
//                            .append("\n\n");

                    responseListener1Completed = true;

                    // Check if both requests are completed
                    Handler handler = new Handler(Looper.getMainLooper());
                    ExecutorService executorService = Executors.newSingleThreadExecutor();

                    executorService.execute(new Runnable() {

                        @Override
                        public void run() {

                            while (!secondRequestCompleted){}

                            handler.post(new Runnable() {

                                @Override
                                public void run() {
                                    humidityText.setText(String.valueOf(humidity));
                                    temperatureText.setText(String.valueOf(temp));
                                }
                            });
                        }
                    });
/*                    if (secondRequestCompleted) {
                        // Set the text of tvResult with the combined result
                        //weatherInfo.setText(combinedResult.toString());
                        humidityText.setText(String.valueOf(humidity));
                        temperatureText.setText(String.valueOf(temp));

                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        // Define a method to handle the response from the second request
        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray jsonArray = jsonResponse.getJSONArray("list");
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("components");
                    double pm25 = jsonObject1.getDouble("pm2_5");
                    double pm10 = jsonObject1.getDouble("pm10");

//                    combinedResult.append("Additional weather info: ")
//                            .append("\n PM2_5: ").append(df.format(pm25))
//                            .append("\n PM10: ").append(df.format(pm10));



                    // Mark the second request as completed
                    secondRequestCompleted = true;

                    // Check if both requests are completed
                    if (responseListener1Completed) {
                        // Set the text of tvResult with the combined result
                        //weatherInfo.setText(combinedResult.toString());
                        pm25Text.setText(String.valueOf(pm25));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };



        // Create the second request and add it to the request queue


        // Create the first request and add it to the request queue
        StringRequest stringRequest1 = new StringRequest(Request.Method.GET, tempUrl1, responseListener1, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors for the first request
            }
        });
        requestQueue.add(stringRequest1);

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, tempUrl2, responseListener2, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle errors for the second request
            }
        });
        requestQueue.add(stringRequest2);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

}

