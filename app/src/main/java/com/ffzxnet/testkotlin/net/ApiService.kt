package com.ffzxnet.testkotlin.net


import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * 创建者： feifan.pi 在 2017/2/20.
 */

interface ApiService {


    //登录
    @POST("member/login.do")
    fun login(@Query("params") params: String): Observable<BaseApiResultData<String>>

    //获取首页banner
    @get:POST("member/getBanner.do")
    val banner: Observable<BaseApiResultData<List<BannerResponse>>>

    companion object {
        /**
         * 授权类型
         */
        val AUTH_TYPE = "app"
    }
}
