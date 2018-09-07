package com.vipyoung.app2.net

import android.text.TextUtils
import okhttp3.ResponseBody
import java.io.*

/**
 * @author awa.pi
 * @date 创建时间:2018/5/29
 * @description 下载管理
 */

object DownLoadManager {
    private val TAG = "DownLoadManager"

    private val APK_CONTENTTYPE = "application/vnd.android.package-archive"

    private val PNG_CONTENTTYPE = "image/png"

    private val JPG_CONTENTTYPE = "image/jpg"

    private val JPEG_CONTENTTYPE = "image/jpeg"

    private val ZIP_CONTENTTYPE = "application/zip"

    private val Mp3_CONTENTTYPE = "audio/mp3"

    private var fileSuffix = ""

    /**
     * test Code
     * ApiImp.getInstance().downloadFile("http://a.hiphotos.baidu.com/image/pic/item/d009b3de9c82d1584cab701b8c0a19d8bc3e426a.jpg"
     * , null, new IApiSubscriberCallBack<ResponseBody>() {
     *
     * @param body
     * @param filePath 文件完整路径和文件名称，不需要文件的后缀
     * @return
     * @Override public void onCompleted() {
     *
     *
     * }
     * @Override public void onError(ErrorResponse error) {
     *
     *
     * }
     * @Override public void onNext(ResponseBody responseBody) {
     * String filePath = FileUtil.getSdcardRootDirectory();
     * String fileName =  SystemClock.currentThreadTimeMillis()+"";
     * if (DownLoadManager.writeResponseBodyToDisk(responseBody, filePath, fileName)) {
     * ToastUtil.showToastShort("下载成功");
     * } else {
     * ToastUtil.showToastShort("下载失败，请重新下载");
     * }
     * }
     * });
     * @description 下载文件
    </ResponseBody> */
    fun writeResponseBodyToDisk(body: ResponseBody, filePath: String, fileName: String): Boolean {


        val type = body.contentType()!!.toString()

        if (type == APK_CONTENTTYPE) {
            fileSuffix = ".apk"
        } else if (type == PNG_CONTENTTYPE) {
            fileSuffix = ".png"
        } else if (type == JPG_CONTENTTYPE || type == JPEG_CONTENTTYPE) {
            fileSuffix = ".jpg"
        } else if (type.endsWith(ZIP_CONTENTTYPE)) {
            fileSuffix = ".zip"
        } else if (type.endsWith(Mp3_CONTENTTYPE)) {
            fileSuffix = ".mp3"
        } else {
            fileSuffix = ".mp3"
        }

        // 其他类型同上 自己判断加入.....

        if (TextUtils.isEmpty(fileSuffix)) {
            //没适配到对应的资源
            return false
        }

        try {
            // todo change the file location/name according to your needs
            val path = File(filePath)
            if (!path.exists()) {
                path.mkdirs()
            }
            val fileAbsolutePath = filePath + fileName
            val file = File(fileAbsolutePath)
            if (!file.exists()) {
                file.createNewFile()
            }
            val futureStudioIconFile = File(fileAbsolutePath)
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null

            try {
                val fileReader = ByteArray(4096)

                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0

                inputStream = body.byteStream()
                outputStream = FileOutputStream(futureStudioIconFile)

                while (true) {
                    val read = inputStream!!.read(fileReader)

                    if (read == -1) {
                        break
                    }

                    outputStream.write(fileReader, 0, read)

                    fileSizeDownloaded += read.toLong()

                }

                outputStream.flush()


                return true
            } catch (e: IOException) {
                return false
            } finally {
                inputStream?.close()

                outputStream?.close()
            }
        } catch (e: IOException) {
            return false
        }

    }
}
