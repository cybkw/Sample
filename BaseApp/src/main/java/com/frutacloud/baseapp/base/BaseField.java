package com.frutacloud.baseapp.base;

import android.os.Environment;

import java.util.HashMap;

/**
 * 全局静态字段
 * 工具类中可能用到的字段
 */
public class BaseField {
    /**
     * sdcard 路径 + 应用名称
     */
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath() + "/Appname";

    /**
     * 屏幕尺寸
     */
    public static int SCREEN_WIDHT = 480;// 宽度
    public static int SCREEN_HEIGHT = 800; // 高度

    /**
     * 省列表
     */
    public static HashMap<String, String> REGIONS = new HashMap<String, String>();

    /**
     * 城市列表
     */
    public static HashMap<String, String> CITIS = new HashMap<String, String>();
}
