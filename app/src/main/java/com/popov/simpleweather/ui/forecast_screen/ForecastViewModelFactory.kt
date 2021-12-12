package com.popov.simpleweather.ui.forecast_screen

import android.os.Bundle
import com.popov.simpleweather.data.models.weather.OneCallResponse
import com.popov.simpleweather.di.ViewModelAssistedFactory
import com.popov.simpleweather.domain_logic.view_converters.ForecastConverter
import com.popov.simpleweather.ui.forecast_screen.ForecastPagerFragment.Companion.ARG_ONE_CALL
import com.popov.simpleweather.ui.forecast_screen.ForecastPagerFragment.Companion.ARG_POSITION
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ForecastViewModelFactory @Inject constructor(
    private val forecastConverter: ForecastConverter
) : ViewModelAssistedFactory<ForecastViewModel> {


    override fun create(arguments: Bundle): ForecastViewModel {
        val position = arguments.getInt(ARG_POSITION, 0)
        val oneCall = arguments.getParcelable<OneCallResponse>(ARG_ONE_CALL)
        return ForecastViewModel(position, oneCall, forecastConverter)
    }
}
