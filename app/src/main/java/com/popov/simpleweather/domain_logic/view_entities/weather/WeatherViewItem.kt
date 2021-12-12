package com.popov.simpleweather.domain_logic.view_entities.weather

import com.popov.simpleweather.data.models.weather.OneCallResponse

data class WeatherViewItem(
    val oneCallResponse: OneCallResponse,
    val header: WeatherHeader,
    val dayList: List<Days>
)