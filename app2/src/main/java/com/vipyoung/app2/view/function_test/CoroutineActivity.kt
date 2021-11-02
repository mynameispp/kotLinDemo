package com.vipyoung.app2.view.function_test

import android.os.Bundle
import android.util.Log
import android.view.View
import com.vipyoung.app2.base.view.BaseActivity
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_thread.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

//携程Demo
class CoroutineActivity : BaseActivity(), View.OnClickListener, CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var ll: Job? = null

    override fun getContentViewByBase(savedInstanceState: Bundle?): Int {
        return R.layout.activity_thread
    }

    override fun createdViewByBase(savedInstanceState: Bundle?) {
        star_xiecheng.setOnClickListener(this)
        close_xiecheng.setOnClickListener(this)
        star_time_out_xiecheng.setOnClickListener(this)
        star_io_xiecheng.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.star_xiecheng -> {
                ll = launch {
                    starCoroutineLaunch()
                }
            }
            R.id.close_xiecheng -> {
                launch {
                    stopCoroutineLaunch()
                }
            }
            R.id.star_time_out_xiecheng -> {
                launch {
                    starCoroutineLaunch(3000L)
                }
            }
            R.id.star_io_xiecheng -> {
                launch {
                    starIOCoroutineLaunch()
                }
            }
        }
    }

    suspend fun starCoroutineLaunch() {
        Log.e("dddd", "开始打印\n")
        //3秒关闭
//        ll = coroutineScope {
//            launch {
        var index = 1
        while (isActive) {
            thread_content.text = "${index++}"
            delay(1000)
        }
//            }
//        }
    }

    suspend fun stopCoroutineLaunch() {
        if (ll?.isActive == true) {
            ll?.cancelAndJoin()
            Log.e("dddd", "关闭协程")
        }
    }

    suspend fun starCoroutineLaunch(timeOut: Long) {
        Log.e("dddd", "开始倒计时打印\n")
        try {
            //timeOut秒超时关闭
            withTimeout(timeOut) {
//                coroutineScope {
                    launch {
                        var index = 1
                        //                    withContext(Dispatchers.IO) {//切换到IO线程
                        while (isActive) {
                            //                            Log.e("dddd", "${index++}\n")
                            thread_content.text = "${index++}"
                            delay(1000)
                        }
                        //                    }
                    }
//                }
            }
        } finally {
            Log.e("dddd", "结束倒计时打印\n")
            thread_content.text="${timeOut/1000}秒，超时结束"
        }
    }

    suspend fun starIOCoroutineLaunch() {
        Log.e("dddd", "开始处理耗时任务\n")
        thread_content.text="开始处理耗时任务"
        //切换到IO线程
        withContext(Dispatchers.IO) {
            //网络或耗时任务，IO线程
            Log.e("dddd", "处理耗时任务中\n")
            withContext(Dispatchers.Main){
                //主线程
                thread_content.text = "处理耗时任务中"
                delay(3000)
            }
            delay(3000)
        }
        //主线程
        thread_content.text = "处理耗时任务完成"
        Log.e("dddd", "处理耗时任务完成\n")
    }
}