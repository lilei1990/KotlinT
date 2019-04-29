package com.lilei.lilei.kotlint

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.ActivityUtils
import kotlinx.android.synthetic.main.activity_main.*
import com.alibaba.android.arouter.launcher.ARouter
import com.config.ARouterConfig
import com.lilei.common_base.test.CommonBaseMainActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
            ARouter.getInstance().build(ARouterConfig.LOC_HOME).navigation()
//            ActivityUtils.startActivity(CommonBaseMainActivity::class.java)
        }
        button1.setOnClickListener {
            // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
            ARouter.getInstance().build("/location/activity").navigation()
//            ActivityUtils.startActivity(CommonBaseMainActivity::class.java)
        }
        map.onCreate(savedInstanceState)
    }

    override fun onCreateView(name: String?, context: Context?, attrs: AttributeSet?): View? {

        return super.onCreateView(name, context, attrs)
    }
}
