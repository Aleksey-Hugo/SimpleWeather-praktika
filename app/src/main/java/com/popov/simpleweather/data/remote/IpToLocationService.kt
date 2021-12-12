package com.popov.simpleweather.data.remote

import com.popov.simpleweather.data.models.ip_to_location.IpToLocation
import retrofit2.http.GET

interface IpToLocationService {
    @GET("json/?fields=24784")
    suspend fun getLocation(
    ): ResponseResult<IpToLocation>
}