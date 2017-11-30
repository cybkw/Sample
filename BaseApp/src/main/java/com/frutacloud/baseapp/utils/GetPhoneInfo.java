package com.frutacloud.baseapp.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/11/30.
 */

public class GetPhoneInfo {
    public String PhoneModel;
    public String PhoneVersion;
    public String PhoneResolution;
    public String ZcmVersion;
    public String AvailableRom;
    private Context context;

    public GetPhoneInfo(Context context) {
        this.context = context;
        PhoneModel = android.os.Build.MODEL;
        PhoneVersion = android.os.Build.VERSION.RELEASE;
        PhoneResolution = getDisplayWAndH();
        ZcmVersion = getAppVersionName(this.context);
        AvailableRom = "ROM剩余存储空间: " + getAvailableInternalMemorySize() + "MB"
                + ",内置SDCard剩余存储空间: " + getAvailableExternalMemorySize() + "MB";
    }

    // 获取当前版本号
    private String getAppVersionName(Context context) {
        String versionName = "";
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    "com.x1.ui", 0);
            versionName = packageInfo.versionName;
            if (versionName == null) {
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionName;
    }

    // 获取屏幕分辨率
    @SuppressWarnings("deprecation")
    private String getDisplayWAndH() {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        String string = "屏幕分辨率: " + display.getWidth() + "x"
                + display.getHeight();
        return string;
    }

    /**
     * @return ROM存储路径
     */
    private String getInternalMemoryPath() {
        return Environment.getDataDirectory().getPath();
    }

    /**
     * @return 内置sd卡路径
     */
    private String getExternalMemoryPath() {
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * @param path 文件路径
     * @return 文件路径的StatFs对象
     * @throws Exception 路径为空或非法异常抛出
     */
    private StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param stat 文件StatFs对象
     * @return 剩余存储空间的MB数
     */
    @SuppressWarnings("deprecation")
    private float calculateSizeInMB(StatFs stat) {
        if (stat != null)
            return stat.getAvailableBlocks()
                    * (stat.getBlockSize() / (1024f * 1024f));
        return 0.0f;
    }

    /**
     * @return ROM剩余存储空间的MB数
     */
    private float getAvailableInternalMemorySize() {

        String path = getInternalMemoryPath();// 获取数据目录
        StatFs stat = getStatFs(path);
        return calculateSizeInMB(stat);
    }

    /**
     * @return 内置SDCard剩余存储空间MB数
     */
    private float getAvailableExternalMemorySize() {

        String path = getExternalMemoryPath();// 获取数据目录
        StatFs stat = getStatFs(path);
        return calculateSizeInMB(stat);

    }
}
