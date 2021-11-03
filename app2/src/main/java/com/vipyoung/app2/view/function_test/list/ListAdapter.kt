package com.vipyoung.app2.view.function_test.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.vipyoung.app2.application.GlideApp
import com.vipyoung.testkotlin.R
import kotlinx.android.synthetic.main.item_list.view.*

class ListAdapter(var listData: MutableList<AdapterBean>?, var adapterListen: AdapterListen?)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(), View.OnClickListener {

    var isStagger: Boolean = false

    interface AdapterListen {
        fun itemClick(data: AdapterBean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return MyHolder(view)
    }

    override fun getItemCount(): Int {
        return listData?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as MyHolder).setData(listData!!.get(position), isStagger)
    }

    override fun onClick(p0: View?) {
        adapterListen?.let {
            adapterListen!!.itemClick(p0!!.getTag() as AdapterBean)
        }
    }

    class MyHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun setData(data: AdapterBean, isStagger: Boolean) = with(itemView) {
            //设置标题
            item_list_txt.text = data.title
            //设置图片
            val params: ViewGroup.LayoutParams = item_list_image.layoutParams
            if (isStagger) {
                //瀑布流设置图片的相对于屏幕的宽高比
                params.width = 700 / 3
                params.height = (200 + Math.random() * 400).toInt()
                item_list_image.layoutParams = params
            } else {
                params.width = 200
                params.height = 200
                item_list_image.layoutParams = params
            }

            GlideApp.with(item_list_image)
                   .load(data.image)
                   .into(item_list_image)
        }
    }
}