package com.vipyoung.app2.data

import okhttp3.MultipartBody

/**
 * 创建者： Pi 在 2018/8/2.
 * 注释：上传文件
 */

data class UpLoadRequest(var fileUrl: MultipartBody.Part)
