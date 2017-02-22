package com.duchen.template.usage.Kotlin

import java.util.*

data class ForecastResult(val desc: String, val data: ActualData)

data class ActualData (val wendu:String , val ganmao:String, var city:String , var forecast:List<Forecast>){
    operator fun get(position: Int): Forecast = forecast[position]
    fun size(): Int = forecast.size
}


data class Forecast(val fengxiang:String,val fengli:String,val high:String,val type:String,val low:String,val date:String)
