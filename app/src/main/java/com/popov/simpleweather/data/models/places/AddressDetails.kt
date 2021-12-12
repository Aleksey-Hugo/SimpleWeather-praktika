package com.popov.simpleweather.data.models.places


import com.google.gson.annotations.SerializedName

data class AddressDetails(
    @SerializedName("Country")
    val country: Country
)