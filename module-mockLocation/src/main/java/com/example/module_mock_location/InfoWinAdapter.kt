package com.example.module_mock_location

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView

import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.example.module_mock_location.R.id.id_location_wigdet
import com.lilei.common_base.BaseApplication
import kotlinx.android.synthetic.main.view_infowindow.view.*


/**
 * Created by Teprinciple on 2016/8/23.
 * 地图上自定义的infowindow的适配器
 */
class InfoWinAdapter : AMap.InfoWindowAdapter {
    private val mContext = BaseApplication.mApplication
    private var latLng: LatLng? = null


    override fun getInfoWindow(marker: Marker): View {
        initData(marker)
        return initView()
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    private fun initData(marker: Marker) {
        latLng = marker.position

    }

    private fun initView(): View {
        val view = LayoutInflater.from(mContext).inflate(R.layout.view_infowindow, null)
//        call = view.findViewById<View>(R.id.call_LL) as LinearLayout
//        nameTV = view.findViewById<View>(R.id.name) as TextView
//        addrTV = view.findViewById<View>(R.id.addr) as TextView
//
//        nameTV!!.text = agentName
//        addrTV!!.text = String.format("sss", snippet)
//        view.call_LL.setOnClickListener({
//            idLocationWidget.setMangerLocationData(latLng.latitude, mLocationBean.getLongitude())
//            idLocationWidget.startMockLocation()
//        })
//        id_location_wigdet.setma(mLocationBean.getLatitude(), mLocationBean.getLongitude())
//        idLocationWidget.startMockLocation()

        return view
    }


}
