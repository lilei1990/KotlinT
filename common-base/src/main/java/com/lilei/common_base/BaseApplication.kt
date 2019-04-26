package com.lilei.common_base

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.Utils
import com.config.AppConfig

/**
 * 作者 : LiLei
 * 时间 : 2019/04/18.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */
open class BaseApplication : Application() {
    companion object {
        lateinit var mApplication: Application
    }

    override fun onCreate() {
        super.onCreate()
        mApplication = this
        //初始化工具类
        Utils.init(this)
        //初始化阿里路由
        initARouter()
    }

    private fun initARouter() {
        if (AppConfig.isDebug) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(mApplication ); // 尽可能早，推荐在Application中初始化
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        //MultiDex分包方法 必须最先初始化,防止分包后找不到class
        MultiDex.install(this)
    }
}