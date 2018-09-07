package com.vipyoung.app2.net


import com.vipyoung.app2.base.data.BaseResponse


/**
 * 创建者： feifan.pi 在 2017/3/22.
 */

class ErrorResponse : BaseResponse {
    /**
     * -1，-2系统返回错误
     * -3,其他异常
     */
    var code: Int = 0
    var message: String? = null
    var url: String? = null

    constructor() {}

    constructor(code: Int) {
        this.code = code
    }

}
