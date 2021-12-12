package com.popov.simpleweather.ui.weather_screen

import com.popov.simpleweather.domain_logic.view_entities.weather.WeatherViewItem

sealed class WeatherState {
    object EmptyWeather : WeatherState()
    data class ActualWeather(val weatherViewItem: WeatherViewItem, val error: Throwable? = null) :
        WeatherState()
    object ErrorWeather:WeatherState()
}