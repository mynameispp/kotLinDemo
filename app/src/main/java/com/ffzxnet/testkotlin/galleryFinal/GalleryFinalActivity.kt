package com.ffzxnet.testkotlin.galleryFinal

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import cn.finalteam.galleryfinal.GalleryFinal

import com.ffzxnet.testkotlin.R
import kotlinx.android.synthetic.main.activity_gallery_final.*
import com.ffzxnet.testkotlin.MainActivity
import cn.finalteam.galleryfinal.FunctionConfig
import cn.finalteam.galleryfinal.model.PhotoInfo
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.backends.pipeline.PipelineDraweeController
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.request.ImageRequest
import com.facebook.imagepipeline.request.ImageRequestBuilder
import com.facebook.imagepipeline.request.ImageRequestBuilder.newBuilderWithSource


class GalleryFinalActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_final)

        single.setOnClickListener(this)
        more_check.setOnClickListener(this)
        open_camera.setOnClickListener(this)
        crop.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.single -> {
                //单选
                GalleryFinal.openGallerySingle(1000, object : GalleryFinal.OnHanlderResultCallback {
                    override fun onHanlderSuccess(reqeustCode: Int, resultList: MutableList<PhotoInfo>?) {
                        for (item in resultList!!) {
                            Log.e("图片地址", item.photoPath)
                            showByFresco(item)
                        }
                    }

                    override fun onHanlderFailure(requestCode: Int, errorMsg: String?) {
                        Log.e("获取图片报错", errorMsg)
                    }
                })
                //带配置
//                GalleryFinal.openGallerySingle(1000, functionConfig, mOnHanlderResultCallback);
            }
            R.id.more_check -> {
                //多选
//                GalleryFinal.openGalleryMuti(1001, mOnHanlderResultCallback)
                //带配置
                val config = FunctionConfig.Builder()
                        .setMutiSelectMaxSize(9)
                        .build()
                GalleryFinal.openGalleryMuti(1001, config, object : GalleryFinal.OnHanlderResultCallback {
                    override fun onHanlderSuccess(reqeustCode: Int, resultList: MutableList<PhotoInfo>?) {
                        for (item in resultList!!) {
                            Log.e("图片地址", item.photoPath)
                        }
                    }

                    override fun onHanlderFailure(requestCode: Int, errorMsg: String?) {
                        Log.e("获取图片报错", errorMsg)
                    }
                })
            }
            R.id.open_camera -> {
                //打开照相机
                GalleryFinal.openCamera(1002, object : GalleryFinal.OnHanlderResultCallback {
                    override fun onHanlderSuccess(reqeustCode: Int, resultList: MutableList<PhotoInfo>?) {
                        for (item in resultList!!) {
                            Log.e("图片地址", item.photoPath)
                        }
                    }

                    override fun onHanlderFailure(requestCode: Int, errorMsg: String?) {
                        Log.e("获取图片报错", errorMsg)
                    }
                });
                //带配置
//                GalleryFinal.openCamera(1002, functionConfig, mOnHanlderResultCallback);
            }
            R.id.crop -> {
                //裁剪
                GalleryFinal.openCrop(1003, "/storage/emulated/0/GalleryFinal/edittemp/1495527877106_crop_crop.jpg", object : GalleryFinal.OnHanlderResultCallback {
                    override fun onHanlderSuccess(reqeustCode: Int, resultList: MutableList<PhotoInfo>?) {
                        for (item in resultList!!) {
                            Log.e("图片地址", item.photoPath)
                        }
                    }

                    override fun onHanlderFailure(requestCode: Int, errorMsg: String?) {
                        Log.e("获取图片报错", errorMsg)
                    }
                })
                //带配置
//                GalleryFinal.openCrop(1003, functionConfig, mOnHanlderResultCallback);
            }
        }
    }

    private fun showByFresco(info:PhotoInfo){
        fresco_img.setImageURI(Uri.parse(info.photoPath))
//        val request = newBuilderWithSource(Uri.parse(info.photoPath))
////                .setProgressiveRenderingEnabled(true)//支持图片渐进式加载
//                .setResizeOptions(ResizeOptions(info.width, info.height))
//                //缩放,在解码前修改内存中的图片大小, 配合Downsampling可以处理所有图片,否则只能处理jpg,
//                // 开启Downsampling:在初始化时设置.setDownsampleEnabled(true)
//                .build()
//
//        val controller = Fresco.newDraweeControllerBuilder()
//                .setImageRequest(request)
//                .setOldController(fresco_img.getController())
//                .setAutoPlayAnimations(true) //自动播放gif动画
//                .build() as PipelineDraweeController
//
//        fresco_img.setController(controller)
    }

}
