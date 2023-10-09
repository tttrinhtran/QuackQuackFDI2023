package com.example.geocare.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geocare.Model.User;
import com.example.geocare.Product.ProductActivity;
import com.example.geocare.Profile.ProfileActivity;
import com.example.geocare.R;
import com.example.geocare.Scan.ScanActivity;
import com.example.geocare.Schedule.ScheduleActivity;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.imageview.ShapeableImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity implements LocationListener {
    // for UI
    // private User user;
    private CoordinatorLayout homeScreenLayout;
    private LottieAnimationView lottieAnimationView;
    private TextView title;
    private ShapeableImageView heart; // avatar
    private ConstraintLayout bottomSheet;
    private TextView city;
    private TextView district;
    private ImageView decorLine;
    private TextView temperatureText, temperatureText1;
    private TextView weatherDes;
    private TextView humidityText;
    private TextView pm25Text;
    private TextView UVText;
    private TextView timeText;
    private TextView dateText;
    private String districtIntent;
    private String cityIntent;
    private BottomSheetBehavior<View> bottomSheetBehavior;
    Intent i;
    JSONObject weatherObject;
    JSONObject airQualityObject;

    // For NavBar
    private ImageView homeIcon, producIcon, scanIcon, scheduleIcon, profileIcon;

    private LocationManager locationManager;
    private Weather weatherInfo;
    private boolean responseListener1Completed = false;
    private boolean secondRequestCompleted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Locale.setDefault(new Locale("en"));

        getData();
        fetch_UI();
        init_UI();


        navBar();
        getCurrentTime();
        setData();
        setUpWeatherLayout();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Make the TextView visible and animate it
                title.animate()
                        .alpha(1f).translationY(0);
                heart.animate()
                        .alpha(1f).translationY(0);
                temperatureText.animate()
                        .alpha(1f).translationY(0);
                temperatureText1.animate()
                        .alpha(1f).translationY(0);
                city.animate()
                        .alpha(1f).translationY(0);
                district.animate()
                        .alpha(1f).translationY(0);
                decorLine.animate()
                        .alpha(1f).translationY(0);
                weatherDes.animate()
                        .alpha(1f).translationY(0);
                timeText.animate()
                        .alpha(1f).translationY(0);
                dateText.animate()
                        .alpha(1f).translationY(0);
                bottomSheet.animate()
                        .alpha(1f).translationY(0);

                humidityText.animate()
                        .alpha(1f).translationY(0);
                pm25Text.animate()
                        .alpha(1f).translationY(0);
                UVText.animate()
                        .alpha(1f).translationY(0);
                lottieAnimationView.playAnimation();

            }
        }, 2000);
    }

    private void getData() {
        i = getIntent();
        weatherInfo = (Weather) i.getSerializableExtra("WEATHER_OBJECT");
        districtIntent = (String) i.getStringExtra("DISTRICT");
        cityIntent = (String) i.getStringExtra("CITY");
    }

    private void fetchDataInBackground() {
        if (weatherObject != null && airQualityObject != null) {
            setData();
            setUpWeatherLayout();
        }
    }


    private void setData() {
        temperatureText.setText(String.valueOf(weatherInfo.getTemperature()));
        weatherDes.setText(weatherInfo.getDescription());
        UVText.setText(String.valueOf(weatherInfo.getUvi()));
        pm25Text.setText(String.valueOf(weatherInfo.getPm25()));
        humidityText.setText(String.valueOf(weatherInfo.getHumidity()));

        if ("Quận 1".equals(districtIntent)) {
            district.setText("District 1");
        } else if ("Quận 5".equals(districtIntent)) {
            district.setText("District 5");
        } else if("Quận 10".equals(districtIntent)){
            district.setText("District 10");
        }
        else{
            district.setText(districtIntent);
        }
        if("Thành phố Hồ Chí Minh".equals(cityIntent)){
            city.setText("Ho Chi Minh City");
        } else{
            city.setText(cityIntent);
        }

    }

    private boolean getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.US);

        // Get the current time in hours and minutes
        String currentTime = timeFormat.format(new Date());
        String[] timeParts = currentTime.split(":");
        int currentHour = Integer.parseInt(timeParts[0]);
        int currentMinute = Integer.parseInt(timeParts[1].substring(0, 2)); // Remove "am" or "pm"

        // Check if the current time is between 6 AM and 6 PM
        boolean check = (currentHour >= 6 && currentHour < 18);

        // Update UI elements with the current time
        timeText.setText(currentTime.toLowerCase());

        // Construct the date and update the dateText if needed
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd EEEE", Locale.US);
        String currentDate = dateFormat.format(new Date());
        dateText.setText(currentDate);

        return check;
    }


    private void init_UI() {
        bottomSheetBehavior.setPeekHeight(750);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        lottieAnimationView.animate().setDuration(5000);
        title.setTranslationY(100);
        title.setAlpha(0);
        heart.setTranslationY(100); // deprecate
        temperatureText.setTranslationY(100);
        temperatureText.setAlpha(0);
        temperatureText1.setTranslationY(100);
        temperatureText1.setAlpha(0);
        humidityText.setTranslationY(100);
        humidityText.setAlpha(0);
        pm25Text.setTranslationY(100);
        pm25Text.setAlpha(0);
        UVText.setTranslationY(100);
        UVText.setAlpha(0);
        city.setTranslationY(100);
        city.setAlpha(0);
        district.setTranslationY(100);
        district.setAlpha(0);
        decorLine.setTranslationY(100); // deprecate
        weatherDes.setTranslationY(100);
        weatherDes.setAlpha(0);
        timeText.setTranslationY(100);
        timeText.setAlpha(0);
        dateText.setTranslationY(100);
        dateText.setAlpha(0);
        bottomSheet.setTranslationY(100);
        //setUpWeatherLayout();
    }

    private void fetch_UI() {
        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.homeBottomSheet));
        lottieAnimationView = findViewById(R.id.lottie);
        title = findViewById(R.id.HomeScreenTitleText);
        heart = findViewById(R.id.HomeScreenAvatarTitle);
        bottomSheet = findViewById(R.id.homeBottomSheet);
        temperatureText = findViewById(R.id.HomeScreenTemperature);
        temperatureText1 = findViewById(R.id.HomeScreenTemperature1);
        humidityText = findViewById(R.id.MoistureIndex);
        pm25Text = findViewById(R.id.DustIndex);
        UVText = findViewById(R.id.UVIndex);
        city = findViewById(R.id.HomeScreenCity);
        district = findViewById(R.id.HomeScreenDistrct);
        weatherDes = findViewById(R.id.HomeScreenWeatherDes);
        timeText = findViewById(R.id.HomeScreenTime);
        dateText = findViewById(R.id.HomeScreenDate);
        decorLine = findViewById(R.id.HomeScreenDecorLine);
        bottomSheet = findViewById(R.id.homeBottomSheet);

        // NavBar
        homeIcon = findViewById(R.id.NaviBarHomeIcon);
        producIcon = findViewById(R.id.NaviBarProductIcon);
        scanIcon = findViewById(R.id.NaviBarScanIcon);
        scheduleIcon = findViewById(R.id.NaviBarScheduleIcon);
        profileIcon = findViewById(R.id.NaviBarProfileIcon);

        // layout Weather
        homeScreenLayout = findViewById(R.id.HomeScreen);
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, HomeActivity.this, null);
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, HomeActivity.this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Locale englishLocale = new Locale("en", "US");
            Geocoder geocoder = new Geocoder(HomeActivity.this, englishLocale);

            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String address = addresses.get(0).getAddressLine(0);
            String d = addresses.get(0).getSubAdminArea();
            if ("Quận 1".equals(d)) {
                district.setText("District 1");
            } else if ("Quận 5".equals(d)) {
                district.setText("District 5");
            } else if("Quận 10".equals(d)){
                district.setText("District 10");
            }
            else{
                district.setText(d);
            }
            String c = addresses.get(0).getAdminArea();
            if("Thành phố Hồ Chí Minh".equals(c)){
                city.setText("Ho Chi Minh City");
            } else{
                city.setText(c);
            }
            // Now, retrieve the JSON data
            getWeatherData(location.getLatitude(), location.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getWeatherData(double latitude, double longitude) {
        String tempUrl1 = "https://api.openweathermap.org/data/3.0/onecall?lat=" + latitude + "&lon=" + longitude + "&exclude=current" + "&appid=cda3653189073bccea02deb614b1b762";
        String tempUrl2 = "https://api.openweathermap.org/data/2.5/air_pollution/forecast?lat=" + latitude + "&lon=" + longitude + "&appid=dcfabdd5c7fc896819d09133733b7eea";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());


        Response.Listener<String> responseListener1 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    weatherObject = new JSONObject(response);
                    responseListener1Completed = true;

//                    weatherInfo = new Weather(jsonResponse, airQualityJsonObject);

                    // Update UI with weather info
//                    humidityText.setText(String.valueOf(weatherInfo.getHumidity()) + "%");
//                    temperatureText.setText(String.valueOf(weatherInfo.getTemperature()));
//                    UVText.setText(String.valueOf(weatherInfo.getUvi()) + "/10");
//                    String del = Character.toUpperCase(weatherInfo.getDescription().charAt(0)) + weatherInfo.getDescription().substring(1);
//                    weatherDes.setText(del);
//                    pm25Text.setText(String.valueOf(weatherInfo.getPm25()));

                    Handler handler = new Handler(Looper.getMainLooper());
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {
                            while (!secondRequestCompleted){}
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        weatherInfo = new Weather(weatherObject, airQualityObject);
//                                        setData();
//                                        setUpWeatherLayout();
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    airQualityObject = new JSONObject(response);
                    secondRequestCompleted = true;

                    Handler handler = new Handler(Looper.getMainLooper());
                    ExecutorService executorService = Executors.newSingleThreadExecutor();

                    executorService.execute(new Runnable() {
                        @Override
                        public void run() {

                            while (!responseListener1Completed){}
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        weatherInfo = new Weather(weatherObject, airQualityObject);
//                                        setData();
//                                        setUpWeatherLayout();
                                    } catch (JSONException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            });
                        }
                    });
                }catch (JSONException e ){
                    e.printStackTrace();
                }
            }
        };

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
        // ... Rest of your code
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

    private void navBar() {
        homeIcon.setImageResource(R.drawable.home_icon_filled);
        producIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ProductActivity.class);
                startActivity(i);
            }
        });

        scanIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ScanActivity.class);
                startActivity(i);
            }
        });

        scheduleIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ScheduleActivity.class);
                startActivity(i);
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });
    }

    private int chooseLayout() {
        int res = 0, checkWeather = 0;
        boolean checkDay = getCurrentTime();
        String des = weatherInfo.getDescription();

        if (des.contains("rain")) {
            checkWeather = 1;
        } else if (des.contains("cloud")) {
            checkWeather = 2;
        } else {
            checkWeather = 3;
        }

        if(checkWeather != 3){
            res = checkWeather + 2;
        } else{
            if(checkDay == true){ // day
                res = 1;
            } else{
                res = 2;
            }
        }
        return res;
    }

    private void setUpWeatherLayout(){
        //int pick = chooseLayout();
        int pick = 4;

        List<Integer> colors = new ArrayList<>();

        // Add elements to the list
        colors.add(R.color.light_yellow);
        colors.add(R.color.blue_deep);
        colors.add(R.color.blue_rain);
        colors.add(R.color.blue_snow);

        List<Integer> colorText = new ArrayList<>();

        // Add elements to the list
        colorText.add(R.color.blue_deep);
        colorText.add(R.color.blue_baby);
        colorText.add(R.color.white);
        colorText.add(R.color.white);

        homeScreenLayout.setBackgroundColor(ContextCompat.getColor(this, colors.get(pick - 1)));

        // text
        title.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        temperatureText.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        temperatureText1.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        city.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        district.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        weatherDes.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        timeText.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        dateText.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        decorLine.setBackgroundColor(ContextCompat.getColor(this,colorText.get(pick-1)));

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) lottieAnimationView.getLayoutParams();

        lottieAnimationView.setAnimation(R.raw.sunny_json);
        params.setMarginStart(300);
        params.topMargin = 0;

        if (pick == 1) {
            lottieAnimationView.setAnimation(R.raw.sunny_json);
            params.setMarginStart(850);
            params.topMargin = 0;
        } else if (pick == 2) {
            lottieAnimationView.setAnimation(R.raw.night_json);
            params.setMarginStart(600);
            params.topMargin = 50;
        } else if (pick == 3) {
            lottieAnimationView.setAnimation(R.raw.rain_json);
            params.setMarginStart(450);
            params.topMargin = 170;
        } else if (pick == 4) {
            lottieAnimationView.setAnimation(R.raw.cloud_json);
            params.setMarginStart(750);
            params.topMargin = 115;
        }

// Apply the modified layout parameters
        lottieAnimationView.setLayoutParams(params);



//        if(pick == 3){
//            homeScreenLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.light_yellow));
//        }
    }

}
