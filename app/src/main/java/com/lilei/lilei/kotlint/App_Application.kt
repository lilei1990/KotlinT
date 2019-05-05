package com.lilei.lilei.kotlint

import com.alibaba.android.arouter.launcher.ARouter
import com.config.ARouterConfig
import com.lilei.common_base.BaseApplication

/**
 * 作者 : LiLei
 * 时间 : 2019/04/18.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */
class App_Application : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        // 1. 应用内简单的跳转(通过URL跳转在'进阶用法'中)
        ARouter.getInstance().build(ARouterConfig.LOC_HOME).navigation()
    }
}