package com.ffzxnet.testkotlin.web_view

import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.ffzxnet.testkotlin.R
import kotlinx.android.synthetic.main.activity_web_view.*
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader


/**
 * 创建者： feifan.pi 在 2017/6/16.
 */

class WebViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val setting = my_web_view.getSettings()
        setting.javaScriptEnabled = true
        setting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        setting.cacheMode = WebSettings.LOAD_NO_CACHE
        setting.setAppCacheEnabled(false)
        setting.blockNetworkImage = false
        setting.loadsImagesAutomatically = true
        setting.setGeolocationEnabled(false)
        setting.setNeedInitialFocus(false)
        setting.saveFormData = false
        setting.useWideViewPort = true
        setting.loadWithOverviewMode = true
        setting.builtInZoomControls = false
        setting.displayZoomControls = false

//        my_web_view.setVerticalScrollBarEnabled(false);
//        my_web_view.setHorizontalScrollBarEnabled(false);
//        my_web_view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //字体大小
        setting.defaultFontSize = 35
        setting.defaultTextEncodingName = "UTF-8"//设置默认为utf-8
        my_web_view.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return false
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                view.loadUrl(request.url.toString())
                return false
            }

        })
//        my_web_view.loadUrl("file:///android_asset/news4.html")
//        my_web_view.loadData(getHtmlDataBySb(getString("news3.txt")), "text/html; charset=UTF-8", null)//正确解码
        val name=intent.getStringExtra("fileName")
        my_web_view.loadData(getHtmlDataBySb(getString(name)), "text/html; charset=UTF-8", null)
    }

    /**
     * 获取标准的html

     * @param bodyHTML
     * *
     * @return
     */
    private fun getHtmlData(bodyHTML: String?): String? {
        val head = "<head><style>img{max-width: 100%; width:auto; height: auto;}</style></head>"
        return "<html>$head<body>$bodyHTML</body></html>"
    }

    private fun getHtmlDataBySb(bodyHTML: String?): String {
        val sb = StringBuffer()
        sb.append("<html>")
                .append("<head>")
                .append("<meta http-equiv='Content-Type' content='text/html'; charset='UTF-8'>")
                .append("<style type='text/css'>")
                .append(".response-img {max-width: 100%;}")
//                .append("#box {width: 100%;height: 100%;display: table;text-align: center;}")
//                .append("#box span {display: table-cell;vertical-align: middle;}")
                .append("</style>")
                .append("<title>")
                .append("</title>")
                .append("</head>")
                .append("<body style='text-align: center; font-size:35px;'>")
                .append(bodyHTML?.replace("font-size",""))
                .append("</body>")
                .append("</html>")
        return sb.toString()
    }

    fun getString(fileName: String): String? {
        var content: String? = null
        try {
            val inputStream = getAssets().open(fileName)
//            val bytes = ByteArray(1024)
//            val arrayOutputStream = ByteArrayOutputStream()
//            while (inputStream.read(bytes) != -1) {
//                arrayOutputStream.write(bytes, 0, bytes.size)
//            }
//            inputStream.close()
//            arrayOutputStream.close()
            val inputStreamReader = InputStreamReader(inputStream, "UTF-8")
            val reader = BufferedReader(inputStreamReader)
            val sb = StringBuffer()
            var line: String? = null
            line = reader.readLine()
            while (line != null) {
                sb.append(line);
                line = reader.readLine()
            }
            inputStream.close()
            inputStreamReader.close()
            reader.close()
            content = sb.toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content
    }

    override fun onDestroy() {
        super.onDestroy()
        my_web_view.stopLoading()
        my_web_view.removeAllViews()
        my_web_view.clearCache(true)
        my_web_view.destroy()
    }
}
