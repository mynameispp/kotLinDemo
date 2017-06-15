package com.ffzxnet.testkotlin.net

/**
 * 创建者： feifan.pi 在 2017/3/6.
 */

class BaseApiResultData<T> : BaseResponse(){
    /**
     * 返回标志
     */
    var status: String? = null

    /**
     * 返回信息
     */
    var resultMsg: String? = null

    /**
     * 数据总量
     */
    var recordsTotal: String? = null

    /**
     * 信息
     */
    var infoData: T? = null

    var isExistsUnionLogin: Boolean = false//关联登录专用
}
