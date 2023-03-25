package com.t.weatherreport;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.List;

public class WeatherActivity extends AppCompatActivity {

    TextView tvResult2;
    TextView id_state1,id_state2,id_state3,id_state4,id_state5;
    TextView id_degree1,id_degree2,id_degree3,id_degree4,id_degree5;
    TextView id_main1,id_main2,id_main3,id_main4,id_main5;
    TextView id_humidity1,id_humidity2,id_humidity3,id_humidity4,id_humidity5;
    TextView id_wind1,id_wind2,id_wind3,id_wind4,id_wind5;
    TextView id_realfeel1,id_realfeel2,id_realfeel3,id_realfeel4,id_realfeel5;

    Button saveTxt;
    private String fileContents;
    private String file = "Weather_report.txt";

    private final String url = "https://api.openweathermap.org/data/2.5/weather";
    private final String appid = "f7ddf1ab3675e803448e1e8019e074cf";
    DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.SOFT_INPUT_MASK_ADJUST);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_weather_details1);

        tvResult2 = findViewById(R.id.tvResult2);
        saveTxt = findViewById(R.id.btnSave);

        id_state1 = findViewById(R.id.id_state1);
        id_state2 = findViewById(R.id.id_state2);
        id_state3 = findViewById(R.id.id_state3);
        id_state4 = findViewById(R.id.id_state4);
        id_state5 = findViewById(R.id.id_state5);

        id_degree1 = findViewById(R.id.id_degree1);
        id_degree2 = findViewById(R.id.id_degree2);
        id_degree3 = findViewById(R.id.id_degree3);
        id_degree4 = findViewById(R.id.id_degree4);
        id_degree5 = findViewById(R.id.id_degree5);

        id_main1 = findViewById(R.id.id_main1);
        id_main2 = findViewById(R.id.id_main2);
        id_main3 = findViewById(R.id.id_main3);
        id_main4 = findViewById(R.id.id_main4);
        id_main5 = findViewById(R.id.id_main5);

        id_humidity1 = findViewById(R.id.id_humidity1);
        id_humidity2 = findViewById(R.id.id_humidity2);
        id_humidity3 = findViewById(R.id.id_humidity3);
        id_humidity4 = findViewById(R.id.id_humidity4);
        id_humidity5 = findViewById(R.id.id_humidity5);

        id_wind1 = findViewById(R.id.id_wind1);
        id_wind2 = findViewById(R.id.id_wind2);
        id_wind3 = findViewById(R.id.id_wind3);
        id_wind4 = findViewById(R.id.id_wind4);
        id_wind5 = findViewById(R.id.id_wind5);

        id_realfeel1 = findViewById(R.id.id_realfeel1);
        id_realfeel2 = findViewById(R.id.id_realfeel2);
        id_realfeel3 = findViewById(R.id.id_realfeel3);
        id_realfeel4 = findViewById(R.id.id_realfeel4);
        id_realfeel5 = findViewById(R.id.id_realfeel5);


    }

    public void ShowWeatherDetails(View view) {

        String tempUrl = "";

        String state_name1 = getIntent().getStringExtra("state_name1");
        String state_name2 = getIntent().getStringExtra("state_name2");
        String state_name3 = getIntent().getStringExtra("state_name3");
        String state_name4 = getIntent().getStringExtra("state_name4");
        String state_name5 = getIntent().getStringExtra("state_name5");

        String []sl_states = {state_name1,state_name2,state_name3,state_name4,state_name5};
        for(int i=0;i<sl_states.length;i++){
            String city = sl_states[i];
            String country = "India, IN";
            if (city.equals("")) {
                Toast.makeText(getBaseContext(), "City field can not be empty!", Toast.LENGTH_LONG).show();
            } else {
                if (!country.equals("")) {
                    tempUrl = url + "?q=" + city + "," + country + "&appid=" + appid;
                } else {
                    tempUrl = url + "?q=" + city + "&appid=" + appid;
                }
                StringRequest stringRequest = new StringRequest(Request.Method.POST, tempUrl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("response",response);

                        String output = "";
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray jsonArray = jsonResponse.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
                            String description = jsonObjectWeather.getString("description");
                            JSONObject jsonObjectMain = jsonResponse.getJSONObject("main");
                            double temp = jsonObjectMain.getDouble("temp") - 273.15;
                            double feelsLike = jsonObjectMain.getDouble("feels_like") - 273.15;
                            float pressure = jsonObjectMain.getInt("pressure");
                            int humidity = jsonObjectMain.getInt("humidity");
                            JSONObject jsonObjectWind = jsonResponse.getJSONObject("wind");
                            String wind = jsonObjectWind.getString("speed");
                            JSONObject jsonObjectClouds = jsonResponse.getJSONObject("clouds");
                            String clouds = jsonObjectClouds.getString("all");
                            JSONObject jsonObjectSys = jsonResponse.getJSONObject("sys");
                            String countryName = jsonObjectSys.getString("country");
                            String cityName = jsonResponse.getString("name");
                            output += "Current weather of " + cityName + " (" + countryName + ")"
                                    + "\n Temp: " + df.format(temp) + " °C"
                                    + "\n Feels Like: " + df.format(feelsLike) + " °C"
                                    + "\n Humidity: " + humidity + "%"
                                    + "\n Description: " + description
                                    + "\n Wind Speed: " + wind + "m/s (meters per second)"
                                    + "\n Cloudiness: " + clouds + "%"
                                    + "\n Pressure: " + pressure + " hPa";

                            tvResult2.append(output);

                            if(city==sl_states[0]) {
                                id_state1.setText(city + " (" + countryName + ")");
                                id_degree1.setText(Math.round(temp) + " °");
                                id_main1.setText(description);
                                id_humidity1.setText(humidity + "%");
                                id_wind1.setText(wind + "m/s");
                                id_realfeel1.setText(df.format(feelsLike) + " °C");
                            }

                            if(city==sl_states[1]) {
                                id_state2.setText(city + " (" + countryName + ")");
                                id_degree2.setText(Math.round(temp) + " °");
                                id_main2.setText(description);
                                id_humidity2.setText(humidity + "%");
                                id_wind2.setText(wind + "m/s");
                                id_realfeel2.setText(df.format(feelsLike) + " °C");
                            }

                            if(city==sl_states[2]) {
                                id_state3.setText(city + " (" + countryName + ")");
                                id_degree3.setText(Math.round(temp) + " °");
                                id_main3.setText(description);
                                id_humidity3.setText(humidity + "%");
                                id_wind3.setText(wind + "m/s");
                                id_realfeel3.setText(df.format(feelsLike) + " °C");
                            }

                            if(city==sl_states[3]) {
                                id_state4.setText(city + " (" + countryName + ")");
                                id_degree4.setText(Math.round(temp) + " °");
                                id_main4.setText(description);
                                id_humidity4.setText(humidity + "%");
                                id_wind4.setText(wind + "m/s");
                                id_realfeel4.setText(df.format(feelsLike) + " °C");
                            }

                            if (city == sl_states[4])
                                {
                                id_state5.setText(city + " (" + countryName + ")");
                                id_degree5.setText(Math.round(temp) + " °");
                                id_main5.setText(description);
                                id_humidity5.setText(humidity + "%");
                                id_wind5.setText(wind + "m/s");
                                id_realfeel5.setText(df.format(feelsLike) + " °C");
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString().trim(), Toast.LENGTH_SHORT).show();
                    }
                });
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                requestQueue.add(stringRequest);
            }


        }
    }


    public void SaveFiles(View view) {

        fileContents = tvResult2.getText().toString();
        try {
            FileOutputStream fout = openFileOutput(file, MODE_PRIVATE);
            fout.write(fileContents.getBytes());
            fout.close();
            File fileDir = new File (getFilesDir(),file);
            Toast.makeText(getBaseContext(), "File Saved"+ fileDir, Toast.LENGTH_LONG).show();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}