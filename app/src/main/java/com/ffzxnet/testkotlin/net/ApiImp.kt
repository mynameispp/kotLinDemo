package com.ffzxnet.testkotlin.net


import android.net.ParseException
import android.text.TextUtils
import android.util.Log
import com.ffzxnet.testkotlin.R
import com.ffzxnet.testkotlin.application.MyApplication
import com.ffzxnet.testkotlin.net.retrofit_gson_factory.CustomGsonConverterFactory
import com.ffzxnet.testkotlin.net.retrofit_gson_factory.ResultErrorException
import com.google.gson.JsonParseException
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.net.ConnectException
import java.net.HttpURLConnection
import java.util.concurrent.TimeUnit

/**
 * 创建者： feifan.pi 在 2017/2/20.
 */

class ApiImp {
    /**
     * 接口的服务类
     */
    private val apiService: ApiService

    /**
     * 初始化Retrofit配置
     */
    init {
        val mBuilder = OkHttpClient.Builder()
        //10秒超时设定
        mBuilder.connectTimeout(10, TimeUnit.SECONDS)
        //打印日志
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
            //打印retrofit日志
            Log.e("RetrofitLog", "RetrofitLog = " + message)
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        mBuilder.addInterceptor(loggingInterceptor)
        //打印日志 End

        val okHttpClient = mBuilder.build()
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APIURL)
                .build()
        //服务器请求接口
        apiService = retrofit.create(ApiService::class.java)
    }

    /**
     * 默认请求配置

     * @param observable 请求的接口 apiService.testObservable(request)
     * *
     * @param observer   结果监听 ApiSubscriber<TestResponse></TestResponse><TestPojo>> observer
    </TestPojo> */
    private fun <T> baseObservableSetting(observable: Observable<*>, observer: IApiSubscriberCallBack<T>) {
        observable.subscribeOn(Schedulers.io()) //在后台线程处理请求
                .observeOn(AndroidSchedulers.mainThread()) //请求结果到主线程
                .subscribe({ result ->
                    //转换成数据最初的一级
                    // as? =“安全的”（可空）转换操作符 为了避免抛出异常，可以使用安全转换操作符 as?，它可以在失败时返回 null
                    val baseApiResultData: BaseApiResultData<*>? = result as? BaseApiResultData<*>
                    if ("10000" == baseApiResultData!!.status) {
                        //成功
                        observer.onNext(result as T)
                    } else {
                        //数据错误
                        val errorResponse = ErrorResponse()
                        val status = -3
                        errorResponse.code = 10012
                        errorResponse.message = "失败"
                        observer.onError(errorResponse)
                    }
                }, { error ->
                    //服务失败
                    errorMsg(observer, error)
                }, {
                    //Completed
                    observer.onCompleted()
                })
    }

    //登陆
    fun login(request: String, observer: IApiSubscriberCallBack<BaseApiResultData<String>>) {
        baseObservableSetting(apiService.login(request.toString()), observer)
    }

    //获取首页banner
    fun getBanner(observer: IApiSubscriberCallBack<BaseApiResultData<List<BannerResponse>>>) {
        baseObservableSetting(apiService.banner, observer)
    }

    //静态区
    companion object {
        //主地址  192.168.2.195:50510   kart.ffzxnet.com
        var APPBASEURL = "http://192.168.5.22:8081/"
        //请求地址
        var APIURL = APPBASEURL + "adel-admin/app/"

        @Volatile private var apiImp: ApiImp? = null

        //单例模式
        // <<< 在这里创建临时变量
        val instance: ApiImp
            get() {
                var inst = apiImp
                if (inst == null) {
                    synchronized(ApiImp::class.java) {
                        inst = apiImp
                        if (inst == null) {
                            inst = ApiImp()
                            apiImp = inst
                        }
                    }
                }
                return inst!!
            }
    }

    //错误处理
    private fun errorMsg(observer: IApiSubscriberCallBack<*>, e: Throwable) {
        var errorResponse = ErrorResponse()
        errorResponse.code = -3
        errorResponse.message = e.message
        if (e is HttpException) {
            val httpException = e as HttpException
            when (httpException.code()) {
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
                errorResponse = GsonUtil.toClass(errorMes!!, ErrorResponse::class.java) as ErrorResponse
            }
        }
        observer.onError(errorResponse)
        observer.onCompleted()
    }
}