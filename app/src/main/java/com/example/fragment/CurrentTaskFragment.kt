package com.example.fragment

import android.graphics.Color
import android.util.Log
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.Fragment

class CurrentTaskFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_current_task, container, false)
        var temp: TextView = view.findViewById<View>(R.id.temperature_value) as TextView
        val wind: TextView = view.findViewById<View>(R.id.wind_value) as TextView
        val main: ImageView = view.findViewById<View>(R.id.main_value) as ImageView
        val feel: TextView = view.findViewById<View>(R.id.feels_value) as TextView
        val pres: TextView = view.findViewById<View>(R.id.pres_value) as TextView
        val desc: TextView = view.findViewById<View>(R.id.desc_value) as TextView
        if(arguments!=null) {
            val bundle = arguments
            val temp_message = bundle!!.getString("temperature")
            val wind_message = bundle!!.getString("wind_speed")
            val main_message = bundle!!.getString("main_weather")
            val feel_message = bundle!!.getString("feels_like")
            val pres_message = bundle!!.getString("pressure")
            val desc_message = bundle!!.getString("description")
            temp.text = temp_message
            wind.text=wind_message
            if(main_message=="cloudly")
            //Log.d("cloudly", "True")
                main.setImageResource(R.drawable.cloudly)
            if(main_message=="clear")
                main.setImageResource(R.drawable.sunny)
            if(main_message=="rainy")
                main.setImageResource(R.drawable.rainy)
            feel.text=feel_message
            pres.text=pres_message
            desc.text=desc_message
        }
        view.setBackgroundColor(Color.GREEN)
        return view
    }
}