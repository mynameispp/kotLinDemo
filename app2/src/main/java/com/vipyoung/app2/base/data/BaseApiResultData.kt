package com.vipyoung.app2.base.data

/**
 * 创建者： feifan.pi 在 2017/3/6.
 */

data class BaseApiResultData<T>(var code: Int
                                , var message: String
                                , var body: T) : BaseResponse() {

}
