package com.ggxiaozhi.sunnyweather.logic.network

import androidx.lifecycle.liveData
import com.ggxiaozhi.sunnyweather.logic.model.DailyResponse
import com.ggxiaozhi.sunnyweather.logic.model.Place
import com.ggxiaozhi.sunnyweather.logic.model.RealTimeResponse
import com.ggxiaozhi.sunnyweather.logic.model.Weather
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.lang.Exception
import java.lang.RuntimeException

/**
 * Create by ggxz
 * 2020/6/5
 * description:第一版  缺点是 每个调用方法都需要 try{}catch{}
 * 优化后可以统一处理
 */
@Deprecated("@see Repository")
object RepositoryOld {


    //这里是将协程和非协程转换 同时返回 liveData   liveData(Dispatchers.IO)ktx 中的 网络请求要在IO中
    fun searchPlace(query: String) = liveData(Dispatchers.IO) {
        val result = try {//这里用 try()catch 是因为  SunnyWeatherNetWork.searchPlaces(query) 里面用了await
            //注意这个方法是suspend 修饰的挂起函数 只有协成才能调用
            val placeResponse = SunnyWeatherNetWork.searchPlaces(query)
            if (placeResponse.status == "ok") {
                val places = placeResponse.places
                Result.success(places)
            } else {
                Result.failure(RuntimeException("response status is ${placeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<List<Place>>(e)
        }

        emit(result)
    }


    fun refreshWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
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
        } catch (e: Exception) {
            Result.failure<Weather>(e)
        }
        emit(result)
    }

    fun getRealTimeWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            val realTimeResponse = SunnyWeatherNetWork.getRealTimeWeather(lng, lat)
            if (realTimeResponse.status == "ok") {
                Result.success(realTimeResponse.result)
            } else {
                Result.failure(RuntimeException("response status is ${realTimeResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<RealTimeResponse.Result>(e)
        }
        emit(result)
    }

    fun getDailyWeather(lng: String, lat: String) = liveData(Dispatchers.IO) {
        val result = try {
            val dailyResponse = SunnyWeatherNetWork.getDailyWeather(lng, lat)
            if (dailyResponse.status == "ok") {
                Result.success(dailyResponse.result)
            } else {
                Result.failure(RuntimeException("response status is ${dailyResponse.status}"))
            }
        } catch (e: Exception) {
            Result.failure<DailyResponse.Result>(e)
        }
        emit(result)
    }

}