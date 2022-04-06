package com.example.fragment

import android.content.Intent
import android.content.res.Configuration
import com.example.fragment.CurrentTaskFragment
import com.example.fragment.FinishTaskFragment
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.URL
import java.util.*
import com.example.fragment.Weather
import org.json.JSONException
import org.json.JSONObject
import android.view.LayoutInflater
import android.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fragment.databinding.ActivityMainBinding
import androidx.fragment.app.DialogFragment



class MainActivity : AppCompatActivity() {
    lateinit var fm: FragmentManager
    lateinit var ft: FragmentTransaction
    lateinit var fr1: Fragment
    lateinit var fr2: Fragment
    lateinit var toShort: Button
    lateinit var toLong: Button
    lateinit var finder:Button
    lateinit var binding: ActivityMainBinding
    val class_weather=Weather()
    var current_fragm="F"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fm = supportFragmentManager
        ft = fm.beginTransaction()
        fr2 = FinishTaskFragment()
        finder=findViewById(R.id.find)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.weather=Weather()
        val fr = fm.findFragmentById(R.id.container_fragm)
        if (fr == null) {
            fr1 = CurrentTaskFragment()
            fm.beginTransaction().add(R.id.container_fragm, fr1)
                .commit()
        } else
            fr1 = fr

        toShort = findViewById(R.id.short_ver)
        toLong = findViewById(R.id.full_ver)

        val change_design:Button=findViewById(R.id.design_btn)
        change_design.setOnClickListener{
            Log.d("button","pressed")
            MyDialog(this).show(supportFragmentManager, "test")
        }

        toShort.setOnClickListener {
            if (current_fragm == "F") {
                val ft = fm.beginTransaction()

                val bundle = Bundle()
                bundle.putString("temperature", class_weather.temperature)
                bundle.putString("wind_speed", class_weather.wind_speed)
                bundle.putString("main_weather", class_weather.main_weather)
                fr2.arguments = bundle
                //ft.add(R.id.container_fragm, fr2)
                ft.replace(R.id.container_fragm, fr2)
                ft.commit()
            }
            current_fragm="S"
        }

        toLong.setOnClickListener {
            if (current_fragm == "S") {
                val ft = fm.beginTransaction()
                val bundle = Bundle()
                bundle.putString("temperature", class_weather.temperature)
                bundle.putString("wind_speed", class_weather.wind_speed)
                bundle.putString("main_weather", class_weather.main_weather)
                bundle.putString("feels_like", class_weather.feels_like)
                bundle.putString("pressure", class_weather.pressure)
                bundle.putString("description", class_weather.description)
                fr1.arguments = bundle
                //ft.add(R.id.container_fragm, fr1)
                ft.replace(R.id.container_fragm, fr1)
                ft.commit()
            }


        current_fragm="F"
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.app_name)
        toolbar.inflateMenu(R.menu.languages)
        toolbar.setOnMenuItemClickListener { item -> setLocaleLanguage(item.toString()); true }
    }

    suspend fun loadWeather() {

        class_weather.error=""
        val API_KEY = "7bc74e67ab92d8b035ce120f678a5799"
        //val City="Irkutsk"
        val City=binding.City.text
        val weatherURL = "https://api.openweathermap.org/data/2.5/weather?q="+City+"&appid="+API_KEY+"&units=metric";
        try {
            val stream = URL(weatherURL).getContent() as InputStream
            val data = Scanner(stream).nextLine()
            var obj = JSONObject(data)
            if (obj["cod"].toString() != "200") {
                Log.d("compleated", "False1")
                class_weather.error = "Ошибка в названии города"
            }

            else {
                Log.d("compleated","True")
                val main=obj.getJSONObject("main")
                val weather=obj.getJSONArray("weather").getJSONObject(0)
                val wind=obj.getJSONObject("wind")
                val temperature=main["temp"]
                val mainweather=weather["main"]
                val feels_like=main["feels_like"]
                val preassure=main["pressure"]
                val description=weather["description"]
                Log.d("temperature",temperature.toString())
                Log.d("main",mainweather.toString())
                class_weather.temperature=temperature.toString()
                class_weather.feels_like=feels_like.toString()
                class_weather.pressure=preassure.toString()
                class_weather.description=description.toString()
                class_weather.error=""
                if(mainweather.toString()=="Clouds")
                    //class_weather.main_weather=R.drawable.cloudly
                    class_weather.main_weather="cloudly"
                if(mainweather.toString()=="Clear")
                    //class_weather.main_weather=R.drawable.sunny
                    class_weather.main_weather="clear"
                if(mainweather.toString()=="Rain")
                    class_weather.main_weather="rainy"
                    //class_weather.main_weather=R.drawable.rainy
                class_weather.wind_speed=wind["speed"].toString()
                binding.weather=class_weather
            }




        }
        catch(e:Exception)
        {
            Log.d("compleated","False2")
            class_weather.error="Ошибка в названии города"
            binding.weather=class_weather
        }

    }

    fun setLocaleLanguage(localecode: String)
    {
        var coder="ru"
        when(localecode)
        {
            "Русский" -> coder="ru"
            "English" -> coder="en"
        }
        Log.d("localcode", coder)
        val locale = Locale(coder)
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        @Suppress("DEPRECATION")
        config.locale = locale
        @Suppress("DEPRECATION")
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        var intent: Intent =Intent(this,MainActivity::class.java)
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }

    public fun onClick(v: View) {

        GlobalScope.launch (Dispatchers.IO) {
            loadWeather()
        }
    }

}
