package com.popov.simpleweather.data.remote

import com.popov.simpleweather.data.remote.ResponseResultCall
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Converter
import java.lang.reflect.Type

class ResponseResultCall {

}

class ResponseResultAdapter<T : Any>(
    private val successType: Type,
    private val errorBodyConverter: Converter<ResponseBody,Nothing>
):CallAdapter<T, Call<ResponseResult<T>>> {
    override fun responseType(): Type {
        return successType
    }

    override fun adapt(call: Call<T>): Call<ResponseResult<T>> {
        return ResponseResultCall(call,errorBodyConverter)
    }
}