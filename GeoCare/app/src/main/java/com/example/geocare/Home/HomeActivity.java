package com.example.geocare.Home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HomeActivity extends AppCompatActivity implements LocationListener {
    // for UI
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

        fetch_UI();
        init_UI();

        if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        BottomSheetBehavior<View> bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.homeBottomSheet));
        bottomSheetBehavior.setPeekHeight(700);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        navBar();
        getLocation();
        getCurrentTime();
        //setData();
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

                // for bottomSheet components
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        humidityText.animate()
                                .alpha(1f).translationY(0);
                        pm25Text.animate()
                                .alpha(1f).translationY(0);
                        UVText.animate()
                                .alpha(1f).translationY(0);
                    }
                }, 2000);
            }
        }, 3000);
    }

    private void setData() {
        temperatureText.setText(String.valueOf(weatherInfo.getTemperature()));
        weatherDes.setText(weatherInfo.getDescription());
        UVText.setText(String.valueOf(weatherInfo.getUvi()));
        pm25Text.setText(String.valueOf(weatherInfo.getUvi()));
        humidityText.setText(String.valueOf(weatherInfo.getHumidity()));
    }

    private void getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd EEEE", Locale.US);
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.US);

        String currentDate = dateFormat.format(new Date());
        String currentTime = timeFormat.format(new Date());

        timeText.setText(currentTime.toLowerCase());
        dateText.setText(currentDate);
    }

    private void init_UI() {
        lottieAnimationView.animate().setDuration(3000);
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
    }

    private void fetch_UI() {
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
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, HomeActivity.this, null);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 5, HomeActivity.this);
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
            } else{
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
                                        setData();
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
                                        setData();
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
}
