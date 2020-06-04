package com.ggxiaozhi.sunnyweather.ui.place

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.ggxiaozhi.sunnyweather.R
import com.ggxiaozhi.sunnyweather.logic.model.Place
import kotlinx.android.synthetic.main.place_item.view.*

/**
 *
 * @Description:   Place 适配器
 * @Author:         ggxz
 * @CreateDate:     2020/6/4 22:46
 * @UpdateUser:
 * @UpdateDate:     2020/6/4 22:46
 * @UpdateRemark:   更新说明
 * @Version:        1.0
 */

class PlaceAdapter( private val placeList: List<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.place_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        holder.placeName.text = place.name
        holder.placeAddress.text = place.address
    }

    /**
     *
    1、inner修饰的内部类可理解为java中的非静态内部类，调用方式：

    外部类().内部类().方法()；

    2、kotlin内部类默认为静态的，理解为java中使用static修饰的内部类，调用方式：

    外部类.内部类().方法()；

    3、如果需要在静态内部类中使用外部类，可参考java中实现方式，在内部类中定义外部类弱引用，通过构造方法传入外部类对象；
     */
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val placeName: TextView = view.findViewById(R.id.placeName)
        val placeAddress: TextView = view.findViewById(R.id.placeAddress)
    }


}