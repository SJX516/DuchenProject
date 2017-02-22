package com.duchen.template.usage.Kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import com.duchen.template.usage.AppActivityBase
import com.duchen.template.usage.R
import com.duchen.template.usage.Kotlin.request.Request
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*

class KotlinMainActivity : AppActivityBase() {

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7" )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        val forecastList = findViewById(R.id.forecast_list) as RecyclerView
        forecastList.layoutManager = LinearLayoutManager(this)

        async() {
            val result = Request("杭州").execute()
            uiThread {
                forecastList.adapter = ForecastListAdapter( result.data ){ forecast -> toast(forecast.date) }
//                forecastList.adapter = ForecastListAdapter( result.data ){ toast(it.date) }
            }

        }
    }

    override fun handleClick(id: Int, v: View?) {
    }
}