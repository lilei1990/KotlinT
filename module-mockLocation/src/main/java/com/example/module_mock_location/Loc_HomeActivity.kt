package com.example.module_mock_location

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.model.MarkerOptions
import com.amap.api.maps.model.MyLocationStyle
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.PermissionUtils
import com.blankj.utilcode.util.ScreenUtils
import com.config.ARouterConfig
import com.example.module_mock_location.mocklocationlib.Coordtransform
import com.example.module_mock_location.mocklocationlib.LocationBean
import com.example.module_mock_location.mocklocationlib.LocationWidget
import com.lilei.common_base.BaseApplication
import kotlinx.android.synthetic.main.activity_loc__home.*


@Route(path = ARouterConfig.LOC_HOME)
class Loc_HomeActivity : AppCompatActivity() {

    val newInstance = LocationWidget.newInstance(BaseApplication.mApplication)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loc__home)
//        button.setOnClickListener {
//            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
//            ARouter.getInstance().build(ARouterConfig.LOC_HOME).navigation()
////            ActivityUtils.startActivity(CommonBaseMainActivity::class.java)
//        }
//        button1.setOnClickListener {
//            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
//            ARouter.getInstance().build("/location/activity").navigation()
////            ActivityUtils.startActivity(CommonBaseMainActivity::class.java)
//        }
        map.onCreate(savedInstanceState)
        initPermission()

    }

    fun initPermission() {
        PermissionUtils.permission(PermissionConstants.STORAGE, PermissionConstants.LOCATION)

                .callback(object : PermissionUtils.FullCallback {
                    override fun onGranted(permissionsGranted: List<String>) {
                        init()
                    }

                    override fun onDenied(permissionsDeniedForever: List<String>,
                                          permissionsDenied: List<String>) {
                        PermissionUtils.launchAppDetailsSettings()
                        AppUtils.exitApp()
                    }
                })
                .theme { activity -> ScreenUtils.setFullScreen(activity) }
                .request()
    }

    fun init() {

        val myLocationStyle: MyLocationStyle = MyLocationStyle()
        myLocationStyle.interval(2000) //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)          //定位一次，且将视角移动到地图中心点。
        map.map.setMyLocationStyle(myLocationStyle)//设置定位蓝点的Style
        map.map.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        map.map.setMyLocationEnabled(true)// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
//        //声明AMapLocationClient类对象
//        var mLocationClient: AMapLocationClient? = null
////声明定位回调监听器
//        val mLocationListener = AMapLocationListener() {
//            Log.e("ssss", it.longitude.toString())
//        }
////初始化定位
//        mLocationClient = AMapLocationClient(applicationContext)
////设置定位回调监听
//        mLocationClient.setLocationListener(mLocationListener)
//        mLocationClient.startLocation()


        map.map.setOnMapClickListener({
            map.map.clear(true)
            val marker = map.map.addMarker(MarkerOptions().position(it))
            newInstance.show(supportFragmentManager,"")
            var mLocationBean = LocationBean()
            val gcJ02ToWGS84 = Coordtransform.GCJ02ToWGS84(it!!.longitude, it!!.latitude)
            mLocationBean.setLatitude(gcJ02ToWGS84[1])
            mLocationBean.setLongitude(gcJ02ToWGS84[0])
            newInstance.setMangerLocationData(mLocationBean.latitude, mLocationBean.longitude)
            newInstance.refreshData()
        })
        val infoWinAdapter = InfoWinAdapter()
        map.map.setInfoWindowAdapter(infoWinAdapter)
        map.map.setOnMarkerClickListener({
            it.showInfoWindow()
            true //返回 “false”，除定义的操作之外，默认操作也将会被执行
        })
    }

    override fun onResume() {
        super.onResume()
        newInstance.refreshData()
    }

    override fun onDestroy() {
        super.onDestroy()
        newInstance.stopMock()
    }
}