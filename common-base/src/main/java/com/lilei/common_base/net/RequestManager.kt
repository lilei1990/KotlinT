/*
 * Copyright (C) guolin, Suzhou Quxiang Inc. Open source codes for study only.
 * Do not use for commercial purpose.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lilei.common_network

import com.lilei.common_base.net.BaseObserverListtener
import com.lilei.common_base.net.BaseResponse
import com.lilei.common_base.net.bean.ErrorBean
import com.lilei.common_base.net.Interceptor.CommonQueryParamsInterceptor
import com.lilei.common_base.net.Interceptor.HeaderInterceptor
import com.lilei.common_base.net.Interceptor.MutiBaseUrlInterceptor
import com.lilei.common_base.net.RxSchedulers
import com.smart.novel.net.BaseHttpResponse
import com.zongxueguan.naochanle_android.retrofitrx.ApiService
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * 网络请求模式的基类，所有的请求封装都应该要继承此类。这里会提供网络模块的配置，以及请求的具体逻辑处理等。
 *
 */
object RequestManager {


    /**
     * 请求接口实例对象
     */
    private var mInstance: RequestManager? = null
    private val DEFAULT_TIMEOUT = 60L

    private var retrofit: Retrofit? = null


    val requestService: ApiService
        get() = getRetrofit().create<ApiService>(ApiService::class.java!!)


     fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            synchronized(RequestManager::class.java) {
                if (retrofit == null) {
                    val mClient = OkHttpClient.Builder()
                            //添加离线缓存
//                            .cache( Cache (File(context.getExternalFilesDir("okhttpCache"), ""), 14 * 1024 * 100))
//                            .addInterceptor(CacheInterceptor ())
//                            .addNetworkInterceptor(CacheInterceptor())//必须要有，否则会返回504
                            //添加公告查询参数
                            .addInterceptor(CommonQueryParamsInterceptor())
                            .addInterceptor(HeaderInterceptor())//头部拦截器
                            .addInterceptor(LoggingInterceptor())//添加请求拦截(可以在此处打印请求信息和响应信息)
                            .addInterceptor(MutiBaseUrlInterceptor()) //多个BaseUrl的处理
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build()
                    retrofit = Retrofit.Builder()
                            .baseUrl(ApiService.BASE_SERVER_IP_1)//默认的BaseUrl 建议以/结尾
                            .addConverterFactory(GsonConverterFactory.create())//设置 Json 转换器
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//RxJava 适配器
                            .client(mClient)
                            .build()
                }
            }
        }
        return retrofit!!
    }


    /**
     * Retrofit上传文件
     *
     * @param mImagePath
     * @return
     */
    fun getUploadFileRequestBody(mImagePath: String): RequestBody {
        val file = File(mImagePath)
        //构建body
        return MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
                .build()
    }

    /**
     * 建立请求:data下无嵌套json的正常情况
     */
    fun <T> doCommonRequest(observable: Observable<BaseHttpResponse<T>>, observerListener: BaseObserverListtener<T>): DisposableObserver<BaseHttpResponse<T>> {

        return observable
                .compose(RxSchedulers.io_main())
                .subscribeWith(object : DisposableObserver<BaseHttpResponse<T>>() {

                    override fun onNext(result: BaseHttpResponse<T>) {
                        if (result.code === 200) {
                            observerListener.onSuccess(result.data!!)
                        } else {
                            val errorBean = ErrorBean()
                            errorBean.code = result.code.toString()
                            errorBean.msg = result.msg.toString()
                            observerListener.onBusinessError(errorBean)
                        }
                    }

                    override fun onError(e: Throwable) {
                        observerListener.onError(e)
                    }

                    override fun onComplete() {
                        observerListener.onComplete()
                    }
                })

    }

    /**
     * 建立请求
     */
    fun <T> doRequestOther(observable: Observable<BaseResponse<T>>, observerListener: BaseObserverListtener<T>): DisposableObserver<BaseResponse<T>> {

        return observable
                .compose(RxSchedulers.io_main())
                .subscribeWith(object : DisposableObserver<BaseResponse<T>>() {

                    override fun onNext(result: BaseResponse<T>) {
                        if (result.code === 200) {
                            if (result.data?.content != null) {
                                observerListener.onSuccess(result.data!!.content!!)
                            } else {
                                observerListener.onSuccess(result.data as T)
                            }
                        } else {
                            val errorBean = ErrorBean()
                            errorBean.code = result.code.toString()
                            errorBean.msg = result.msg.toString()
                            observerListener.onBusinessError(errorBean)
                        }
                    }

                    override fun onError(e: Throwable) {
                        observerListener.onError(e)
                    }

                    override fun onComplete() {
                        observerListener.onComplete()
                    }
                })

    }

    /**
     * 建立请求(未封装Json数据的情形)
     */
    fun <T> doRequest(observable: Observable<T>, observerListener: BaseObserverListtener<T>): DisposableObserver<T> {

        return observable
                .compose(RxSchedulers.io_main())
                .subscribeWith(object : DisposableObserver<T>() {

                    override fun onNext(result: T) {
                        observerListener.onSuccess(result)
                    }

                    override fun onError(e: Throwable) {
                        observerListener.onError(e)
                    }

                    override fun onComplete() {
                        observerListener.onComplete()
                    }
                })

    }

}