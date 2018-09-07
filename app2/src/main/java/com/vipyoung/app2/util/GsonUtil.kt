package com.vipyoung.app2.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

import java.lang.reflect.Type

/**
 * 创建者： feifan.pi 在 2016/9/6.
 */
object GsonUtil {
    private val gson = Gson()

    /**
     * 对象转成json
     *
     * @param o
     * @return
     */
    fun toJson(o: Any): String {
        return gson.toJson(o)
    }

    /**
     * json转成对象
     *
     * @param s json
     * @param c Object.class
     * @return
     */
    fun toClass(s: String, c: Class<*>): Any? {
        try {
            return gson.fromJson(s, c)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            return null
        }

    }

    /**
     * json转成对象
     *
     * @param s    json
     * @param type Type
     * @return
     */
    fun toClass(s: String, type: Type): Any? {
        try {
            return gson.fromJson<Any>(s, type)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            return null
        }

    }
}
