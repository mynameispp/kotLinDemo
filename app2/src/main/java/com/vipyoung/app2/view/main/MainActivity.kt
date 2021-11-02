package com.vipyoung.app2.view.main

import android.os.Bundle
import android.widget.RadioGroup
import com.vipyoung.app2.base.view.BaseActivity
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.app2.view.main.fragment.MyFragmnet
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), MainContract.View, RadioGroup.OnCheckedChangeListener {
    private lateinit var userInfo: UserInfo
    private lateinit var mainPresenter: MainPresenter

    override fun getContentViewByBase(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main
    }

    override fun createdViewByBase(savedInstanceState: Bundle?) {
        MainPresenter(this, this)
        mainPresenter.start()
    }


    override fun initView() {
        //初始化
        main_menu_layout.setOnCheckedChangeListener(this)
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_content, MyFragmnet(), "MyFragment").commit()
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mainPresenter = presenter as MainPresenter
    }


    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when (p1) {
            R.id.main_menu1 -> {

            }
            R.id.main_menu2 -> {

            }
        }
    }
}
