package com.popov.simpleweather.ui.select_place_screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.popov.simpleweather.data.DataRepository
import com.popov.simpleweather.data.models.places.Places
import com.popov.simpleweather.data.remote.ResponseResult
import com.popov.simpleweather.domain_logic.view_entities.weather.PlaceViewItem
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class SelectPlaceViewModel @Inject constructor(
    private var repository: DataRepository
) : ViewModel() {
    private val parentJob = Job()
    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.IO
    private val scope = CoroutineScope(coroutineContext)
    val searchHistory = MutableLiveData<List<PlaceViewItem>>()

    init {
        scope.launch {
            val history = repository.getSearchHistory()
            searchHistory.postValue(history)
        }

    }

    override fun onCleared() {
        scope.cancel()
        super.onCleared()
    }

    suspend fun getPlaces(query: String): ResponseResult<Places> {
        return repository.getPlaces(query)
    }
}