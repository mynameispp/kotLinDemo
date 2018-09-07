package com.vipyoung.app2.base.view

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.trello.rxlifecycle2.components.support.RxFragment
import com.vipyoung.app2.util.LoadingDialog

/**
 * 创建者： Pi 在 2018/9/6.
 * 注释：Fragment 父类
 */
abstract class BaseFragment : RxFragment(), BaseView{
    var myContext: Context? = null
    lateinit var loadingDialog: LoadingDialog

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        myContext = context
    }

    //获取布局文件ID
    protected abstract fun getLayoutId(): Int

    protected abstract fun onMyCreateView(rootView: View, savedInstanceState: Bundle?)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(getLayoutId(), container, false)
        loadingDialog = LoadingDialog()
        onMyCreateView(view, savedInstanceState)
        return view
    }

    override fun TokenInvalid() {
        Toast.makeText(context, "Fragment 重新登录", Toast.LENGTH_LONG).show()
    }

    override fun showLoadingDialog(b: Boolean) {
        if (b) {
            loadingDialog.show(fragmentManager,javaClass.simpleName)
        } else {
            loadingDialog.dismiss()
        }
    }

    override fun showLoadingDialog(b: Boolean, msg: String) {
        if (b) {
            if (TextUtils.isEmpty(msg)) {
                loadingDialog.show(fragmentManager, javaClass.simpleName)
            } else {
                loadingDialog.setMessage(msg)
                loadingDialog.show(fragmentManager, javaClass.simpleName)
            }
        } else {
            loadingDialog.dismiss()
        }
    }
}
