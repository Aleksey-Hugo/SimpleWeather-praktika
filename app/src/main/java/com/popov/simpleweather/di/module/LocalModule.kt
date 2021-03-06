package com.popov.simpleweather.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.popov.simpleweather.data.local.LocalDataSourceImpl
import com.popov.simpleweather.data.local.WeatherDao
import com.popov.simpleweather.data.local.WeatherDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalModule {

    @Singleton
    @Provides
    fun getDataBase(context: Context): WeatherDao {
        return WeatherDataBase
            .getInstance(context)
            .weatherDataDao()
    }
    @Singleton
    @Provides
    fun getLocalDataSource(dao: WeatherDao):LocalDataSourceImpl{
        return LocalDataSourceImpl(dao)
    }
    @Singleton
    @Provides
    fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}