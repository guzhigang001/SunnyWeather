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
    private val placeService = ServiceCreator.create<PlaceService>()

    //Retrofit 默认提供了await方法
    suspend fun searchPlaces(query: String) = placeService.searchPlace(query).await()


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
                    else continuation.resumeWithException(RuntimeException("ResponseBody is null"))
                }

            })


        }
    }


}