package com.vipyoung.app2.base.data


import com.vipyoung.app2.util.GsonUtil
import java.io.Serializable

/**
 * 创建者： feifan.pi 在 2017/3/6.
 */

open class BaseRequest{
    var current: Int? = null//	当前页面	number	@mock=1
    var size: Int? = null//条数	number	@mock=10

    override fun toString(): String {
        return GsonUtil.toJson(this)
    }
}
