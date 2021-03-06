package com.vipyoung.app2.base.view

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：Fragment 父类接口，子类的View接口会继承它
 */
interface BaseFragmentView<T> : BaseView {
    fun initView()
    fun setPresenter(presenter: T)
}
