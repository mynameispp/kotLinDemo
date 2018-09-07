package com.vipyoung.app2.net

import android.content.Context
import java.io.IOException
import java.security.KeyManagementException
import java.security.KeyStore
import java.security.KeyStoreException
import java.security.NoSuchAlgorithmException
import java.security.cert.CertificateException
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory

/**
 * @date on 2018-05-27
 * describe 证书SSL
 * @author by PI
 */
class SslContextFactory {
    internal var sslContext: SSLContext? = null

    /**
     * 单项认证
     * @param context
     * @return
     */
    fun singleCertification(context: Context): SSLContext {
        try {
            //获取证书
            val stream = context.assets.open("https.cer")

            sslContext = SSLContext.getInstance("TLS")
            //使用默认证书
            val keystore = KeyStore.getInstance(KeyStore.getDefaultType())
            //去掉系统默认证书
            keystore.load(null)
            val certificate = CertificateFactory.getInstance("X.509").generateCertificate(stream)
            //设置自己的证书
            keystore.setCertificateEntry("undertow", certificate)
            //通过信任管理器获取一个默认的算法
            val algorithm = TrustManagerFactory.getDefaultAlgorithm()
            //算法工厂创建
            val instance = TrustManagerFactory.getInstance(algorithm)
            instance.init(keystore)
            sslContext!!.init(null, instance.trustManagers, null)
            stream.close()
        } catch (e: CertificateException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: KeyStoreException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: KeyManagementException) {
            e.printStackTrace()
        }

        return sslContext!!
    }

    companion object {
        private val CLIENT_TRUST_PASSWORD = "a3431265"//信任证书密码
        private val CLIENT_AGREEMENT = "TLS"//使用协议
        private val CLIENT_TRUST_MANAGER = "X509"
        private val CLIENT_TRUST_KEYSTORE = "BKS"
    }
}
