package com.ggxiaozhi.sunnyweather.logic.model

/**
 * Create by ggxz
 * 2020/6/5
 * description:用于缓存数据 非网络返回实体
 */

data class Weather(val realTime: RealTimeResponse.RealTime, val daily: DailyResponse.Daily)