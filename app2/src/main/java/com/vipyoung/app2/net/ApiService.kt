package com.vipyoung.app2.net


import com.vipyoung.app2.base.data.BaseApiResultData
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UserInfo
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author by PI
 * @date on 2018-05-27
 * describe 服务器请求接口
 */

interface ApiService {
    //登录
    @POST("/passport/user/nologin/login")
    fun login(@Body loginRequest: LoginRequest): Observable<BaseApiResultData<UserInfo>>

    //更新用户信息
    @GET("/passport/user/getTokenUser")
    fun upUserInfo(): Observable<BaseApiResultData<UserInfo>>

    //获取学校班级等信息
    @POST
    fun getUserSchoolInfo(@Url url: String): Observable<BaseApiResultData<UserInfo>>

    //下载文件
    @Streaming
    @GET
    fun downloadFile(@Url url: String): Observable<ResponseBody>

    //上傳头像
    @Multipart
    @POST
    fun upLoadFile(@Url url: String, @Part part: MultipartBody.Part): Observable<BaseApiResultData<String>>

}

