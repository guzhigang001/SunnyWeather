package com.ggxiaozhi.sunnyweather.ui.place

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.ggxiaozhi.sunnyweather.logic.model.Place
import com.ggxiaozhi.sunnyweather.logic.network.Repository

/**
 *
 * @Description:
 * @Author:         ggxz
 * @CreateDate:     2020/6/4 22:22
 * @UpdateUser:
 * @UpdateDate:     2020/6/4 22:22
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

class PlaceViewModel : ViewModel() {

    //MutableLiveData 内容可变  LiveData不可变
    //这个其实是传入的query参数  我们观察外界传过来
    private val searchLiveData = MutableLiveData<String>()

    //缓存数据
    val placeList = ArrayList<Place>()

    //外界使用 placeLiveData
    val placeLiveData = Transformations.switchMap(searchLiveData) { query ->
        //观察searchLiveData
        Repository.searchPlace(query)
    }

    fun searchPlace(query: String) {
        //searchLiveData 是可观察的 当外界传入query时 会通知她的观察者
        searchLiveData.value = query
    }
}