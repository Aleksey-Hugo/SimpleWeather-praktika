package com.popov.simpleweather

import android.app.Application
import com.facebook.stetho.Stetho
import com.popov.simpleweather.di.components.AppComponent
import com.popov.simpleweather.di.components.DaggerAppComponent
import com.popov.simpleweather.di.module.AppModule

class App : Application() {
    lateinit var appComponent:AppComponent
        private set

    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)).build()
        Stetho.initializeWithDefaults(this)
    }
}