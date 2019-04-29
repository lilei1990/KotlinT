package com

import android.app.Application

/**
 * 作者 : LiLei
 * 时间 : 2019/04/18.
 * 邮箱 :416587959@qq.com
 * 描述 :扩展Application
 */
open class ExBaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //扩展模块的初始化
        initEx()
    }

    /**
     * 扩展模块的初始化
     */
    private fun initEx() {
        initExMap()
    }

    /**
     * 初始化地图
     */
    private fun initExMap() {


    }

}