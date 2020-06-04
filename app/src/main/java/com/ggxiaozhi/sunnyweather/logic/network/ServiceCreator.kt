package com.ggxiaozhi.sunnyweather.logic.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Create by ggxz
 * 2020/6/4
 * description:  统一对外提供Retrofit反射得到的Service
 */

object ServiceCreator {
    private const val BASE_URL = "https://api.caiyunapp.com/"

    //创建retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        //配置Gson 来将返回的数据直接装成实体
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun <T> create(serviceClass: Class<T>): T = retrofit.create(serviceClass)

    //泛型实例化标准写法 inline+reified
    inline fun <reified T> create(): T = create(T::class.java)
}