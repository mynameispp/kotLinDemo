package com.vipyoung.app2.view.function_test.list

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import com.vipyoung.app2.base.view.BaseActivity
import com.vipyoung.app2.util.ToastUtil
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity(), ListAdapter.AdapterListen {
    var myAdapter:ListAdapter?=null
    override fun getContentViewByBase(savedInstanceState: Bundle?): Int {
        return R.layout.activity_list
    }

    override fun createdViewByBase(savedInstanceState: Bundle?) {
        list_rv.layoutManager = LinearLayoutManager(this)

        val data = mutableListOf<AdapterBean>()
        for (i in 0 until 30) {
            data.add(AdapterBean("标题$i").apply { image = "https://t7.baidu.com/it/u=1415984692,3889465312&fm=193&f=GIF" })
        }
        myAdapter=ListAdapter(data, this)
        list_rv.adapter = myAdapter

        list_ver_btn.setOnClickListener {
            myAdapter?.isStagger=false
            list_rv.layoutManager = LinearLayoutManager(this)
        }
        list_hor_btn.setOnClickListener {
            myAdapter?.isStagger=false
            list_rv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        }
        list_grid_btn.setOnClickListener {
            myAdapter?.isStagger=false
            list_rv.layoutManager = GridLayoutManager(this,4)
        }
        list_pubu_btn.setOnClickListener {
            myAdapter?.isStagger=true
            list_rv.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
        }
    }

    override fun itemClick(data:AdapterBean) {
        ToastUtil.showToastShort("点击${data.title}")
    }
}