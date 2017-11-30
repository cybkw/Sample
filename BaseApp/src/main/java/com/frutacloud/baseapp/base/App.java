package com.frutacloud.baseapp.base;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.ImageView;

import com.android.pc.ioc.app.Ioc;
import com.frutacloud.baseapp.exception.AppUncaughtExceptionHandler;
import com.frutacloud.baseapp.utils.FileUtil;
import com.frutacloud.baseapp.utils.Tools;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by Administrator on 2017/11/2.
 * 基类Application程序入口
 */

public class App extends Application {

    /**
     * 图片加载下载工具类
     */
    public static ImageLoader IMAGE_LOADER;
    private static DisplayImageOptions IMAGE_OPTIONS;
    private static App mInstance = null;

    /**
     * 下载图片
     *
     * @param uri
     * @param imageView cacheInMemory():是否存入内存缓存,cacheOnDisc()是否使用硬盘缓存
     */
    public static void loadImage(String uri, ImageView imageView) {
        if (null == IMAGE_OPTIONS) {
            IMAGE_OPTIONS = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        }
        IMAGE_LOADER.displayImage(uri, imageView, IMAGE_OPTIONS);
    }

    public static App getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mInstance;
    }

    @Override
    public void onCreate() {

        mInstance = this;
        Ioc.getIoc().init(this); //注入LoonAndroid框架支持
        Tools.init(this);
        super.onCreate();

        // 初始化文件目录
        FileUtil.getInstance().createFiles("BaseApp"); //创建一个文件夹
        initImageLoader();


        // 捕捉异常
        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }


    /* *
         * 初始化imageLoader
         */
    private void initImageLoader() {
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).threadPriority(Thread.NORM_PRIORITY)
                .denyCacheImageMultipleSizesInMemory().diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).writeDebugLogs().build();
        IMAGE_LOADER = ImageLoader.getInstance();
        IMAGE_LOADER.init(imageLoaderConfiguration);
    }

    /**
     * 获取自身App安装包信息
     *
     * @return
     */
    public PackageInfo getLocalPackageInfo() {
        return getPackageInfo(getPackageName());
    }

    /**
     * 获取App安装包信息
     *
     * @return
     */
    public PackageInfo getPackageInfo(String packageName) {
        PackageInfo info = null;
        try {
            info = getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return info;
    }

}
