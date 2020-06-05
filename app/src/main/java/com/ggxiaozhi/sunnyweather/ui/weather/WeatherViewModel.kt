package com.ggxiaozhi.sunnyweather.ui.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ggxiaozhi.sunnyweather.logic.model.Location
import com.ggxiaozhi.sunnyweather.logic.network.Repository

/**
 * Create by ggxz
 * 2020/6/5
 * description:
 */

class WeatherViewModel : ViewModel() {


    private val locationLiveData = MutableLiveData<Location>()

    private var locationLng = ""
    private var locationLat = ""
    private var placeName = ""

    val weatherLiveData=Transformations.switchMap(locationLiveData){location->
        Repository.refreshWeather(location.lng,location.lat)
    }

    fun refreshWeather(lng: String, lat: String) {       
        locationLiveData.value = Location(lat, lng)
    }
}