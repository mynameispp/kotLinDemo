package com.ffzxnet.testkotlin.net


import java.io.Serializable


/**
 * 创建者： feifan.pi 在 2017/3/22.
 */

class ErrorResponse : Serializable {
    /**
     * -1，-2系统返回错误
     * -3,其他异常
     */
    //    private String msg;//异常信息

    var code: Int = 0
    var message: String? = null//异常信息
    var url: String? = null

    companion object {
        /**
         * 未授权
         */
        val ErrorUnauthorizedCode = -1
        /**
         * 服务器返回异常
         */
        val ErrorSystemCode = -2
        /**
         * 其他异常
         */
        val ErrorOtherCode = -3
    }

}
