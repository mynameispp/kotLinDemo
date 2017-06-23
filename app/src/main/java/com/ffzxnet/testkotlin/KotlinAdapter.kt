package com.ffzxnet.testkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.item_main_list.view.*

/**
 * 创建者： feifan.pi 在 2017/6/14.
 */
class KotlinAdapter @JvmOverloads constructor(private var datas: MutableList<String>, clickListen: OnMainListItemClickLsiten? = null) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    //    //数据
//    private var datas: MutableList<String>? = null
//    //点击接口
    private var clickListen: OnMainListItemClickLsiten? = null

    init {
        this.clickListen = clickListen
    }

    //可能返回为null
    fun getDataList(): MutableList<String> {
        return datas
    }

    //添加数据
    fun addDatas(adds: MutableList<String>) {
        val oldSize = datas.size
        datas.addAll(adds)
        notifyItemRangeInserted(oldSize, datas.size)
    }
//    使用下面构造函数：要这样 class KotlinAdapter() ，上面的格式等同于下面的构造格式
//    constructor(datas: MutableList<String>) : this() {
//        this.datas = datas
//    }
//
//    constructor(datas: MutableList<String>, clickListen: OnMainListItemClickLsiten) : this() {
//        this.datas = datas;
//        this.clickListen = clickListen
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main_list, parent, false)
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //将默认的holder 转换为自己定义的 Myholder类型 这样就可以使用自己的定义的方法
        val myHolder: MyHolder = holder as MyHolder
        //绑定数据
//        myHolder.bingViewData(datas!!.get(position))
        //绑定数据 添加点击监听
        myHolder.bingViewData(datas[position], clickListen)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //绑定数据方法
        fun bingViewData(data: String) = with(itemView) {
            list_view_item.text = data
        }

        //绑定数据方法 with(itemView) 可以使用itemView里面的方法和属性
        // 不能无法直接使用“list_view_item” 只能findViewById 如 test()
        fun bingViewData(data: String, clickListen: OnMainListItemClickLsiten?) = with(itemView) {
            list_view_item.text = data
            list_view_item.tag = data
            list_view_item.setOnClickListener { clickListen?.onListItemClick(list_view_item.tag as String) }
        }

        fun test() {
            val item: TextView = itemView.findViewWithTag(R.id.list_view_item) as TextView
            item.text = "sss"
        }
    }

    //点击接口监听
    interface OnMainListItemClickLsiten {
        fun onListItemClick(string: String)
    }
}
