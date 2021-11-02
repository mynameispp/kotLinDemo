package com.vipyoung.app2.view.function_test

import android.os.Bundle
import android.text.TextUtils
import com.google.gson.reflect.TypeToken
import com.vipyoung.app2.base.view.BaseActivity
import com.vipyoung.app2.data.UserInfo
import com.vipyoung.app2.util.GsonUtil
import com.vipyoung.app2.util.ToastUtil
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_genericity.*

//泛型Demo
class GenericityActivity : BaseActivity() {
    override fun getContentViewByBase(savedInstanceState: Bundle?): Int {
        return R.layout.activity_genericity
    }

    override fun createdViewByBase(savedInstanceState: Bundle?) {
        val userInfo = UserInfo("小马").apply {
            realname = "马哥"
            userCode = "001"
            schoolName = "疼训书院"
        }

        //单个对象转换json
        to_json.setOnClickListener {
            json_info.text = GsonUtil.toJson(userInfo)
        }

        //json转换单个对象
        to_class.setOnClickListener {
            if (!TextUtils.isEmpty(json_info.text.toString())) {
                val userInfo2 = GsonUtil.toClass(json_info.text.toString(), UserInfo::class.java) as UserInfo?
                userInfo2?.let {
                    ToastUtil.showToastShort(userInfo2.username)
                } ?: ToastUtil.showToastShort("解析失败")
            }
        }

        //列表对象转换json
        to_list_json.setOnClickListener {
            val listUserInfo= mutableListOf<UserInfo>()
            listUserInfo.add(userInfo)
            listUserInfo.add(userInfo)
            listUserInfo.add(userInfo)
            json_info.text = GsonUtil.toJson(listUserInfo)
        }

        //json转换列表对象
        to_list_class.setOnClickListener {
            if (!TextUtils.isEmpty(json_info.text.toString())) {
                val userInfoList = GsonUtil.toClass(json_info.text.toString()
                        , object :TypeToken<MutableList<UserInfo>>(){}.type) as MutableList<UserInfo>?
                userInfoList?.let {
                    ToastUtil.showToastShort("列表共有${userInfoList.size}个对象")
                } ?: ToastUtil.showToastShort("解析失败")
            }
        }

        //Map对象转换json
        to_map_json.setOnClickListener {
            val listUserInfo= mutableMapOf<String,UserInfo>()
            listUserInfo["1"] = userInfo
            listUserInfo["2"] = userInfo
            listUserInfo.put("3",userInfo)
            listUserInfo.put("4",userInfo)
            listUserInfo.put("5",userInfo)
            json_info.text = GsonUtil.toJson(listUserInfo)
        }

        //json转换Map对象
        to_map_class.setOnClickListener {
            if (!TextUtils.isEmpty(json_info.text.toString())) {
                val userInfoList = GsonUtil.toClass(json_info.text.toString()
                        , object :TypeToken<MutableMap<String,UserInfo>>(){}.type) as MutableMap<String,UserInfo>?
                userInfoList?.let {
                    ToastUtil.showToastShort("Map共有${userInfoList.size}个对象")
                } ?: ToastUtil.showToastShort("解析失败")
            }
        }
    }
}