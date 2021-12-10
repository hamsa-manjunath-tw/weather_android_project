package com.example.weatherapidemo

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.sql.Time

class WeatherDetailsDisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details_display)

        val spinner: ProgressBar;
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.VISIBLE)

        val cityName = intent.getStringExtra("cityName")

        val callback = object : Callback<WeatherInfo> {

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(call: Call<WeatherInfo>, response: Response<WeatherInfo>) {

                if (response.isSuccessful) {
                    val responseReceived = response.body()
                    if (responseReceived != null) {
                        spinner.setVisibility(View.GONE);
                        val weatherDetails: MutableList<String> =
                            mapWeatherDetails(response, cityName!!)

                        val listView: ListView = findViewById<ListView>(R.id.weatherDetailsListView)
                        val adapter = ArrayAdapter(
                            this@WeatherDetailsDisplayActivity,
                            android.R.layout.simple_list_item_1,
                            weatherDetails
                        )

                        listView.adapter = adapter
                    }

                } else {
                    finish()
                }
            }

            override fun onFailure(call: Call<WeatherInfo>, t: Throwable) {
                println("Its failed")
            }
        }

        val retriever = WeatherRetriever()
        retriever.getSearchResult(callback, cityName.toString())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun mapWeatherDetails(response: Response<WeatherInfo>, cityName: String): MutableList<String> {
        var weatherDetails: MutableList<String> = mutableListOf<String>()
        var responseReceived = response.body()
        weatherDetails.add("City Name: $cityName")
        weatherDetails.add("main: ${responseReceived?.weather?.get(0)?.main}")
        weatherDetails.add("Description: ${responseReceived?.weather?.get(0)?.description}")
        weatherDetails.add("Temperature: ${responseReceived?.main?.temp}")
        weatherDetails.add("Feels Like: ${responseReceived?.main?.feels_like}")


        val sunriseTime = java.time.format.DateTimeFormatter.ISO_INSTANT
            .format(java.time.Instant.ofEpochSecond(responseReceived!!.sys.sunrise.toLong()!!))
        val sunsetTime = java.time.format.DateTimeFormatter.ISO_INSTANT
            .format(java.time.Instant.ofEpochSecond(responseReceived!!.sys.sunset.toLong()!!))

        weatherDetails.add("Sunrise time: " + sunriseTime)
        weatherDetails.add("Sunset time: " + sunsetTime)
        return weatherDetails
    }
}