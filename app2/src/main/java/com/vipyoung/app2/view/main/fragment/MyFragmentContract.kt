package com.vipyoung.app2.view.main.fragment

import com.vipyoung.app2.base.view.BaseFragmentView
import com.vipyoung.app2.base.view.BasePresenter
import com.vipyoung.app2.base.view.BaseView
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UserInfo

/**
 * 创建者： Pi 在 2018/9/7.
 * 注释：
 */
interface MyFragmentContract {
    interface Presenter : BasePresenter {
        fun getUserInfo(loginRequest: LoginRequest)
        fun getSchoolInfo()
    }

    interface View : BaseFragmentView<Presenter> {
        fun getUserInfo(userInfo: UserInfo)
        fun getSchoolInfo(userInfo: UserInfo)
    }
}