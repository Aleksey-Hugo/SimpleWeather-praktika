package com.popov.simpleweather.domain_logic.view_entities.forecast

import com.popov.simpleweather.databinding.ForecastMagneticItemBinding
import com.popov.simpleweather.domain_logic.ViewItem
import com.srgpanov.simpleweather.databinding.ForecastMagneticItemBinding

data class MagneticViewItem(val uvIndex: String) : ViewItem<ForecastMagneticItemBinding> {
    override fun bind(binding: ForecastMagneticItemBinding) {
        binding.uvIndexValueTv.text = uvIndex
    }
}