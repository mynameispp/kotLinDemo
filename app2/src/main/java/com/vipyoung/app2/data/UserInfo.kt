package com.vipyoung.app2.data

import com.vipyoung.app2.base.data.BaseResponse

/**
 * @author awa.pi
 * @date 创建时间:2018/5/28
 * @description 用户信息实体类
 */
data class UserInfo(var username: String?) : BaseResponse() {
    var avatar: String? = null//头像
    var birthday: String? = null
    var gender: String? = null
    var introductionBase: String? = null
    var introductionDetail: String? = null
    var realname: String? = null
    var phone: String? = null
    var schoolId: String? = null
    var siteUrl: String? = null//接口地址
    var token: String? = null
    var userCode: String? = null//用户唯一标示
    var bgImage: String? = null//用户中心背景图
    var roleType: Int = 0//账号角色（0=学生，1=老师，2=管理员）

    //获取学生学校班级接口的
    var schoolLogo: String? = null//开机启动页
    var maxBaseVideoScore: Double = 0.toDouble()//语音蓝色比例
    var minBaseVideoScore: Double = 0.toDouble()//语音白色比例
    var schoolName: String? = null//学校名称
    var classCode: String? = null//班级Code
    var standard: String? = null//学生层级


    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (this === other) {
            return true
        }
        val className = other.javaClass.name
        if (className != this.javaClass.name) {
            return false
        }
        return if (other is UserInfo) {
            other.username == username
        } else {
            false
        }
    }
}
