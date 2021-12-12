package com.popov.simpleweather.data.models.places


import com.google.gson.annotations.SerializedName

data class DependentLocality(
    @SerializedName("DependentLocalityName")
    val dependentLocalityName: String
)