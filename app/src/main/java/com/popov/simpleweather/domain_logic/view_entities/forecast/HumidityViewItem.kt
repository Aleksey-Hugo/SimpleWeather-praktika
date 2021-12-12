package com.popov.simpleweather.domain_logic.view_entities.forecast

import com.popov.simpleweather.databinding.ForecastHumidityItemBinding
import com.popov.simpleweather.domain_logic.ViewItem

class HumidityViewItem(
    val humidity: String
) : ViewItem<ForecastHumidityItemBinding> {
    override fun bind(binding: ForecastHumidityItemBinding) {
        binding.humidityPercentMorningTv.text = humidity
    }

}