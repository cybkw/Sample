package com.frutacloud.baseapp.base;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.widget.ImageView;

import com.android.pc.ioc.app.Ioc;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.frutacloud.baseapp.R;
import com.frutacloud.baseapp.exception.AppUncaughtExceptionHandler;
import com.frutacloud.baseapp.utils.FileUtil;
import com.frutacloud.baseapp.utils.Tools;

/**
 * Created by Administrator on 2017/11/2.
 * 基类Application程序入口
 */

public class App extends Application {


    private static App mInstance = null;


    public static App getInstance() {
        if (mInstance == null) {
            throw new IllegalStateException("Application is not created.");
        }
        return mInstance;
    }

    /**
     * 图片加载下载工具类
     */
    public static void loadImage(Context context, String imgUrl, ImageView imageView) {
        Glide.with(context).load(imgUrl)
                .apply(new RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.xtoast_info)
                        .error(R.drawable.xloading_error)
                        .priority(Priority.NORMAL))
                .into(imageView);
    }

    @Override
    public void onCreate() {

        mInstance = this;
        Ioc.getIoc().init(this); //注入LoonAndroid框架支持
        Tools.init(this);
        super.onCreate();

        // 初始化文件目录
        FileUtil.getInstance().createFiles("BaseApp"); //创建一个文件夹

        // 捕捉异常
        AppUncaughtExceptionHandler crashHandler = AppUncaughtExceptionHandler.getInstance();
        crashHandler.init(getApplicationContext());
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
