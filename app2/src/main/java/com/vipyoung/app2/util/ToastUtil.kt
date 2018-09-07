package com.vipyoung.app2.util

import android.content.Context
import android.text.TextUtils
import android.view.Gravity
import android.widget.Toast

import com.vipyoung.app2.application.MyApplication


/**
 * 中间打印
 */
object ToastUtil {
    private var toast: Toast? = null

    fun cancleToast() {
        toast?.cancel()
    }

    /**
     * Toast
     * 对外调用
     *
     * @param msg     信息
     * @param gravity Toast显示的位置
     */
    @JvmOverloads
    fun showToastShort(msg: String?, gravity: Int = Gravity.CENTER) {
        showToastShort(MyApplication.context!!, msg, gravity)
    }

    /**
     * Toast
     * 对外调用
     *
     * @param msg     信息
     * @param gravity Toast显示的位置
     */
    @JvmOverloads
    fun showToastLong(msg: String?, gravity: Int = Gravity.CENTER) {
        if (!TextUtils.isEmpty(msg)) {
            showToastLong(MyApplication.context!!, msg, gravity)
        }
    }

    private fun showToastShort(context: Context, msg: String?, gravity: Int) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT)
        if (gravity > -1) {
            toast?.setGravity(gravity, 0, 0)
        }
        toast?.show()
    }

    private fun showToastLong(context: Context, msg: String?, gravity: Int) {
        toast?.cancel()
        toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
        if (gravity > -1) {
            toast?.setGravity(gravity, 0, 0)
        }
        toast?.show()
    }


}