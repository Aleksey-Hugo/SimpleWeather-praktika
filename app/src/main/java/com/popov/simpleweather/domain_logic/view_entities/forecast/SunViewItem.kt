package com.popov.simpleweather.domain_logic.view_entities.forecast

import com.popov.simpleweather.databinding.ForecastSunItemBinding
import com.popov.simpleweather.domain_logic.ViewItem

data class SunViewItem(
    val sunRise: String,
    val sunSet: String,
    val dayDuration: String
) : ViewItem<ForecastSunItemBinding> {
    override fun bind(binding: ForecastSunItemBinding) {
        binding.sunriseTimeTv.text = sunRise
        binding.sunsetTimeTv.text = sunSet
        binding.solarDayValueTv.text = dayDuration
    }
}