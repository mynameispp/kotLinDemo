package com.vipyoung.app2.view.function_test

import android.os.Bundle
import com.vipyoung.app2.base.view.BaseActivity
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.app2.util.ToastUtil
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_second.*

/**
 * 创建者： Pi 在 2020/10/29
 * 注释：值对比
 */
class ContrastActivity : BaseActivity() {
    override fun getContentViewByBase(savedInstanceState: Bundle?): Int {
        return R.layout.activity_second
    }

    override fun createdViewByBase(savedInstanceState: Bundle?) {

        val userinfo = intent.extras?.getSerializable("userinfo") as UserInfo?
        val user2 = UserInfo("小米")

        val userList = mutableListOf<UserInfo>()
        userList.add(user2)
        user2.realname = "hahah"
        val aaa = "hahah"
        val logS = StringBuilder()
        logS.append("==对象对比==${userinfo?.equals(user2) ?: false}\n")
                .append("==对象是否在列表===${userinfo in userList}\n")
                .append("==对象值对比===${user2.realname == aaa}\n")
                .append("==对象值和明文对比===${user2.realname === "hahah"}\n")
                .append("==明文字符串对比==${"aa" === "aa"}\n")

        userinfo?.let {
            userList.add(userinfo)
            logS.append("==对象是否在列表===${userinfo in userList}\n")
            logS.append("登录进来的账号：${it.realname}")
        } ?: ToastUtil.showToastLong("用户信息为空")

        //打印结果
        second_text.text = logS.toString()
    }

}
