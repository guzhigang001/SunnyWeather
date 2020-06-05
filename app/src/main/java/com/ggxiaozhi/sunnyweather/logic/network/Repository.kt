package com.ggxiaozhi.sunnyweather.logic.network

import androidx.lifecycle.liveData
import com.ggxiaozhi.sunnyweather.logic.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import okhttp3.Dispatcher
import java.lang.Exception
import java.lang.RuntimeException
import kotlin.coroutines.CoroutineContext

/**
 *
 * @Description:  同一外界调用入口 这是一个仓库 在这里处理数据是来自本地还是来自网络
 * //             具体的逻辑都是在这处理 外界是不关心的
 * @Author:         ggxz
 * @CreateDate:     2020/6/4 22:00
 * @UpdateUser:
 * @UpdateDate:     2020/6/4 22:00
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

object Repository {

    //这里是将协程和非协程转换 同时返回 liveData   liveData(Dispatchers.IO)ktx 中的 网络请求要在IO中
    fun searchPlace(query: String) = fire(Dispatchers.IO) {
        //这里用 try()catch 是因为  SunnyWeatherNetWork.searchPlaces(query) 里面用了await
        //注意这个方法是suspend 修饰的挂起函数 只有协成才能调用
        val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
        if (placeResponse.status == "ok") {
            val places = placeResponse.places
            Result.success(places)
        } else {
            Result.failure(RuntimeException("response status is ${placeResponse.status}"))
        }
    }


    fun refreshWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        //coroutineScope 这个方法开启协程(更多的含义是开启协程作用域)有2特点：1继承外部(调用处的协程) 在外部协程下创建协程子作用域
        coroutineScope {
            val deferredRealTime = async {
                SunnyWeatherNetWork.getRealTimeWeather(lng, lat)
            }

            val deferredDaily = async {
                SunnyWeatherNetWork.getDailyWeather(lng, lat)
            }
            //返回Deferred 后调用await可串行执行 直接在方法后调为并行执行
            val realTimeResponse = deferredRealTime.await()
            val dailyResponse = deferredDaily.await()
            if (realTimeResponse.status == "ok" && dailyResponse.status == "ok") {
                val weather =
                    Weather(realTimeResponse.result.realtime, dailyResponse.result.daily)
                Result.success(weather)
            } else {
                Result.failure(
                    RuntimeException(
                        "realtime response status is ${realTimeResponse.status}" +
                                "daily response status is ${dailyResponse.status}"
                    )
                )
            }
        }
    }


    fun getRealTimeWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        val realTimeResponse = SunnyWeatherNetWork.getRealTimeWeather(lng, lat)
        if (realTimeResponse.status == "ok") {
            Result.success(realTimeResponse.result)
        } else {
            Result.failure(RuntimeException("response status is ${realTimeResponse.status}"))
        }
    }


    fun getDailyWeather(lng: String, lat: String) = fire(Dispatchers.IO) {
        val dailyResponse = SunnyWeatherNetWork.getDailyWeather(lng, lat)
        if (dailyResponse.status == "ok") {
            Result.success(dailyResponse.result)
        } else {
            Result.failure(RuntimeException("response status is ${dailyResponse.status}"))
        }
    }

    private fun <T> fire(context: CoroutineContext, block: suspend () -> Result<T>) =
        liveData<Result<T>>(context) {

            val result = try {

                block()
            } catch (e: Exception) {
                Result.failure<T>(e)
            }
            emit(result)
        }

}

