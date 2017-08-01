package com.ffzxnet.testkotlin.net

import android.net.ParseException
import android.text.TextUtils

import com.ffzxnet.testkotlin.R
import com.ffzxnet.testkotlin.application.MyApplication
import com.ffzxnet.testkotlin.net.retrofit_gson_factory.ResultErrorException
import com.google.gson.JsonParseException

import org.json.JSONException

import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException

import io.reactivex.Observer
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.Disposable
import retrofit2.HttpException

/**
 * 创建者： feifan.pi 在 2017/3/14.
 */

class ApiSubscriber<T>(private val iApiSubscriberCallBack: IApiSubscriberCallBack<T>) : Observer<T> {
    //取消订阅
    private var disposable: Disposable? = null

    override fun onSubscribe(@NonNull d: Disposable) {
        disposable = d
    }

    override fun onComplete() {
        iApiSubscriberCallBack.onCompleted()
        if (!disposable!!.isDisposed) {
            disposable!!.dispose()
        }
    }


    override fun onError(e: Throwable) {
        var errorResponse = ErrorResponse()
        errorResponse.code = -3
        errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_link_exception)
        if (e is HttpException) {
            when (e.code()) {
                HttpURLConnection.HTTP_UNAUTHORIZED -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_access_unauthorized)
                HttpURLConnection.HTTP_FORBIDDEN -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_access_denied)
                HttpURLConnection.HTTP_NOT_FOUND -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_not_find)
                HttpURLConnection.HTTP_CLIENT_TIMEOUT -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_time_out)
                HttpURLConnection.HTTP_GATEWAY_TIMEOUT -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_time_out)
                HttpURLConnection.HTTP_INTERNAL_ERROR -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_service_error)
                HttpURLConnection.HTTP_BAD_GATEWAY -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_service_error)
                HttpURLConnection.HTTP_UNAVAILABLE -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_service_error)
                else -> errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_link_exception)
            }
        } else if (e is JsonParseException || e is JSONException
                || e is ParseException) {
            errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_json)
        } else if (e is ConnectException) {
            errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_access_denied)
        } else if (e is javax.net.ssl.SSLHandshakeException) {
            errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_access_denied)
        } else if (e is ResultErrorException) {
            //系统返回的异常
            val errorMes = e.message
            if (!TextUtils.isEmpty(errorMes)) {
                errorResponse = (GsonUtil.toClass(errorMes!!, ErrorResponse::class.java) as ErrorResponse?)!!
            }
        } else if (e is SocketTimeoutException) {
            errorResponse.message = MyApplication.getStringByResId(R.string.net_error_for_time_out)
        }
        iApiSubscriberCallBack.onError(errorResponse)
        onComplete()
    }

    override fun onNext(t: T) {
        val baseApiResultData = t as BaseApiResultData<*>
        if (baseApiResultData.status == "1") {
            //成功
            iApiSubscriberCallBack.onNext(t)
        } else {
            //用户级错误
            val errorResponse = ErrorResponse()
            errorResponse.message = baseApiResultData.resultMsg
            when (baseApiResultData.status) {

            }
            iApiSubscriberCallBack.onError(errorResponse)
        }

    }

}
