package com.ffzxnet.testkotlin.net


/**
 * 创建者： feifan.pi 在 2017/3/14.
 */

interface IApiSubscriberCallBack<T> {
    //请求结束
    fun onCompleted()

    //请求失败
    fun onError(error: ErrorResponse)

    //请求成功
    fun onNext(result: T)
}
