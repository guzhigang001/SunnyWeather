package com.ggxiaozhi.sunnyweather.ui.place


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ggxiaozhi.sunnyweather.R
import com.ggxiaozhi.sunnyweather.logic.showToast
import kotlinx.android.synthetic.main.fragment_place.*

/**
 * A simple [Fragment] subclass.
 */


class PlaceFragment : Fragment() {


    private val placeViewModel by lazy {
        ViewModelProviders.of(this).get(PlaceViewModel::class.java)
    }

    private lateinit var adapter: PlaceAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_place, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = PlaceAdapter(placeViewModel.placeList)
        recyclerView.adapter = adapter


        searchPlaceEdit.addTextChangedListener { editable ->
            val context = editable.toString()
            if (context.isNotEmpty()) {
                placeViewModel.searchPlace(context)
            } else {
                recyclerView.visibility = View.GONE
                bgImageView.visibility = View.VISIBLE
                placeViewModel.placeList.clear()
                adapter.notifyDataSetChanged()
            }
        }
        placeViewModel.placeLiveData.observe(this, Observer { result ->
            val places = result.getOrNull()
            if (places != null) {
                recyclerView.visibility = View.VISIBLE
                bgImageView.visibility = View.GONE
                placeViewModel.placeList.clear()
                placeViewModel.placeList.addAll(places)
                adapter.notifyDataSetChanged()
            } else {
                "未能查询到任何地点".showToast()
                result.exceptionOrNull()?.printStackTrace()
            }

        })
    }


}
