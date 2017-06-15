package com.ffzxnet.testkotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.ffzxnet.testkotlin.net.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),KotlinAdapter.OnMainListItemClickLsiten {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test.text="哈哈哈"

        val datas= mutableListOf<String>()
        for (i in 0..10){
            datas.add("data $i")
        }

        list_view.layoutManager=LinearLayoutManager(this)
        //数据
//        list_view.adapter=JavaAdapter(datas)
        //数据+点击事件监听
        list_view.adapter=KotlinAdapter(datas,this)

        ApiImp.instance.getBanner(object:IApiSubscriberCallBack<BaseApiResultData<List<BannerResponse>>>{
            override fun onCompleted() {
                //请求失败还是成功都会在最后调用此方法，不要问为什么，因为是我写的
                Log.e("ssss","onCompleted")
            }

            override fun onError(error: ErrorResponse) {
                Log.e("ssss",error.message)
            }

            override fun onNext(result: BaseApiResultData<List<BannerResponse>>) {
                for (item in result.infoData!!){
                    Log.e("item","${item.title}")
                }
            }
        })

    }
    //实现adapter里的点击监听接口
    override fun onListItemClick(string: String) {
        Toast.makeText(this,string,Toast.LENGTH_SHORT).show()
    }

    class getBanner :IApiSubscriberCallBack<BaseApiResultData<List<BannerResponse>>>{
        override fun onCompleted() {
            Log.e("ssss","onCompleted")
        }

        override fun onError(error: ErrorResponse) {
            Log.e("ssss",error.toString())
        }

        override fun onNext(result: BaseApiResultData<List<BannerResponse>>) {
            for (item in result.infoData!!){
                Log.e("item","${item.title}")
            }
        }
    }
}
