package com.vipyoung.app2.net

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

import io.reactivex.Observer
import okhttp3.ResponseBody

/**
 * @date 创建时间:2018/5/29
 * @author awa.pi
 * @description  文件下载
 */

abstract class FileDownLoadObserver<T> : Observer<T> {

    override fun onNext(t: T) {
        onDownLoadSuccess(t)
    }

    override fun onError(e: Throwable) {
        onDownLoadFail(e)
    }

    //可以重写，具体可由子类实现
    override fun onComplete() {}

    //下载成功的回调
    abstract fun onDownLoadSuccess(t: T)

    //下载失败回调
    abstract fun onDownLoadFail(throwable: Throwable)

    //下载进度监听
    abstract fun onProgress(progress: Int, total: Long)

    /**
     * 将文件写入本地
     *
     * @param responseBody 请求结果全体
     * @param destFileDir  目标文件夹
     * @param destFileName 目标文件名
     * @return 写入完成的文件
     * @throws IOException IO异常
     */
    @Throws(IOException::class)
    fun saveFile(responseBody: ResponseBody, destFileDir: String, destFileName: String): File {
        var inputStream: InputStream? = null
        val buf = ByteArray(2048)
        var len = 0
        var fos: FileOutputStream? = null
        try {
            inputStream = responseBody.byteStream()
            val total = responseBody.contentLength()
            var sum: Long = 0

            val dir = File(destFileDir)
            if (!dir.exists()) {
                dir.mkdirs()
            }
            val file = File(dir, destFileName)
            fos = FileOutputStream(file)
            len=if (inputStream != null) inputStream.read(buf) else 0
            while (len!= -1) {
                sum += len.toLong()
                fos.write(buf, 0, len)
                val finalSum = sum
                //这里就是对进度的监听回调
                onProgress((finalSum * 100 / total).toInt(), total)
                len=if (inputStream != null) inputStream.read(buf) else 0
            }
            fos.flush()

            return file

        } finally {
            try {
                inputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
