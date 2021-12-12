package com.popov.simpleweather.data.models.entity.query_entity

import androidx.room.Embedded
import androidx.room.Relation
import com.popov.simpleweather.data.models.entity.PlaceEntity
import com.popov.simpleweather.data.models.entity.SimpleWeatherEntity
import com.popov.simpleweather.domain_logic.view_entities.weather.PlaceViewItem

data class PlacesWithSimpleWeather(
    @Embedded
    val placeEntity: PlaceEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "placeId"
    )
    val simpleWeatherEntity: SimpleWeatherEntity?
) {
    fun toPlaceEntity(): PlaceViewItem {
        return PlaceViewItem(
            title = placeEntity.title,
            favorite = true,
            lat = placeEntity.lat,
            lon = placeEntity.lon,
            cityFullName = placeEntity.fullName,
            simpleWeather = simpleWeatherEntity
        )
    }
}