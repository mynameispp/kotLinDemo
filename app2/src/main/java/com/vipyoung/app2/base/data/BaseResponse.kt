package com.vipyoung.app2.base.data


import com.vipyoung.app2.util.GsonUtil
import java.io.Serializable

/**
 * 创建者： feifan.pi 在 2017/3/6.
 */

open class BaseResponse : Serializable {

    override fun toString(): String {
        return GsonUtil.toJson(this)
    }

}
