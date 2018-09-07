package com.vipyoung.app2.application

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.StrictMode
import android.support.v4.content.ContextCompat


/**
 * 创建者： feifan.pi 在 2017/5/5.
 */

class MyApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        //        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //            language = getResources().getConfiguration().getLocales().get(0);
        //        } else {
        //            language = getResources().getConfiguration().locale;
        //        }
        //处理Android 7.0给出一个file://格式的URI会抛出FileUriExposedException
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }
        //获取手机屏幕大小
//        Constans.Screen_Width = ScreenUtils.getScreenWidth(context)
//        Constans.Screen_Height = ScreenUtils.getScreenHeight(context)
//        Constans.Screen_Status_Height = ScreenUtils.getStatusHeight(context)

    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallback(callbacks: Application.ActivityLifecycleCallbacks) {
        registerActivityLifecycleCallbacks(callbacks)
    }


    companion object {
        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
            private set

        fun getStringByResId(strId: Int): String {
            return MyApplication.context!!.resources.getString(strId)
        }

        fun getColorByResId(colorId: Int): Int {
            return ContextCompat.getColor(MyApplication.context!!, colorId)
        }

        fun getDrawableByResId(drawableId: Int): Drawable? {
            return ContextCompat.getDrawable(MyApplication.context!!, drawableId)
        }
    }
}
