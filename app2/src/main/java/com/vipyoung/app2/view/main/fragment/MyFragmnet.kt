package com.vipyoung.app2.view.main.fragment

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.vipyoung.app2.base.view.BaseFragment
import com.vipyoung.app2.data.LoginRequest
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.app2.view.function_test.ContrastActivity
import com.vipyoung.app2.view.function_test.ThreadActivity
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_thread.*
import kotlinx.android.synthetic.main.fragment.*

/**
 * 创建者： Pi 在 2018/9/5.
 * 注释：
 */
class MyFragmnet : BaseFragment(), MyFragmentContract.View {
    private lateinit var myFragmentPresenter: MyFragmentPresenter

    override fun getLayoutId(): Int {
        return R.layout.fragment
    }

    override fun onMyCreateView(rootView: View, savedInstanceState: Bundle?) {

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        MyFragmentPresenter(this, this)
        myFragmentPresenter.start()
    }

    override fun initView() {
        my_edit_text.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s?.length ?: 0 > my_edit_layout.counterMaxLength) {
                    my_edit_layout.error = "超出限定字数了..."
                } else {
                    my_edit_layout.error = ""
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        login_btn.setOnClickListener {
            myFragmentPresenter.getUserInfo(LoginRequest(my_edit_text.text.toString(), edit_pwd.text.toString()))
        }

        menu1.setOnClickListener {
            startActivity(Intent(myContext,ContrastActivity::class.java))
        }

        menu2.setOnClickListener {
            startActivity(Intent(myContext,ThreadActivity::class.java))
        }
    }


    override fun setPresenter(presenter: MyFragmentContract.Presenter) {
        myFragmentPresenter = presenter as MyFragmentPresenter
    }

    override fun getUserInfo(userInfo: UserInfo) {
        user_info.text = userInfo.realname
    }

    override fun getSchoolInfo(userInfo: UserInfo) {
        user_info.text = "来自 ${userInfo.schoolName} 的 ${user_info.text} "
    }
}