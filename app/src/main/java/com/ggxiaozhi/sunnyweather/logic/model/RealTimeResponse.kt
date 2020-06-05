package com.ggxiaozhi.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Create by ggxz
 * 2020/6/5
 * description: 实时获取天气数据
 */

data class RealTimeResponse(val status: String, val result: Result) {
    data class Result(val realtime: RealTime)

    data class RealTime(
        val temperature: Float, val skycon: String, @SerializedName("air_quality")
        val airQuality: AirQuality
    )

    data class AirQuality(val api: AQI)

    data class AQI(val chn: Float)
}

