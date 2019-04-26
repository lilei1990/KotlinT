package com.jojo.design.module_core.net

import com.lilei.common_network.RequestManager


/**
 *    author : JOJO
 *    e-mail : 18510829974@163.com
 *    date   : 2018/12/7 4:11 PM
 *    desc   : 单例提供Retrofit请求的Service
 */
object NetFoundProvider {
    val requestService: ApiFoundService
        get() = RequestManager.getRetrofit().create<ApiFoundService>(ApiFoundService::class.java)
}