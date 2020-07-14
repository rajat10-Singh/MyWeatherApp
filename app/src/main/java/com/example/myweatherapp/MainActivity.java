package com.example.myweatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Button button;
    EditText city;
    TextView result;

    String baseURL = "https://api.openweathermap.org/data/2.5/weather?q=";
    String API = "&appid=efe10044a30b4347ebeec6b198e71b18";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        city = findViewById(R.id.getCity);
        result = findViewById(R.id.result);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myURL = baseURL + city.getText().toString() + API;
//                Log.i("URL", "URL: " + myURL);

                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, myURL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("JSON", "JSON: " + response);
                        try {
                            String info = response.getString("weather");
                            Log.i("INFO", "INFO: "+ info);
                            JSONArray jsonArray = new JSONArray(info);
                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject parObj = jsonArray.getJSONObject(i);

                                String myWeather = parObj.getString("main");
                                result.setText(myWeather);
                                Log.i("ID", "ID: " + parObj.getString("id"));
                                Log.i("MAIN", "MAIN: " + parObj.getString("main"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error", "Something went wrong" + error);
                    }
                });

                VolleySingleton.getInstance(MainActivity.this).addToRequestQue(request);
            }
        });


    }
}
