package com.vipyoung.app2.net


import android.util.Log
import com.vipyoung.app2.Constans
import com.vipyoung.app2.base.data.BaseApiResultData
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UpLoadRequest
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.app2.net.retrofit_gson_factory.CustomGsonConverterFactory
import com.vipyoung.app2.net.retrofit_gson_factory.NetworkScheduler
import io.reactivex.Observable
import io.reactivex.annotations.NonNull
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.io.IOException
import java.net.HttpURLConnection
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

/**
 * @author by PI
 * @date on 2018-05-27
 * describe 服务器请求接口实现类
 */

class ApiImp {
    /**
     * 接口的服务类
     */
    private val apiService: ApiService

    private val okHttpClient: OkHttpClient

    /**
     * 初始化Retrofit配置
     */
    init {
        val mBuilder = OkHttpClient.Builder()
        //打印日志
        val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            //打印retrofit日志
            Log.e("log", it);
        })
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        mBuilder.addInterceptor(loggingInterceptor)
        //打印日志 End
        //添加head信息和Token状态重定向
        val interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val originalRequest = chain.request()
                //添加Token
                val headers = originalRequest.headers()
                val newHeaders = headers.newBuilder()
                        .add("Authorization", "bearer " + Constans.appToken ?: "")
                        .add("fromType", Constans.DEVICE_TYPE)
                        .add("systemId", Constans.SYSTEM_ID)
                        .build()
                val newReq = originalRequest.newBuilder().headers(newHeaders).build()
                //                Response response = chain.proceed(newReq);
                //                if (isTokenExpired(response)) {//根据和服务端的约定判断token过期
                //                    //同步请求方式，获取最新的Token
                //                    if (response.request().url().toString().endsWith("/auth/user/token/refresh")) {
                //                        //Token 失效后 通过 refresh token 重新获取，如果再次失败 那么就跳转到登录页
                //                        SharedPreferencesUtil.getInstance().clear();
                //                        Intent intent = new Intent(MyApplication.getContext(), LoginActivity.class);
                //                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                //                        MyApplication.getContext().startActivity(intent);
                //                    } else if (TextUtils.isEmpty(Constans.appToken)) {
                //                        getAccessToken();
                //                    } else {
                //                        refreshToken();
                //                    }
                //                    Headers headers2 = originalRequest.headers();
                //                    Headers newHeaders2 = headers2.newBuilder().add("Authorization", "bearer " + Constans.appToken)
                //                            .add("fromType", Constans.DEVICE_TYPE)
                //                            .add("systemId", Constans.SYSTEM_ID)
                //                            .build();
                //                    Request newReq2 = originalRequest.newBuilder().headers(newHeaders2).build();
                //                    return chain.proceed(newReq2);
                //                } else {
                //                return response;
                return chain.proceed(newReq)
                //                }

            }

            /**
             * 根据Response，判断Token是否失效
             *
             * @param response
             * @return
             */
            private fun isTokenExpired(response: Response): Boolean {
                return if (response.code() == 404 || response.code() == 300
                        || response.code() == HttpURLConnection.HTTP_UNAUTHORIZED) {
                    true
                } else false
            }
        }
        mBuilder.addInterceptor(interceptor)
        //添加head信息和Token状态重定向 End
        //添加证书
        //        hasSSLKey(mBuilder);
        noSSLKey(mBuilder)
        //设置Token监听
        //        setUpAuthenticator(mBuilder);
        okHttpClient = mBuilder
                .readTimeout(1, TimeUnit.MINUTES)//设置超时 3分钟
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时
                .writeTimeout(1, TimeUnit.MINUTES)//设置超时
                .build()
        val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(APPBASEURL)
                .build()
        //服务器请求接口
        apiService = retrofit.create(ApiService::class.java)
    }


    /**
     * 信任所有证书
     *
     * @param mBuilder
     */
    internal fun noSSLKey(mBuilder: OkHttpClient.Builder) {
        try {
            val trustManagers = arrayOf<TrustManager>(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(x509Certificates: Array<java.security.cert.X509Certificate>, s: String) {

                }

                @Throws(CertificateException::class)
                override fun checkServerTrusted(x509Certificates: Array<java.security.cert.X509Certificate>, s: String) {

                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }

            })
            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagers, SecureRandom())
            val emptyHostNameVerifier = EmptyHostNameVerifier()

            mBuilder.sslSocketFactory(sslContext.socketFactory)
            mBuilder.hostnameVerifier(emptyHostNameVerifier)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    internal class EmptyHostNameVerifier : HostnameVerifier {

        override fun verify(s: String, sslSession: SSLSession): Boolean {

            return true
        }
    }

    /**
     * 默认请求配置
     *
     * @param observable 请求的接口 apiService.testObservable(request)
     * */
    private fun <T> baseObservable(observable: Observable<T>): Observable<T> {
        return observable.compose(NetworkScheduler.compose())//设置事务处理线程环境
    }


    //======================= 接口实现 ========================================================

    //登录
    fun loginByName(request: LoginRequest): Observable<BaseApiResultData<UserInfo>> {
        return baseObservable(apiService.login(request))
    }


    //获取用户学校班级等信息
    fun getUserSchoolInfo(): Observable<BaseApiResultData<UserInfo>> {
        return baseObservable(apiService.getUserSchoolInfo(APPBASEURL + "/org/userNowClassSchool"))
    }

    //获取用户信息
    fun upUserInfo(): Observable<BaseApiResultData<UserInfo>> {
        return baseObservable(apiService.upUserInfo())
    }

    //下载文件
    fun downloadFile(@NonNull url: String): Observable<ResponseBody> {
        return baseObservable(apiService.downloadFile(url))
    }

    //上傳头像
    fun upLoadFile(upLoadRequest: UpLoadRequest): Observable<BaseApiResultData<String>> {
        return baseObservable(apiService.upLoadFile(APPBASEURL + "/org/qiniu/upload", upLoadRequest.fileUrl))
    }

    companion object {
        // 是否为线上环境
        val IS_PRODUCTION_ENVIRONMENT = false

        var APPBASEURL: String//接口地址
        var Umen_AppKey: String//友盟
        var Umen_Message_Secret: String//友盟

        init {
            if (IS_PRODUCTION_ENVIRONMENT) {
                APPBASEURL = "xxxx"//线上地址
                Umen_AppKey = "xxxxx"
                Umen_Message_Secret = "xxxx"
            } else {
                APPBASEURL = "http://devapi.vip-young.com"//测试地址
                Umen_AppKey = "xxxx"
                Umen_Message_Secret = "xxxxx"
            }
        }

        @Volatile
        private var apiImp: ApiImp? = null

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

}
