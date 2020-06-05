package com.ggxiaozhi.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Create by ggxz
 * 2020/6/5
 * description:获取未来几天天气数据
 */

data class DailyResponse(val status: String, val result: Result) {
    data class Result(val daily: Daily)

    data class Daily(
        val temperature: List<Temperature>, val skycon: List<SkyCon>,
        @SerializedName("life_index") val lifeIndex: LifeIndex
    )

    data class Temperature(val max: Float, val min: Float)

    data class SkyCon(val value: String, val data: String)

    data class LifeIndex(
        val coldRisk: List<LifeDescription>, val carWashing: List<LifeDescription>,
        val ultraviolet: List<LifeDescription>, val dressing: List<LifeDescription>
    )

    data class LifeDescription(val desc: String)
}