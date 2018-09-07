package com.vipyoung.app2.net

import android.util.SparseArray

/**
 * 根据后台返回的Code显示对应的提示语句
 * 创建者： awa.pi 在 2018/5/15.
 */

object ErrorCodeMessageUtil {
    var errorCodeMessageMap: SparseArray<String>

    init {
        //添加提示语句
        errorCodeMessageMap = SparseArray()
        errorCodeMessageMap.put(10032, "你已申请过，请等待群管理员审核")
        errorCodeMessageMap.put(202, "账号或密码有误")
    }

    fun getMessageByErrorCode(code: Int): String {
        return errorCodeMessageMap.get(code)?:"未知错误"
    }
}
