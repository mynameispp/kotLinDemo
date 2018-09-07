package com.vipyoung.app2.net

import android.net.ParseException
import android.text.TextUtils
import com.google.gson.JsonParseException
import com.vipyoung.app2.base.data.BaseApiResultData
import com.vipyoung.app2.net.retrofit_gson_factory.ResultErrorException
import com.vipyoung.app2.util.GsonUtil
import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException

/**
 * @author by PI
 * @date on 2018-05-27
 * describe 服务器返回数据处理类
 */

abstract class ApiSubscriber<T> : Observer<T> {

    //请求结束
    abstract fun onCompleted()

    //请求失败
    abstract fun onFailure(error: ErrorResponse)

    //请求成功
    abstract fun onSuccess(data: T)

    //Token失效
    abstract  fun TokenInvalid()

    override fun onSubscribe(@NonNull d: Disposable) {
    }

    override fun onComplete() {
        onCompleted()
    }


    override fun onError(e: Throwable) {
        val errorResponse = ErrorResponse()
        errorResponse.code = -3
        if (e is HttpException) {
            //取出错误代码里面服务器返回的数据
            val body = e.response().errorBody()
            try {
                val resultData = GsonUtil.toClass(body!!.string(), BaseApiResultData::class.java) as BaseApiResultData<*>?
                if (null != resultData) {
                    errorResponse.code = e.code()
                    errorResponse.message = resultData.message
                } else {
                    if (401==e.code()){
                        //token失效
                        TokenInvalid()
                        onComplete()
                        return
                    }else {
                        errorResponse.code = e.code()
                        errorResponse.message = "${e.code()} 网络问题"
                    }
                }
            } catch (e1: IOException) {
                e1.printStackTrace()
                errorResponse.code = e.code()
                errorResponse.message = "网络问题"
            }
        } else if (e is JsonParseException || e is JSONException
                || e is ParseException) {
        } else if (e is ConnectException) {
        } else if (e is javax.net.ssl.SSLHandshakeException) {
        } else if (e is KotlinNullPointerException) {
            errorResponse.message = e.message
        } else if(e is ResultErrorException){
            errorResponse.message = e.message
        }
        onFailure(errorResponse)
        onComplete()
    }

    override fun onNext(t: T) {
        if (t is BaseApiResultData<*>) {
            val baseApiResultData = t as BaseApiResultData<*>
            if (baseApiResultData.code == 200) {
                //成功
                onSuccess(t)
            } else {
                //用户级错误
                val errorResponse = ErrorResponse()
                val errorMessage = ErrorCodeMessageUtil.getMessageByErrorCode(baseApiResultData.code)
                errorResponse.message = if (TextUtils.isEmpty(errorMessage)) baseApiResultData.message else errorMessage
                errorResponse.code = baseApiResultData.code
                onFailure(errorResponse)
            }
        } else {
            //第三方接口回调自己处理
            onSuccess(t)
        }

    }

}
