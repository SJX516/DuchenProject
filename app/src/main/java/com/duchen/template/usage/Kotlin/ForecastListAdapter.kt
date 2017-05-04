package com.duchen.template.usage.Kotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.duchen.template.usage.Kotlin.extensions.ctx
import com.duchen.template.usage.R
import org.jetbrains.anko.find
import kotlin.properties.Delegates


class ForecastListAdapter(val items: ActualData, val itemClick: (Forecast) -> Unit) :
        RecyclerView.Adapter<ForecastListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.item_kotlin_forecast, parent, false)
        return ViewHolder(view, itemClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindForecast(items[position])
    }

    override fun getItemCount(): Int = items.size()

    class ViewHolder(val view: View, val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder(view) {
        private val dateView: TextView
        private val typeView: TextView
        private val highTemperatureView: TextView
        private val lowTemperatureView: TextView

        val number = Delegates.vetoable(0) {
            d, old, new ->
            new >= 0
        }

        init {
            dateView = view.find(R.id.text_date)
            typeView = view.find(R.id.text_desc)
            highTemperatureView = view.find(R.id.text_max_temperature)
            lowTemperatureView = view.find(R.id.text_min_temperature)
        }

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                dateView.text = date
                typeView.text = type
                highTemperatureView.text = high
                lowTemperatureView.text = low
                itemView.setOnClickListener { itemClick(forecast) }
            }
        }

    }

}