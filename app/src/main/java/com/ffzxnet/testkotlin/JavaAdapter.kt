package com.ffzxnet.testkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_main_list.view.*

/**
 * 创建者： feifan.pi 在 2017/6/14.
 */

class JavaAdapter(private var datas: MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    fun getDatas():MutableList<String>{
        return datas
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        //将默认的holder 转换为自己定义的 Myholder类型 这样就可以使用自己的定义的方法
        val myHolder:MyHolder= holder as MyHolder
        //绑定数据
        myHolder.bingViewData(datas.get(position))
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //绑定方法
        fun bingViewData(data: String) = with(itemView) {
            list_view_item.text = data
        }
    }
}
