package com.frutacloud.baseapp.base;

import android.app.Application;
import android.widget.ImageView;

import com.android.pc.ioc.app.Ioc;
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

    @Override
    public void onCreate() {

        Ioc.getIoc().init(this); //注入LoonAndroid框架支持
        Tools.init(this);
        super.onCreate();

        FileUtil.getInstance().createFiles("BaseApp"); //创建一个文件
        initImageLoader();
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
}
