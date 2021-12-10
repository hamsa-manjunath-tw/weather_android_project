package com.example.weatherapidemo


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cityName: TextView = findViewById<TextView>(R.id.cityName)
        val searchButton: Button = findViewById<Button>(R.id.searchButton)

        var nameError: TextInputEditText = findViewById(R.id.cityNameError)
        nameError.visibility = View.GONE
        searchButton.setOnClickListener(View.OnClickListener() {

            if (cityName.text.isEmpty()) {
                nameError.setError(getResources().getString(R.string.emptyCityName));
                nameError.visibility = View.VISIBLE

            } else {
                nameError.setError(null)
                nameError.visibility = View.GONE
                val intent = Intent(this, WeatherDetailsDisplayActivity::class.java)
                intent.putExtra("cityName", cityName.text.toString())
                startActivity(intent)
            }
            nameError.setError(getResources().getString(R.string.incorrectCityName))
            nameError.visibility = View.VISIBLE
        })

    }

}
