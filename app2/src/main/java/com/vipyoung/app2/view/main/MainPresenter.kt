package com.vipyoung.app2.view.main

import com.trello.rxlifecycle2.LifecycleProvider
import com.trello.rxlifecycle2.android.ActivityEvent
import org.jetbrains.annotations.NotNull

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：
 */
class MainPresenter(@NotNull val lifecycleProvider: LifecycleProvider<ActivityEvent>, @NotNull val myView: MainContract.View) : MainContract.Presenter {
    init {
        myView.setPresenter(this)
    }

    override fun start() {
        myView.initView()
    }

}