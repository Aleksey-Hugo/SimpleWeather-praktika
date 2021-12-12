package com.popov.simpleweather.ui.weather_screen


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.popov.simpleweather.R
import com.popov.simpleweather.databinding.DetailWeatherItemBinding
import com.popov.simpleweather.databinding.HourlyWeatherItemBinding
import com.popov.simpleweather.domain_logic.view_entities.weather.WeatherHeaderDetail

class WeatherDetailAdapter : RecyclerView.Adapter<WeatherDetailHolders>() {
    var item: WeatherHeaderDetail? = null

    companion object {
        const val HEADER: Int = 1
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherDetailHolders {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.detail_weather_item -> WeatherDetailHolders.HeaderHolder(
                DetailWeatherItemBinding.inflate(inflater, parent, false)
            )
            R.layout.hourly_weather_item -> WeatherDetailHolders.HourlyHolder(
                HourlyWeatherItemBinding.inflate(inflater, parent, false)
            )
            else -> throw IllegalStateException("Type error")
        }
    }

    override fun getItemCount(): Int {
        return item?.weatherDetailHourly?.size?.plus(1) ?: 0
    }

    override fun onBindViewHolder(holder: WeatherDetailHolders, position: Int) {
        return when (holder) {
            is WeatherDetailHolders.HeaderHolder -> {
                holder.bind(item?.weatherDetailCurrent ?: return)
            }
            is WeatherDetailHolders.HourlyHolder ->
                holder.bind(item?.weatherDetailHourly?.get(position - HEADER) ?: return)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            R.layout.detail_weather_item
        } else {
            R.layout.hourly_weather_item
        }
    }

    fun setData(headerDetail: WeatherHeaderDetail) {
        item = headerDetail
        notifyDataSetChanged()
    }


}


