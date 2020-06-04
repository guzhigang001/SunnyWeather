package com.ggxiaozhi.sunnyweather.logic.network

import com.ggxiaozhi.sunnyweather.MyApplication
import com.ggxiaozhi.sunnyweather.logic.model.PlaceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Create by ggxz
 * 2020/6/4
 * description: 实际网络请求的接口
 */

interface PlaceService {
    //https://api.caiyunapp.com/v2/place?query=%E5%8C%97%E4%BA%AC&token=TAkhjf8d1nlSlspN&lang=zh_CN
    @GET("v2/place?token=${MyApplication.TOKEN}&lang=zh_CN")
    fun searchPlace(@Query("query") query: String): Call<PlaceResponse>
}