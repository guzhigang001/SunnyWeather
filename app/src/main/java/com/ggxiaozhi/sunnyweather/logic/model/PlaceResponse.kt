package com.ggxiaozhi.sunnyweather.logic.model

import com.google.gson.annotations.SerializedName

/**
 * Create by ggxz
 * 2020/6/4
 * description:
 *
 * url:https://api.caiyunapp.com/v2/place?query=%E5%8C%97%E4%BA%AC&token=TAkhjf8d1nlSlspN&lang=zh_CN
 */

//这里好像
data class PlaceResponse(val status: String, val places: List<Place>)

data class Place(
    val name: String,
    val location: Location, @SerializedName("formatted_address") val address: String
)

data class Location(val lat: String, val lng: String)