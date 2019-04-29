package com.lilei.common_base.test

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.lilei.common_base.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.base_activity_main.*
// 在支持路由的页面上添加注解(必选)
// 这里的路径需要注意的是至少需要有两级，/xx/xx
@Route(path = "/CommonBase/activity")
class CommonBaseMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.base_activity_main)


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

    fun getCategories() {
        NetFoundProvider.requestService.getCategories().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
            button.text = it.last().name+""
        }, {
            button.text = "错误:" + it.message
        })

    }
}
