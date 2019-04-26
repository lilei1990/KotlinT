package com.lilei.common_base.net

import com.lilei.common_base.net.bean.ErrorBean

/**
 * 作者 : LiLei
 * 时间 : 2019/04/18.
 * 邮箱 :416587959@qq.com
 * 描述 :
 */
interface BaseObserverListtener <T>{
     fun onSuccess(result: T)
     fun onComplete()
     fun onError(e: Throwable)
     fun onBusinessError(errorBean: ErrorBean)
}