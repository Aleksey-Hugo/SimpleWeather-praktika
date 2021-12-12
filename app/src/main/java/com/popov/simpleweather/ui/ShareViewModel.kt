package com.popov.simpleweather.ui

import androidx.lifecycle.ViewModel
import com.popov.simpleweather.other.SingleLiveEvent

class ShareViewModel: ViewModel(){
    val refreshWeather=SingleLiveEvent<Unit>()
}