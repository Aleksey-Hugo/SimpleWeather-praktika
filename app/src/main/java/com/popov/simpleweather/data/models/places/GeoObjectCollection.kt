package com.popov.simpleweather.data.models.places


import com.google.gson.annotations.SerializedName

data class GeoObjectCollection(
    @SerializedName("featureMember")
    val featureMember: List<FeatureMember>,
    @SerializedName("metaDataProperty")
    val metaDataProperty: MetaDataPropertyX
)