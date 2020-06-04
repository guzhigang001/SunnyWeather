package com.ggxiaozhi.sunnyweather

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Create by ggxz
 * 2020/6/4
 * description:
 */

class MyApplication : Application() {
    companion object {
        @SuppressLint("StaticFieldLeak")
        //这里可能产生内存泄漏  但是全局的Context时没问题的 忽略就好
        lateinit var context: Context

        //彩云平台密钥
        const val TOKEN = "TAkhjf8d1nlSlspN"
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }


}