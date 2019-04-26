package com.lilei.common_base.net.Interceptor

import android.util.Log
import com.blankj.utilcode.util.NetworkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * 作者 : LiLei
 * 时间 : 2019/04/22.
 * 邮箱 :416587959@qq.com
 * 描述 :缓存的拦截器
 */

 class CacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!NetworkUtils.isConnected()) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
        }
        val response = chain.proceed(request)
        if (NetworkUtils.isConnected()) {
            val cacheControl = request.cacheControl().toString()
            Log.e("Tag", "有网")
            return response.newBuilder().header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build()
        } else {
            Log.e("Tag", "无网")
            return response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + "60 * 60 * 24 * 7")
                    .removeHeader("Pragma").build()
        }
    }
}
