package com.popov.simpleweather.ui.weather_screen

import android.os.Bundle
import android.util.Log
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.popov.simpleweather.R
import com.popov.simpleweather.data.DataRepository
import com.popov.simpleweather.data.PreferencesStorage
import com.popov.simpleweather.data.PreferencesStorage.Companion.IS_FIRST_START
import com.popov.simpleweather.data.location.LocationProvider
import com.popov.simpleweather.data.models.other.GeoPoint
import com.popov.simpleweather.di.ViewModelAssistedFactory
import com.popov.simpleweather.domain_logic.view_converters.DetailConverter
import com.popov.simpleweather.domain_logic.view_entities.weather.PlaceViewItem
import com.popov.simpleweather.domain_logic.view_entities.weather.WeatherViewItem
import com.popov.simpleweather.other.MutableLiveDataKt
import com.popov.simpleweather.other.SingleLiveEvent
import com.popov.simpleweather.other.collectIn
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


class DetailViewModel constructor(
    private val argPlaceView: PlaceViewItem?,
    private val repository: DataRepository,
    preferences: PreferencesStorage,
    private val locationProvider: LocationProvider,
    private val converter: DetailConverter
) : ViewModel() {

    val weatherPlace = MutableLiveData<PlaceViewItem?>()
    var weatherData = MutableLiveDataKt<WeatherState>(WeatherState.EmptyWeather)
    val favoriteCheckBox = MutableLiveDataKt(false)
    val loadingState = MutableLiveDataKt(false)
    val showSnackBar = SingleLiveEvent<@StringRes Int>()
    val errorConnectionSnackBar = SingleLiveEvent<Boolean>()
    val requestLocationPermission = SingleLiveEvent<Unit>()

    private var weatherJob: Job? = null
    private var favoriteCheckBoxJob: Job? = null
    private var currentJob: Job? = null

    private var hasVisited: Boolean by preferences(IS_FIRST_START, false)
    private val isFirstStart: Boolean
        get() = firstStart()


    init {
        locationProvider.start()
        if (isFirstStart) {
            requestLocationPermission.value = Unit
        } else {
            setCurrentPlace(argPlaceView)
        }
    }

    companion object {
        const val ARGUMENT_PLACE = "ARGUMENT_PLACE"
    }


    override fun onCleared() {
        locationProvider.stop()
    }

    fun fetchFreshWeather(shouldShowErrorState: Boolean = false) {
        val geoPoint = weatherPlace.value?.toGeoPoint()
        if (geoPoint != null) {
            viewModelScope.launch {
                loadingState.value = true
                val freshWeather = repository.getFreshWeather(geoPoint)
                val weatherIsNotFresh = freshWeather == null
                errorConnectionSnackBar.value = weatherIsNotFresh
                loadingState.value = false
                if (shouldShowErrorState && weatherIsNotFresh) {
                    weatherData.value = WeatherState.ErrorWeather
                }
            }
        } else {
            setCurrentPlace()
        }
    }

    fun changeFavoriteStatus(checked: Boolean) {
        val place = weatherPlace.value
        checkNotNull(place) { "place null when favorite status changed" }
        if (checked) {
            viewModelScope.launch {
                repository.saveFavoritePlace(place)
                showSnackBar.value = R.string.location_added_to_favorites
            }
        } else {
            viewModelScope.launch { repository.removeFavoritePlace(place) }
        }
    }


    fun onErrorConnectionClick() {
        fetchFreshWeather()
    }

    private fun firstStart(): Boolean {
        return if (!hasVisited) {
            hasVisited = true
            true
        } else {
            false
        }
    }

    fun setCurrentPlace(placeViewItem: PlaceViewItem? = null) {
        Log.d("DetailViewModel", "setCurrentPlace: ${placeViewItem?.title}")
        if (placeViewItem != null) {
            currentJob?.cancel()
            weatherPlace.value = placeViewItem
            changeWeatherPlace(placeViewItem.toGeoPoint())
        } else {
            restartObserveCurrentPlace()
        }
    }

    private fun restartObserveCurrentPlace() {
        Log.d("DetailViewModel", "restartObserveCurrentPlace: ")
        viewModelScope.launch {
            loadingState.value = true
            locationProvider.refreshCurrentPlace()
            loadingState.value = false
        }
        currentJob?.cancel()
        currentJob = repository
            .getCurrentPlaceFlow()
            .collectIn(viewModelScope) { place: PlaceViewItem? ->
                weatherPlace.value = place
                if (place != null) {
                    setupWeatherObservation(place.toGeoPoint())
                } else {
                    weatherData.value = WeatherState.ErrorWeather
                }
            }
    }

    private fun changeWeatherPlace(point: GeoPoint) {
        setupWeatherObservation(point)
        setupFavoriteObservation(point)
    }

    private fun setupWeatherObservation(point: GeoPoint) {
        weatherJob?.cancel()
        weatherJob = repository.getOneCallResponseFlow(point)
            .onEach { if (it == null) fetchFreshWeather(true) }
            .filterNotNull()
            .map { converter.transform(it.oneCallResponse) }
            .flowOn(Dispatchers.Default)
            .collectIn(viewModelScope, this::handleWeather)
    }

    private fun handleWeather(weatherViewItem: WeatherViewItem) {
        val response = weatherViewItem.oneCallResponse
        if (response.isFresh) {
            weatherData.value = WeatherState.ActualWeather(weatherViewItem)
            return
        }
        weatherData.value = WeatherState.ActualWeather(weatherViewItem)
        fetchFreshWeather()
    }

    private fun setupFavoriteObservation(point: GeoPoint) {
        favoriteCheckBoxJob?.cancel()
        favoriteCheckBoxJob = repository
            .placeIsFavoriteFlow(point.pointToId())
            .map { it != null }
            .collectIn(viewModelScope) { favoriteCheckBox.value = it }
    }


    class DetailViewModelFactory @Inject constructor(
        private val repository: DataRepository,
        private val preferences: PreferencesStorage,
        private val locationProvider: LocationProvider,
        private val detailConverter: DetailConverter
    ) : ViewModelAssistedFactory<DetailViewModel> {
        override fun create(arguments: Bundle): DetailViewModel {
            val placeEntity = arguments.getParcelable<PlaceViewItem?>(ARGUMENT_PLACE)
            return DetailViewModel(
                placeEntity,
                repository,
                preferences,
                locationProvider,
                detailConverter
            )
        }
    }
}

