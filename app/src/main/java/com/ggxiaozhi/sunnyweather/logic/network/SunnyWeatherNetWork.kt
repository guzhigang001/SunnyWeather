package com.ggxiaozhi.sunnyweather.logic.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

/**
 * Create by ggxz
 * 2020/6/4
 * description:
 */
object SunnyWeatherNetWork {
    //地点网络请求
    private val placeService = ServiceCreator.create<PlaceService>()

    private val weatherService = ServiceCreator.create<WeatherService>()

    //Retrofit 默认提供了await方法
    suspend fun searchPlaces(query: String) = placeService.searchPlace(query).await()

    //获取实时天气
    suspend fun getRealTimeWeather(lng: String, lat: String) =
        weatherService.getRealTimeWeather(lng, lat).await()

    //获取未来天气
    suspend fun getDailyWeather(lng: String, lat: String) =
        weatherService.getDailyWeather(lng, lat).await()

    //创建Call的扩展函数
    private suspend fun <T> Call<T>.await(): T {

        return suspendCoroutine { continuation ->
            enqueue(object : Callback<T> {
                override fun onFailure(call: Call<T>, t: Throwable) {
                    continuation.resumeWithException(t)
                }

                override fun onResponse(call: Call<T>, response: Response<T>) {
                    val body = response.body()
                    if (body != null) continuation.resume(body)
                    else continuation.resumeWithException(RuntimeException("ResponseBody is null"))//失败抛出一个异常
                }

            })


        }
    }


}