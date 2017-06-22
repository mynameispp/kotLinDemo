package com.ffzxnet.testkotlin.galleryFinal

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import cn.finalteam.galleryfinal.widget.GFImageView
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.drawable.ProgressBarDrawable
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder
import com.facebook.drawee.view.DraweeHolder
import com.facebook.imagepipeline.common.ResizeOptions
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.request.ImageRequestBuilder

/**
 * 创建者： feifan.pi 在 2017/6/19.
 */

class FrescoImageLoader @JvmOverloads constructor(private val context: Context, config: Bitmap.Config = Bitmap.Config.RGB_565) : cn.finalteam.galleryfinal.ImageLoader {

    init {
        val imagePipelineConfig = ImagePipelineConfig.newBuilder(context)
                .setBitmapsConfig(config)
                .build()
        Fresco.initialize(context, imagePipelineConfig)
    }

    override fun displayImage(activity: Activity, path: String, imageView: GFImageView, defaultDrawable: Drawable, width: Int, height: Int) {
        val resources = context.resources
        val hierarchy = GenericDraweeHierarchyBuilder(resources)
                .setFadeDuration(300)
                .setPlaceholderImage(defaultDrawable)
                .setFailureImage(defaultDrawable)
                .setProgressBarImage(ProgressBarDrawable())
                .build()

        val draweeHolder = DraweeHolder.create(hierarchy, context)
        imageView.setOnImageViewListener(object : GFImageView.OnImageViewListener {
            override fun onDetach() {
                draweeHolder.onDetach()
            }

            override fun onAttach() {
                draweeHolder.onAttach()
            }

            override fun verifyDrawable(dr: Drawable): Boolean {
                if (dr === draweeHolder.hierarchy.topLevelDrawable) {
                    return true
                }
                return false
            }

            override fun onDraw(canvas: Canvas) {
                val drawable = draweeHolder.hierarchy.topLevelDrawable
                if (drawable == null) {
                    imageView.setImageDrawable(defaultDrawable)
                } else {
                    imageView.setImageDrawable(drawable)
                }
            }
        })
        val uri = Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(path)
                .build()
        val imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setResizeOptions(ResizeOptions(width, height))//图片目标大小
                .build()
        val controller = Fresco.newDraweeControllerBuilder()
                .setOldController(draweeHolder.controller)
                .setImageRequest(imageRequest)
                .build()
        draweeHolder.controller = controller
    }

    override fun clearMemoryCache() {

    }
}
