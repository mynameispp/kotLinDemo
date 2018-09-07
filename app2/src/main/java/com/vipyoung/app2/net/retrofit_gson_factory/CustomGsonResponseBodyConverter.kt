package com.vipyoung.app2.net.retrofit_gson_factory

import android.text.TextUtils

import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.nio.charset.Charset

import okhttp3.MediaType
import okhttp3.ResponseBody
import retrofit2.Converter

import okhttp3.internal.Util.UTF_8

/**
 * 创建者： feifan.pi 在 2017/3/14.
 */

internal class CustomGsonResponseBodyConverter<T>(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        //服务器返回的数据
        val response = value.string()
        if (TextUtils.isEmpty(response)) {
            throw ResultErrorException("服务器繁忙，请稍后重试...")
        }
        //        int code = 0;
        //        try {
        //            //查看是不是错误json
        //            JSONObject jsonObject = new JSONObject(response);
        //            //这里可以根据判断来修改json数据或格式
        //            code = jsonObject.getInt("code");
        //        } catch (JSONException e) {
        //            LogUtil.e("CustomGsonResponseBodyConverter", e.toString());
        //        }

        //        //服务端返回失败的code，可以在这里处理，也可以给ApiSubscriber onNext()方法处理
        //        if (code < 0 || code == 1) {
        //            //如果是错误的json 数据抛出异常,进入ApiSubscriber onError（）回调
        //            throw new ResultErrorException(response);
        //        }

        val contentType = value.contentType()
        val charset = if (contentType != null) contentType.charset(UTF_8) else UTF_8
        val inputStream = ByteArrayInputStream(response.toByteArray())
        val reader = InputStreamReader(inputStream, charset!!)
        val jsonReader = gson.newJsonReader(reader)
        try {
            return adapter.read(jsonReader)
        } finally {
            value.close()
        }
    }
}
