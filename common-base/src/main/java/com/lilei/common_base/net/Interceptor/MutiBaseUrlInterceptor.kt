package com.lilei.common_base.net.Interceptor

import android.util.Log
import com.zongxueguan.naochanle_android.retrofitrx.ApiService
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 作者 : LiLei
 * 时间 : 2019/04/22.
 * 邮箱 :416587959@qq.com
 * 描述 :处理多个BaseUrl的情形
 */

class MutiBaseUrlInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        //获取request
        val request = chain.request()
        //从request中获取原有的HttpUrl实例oldHttpUrl
        var oldHttpUrl = request.url()
        //获取request的创建者builder
        val builder = request.newBuilder()
        //从request中获取headers，通过给定的键url_name
        val headerValues = request.headers("base_url")
        if (headerValues != null && headerValues.size > 0) {
            //如果有这个header，先将配置的header删除，因此header仅用作app和okhttp之间使用
            builder.removeHeader("base_url")
            //匹配获得新的BaseUrl
            val headerValue = headerValues.get(0)
            var newBaseUrl: HttpUrl?
            if ("kaiyan" == headerValue) {
                newBaseUrl = HttpUrl.parse(ApiService.BASE_SERVER_IP_2)
            } else {
                newBaseUrl = oldHttpUrl
                return chain.proceed(builder.url(newBaseUrl).build())
            }
            //在oldHttpUrl的基础上重建新的HttpUrl，修改需要修改的url部分
            Log.e("Url", "intercept-oldHttpUrl: " + oldHttpUrl.toString())
            //oldHttpUrl:http://api.xiangqu.com/categories?key=0db6ffd00372064035ef33763dd1c61e&t=1547700576328
            val newUrl = oldHttpUrl
                    .newBuilder()
                    .scheme("http")//更换网络协议,根据实际情况更换成https或者http
                    .host(newBaseUrl!!.host())//更换主机名
                    .port(newBaseUrl.port())//更换端口
//                        .addPathSegment("api")//添加第一个参数 (已经包含Segment的HttpUrl，addPathSegment时，无法直接跟在host后面，只能在最后一个参数后面接)
//                        .addPathSegment("v4")//添加第二个参数
                    .build()
            //重建这个request，通过builder.url(finalRequestUrl).build()
            Log.e("Url", "intercept: " + newUrl.toString())
            //newUrl: http://baobab.kaiyanapp.com/categories?key=0db6ffd00372064035ef33763dd1c61e&t=1547700576328
            //期望的finalUrl: http://baobab.kaiyanapp.com/api/v4/categories?key=0db6ffd00372064035ef33763dd1c61e&t=1547700576328
            var spltUrls = newUrl.toString().split(".com")
            var finalUrl = spltUrls[0] + ".com" + "/api/v4" + spltUrls[1]
            Log.e("Url", "finalUrl: " + finalUrl)
            return chain.proceed(builder.url(finalUrl).build())
        }
        return chain.proceed(request)
    }
}
