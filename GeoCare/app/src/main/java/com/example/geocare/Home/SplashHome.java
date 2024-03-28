package com.example.geocare.Home;

import static com.example.geocare.Constants.KEY_COLLECTION_USERS;
import static com.example.geocare.Constants.KEY_COLLECTION_WEATHER;

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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.geocare.Constants;
import com.example.geocare.Database.FirebaseDatabaseController;
import com.example.geocare.Database.SharedPreferenceManager;
import com.example.geocare.Model.User;
import com.example.geocare.R;
import com.example.geocare.Schedule.ProductItem;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashHome extends AppCompatActivity implements LocationListener {
    private ConstraintLayout splashScreenLayout;
    private LottieAnimationView lottieAnimationView;
    private View supportView;
    private LocationManager locationManager;
    private TextView title;
    private TextView title1;
    private TextView title2;
    private TextView description;
    private TextView temperature; private  TextView temperature1;
    private TextView district;
    private TextView city;
    private Weather weatherInfo;
    JSONObject weatherObject;
    JSONObject airQualityObject;
    private boolean responseListener1Completed = false;
    private boolean secondRequestCompleted = false;
    private String d;
    private String c;
    private int pick;

    private User user;
    ArrayList<ProductItem> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_home);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        Locale.setDefault(new Locale("en"));
        getUser();
        setUpproductList();

        if (ContextCompat.checkSelfPermission(SplashHome.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashHome.this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 100);
        }

        getLocation();
        fetch_UI();
        init_UI();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                title.animate()
                        .alpha(1f).translationX(0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        lottieAnimationView.playAnimation();
                    }
                }, 800);


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        title1.animate()
                                .alpha(1f).translationX(0);
                        title2.animate()
                                .alpha(1f).translationX(0);
                        description.animate()
                                .alpha(1f).translationX(0);
                        district.animate()
                                .alpha(1f).translationX(0);
                        city.animate()
                                .alpha(1f).translationX(0);
                        temperature.animate()
                                .alpha(1f).translationX(0);
                        temperature1.animate()
                                .alpha(1f).translationX(0);
                    }
                }, 2500);

            }
        }, 1200);
//        updateUI();

        supportView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SplashHome.this, SplashHome2.class);
                saveWeather();
                i.putExtra("CITY", c);
                i.putExtra("DISTRICT", d);
                i.putExtra("PICK", pick);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        saveUser();
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, SplashHome.this, null);
            locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, SplashHome.this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onLocationChanged(Location location) {
       // Toast.makeText(this, "" + location.getLatitude() + "," + location.getLongitude(), Toast.LENGTH_SHORT).show();
        try {
            Locale englishLocale = new Locale("en", "US");
            Geocoder geocoder = new Geocoder(SplashHome.this, englishLocale);

            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            String address = addresses.get(0).getAddressLine(0);
            d = addresses.get(0).getSubAdminArea();
            if ("Quận 1".equals(d)) {
                district.setText("in District 1");
            } else if ("Quận 5".equals(d)) {
                district.setText("in District 5");
            } else if("Quận 10".equals(d)){
                district.setText("in District 10");
            }
            else{
                district.setText(d);
            }
            c = addresses.get(0).getAdminArea();
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
                                        updateUI();
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
                                        updateUI();
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

    private void init_UI() {
        lottieAnimationView.animate().setDuration(5000);
        title.setTranslationX(-100); title.setAlpha(0);
        title1.setTranslationX(-100); title1.setAlpha(0);
        title2.setTranslationX(-100); title2.setAlpha(0);
        description.setTranslationX(-100); description.setAlpha(0);
        district.setTranslationX(-100); district.setAlpha(0);
        city.setTranslationX(-100); city.setAlpha(0);
        temperature.setTranslationX(-100); temperature.setAlpha(0);
        temperature1.setTranslationX(-100); temperature1.setAlpha(0);
        title.setText("Hello "+user.getUserName());
    }

    private void fetch_UI() {
        splashScreenLayout = findViewById(R.id.HomeSplashScreen);
        lottieAnimationView = findViewById(R.id.lottieSplash);
        supportView = findViewById(R.id.SplashSupport);
        title = findViewById(R.id.SplashTitle);
        title1 = findViewById(R.id.SplashLocation1);
        title2 = findViewById(R.id.SplashTitle2);
        description = findViewById(R.id.SplashDescription);
        district = findViewById(R.id.SpashDistrict);
        city = findViewById(R.id.SplashCity);
        temperature = findViewById(R.id.SplashTemperature);
        temperature1 = findViewById(R.id.SplashTemperature1);
    }

    private boolean getCurrentTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mma", Locale.US);

        // Get the current time in hours and minutes
        String currentTime = timeFormat.format(new Date());
        String[] timeParts = currentTime.split(":");
        int currentHour = Integer.parseInt(timeParts[0]);

        // Check if the current time is between 6 AM and 6 PM
        boolean check = (currentHour >= 6 && currentHour < 18);
        return check;
    }

    private void updateUI() {
        if (weatherObject != null && airQualityObject != null) {
            setData();
            setUpWeatherLayout();
        }
    }

    private void setData() {
        temperature.setText(String.valueOf(weatherInfo.getTemperature()));
        description.setText(weatherInfo.getDescription());
    }


    private int chooseLayout() {
        int res = 0;
        String des = weatherInfo.getDescription();
        if(des.contains("rain")){
            res = 1;
            return res;
        }

        boolean checkDay = getCurrentTime();
        if(checkDay == false){ // night
            res = 2;
            return res;
        }

        double cloud = weatherInfo.getCloud();
        double uv = weatherInfo.getUvi();
        int temp = weatherInfo.getTemperature();

        double weight = (temp*2 + uv + 100-cloud)/4;
        if(weight >= 32){
            res = 3;
        }
        else{
            res = 4;
        }
        return res;
    }

    private void setUpWeatherLayout(){
         pick = chooseLayout();
        // int pick = 1;

        List<Integer> colors = new ArrayList<>();

        // Add elements to the list
        colors.add(R.color.blue_rain);
        colors.add(R.color.blue_deep);
        colors.add(R.color.light_yellow);
        colors.add(R.color.blue_snow);

        List<Integer> colorText = new ArrayList<>();

        // Add elements to the list
        colorText.add(R.color.white);
        colorText.add(R.color.blue_baby);
        colorText.add(R.color.blue_deep);
        colorText.add(R.color.blue_deep);

        splashScreenLayout.setBackgroundColor(ContextCompat.getColor(this, colors.get(pick - 1)));

        // text
        title.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        title1.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        title2.setTextColor(ContextCompat.getColor(this, colorText.get(pick-1)));
        temperature.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        temperature1.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        city.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        district.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));
        description.setTextColor(ContextCompat.getColor(this,colorText.get(pick-1)));

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) lottieAnimationView.getLayoutParams();

        if (pick == 3) {
            lottieAnimationView.setAnimation(R.raw.sunny_json);
            params.setMarginStart(850);
            params.topMargin = 300;
        } else if (pick == 2) {
            lottieAnimationView.setAnimation(R.raw.night_json);
            params.setMarginStart(600);
            params.topMargin = 350;
        } else if (pick == 1) {
            lottieAnimationView.setAnimation(R.raw.rain_json);
            params.setMarginStart(450);
            params.topMargin = 500;
        } else if (pick == 4) {
            lottieAnimationView.setAnimation(R.raw.cloud_json);
            params.setMarginStart(750);
            params.topMargin = 350;
        }

        lottieAnimationView.setLayoutParams(params);

    }
    void getUser()
    {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager(User.class,this);
        user= (User) sharedPreferenceManager.retrieveSerializableObjectFromSharedPreference(KEY_COLLECTION_USERS);

        productList = user.getUserProductList();
    }
    void saveWeather()
    {
        SharedPreferenceManager sharedPreferenceManagerWeather=new SharedPreferenceManager(Weather.class,this);
        sharedPreferenceManagerWeather.storeSerializableObjectToSharedPreference(weatherInfo,KEY_COLLECTION_WEATHER);
    }

    private void setUpproductList() {

        // Get the current date
        LocalDate currentDate = LocalDate.now();

        // Convert LocalDate to a string using a specific format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = currentDate.format(formatter);



        FirebaseDatabaseController<ProductItem> instance = new FirebaseDatabaseController<>(ProductItem.class);

        LocalTime currentTime = LocalTime.now();
        int currentHour = currentTime.getHour();
        String day = "Sun";

        if (currentHour <= 17 && currentHour >= 3) day = "Sun";
        else day = "Night";


        // Micellar
        String micellarID = "Micellar-";
        if(Objects.equals(user.getUserSkinType(), "Oily")) micellarID += "Oil";
        else micellarID += user.getUserSkinType();
        micellarID += "-" + day ;

        String micellarImageString = "micellar_";
        if(Objects.equals(user.getUserSkinType(), "Oily")) micellarImageString += "oil";
        else micellarImageString += user.getUserSkinType().toLowerCase();

        ProductItem micellar = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, micellarID );
        if( micellar != null ) micellar.setImageIDResource(getResources().getIdentifier(micellarImageString, "drawable", getPackageName()));

        if( micellar != null ) productList.add(micellar);

        // Cleanser
        String cleanserID = "Cleaser-";
        if(Objects.equals(user.getUserSkinType(), "Oily")) cleanserID += "Oil";
        else cleanserID += user.getUserSkinType();
        cleanserID += "-" + day ;

        Log.d("Hoktro", "setUpproductList: " + cleanserID );

        String cleanserImageString = "cleanser_";
        if(Objects.equals(user.getUserSkinType(), "Oily")) cleanserImageString += "oil";
        else cleanserImageString += user.getUserSkinType().toLowerCase();

        ProductItem cleanser = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, cleanserID );
        if( cleanser != null ) cleanser.setImageIDResource(getResources().getIdentifier(cleanserImageString, "drawable", getPackageName()));

        if( cleanser != null ) productList.add(cleanser);

        // Toner
        String tonerID = "Toner-";
        if(Objects.equals(user.getUserSkinType(), "Oily")) tonerID += "Oil";
        else tonerID += user.getUserSkinType();
        tonerID += "-" + day ;

        String tonerImageString = "toner_";
        if(Objects.equals(user.getUserSkinType(), "Oily")) tonerImageString += "oil";
        else tonerImageString += user.getUserSkinType().toLowerCase();

        ProductItem toner = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, tonerID );
        if( toner != null ) toner.setImageIDResource(getResources().getIdentifier(tonerImageString, "drawable", getPackageName()));

        if( toner != null ) productList.add(toner);

        // Serum
        String serumID = "Serum-";
        if(Objects.equals(user.getUserSkinType(), "Oily")) serumID += "Oil";
        else serumID += user.getUserSkinType();
        serumID += "-" + day ;

        String serumImageString = "serum_";
        if(Objects.equals(user.getUserSkinType(), "Oily")) serumImageString += "oil";
        else serumImageString += user.getUserSkinType().toLowerCase();

        ProductItem serum = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, serumID );
        if( serum != null ) serum.setImageIDResource(getResources().getIdentifier(serumImageString, "drawable", getPackageName()));

        if( serum != null ) productList.add(serum);

        // Mask
        String maskID = "Mask-";
        if(Objects.equals(user.getUserSkinType(), "Oily")) maskID += "Oil";
        else maskID += user.getUserSkinType();
        maskID += "-" + day ;

        String maskImageString = "mask_";
        if(Objects.equals(user.getUserSkinType(), "Oily")) maskImageString += "oil";
        else maskImageString += user.getUserSkinType().toLowerCase();

        ProductItem mask = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, maskID );
        if( mask != null ) mask.setImageIDResource(getResources().getIdentifier(maskImageString, "drawable", getPackageName()));

        if( mask != null ) productList.add(mask);

        // Moisturizer
        String moisturizerID = "Moisturizer-";
        if(Objects.equals(user.getUserSkinType(), "Oily")) moisturizerID += "Oil";
        else moisturizerID += user.getUserSkinType();
        moisturizerID += "-" + day ;

        String moisturizerImageString = "moisturizer_";
        if(Objects.equals(user.getUserSkinType(), "Oily")) moisturizerImageString += "oil";
        else moisturizerImageString += user.getUserSkinType().toLowerCase();

        ProductItem moisturizer = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, moisturizerID );
        if( moisturizer != null ) moisturizer.setImageIDResource(getResources().getIdentifier(moisturizerImageString, "drawable", getPackageName()));

        if( moisturizer != null ) productList.add(moisturizer);

        // Sunscreen
        String sunscreenID = "Sunscreen-";
        if(Objects.equals(user.getUserSkinType(), "Oily")) sunscreenID += "Oil";
        else sunscreenID += user.getUserSkinType();
        sunscreenID += "-" + day ;

        String sunscreenImageString = "sunscreen_";
        if(Objects.equals(user.getUserSkinType(), "Oily")) sunscreenImageString += "oil";
        else sunscreenImageString += user.getUserSkinType().toLowerCase();

        ProductItem sunscreen = instance.retrieveObjectsFirestoreByID( Constants.KEY_COLLECTION_ROUTINE, sunscreenID );
        if( sunscreen != null ) sunscreen.setImageIDResource(getResources().getIdentifier(sunscreenImageString, "drawable", getPackageName()));

        if( sunscreen != null ) productList.add(sunscreen);

        // Remove redundant
        if(dateString.equals(user.getUserCurrentDay())) {
            if( user.getUserDayOrNight() == 0 && currentHour <= 17 && currentHour >= 3 ) productList.subList(0, user.getUserNumberDone()).clear();
            else if( user.getUserDayOrNight() == 1 && ( currentHour > 17 || currentHour < 3 ) ) productList.subList(0, user.getUserNumberDone()).clear();
            else {
                user.setUserNumberDone(0);
                FirebaseFirestore database = FirebaseFirestore.getInstance();
                DocumentReference documentReference = database.collection(KEY_COLLECTION_USERS).document(user.getUserEmail());
                documentReference.update(
                        "UserNumberDone", 0
                );
            }
        }

        // Refresh day and time

        int DayOrNight = 0;
        if( currentHour <= 17 && currentHour >= 3 )  DayOrNight = 0;
        else DayOrNight = 1;

        FirebaseFirestore database = FirebaseFirestore.getInstance();
        DocumentReference documentReference = database.collection(KEY_COLLECTION_USERS).document(user.getUserEmail());
        documentReference.update(
                "UserCurrentDay", dateString,
                "UserDayOrNight", DayOrNight
        );

        user.setUserDayOrNight(DayOrNight);
        user.setUserCurrentDay(dateString);

        // Save local
        user.setUserProductList(productList);
        saveUser();
    }

    private void saveUser()
    {
        SharedPreferenceManager sharedPreferenceManager=new SharedPreferenceManager<>(User.class, this);
        sharedPreferenceManager.storeSerializableObjectToSharedPreference(user,KEY_COLLECTION_USERS);
    }
}