package com.vipyoung.app2.view

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_second.*

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：
 */
class SecondActivity : RxAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        if (null != intent.extras) {
            val userinfo = intent.extras.getSerializable("userinfo") as UserInfo
            second_text.text = userinfo.toString()
        }
    }
}
