package com.lilei.common_base.test

import java.io.Serializable

/**
 *    date   : 2019/1/18 12:42 PM
 *    desc   : 视频分类
 */
data class CategoryBean(var id: String, var name: String, var description: String, var bgPicture: String, var headerImage: String) : Serializable {
}