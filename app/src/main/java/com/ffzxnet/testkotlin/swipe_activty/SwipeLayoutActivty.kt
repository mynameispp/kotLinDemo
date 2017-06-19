package com.ffzxnet.testkotlin.swipe_activty

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.ffzxnet.testkotlin.JavaAdapter
import com.ffzxnet.testkotlin.R
import com.ffzxnet.testkotlin.swipe_layout.OnLoadMoreListener
import com.ffzxnet.testkotlin.swipe_layout.OnRefreshListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_swipe.*

/**
 * 创建者： feifan.pi 在 2017/6/19.
 */

class SwipeLayoutActivty : AppCompatActivity(), OnRefreshListener, OnLoadMoreListener {

    private var adapter: JavaAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe)

        swipe_layout.setOnRefreshListener(this)
        swipe_layout.setOnLoadMoreListener(this)

        swipe_target.layoutManager = LinearLayoutManager(this)

    }

    override fun onResume() {
        super.onResume()
        autoRefresh()
    }

    override fun onRefresh() {
        swipe_target.postDelayed(Runnable {
            if (null == adapter) {
                val datas = mutableListOf<String>()
                for (i in 0..10) {
                    datas.add("data $i")
                }
                adapter = JavaAdapter(datas)
                swipe_target.adapter = adapter
            } else {
                var refres = mutableListOf<String>()
                for (i in 0..2) {
                    refres.add("刷新的data $i")
                }
                adapter!!.getDatas().addAll(0, refres)
                adapter!!.notifyItemRangeInserted(0, refres.size)
                //自动滑到刷新的数据第一条
                swipe_target.scrollToPosition(0)
            }
            swipe_layout.setRefreshing(false)
        }, 2000)
    }

    override fun onLoadMore() {
        swipe_target.postDelayed(Runnable {
            val oldIndex = adapter!!.getDatas().size
            adapter!!.getDatas().add("加载的data")
            adapter!!.getDatas().add("加载的data")
            adapter!!.getDatas().add("加载的data")
            adapter!!.notifyItemInserted(oldIndex)
            //自动滑到加载的数据第一条
            swipe_target.scrollToPosition(oldIndex)
            swipe_layout.setLoadingMore(false)
        }, 2000)
    }

    private fun autoRefresh() {
        swipe_layout.post(Runnable {
            swipe_layout.setRefreshing(true)
        })
    }
}
