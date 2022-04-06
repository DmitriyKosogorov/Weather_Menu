package com.example.fragment

import android.util.Log
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import com.example.fragment.databinding.FragmentFinishTaskBinding


class FinishTaskFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_finish_task, container, false)
        var temp:TextView = view.findViewById<View>(R.id.temperature_value) as TextView
        val wind:TextView = view.findViewById<View>(R.id.wind_value) as TextView
        val main:ImageView = view.findViewById<View>(R.id.main_value) as ImageView
        val bundle = arguments
        val temp_message = bundle!!.getString("temperature")
        val wind_message = bundle!!.getString("wind_speed")
        val main_message = bundle!!.getString("main_weather")
        temp.text = temp_message
        wind.text=wind_message
        if (main_message != null) {
            Log.d("MESSSSSSSAGE", main_message)
        }
        if(main_message=="cloudly")
            //Log.d("cloudly", "True")
            main.setImageResource(R.drawable.cloudly)
        if(main_message=="clear")
            main.setImageResource(R.drawable.sunny)
        if(main_message=="rainy")
            main.setImageResource(R.drawable.rainy)
        view.setBackgroundColor(Color.YELLOW)
        return view
    }

}