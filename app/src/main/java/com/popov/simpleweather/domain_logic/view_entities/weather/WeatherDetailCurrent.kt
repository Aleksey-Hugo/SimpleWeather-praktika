package com.popov.simpleweather.domain_logic.view_entities.weather

import com.popov.simpleweather.databinding.DetailWeatherItemBinding
import com.popov.simpleweather.domain_logic.ViewItem
import com.popov.simpleweather.databinding.DetailWeatherItemBinding

data class WeatherDetailCurrent(
    val windSpeed: String,
    val windSpeedValue: String,
    val windDirectionIcon: Int,
    val pressureValue: String,
    val pressureMeasurement: String,
    val humidity: String
) : ViewItem<DetailWeatherItemBinding> {
    override fun bind(binding: DetailWeatherItemBinding) {
        binding.windSpeedTextTv.text = windSpeed
        binding.windSpeedValueTv.text = windSpeedValue
        binding.windDirectionIconIv.setImageResource(windDirectionIcon)
        binding.pressureValueTv.text = pressureValue
        binding.pressureTextTv.text = pressureMeasurement
        binding.humidityValueTv.text = humidity
    }
}