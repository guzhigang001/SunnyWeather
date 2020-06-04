package com.ggxiaozhi.sunnyweather.logic.network

import androidx.lifecycle.liveData
import com.ggxiaozhi.sunnyweather.logic.model.Place
import com.ggxiaozhi.sunnyweather.logic.model.PlaceResponse
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher
import java.lang.Exception
import java.lang.RuntimeException

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

}

