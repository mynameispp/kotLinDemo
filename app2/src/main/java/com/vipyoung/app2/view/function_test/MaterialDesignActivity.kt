package com.vipyoung.app2.view.function_test

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.vipyoung.app2.application.GlideApp
import com.vipyoung.app2.base.view.BaseActivity
import com.vipyoung.app2.util.ToastUtil
import com.vipyoung.app2.view.function_test.list.AdapterBean
import com.vipyoung.app2.view.function_test.list.ListAdapter
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.activity_material_design.*

class MaterialDesignActivity : BaseActivity(), ListAdapter.AdapterListen {
    override fun getContentViewByBase(savedInstanceState: Bundle?): Int {
        return R.layout.activity_material_design
    }

    override fun createdViewByBase(savedInstanceState: Bundle?) {
        GlideApp.with(material_design_toolbar_bg)
                .load("https://t7.baidu.com/it/u=1415984692,3889465312&fm=193&f=GIF")
                .into(material_design_toolbar_bg)
        toolbar.title = "Material Design风格控件"
//        setSupportActionBar(toolbar)

        material_design_fb.setOnClickListener {
            Snackbar.make(it, "点击FB", Snackbar.LENGTH_SHORT).show()
        }

        val data = mutableListOf<AdapterBean>()
        for (i in 0 until 30) {
            data.add(AdapterBean("标题$i").apply { image = "https://t7.baidu.com/it/u=1415984692,3889465312&fm=193&f=GIF" })
        }
        material_design_rv.layoutManager = LinearLayoutManager(this)
        val myAdapter = ListAdapter(data, this)
        material_design_rv.adapter = myAdapter
    }

    override fun itemClick(data: AdapterBean) {
        ToastUtil.showToastShort("点击列表")
    }
}