package com.lilei.common_base.net.bean

/**
 * date   : 2018/12/2 1:49 PM
 * desc   :
 */
class DataBean<T> {
    var content: T? = null
    var numberOfElements: Int = 0
    var size: Int = 0
    var totalElements: Int = 0
    var totalPages: Int = 0
}
