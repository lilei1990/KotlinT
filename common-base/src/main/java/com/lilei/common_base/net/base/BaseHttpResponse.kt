package com.smart.novel.net


/**
 *    date   : 2018/12/2 1:49 PM
 *    desc   : 数据格式统一封装(data为实际数据)
 */
class BaseHttpResponse<T>(code: Int, msg: String, data: T) {
    var code: Int = 0
    var msg: String? = null
    var success: Boolean = false
    var data: T? = null
}
