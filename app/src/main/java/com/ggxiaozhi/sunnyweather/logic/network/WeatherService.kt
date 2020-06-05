package com.ggxiaozhi.sunnyweather.logic.network

import com.ggxiaozhi.sunnyweather.MyApplication
import com.ggxiaozhi.sunnyweather.logic.model.DailyResponse
import com.ggxiaozhi.sunnyweather.logic.model.RealTimeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Create by ggxz
 * 2020/6/5
 * description:天气网络请求
 */

interface WeatherService {
    @GET("/v2.5/${MyApplication.TOKEN}/{lng},{lat}/realtime.json")
    //https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/121.6544,25.1552/realtime.json
    fun getRealTimeWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<RealTimeResponse>

    @GET("/v2.5/${MyApplication.TOKEN}/{lng},{lat}/daily.json")
    //https://api.caiyunapp.com/v2.5/TAkhjf8d1nlSlspN/121.6544,25.1552/daily.json
    fun getDailyWeather(@Path("lng") lng: String, @Path("lat") lat: String): Call<DailyResponse>
}