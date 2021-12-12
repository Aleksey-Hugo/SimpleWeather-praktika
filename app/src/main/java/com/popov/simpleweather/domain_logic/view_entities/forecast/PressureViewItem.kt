package com.popov.simpleweather.domain_logic.view_entities.forecast

import com.popov.simpleweather.databinding.ForecastPressureItemBinding
import com.popov.simpleweather.domain_logic.ViewItem


data class PressureViewItem(
    val value: String,
    val measurement: String
) : ViewItem<ForecastPressureItemBinding> {
    override fun bind(binding: ForecastPressureItemBinding) {
        binding.pressureValueTv.text = value
        binding.pressureScaleTv.text = measurement
    }
}