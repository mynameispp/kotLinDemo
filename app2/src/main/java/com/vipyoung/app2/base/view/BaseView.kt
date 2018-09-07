package com.vipyoung.app2.base.view

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：
 */
interface BaseView {
    //Token失效
    fun TokenInvalid()

    fun showLoadingDialog(b: Boolean)

    fun showLoadingDialog(b: Boolean, msg: String)
}