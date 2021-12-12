package com.popov.simpleweather.data.models.places


import com.google.gson.annotations.SerializedName

data class Component(
    @SerializedName("kind")
    val kind: String,
    @SerializedName("name")
    val name: String
)