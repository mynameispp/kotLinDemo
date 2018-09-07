package com.vipyoung.app2.view.main

import android.content.Intent
import android.os.Bundle
import com.vipyoung.app2.base.view.BaseActivity
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.app2.view.SecondActivity
import com.vipyoung.app2.view.main.fragment.MyFragmnet
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View {
    private lateinit var userInfo: UserInfo
    private lateinit var mainPresenter: MainPresenter

    override fun getContentViewByBase(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun createdViewByBase(savedInstanceState: Bundle?) {
        MainPresenter(this, this)
        mainPresenter.start()
    }


    override fun getUserInfo(userInfo: UserInfo) {
        this.userInfo = userInfo
        student_name.text = "Acticty haha ${userInfo.realname}"
    }

    override fun getSchoolInfo(userInfo: UserInfo) {
        this.userInfo.schoolName=userInfo.schoolName
        school_name.text = "Acticty heihei ${userInfo.schoolName}"
    }

    override fun initView() {
        //初始化
        student_name.setOnClickListener {
            val intent = Intent(it.context, SecondActivity::class.java)
            val bundle = Bundle()
            userInfo.realname="你好，小${userInfo.realname}"
            bundle.putSerializable("userinfo", userInfo)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_content, MyFragmnet(), "MyFragment").commit()
        //获取用户信息
        mainPresenter.getUserInfo(LoginRequest("xxx", "xxxx"))
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mainPresenter = presenter as MainPresenter
    }
}
