package com.popov.simpleweather.ui.weather_screen


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.popov.simpleweather.databinding.DetailWeatherItemBinding
import com.popov.simpleweather.databinding.HourlyWeatherItemBinding
import com.popov.simpleweather.domain_logic.view_entities.weather.WeatherDetailCurrent
import com.popov.simpleweather.domain_logic.view_entities.weather.WeatherDetailHourly

sealed class WeatherDetailHolders(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class HeaderHolder(private val binding: DetailWeatherItemBinding) :
        WeatherDetailHolders(binding.root) {
        fun bind(current: WeatherDetailCurrent) {
            current.bind(binding)
        }
    }

    class HourlyHolder(private val binding: HourlyWeatherItemBinding) :
        WeatherDetailHolders(binding.root) {
        fun bind(hourly: WeatherDetailHourly) {
            hourly.bind(binding)
        }
    }
}