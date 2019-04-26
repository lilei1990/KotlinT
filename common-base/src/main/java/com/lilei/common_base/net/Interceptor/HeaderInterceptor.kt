package com.lilei.common_base.net.Interceptor

import com.lilei.common_base.net.Conf
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 作者 : LiLei
 * 时间 : 2019/04/22.
 * 邮箱 :416587959@qq.com
 * 描述 :请求头需要携带的参数
 */
class HeaderInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val requestBuilder = request.newBuilder()
                .addHeader("Connection", Conf.headerConnection)
                .addHeader("User-Agent", Conf.userAgent)
                .method(request.method(), request.body())
                .build()
        return chain.proceed(requestBuilder)
    }
}