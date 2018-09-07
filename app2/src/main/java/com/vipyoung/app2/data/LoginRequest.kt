package com.vipyoung.app2.data

import com.vipyoung.app2.base.data.BaseRequest

/**
 * 创建者： awa.pi 在 2018/5/28.
 */

data class LoginRequest(
        /**
         * 用户名
         */
        var username: String?,
        /**
         * 密码
         */
        var password: String?) : BaseRequest() {

    var clientType = 0

}
