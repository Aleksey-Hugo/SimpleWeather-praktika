package com.popov.simpleweather.domain_logic.use_case

import com.popov.simpleweather.data.DataRepository
import com.popov.simpleweather.data.models.other.GeoPoint
import com.popov.simpleweather.data.models.weather.OneCallResponse
import com.popov.simpleweather.domain_logic.view_converters.DetailConverter
import com.popov.simpleweather.domain_logic.view_entities.weather.WeatherViewItem
import com.popov.simpleweather.ui.weather_screen.WeatherState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RefreshWeather @Inject constructor(
    val repository: DataRepository,
    private val converter: DetailConverter
) {
    suspend operator fun invoke(geoPoint: GeoPoint): WeatherState.ActualWeather? {
        val freshResponse = repository.getFreshWeather(geoPoint)
        return if (freshResponse != null) {
            WeatherState.ActualWeather(freshResponse.toViewItem())
        } else {
            null
        }
    }

    private suspend fun OneCallResponse.toViewItem(): WeatherViewItem =
        withContext(Dispatchers.Default) { converter.transform(this@toViewItem) }
}