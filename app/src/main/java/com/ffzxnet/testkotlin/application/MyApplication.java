package com.ffzxnet.testkotlin.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.multidex.MultiDex;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.memory.MemoryTrimType;
import com.facebook.common.memory.MemoryTrimmable;
import com.facebook.common.memory.MemoryTrimmableRegistry;
import com.facebook.common.memory.NoOpMemoryTrimmableRegistry;
import com.facebook.common.util.ByteConstants;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.QualityInfo;

import java.util.Locale;

/**
 * 创建者： feifan.pi 在 2017/5/5.
 */

public class MyApplication extends Application {
    private static Context context;
    public static int Screen_Width;//手机屏幕宽度
    public static int Screen_Height;//手机屏幕高度
    //默认中文
    public static Locale language;

    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();
//        if (initOOM()) {
//            return;
//        }
        context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            language = getResources().getConfiguration().getLocales().get(0);
        } else {
            language = getResources().getConfiguration().locale;
        }
//        Screen_Width = ScreenUtils.getScreenWidth(context);
//        Screen_Height = ScreenUtils.getScreenHeight(context);
        initFresco();
        initPing();
    }

    /**
     * 初始化监听内存溢出框架
     */
//    private boolean initOOM() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return true;
//        }
//        LeakCanary.install(this);
//        return false;
//    }

    /**
     * 初始化第三方支付
     */
    private void initPing() {
    }

    public static Context getContext() {
        return context;
    }

    private void initFresco() {
        //渐进式加载
        ProgressiveJpegConfig progressiveJpegConfig = new ProgressiveJpegConfig() {
            @Override
            public int getNextScanNumberToDecode(int i) {
                return 0;
            }

            @Override
            public QualityInfo getQualityInfo(int i) {
                return null;
            }
        };

        //当内存紧张时采取的措施
        MemoryTrimmableRegistry memoryTrimmableRegistry = NoOpMemoryTrimmableRegistry.getInstance();
        memoryTrimmableRegistry.registerMemoryTrimmable(new MemoryTrimmable() {
            @Override
            public void trim(MemoryTrimType trimType) {
                final double suggestedTrimRatio = trimType.getSuggestedTrimRatio();
                Log.e("Fresco", String.format("onCreate suggestedTrimRatio : %d", suggestedTrimRatio));
                if (MemoryTrimType.OnCloseToDalvikHeapLimit.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInBackground.getSuggestedTrimRatio() == suggestedTrimRatio
                        || MemoryTrimType.OnSystemLowMemoryWhileAppInForeground.getSuggestedTrimRatio() == suggestedTrimRatio
                        ) {
                    //清除内存缓存
                    Fresco.getImagePipeline().clearMemoryCaches();
                }
            }
        });

        //内存配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(this)
                .setBaseDirectoryName("fresco_cache")//缓存文件名
                .setMaxCacheSize(40 * ByteConstants.MB)//最大缓存
                .setMaxCacheSizeOnLowDiskSpace(10 * ByteConstants.MB)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(2 * ByteConstants.MB)//缓存的最大大小,当设备极低磁盘空间
                .build();

        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(this)
                .setProgressiveJpegConfig(progressiveJpegConfig)//渐进式
                .setBitmapsConfig(Bitmap.Config.RGB_565)//图片等级
                .setMainDiskCacheConfig(diskCacheConfig)//总体内存配置
                .setDownsampleEnabled(true)//要不要向下采样,它处理图片的速度比常规的裁剪scaling更快，同时支持jpg,png，wep格式图片处理
                .setResizeAndRotateEnabledForNetwork(true)
                .setBitmapMemoryCacheParamsSupplier(new FrescoBitmapMemoryCacheSupplier((ActivityManager)
                        getSystemService(ACTIVITY_SERVICE)))
                .setMemoryTrimmableRegistry(memoryTrimmableRegistry)//缓存清理模式
                .build();
        Fresco.initialize(this, imagePipelineConfig);
    }

    public static String getStringByResId(int strId) {
        return MyApplication.getContext().getResources().getString(strId);
    }

    public static int getColorByResId(int colorId) {
        return ContextCompat.getColor(MyApplication.getContext(), colorId);
    }
}
