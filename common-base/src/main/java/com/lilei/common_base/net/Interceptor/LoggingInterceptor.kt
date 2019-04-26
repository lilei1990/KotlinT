package com.lilei.common_network

import android.util.Log
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

/**
 * 作者 : LiLei
 * 时间 : 2019/04/18.
 * 邮箱 :416587959@qq.com
 * 描述 :自定义Log日志打印
 */
class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        //这个chain里面包含了request和response，所以你要什么都可以从这里拿
        val request = chain.request()
        val t1 = System.nanoTime()//请求发起的时间
        val method = request.method()
        val jsonObject = JSONObject()
        if ("POST" == method || "PUT" == method) {
            if (request.body() is FormBody) {
                val body = request.body() as FormBody?
                if (body != null) {
                    for (i in 0 until body.size()) {
                        try {
                            jsonObject.put(body.name(i), body.encodedValue(i))
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }

                    }
                }
                Log.e("request", String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                        request.url(), chain.connection(), jsonObject.toString(), request.method()))
            } else {
                val buffer = Buffer()
                val requestBody = request.body()
                if (requestBody != null) {
                    request.body()!!.writeTo(buffer)
                    val body = buffer.readUtf8()
                    Log.e("request", String.format("发送请求 %s on %s  %nRequestParams:%s%nMethod:%s",
                            request.url(), chain.connection(), body, request.method()))
                }
            }
        } else {
            Log.e("request", String.format("发送请求 %s on %s%nMethod:%s",
                    request.url(), chain.connection(), request.method()))
        }
        val response = chain.proceed(request)
        val t2 = System.nanoTime()//收到响应的时间
        val responseBody = response.peekBody((1024 * 1024).toLong())
        Log.e("request",
                String.format("Retrofit接收响应: %s %n返回json:%s %n耗时：%.1fms",
                        response.request().url(),
                        responseBody.string(),
                        (t2 - t1) / 1e6
                ))
        return response
    }

}