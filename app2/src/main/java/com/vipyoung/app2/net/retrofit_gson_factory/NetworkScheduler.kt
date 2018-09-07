package com.vipyoung.app2.net.retrofit_gson_factory

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * 创建者： Pi 在 2018/9/5.
 * 注释：设置请求线程环境
 */
object NetworkScheduler{
    fun <T> compose(): ObservableTransformer<T, T> {
        return ObservableTransformer { observable ->
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}
