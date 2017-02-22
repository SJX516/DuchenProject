package com.duchen.template.usage.Kotlin.request

import android.util.Log
import com.duchen.template.usage.Kotlin.Forecast
import com.duchen.template.usage.Kotlin.ForecastResult
import com.google.gson.Gson
import java.net.URL


class Request(val cityName:String){

    companion object{
        private val REQUEST_URL = "http://wthrcdn.etouch.cn/weather_mini?city="
    }

    fun execute() : ForecastResult{
        val jsonString = URL(REQUEST_URL + cityName).readText()
        return Gson().fromJson(jsonString , ForecastResult::class.java)
    }
}