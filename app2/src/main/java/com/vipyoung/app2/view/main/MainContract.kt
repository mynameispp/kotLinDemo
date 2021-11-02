package com.vipyoung.app2.view.main

import com.vipyoung.app2.base.view.BaseActivityView
import com.vipyoung.app2.base.view.BasePresenter
import com.vipyoung.app2.base.view.BaseView
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UserInfo

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：
 */
interface MainContract {
    interface Presenter : BasePresenter {
    }

    interface View : BaseActivityView<Presenter> {
    }
}