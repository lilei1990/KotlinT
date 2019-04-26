package com.example.module_mock_location

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import kotlinx.android.synthetic.main.activity_location.*

@Route(path = "/location/activity")
class LocationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)

        button.setOnClickListener {
            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
            ARouter.getInstance().build("/CommonBase/activity").navigation()
//            ActivityUtils.startActivity(CommonBaseMainActivity::class.java)
        }
        button1.setOnClickListener {
            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
            ARouter.getInstance().build("/location/activity").navigation()
//            ActivityUtils.startActivity(CommonBaseMainActivity::class.java)
        }
    }
}
