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

    //任务卡片当前选中的位置
    var TaskSelectPostion = 0

    //用户点击做题暂停
    var userClickPause = false


    //百度语音合成
    /**
     * 发布时请替换成自己申请的appId appKey 和 secretKey。注意如果需要离线合成功能,请在您申请的应用中填写包名。
     * 本demo的包名是com.baidu.tts.sample，定义在build.gradle中。
     */
    //    public static String bd_appId = "10581257";
    //    public static String bd_appKey = "pFN2fOAi72xo58i9Eo6fGLUo";
    //    public static String bd_secretKey = "1UhCPsYl7W7uqth42GbCRObyuaIutii0";
    //    // TtsMode.MIX; 离在线融合，在线优先； TtsMode.ONLINE 纯在线； 没有纯离线
    //    public static TtsMode ttsMode = TtsMode.MIX;
    //    // 离线发音选择，VOICE_FEMALE即为离线女声发音。
    //    // assets目录下bd_etts_speech_female.data为离线男声模型；bd_etts_speech_female.data为离线女声模型
    //    public static String offlineVoice = OfflineResource.VOICE_FEMALE;
}
