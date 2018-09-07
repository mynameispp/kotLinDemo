package com.vipyoung.app2.util


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.TextUtils
import android.view.*
import android.widget.TextView
import com.vipyoung.testkotlin.R

/**
 * 进度弹窗
 */
class LoadingDialog : DialogFragment() {
    private var msg: String? = null
    private var loading_msg: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //去除标题栏
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        val view = inflater.inflate(R.layout.loading_layout, container, false)
        loading_msg = view.findViewById(R.id.loading_msg)
        if (!TextUtils.isEmpty(msg)) {
            loading_msg?.text = msg
        }
        return view
    }

    override fun onStart() {
        super.onStart()

        //设置动画、位置、宽度等属性（注意一：必须放在onStart方法中）
        val window = dialog.window
        if (window != null) {
            // 注意二：一定要设置Background，如果不设置，window属性设置无效
            val layoutParams = window.attributes
            layoutParams.dimAmount = 0.0f
            layoutParams.gravity = Gravity.CENTER // 位置
//            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT//宽度满屏
            window.attributes = layoutParams
        }

    }

    fun setMessage(msg: String) {
        this.msg = msg
        loading_msg?.text = msg
    }
}
