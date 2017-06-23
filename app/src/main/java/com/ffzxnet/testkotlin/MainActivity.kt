package com.ffzxnet.testkotlin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.ffzxnet.testkotlin.galleryFinal.GalleryFinalActivity
import com.ffzxnet.testkotlin.net.*
import com.ffzxnet.testkotlin.swipe_activty.SwipeLayoutActivty
import com.ffzxnet.testkotlin.swipe_layout.OnLoadMoreListener
import com.ffzxnet.testkotlin.swipe_layout.OnRefreshListener
import com.ffzxnet.testkotlin.web_view.WebViewActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), KotlinAdapter.OnMainListItemClickLsiten {

    private var adapter: KotlinAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test.text = "哈哈哈"

        val datas = mutableListOf<String>()
        for (i in 0..10) {
            datas.add("data $i")
        }

        list_view.layoutManager = LinearLayoutManager(this)
        //数据
//        list_view.adapter=JavaAdapter(datas)
        //数据+点击事件监听
        val datas2 = mutableListOf<String>()
        adapter = KotlinAdapter(datas, this)
        val da = adapter!!.getDataList()
        for ((index, dat) in da.withIndex()) {
            Log.e("dddddd", "index $index,data: $dat")
        }
        list_view.adapter = adapter

        ApiImp.instance.getBanner(object : IApiSubscriberCallBack<BaseApiResultData<List<BannerResponse>>> {
            override fun onCompleted() {
                //请求失败还是成功都会在最后调用此方法，不要问为什么，因为是我写的
                Log.e("ssss", "onCompleted")
            }

            override fun onError(error: ErrorResponse) {
                Log.e("ssss", error.message)
            }

            override fun onNext(result: BaseApiResultData<List<BannerResponse>>) {
                var addDatas= mutableListOf<String>()
                for (item in result.infoData!!) {
                    Log.e("item", "${item.title}")
                    addDatas.add(item.title!!)
                }
                adapter!!.addDatas(addDatas)
            }
        })

    }

    //实现adapter里的点击监听接口
    override fun onListItemClick(string: String) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
        when (string) {
            "data 1" -> {
                //跳转
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("fileName", "news1.txt")
                startActivity(intent)
            }
            "data 2" -> {
                //跳转
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("fileName", "news2.txt")
                startActivity(intent)
            }
            "data 3" -> {
                //跳转
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("fileName", "news3.txt")
                startActivity(intent)
            }
            "data 4" -> {
                adapter?.getDataList()?.remove("data 4")
                adapter?.notifyItemRemoved(4)
            }
            "data 5" -> {
                if (null != adapter) {
                    val index = adapter!!.getDataList().indexOf("data 5")
                    adapter?.getDataList()?.remove("data 5")
                    adapter?.notifyItemRemoved(index)
                }
            }
            "data 6" -> {
                val intent = Intent(this, GalleryFinalActivity::class.java)
                startActivity(intent)
            }
            "data 9" -> {
                val intent = Intent(this, SwipeLayoutActivty::class.java)
                startActivity(intent)
            }
            "data 10" -> {
                adapter!!.getDataList().add("data 11")
                adapter!!.notifyItemInserted(11)
            }
            else -> {
                //跳转
                val intent = Intent(this, WebViewActivity::class.java)
                intent.putExtra("fileName", "news2.txt")
                startActivity(intent)
            }
        }

    }

    class getBanner : IApiSubscriberCallBack<BaseApiResultData<List<BannerResponse>>> {
        override fun onCompleted() {
            Log.e("ssss", "onCompleted")
        }

        override fun onError(error: ErrorResponse) {
            Log.e("ssss", error.toString())
        }

        override fun onNext(result: BaseApiResultData<List<BannerResponse>>) {
            for (item in result.infoData!!) {
                Log.e("item", "${item.title}")
            }
        }
    }


}
