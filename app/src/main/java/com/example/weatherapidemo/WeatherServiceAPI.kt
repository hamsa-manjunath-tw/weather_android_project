package com.example.weatherapidemo

import android.app.ProgressDialog
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherService {
    @GET("data/2.5/weather?units=metric&appid=687e39409f49681cbe172402c5160cf7&")
    fun listDetails(@Query("q") q: String?): Call<WeatherInfo>
}

class WeatherInfo(val weather: List<Weather>, val main: MainClass, val sys: Sys)
class Weather(val main: String, val description: String)
class MainClass(val temp: String, val feels_like: String)
class Sys(val sunrise: String, val sunset: String)

class WeatherRetriever {

    val service: WeatherService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        service = retrofit.create(WeatherService::class.java)
    }

    fun getSearchResult(callback: Callback<WeatherInfo>, cityName: String) {

        val call: Call<WeatherInfo> = service.listDetails(cityName)
        call.enqueue(callback)

    }

}

