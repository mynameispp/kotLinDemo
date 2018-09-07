package com.vipyoung.app2.base.view

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.vipyoung.app2.util.LoadingDialog

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：Activity 父类
 */
abstract class BaseActivity : RxAppCompatActivity(), BaseView {
    lateinit var loadingDialog: LoadingDialog
    //获取视图布局，该方法不能获取布局控件
    abstract fun getContentViewByBase(savedInstanceState: Bundle?): Int

    //设置完视图后调用，该方法可以获取布局控件并操作
    abstract fun createdViewByBase(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadingDialog = LoadingDialog()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
////            window.statusBarColor = MyApplication.getColorByResId(R.color.transparent)
//        }
        setContentView(getContentViewByBase(savedInstanceState))
        createdViewByBase(savedInstanceState)
    }

    override fun TokenInvalid() {
        Toast.makeText(this, "重新登录", Toast.LENGTH_LONG).show()
    }

    override fun showLoadingDialog(b: Boolean) {
        if (b) {
            loadingDialog.show(supportFragmentManager, localClassName)
        } else {
            loadingDialog.dismiss()
        }
    }

    override fun showLoadingDialog(b: Boolean, msg: String) {
        if (b) {
            if (TextUtils.isEmpty(msg)) {
                loadingDialog.show(supportFragmentManager, localClassName)
            } else {
                loadingDialog.setMessage(msg)
                loadingDialog.show(supportFragmentManager, localClassName)
            }
        } else {
            loadingDialog.dismiss()
        }
    }
}