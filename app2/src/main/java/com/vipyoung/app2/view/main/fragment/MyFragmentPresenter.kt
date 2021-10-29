package com.vipyoung.app2.view.main.fragment

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.FragmentEvent
import com.trello.rxlifecycle2.kotlin.bindUntilEvent
import com.vipyoung.app2.Constans
import com.vipyoung.app2.base.data.BaseApiResultData
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.app2.net.ApiImp
import com.vipyoung.app2.net.ApiSubscriber
import com.vipyoung.app2.net.ErrorResponse
import com.vipyoung.app2.util.ToastUtil
import org.jetbrains.annotations.NotNull

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：
 */
class MyFragmentPresenter(@NotNull val lifecycleProvider: LifecycleProvider<FragmentEvent>
                          , @NotNull val myView: MyFragmentContract.View) : MyFragmentContract.Presenter {
    init {
        myView.setPresenter(this)
    }

    override fun start() {
        myView.initView()
    }

    override fun getUserInfo(loginRequest: LoginRequest) {
        myView.showLoadingDialog(true, "登录中...")
        ApiImp.instance.loginByName(loginRequest)
                .bindUntilEvent(lifecycleProvider, FragmentEvent.DESTROY)
                .subscribe(object : ApiSubscriber<BaseApiResultData<UserInfo>>() {
                    override fun onSuccess(data: BaseApiResultData<UserInfo>) {
                        myView.getUserInfo(data.body)
                        Constans.appToken = data.body.token
                        getSchoolInfo()
                    }

                    override fun onCompleted() {
                    }

                    override fun onFailure(error: ErrorResponse) {
                        ToastUtil.showToastLong(error.message)
                        val userInfo = UserInfo(loginRequest.username)
                        userInfo.realname = loginRequest.username
                        userInfo.schoolName = "西红柿"
                        myView.getUserInfo(userInfo)
                        myView.showLoadingDialog(false)
                    }

                    override fun TokenInvalid() {
                        myView.TokenInvalid()
                    }
                })
    }

    override fun getSchoolInfo() {
        ApiImp.instance.getUserSchoolInfo()
                .bindUntilEvent(lifecycleProvider, FragmentEvent.DESTROY)
                .subscribe(object : ApiSubscriber<BaseApiResultData<UserInfo>>() {
                    override fun onCompleted() {
                        myView.showLoadingDialog(false)
                    }

                    override fun onFailure(error: ErrorResponse) {
                    }

                    override fun onSuccess(data: BaseApiResultData<UserInfo>) {
                        myView.getSchoolInfo(data.body)
                    }

                    override fun TokenInvalid() {
                        myView.TokenInvalid()
                    }
                })
    }
}