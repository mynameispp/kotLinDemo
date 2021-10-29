package com.vipyoung.app2

import com.vipyoung.app2.data.UserInfo

/**
 * 创建者： feifan.pi 在 2017/5/23.
 */

object Constans {
    //appToken
    var appToken: String? = null
    //手机系统
    val DEVICE_TYPE = "android"
    var SYSTEM_ID = "1"
    var appLife = false

    //屏幕大小和状态栏
    var Screen_Width: Int = 0
    var Screen_Height: Int = 0
    var Screen_Status_Height: Int = 0

    //=============   跳转传参标识  ================/
    //传入界面标题名称
    var Key_Title_Name = "Key_Title_Name"
    //适合要接受一个id的传值，需要传多个的id自己再差固件
    var Key_Id = "Key_Id"
    //标识type类型
    var KEY_TYPE = "Key_Type"
    //标识状态
    var KEY_STATUS = "KEY_STATUS"
    //传列表数据键
    var KEY_LIST_DATA = "KEY_LIST_DATA"
    //传单个数据键
    var KEY_DATA = "KEY_DATA"
    //传单个数据键2
    var KEY_DATA_2 = "KEY_DATA_2"
    //=============   跳转传参标识 End  ================/

    //用户信息
    var userInfo: UserInfo? = null


}
