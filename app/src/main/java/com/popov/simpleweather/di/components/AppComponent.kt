package com.popov.simpleweather.di.components

import com.popov.simpleweather.data.DataRepository
import com.popov.simpleweather.data.location.LocationProvider
import com.popov.simpleweather.di.module.*
import com.popov.simpleweather.ui.favorits_screen.FavoriteFragment
import com.popov.simpleweather.ui.forecast_screen.ForecastPagerFragment
import com.popov.simpleweather.ui.select_place_screen.SelectPlaceFragment
import com.popov.simpleweather.ui.setting_screen.SettingFragment
import com.popov.simpleweather.ui.weather_screen.DetailFragment
import com.popov.simpleweather.ui.weather_widget.WeatherWidgetSettingListFragment
import com.popov.simpleweather.ui.weather_widget.WidgetUpdateHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AppModule::class,
    RemoteModule::class,
    LocalModule::class,
    RepositoryModule::class,
    ViewModelsModule::class])
interface AppComponent {
    fun injectDetailFragment(fragment: DetailFragment)
    fun injectFavoriteFragment(fragment: FavoriteFragment)
    fun injectWidgetSettingsListFragment(fragment: WeatherWidgetSettingListFragment)
    fun injectSelectPlaceFragment(fragment: SelectPlaceFragment)
    fun injectForecastFragment(fragment: ForecastPagerFragment)
    fun injectLocationProvider(provider: LocationProvider)
    fun getRepository(): DataRepository
    fun getWidgetHelperFactory(): WidgetUpdateHelper.Factory
    fun injectSettingsFragment(fragment: SettingFragment)

}