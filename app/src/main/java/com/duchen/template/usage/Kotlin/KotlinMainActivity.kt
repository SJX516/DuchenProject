package com.duchen.template.usage.Kotlin

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.duchen.template.usage.AppActivityBase
import com.duchen.template.usage.Kotlin.request.Request
import com.duchen.template.usage.R
import org.jetbrains.anko.async
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

class KotlinMainActivity : AppActivityBase() {

    private val items = listOf(
            "Mon 6/23 - Sunny - 31/17",
            "Tue 6/24 - Foggy - 21/8",
            "Wed 6/25 - Cloudy - 22/17",
            "Thurs 6/26 - Rainy - 18/11",
            "Fri 6/27 - Foggy - 21/10",
            "Sat 6/28 - TRAPPED IN WEATHERSTATION - 23/18",
            "Sun 6/29 - Sunny - 20/7")

    private var mForecastList : RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun setContentView() {
        setContentView(R.layout.activity_kotlin)
    }

    override fun findViews() {
        mForecastList = findViewById(R.id.forecast_list) as RecyclerView
    }

    override fun initViews() {
        mForecastList?.layoutManager = LinearLayoutManager(this)

        async() {
            val result = Request("杭州").execute()
            uiThread {
                mForecastList?.adapter = ForecastListAdapter(result.data) { forecast -> toast(forecast.date) }
//                forecastList.adapter = ForecastListAdapter( result.data ){ toast(it.date) }
            }

        }
    }

    override fun handleClick(id: Int, v: View?) {
    }
}