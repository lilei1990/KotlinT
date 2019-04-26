package com.lilei.common_base.net.Interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 作者 : LiLei
 * 时间 : 2019/04/22.
 * 邮箱 :416587959@qq.com
 * 描述 :设置公共参数
 */

class CommonQueryParamsInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url().newBuilder()
//                    .addQueryParameter("key", "ecce8a3ef508f54cc1905af133f5b3a5")
//                    .addQueryParameter("t", "1543210514862")
                .addQueryParameter("key", "0db6ffd00372064035ef33763dd1c61e")
                .addQueryParameter("t", "1547700576328")
                .build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}