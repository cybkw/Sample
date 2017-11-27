package com.frutacloud.baseapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.frutacloud.baseapp.base.BaseField;

import java.io.InputStream;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 一系列工具类集合
 */

public class Tools {

    private static Context context;

    public static void init(Context context) {
        Tools.context = context;
    }

    public static Context getContext() {
        synchronized (Tools.class) {
            if (Tools.context == null)
                throw new NullPointerException("Call Tools.init(context) within your Application onCreate() method." +
                        "Or extends XApplication");
            return Tools.context.getApplicationContext();
        }
    }

    /**
     * 判断 列表是否为空
     *
     * @return true为null或空; false不null或空
     */
    public static boolean isEmptyList(List<?> list) {
        return null == list || list.size() == 0;
    }

    /**
     * 判断图片路径
     *
     * @return
     */
    public static boolean isImgUrl(String imgUrl) {
        return isUrl(imgUrl) && (imgUrl.endsWith(".jpg") || imgUrl.endsWith(".png") || imgUrl.endsWith(".JPG") || imgUrl.endsWith(".PNG"));
    }

    /**
     * 判断是否有网络
     *
     * @param context
     * @return
     * @author Michael.Zhang 2013-12-29 16:21:05
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return (mNetworkInfo != null && mNetworkInfo.isAvailable());
    }

    /**
     * 判断当前网络是否是wifi网络.
     *
     * @param context
     * @return boolean
     * @author Michael.Zhang 2014-3-26 11:31:10
     */
    public static boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * 判断当前网络是否是3G网络.
     *
     * @param context
     * @return boolean
     * @author Michael.Zhang 2014-3-26 11:31:10
     */
    public static boolean is3G(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        return (activeNetInfo != null && activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * 判断 多个字段的值否为空
     *
     * @return true为null或空; false不null或空
     * @author Michael.Zhang 2013-08-02 13:34:43
     */
    public static boolean isNull(String... ss) {
        for (int i = 0; i < ss.length; i++) {
            if (null == ss[i] || ss[i].equals("") || ss[i].equalsIgnoreCase("null")) {
                return true;
            }
        }

        return false;
    }

    /**
     * 判断 一个字段的值否为空
     *
     * @param s
     * @return
     * @author Michael.Zhang 2013-9-7 下午4:39:00
     */
    public static boolean isNull(String s) {
        if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        }

        return false;
    }

    /**
     * 判断外部存储器是否存在
     *
     * @return
     * @author Michael.Zhang 2013-07-04 11:30:54
     */
    public static boolean isSDCardExist() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 判断两个字段是否一样
     *
     * @author Michael.Zhang 2013-08-02 13:32:51
     */
    public static boolean isStringEquals(String s0, String s1) {
        return null != s0 && null != s1 && s0.equals(s1);
    }

    /**
     * 判断 http 链接
     *
     * @param url
     * @return
     * @author Michael.Zhang
     */
    public static boolean isUrl(String url) {
        return null != url && url.startsWith("http://");
    }

    /**
     * 验证身份证号码
     *
     * @param idCard 身份证号码
     * @return
     * @author Michael.Zhang 2014-1-20 16:22:01
     */
    public static boolean isIdCard(String idCard) {
        if (isNull(idCard))
            return false;
        String pattern = "^[0-9]{17}[0-9|xX]{1}$";
        return idCard.matches(pattern);
    }

    /**
     * 验证手机号码
     *
     * @param phone
     * @return
     * @author Michael.Zhang 2014-1-20 16:22:01
     */
    public static boolean isPhone(String phone) {
        if (isNull(phone)) {
            return false;
        }

        //String pattern = "^((13[0-9])|(147)|(15[0-9])|(18[0-9]))\\d{8}$";
        String pattern = "^1\\d{10}";
        return phone.matches(pattern);
    }

    /**
     * 调用系统邮件编辑器
     */
    public static void sendEmial(Context context, String email, String content) {
        Intent data = new Intent(Intent.ACTION_SENDTO);
        data.setData(Uri.parse(email));
        data.putExtra(Intent.EXTRA_SUBJECT, "意见反馈");
        data.putExtra(Intent.EXTRA_TEXT, content);
        context.startActivity(data);
    }

    /**
     * 调用系统短息编辑器
     */
    public static void sendSMS(Context context, String phone, String content) {
        Uri smsToUri = Uri.parse("smsto:" + phone);// 联系人电话号码
        Intent mIntent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
        mIntent.putExtra("sms_body", content);// 短信内容
        context.startActivity(mIntent);
    }

    /**
     * MD5加密
     *
     * @param plainText
     * @return
     */
    public static String MD5(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) {
                    i += 256;
                }

                if (i < 16) {
                    buf.append("0");
                }

                buf.append(Integer.toHexString(i));
            }

            return buf.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取屏幕信息
     * 宽高
     */
    public static void getScreenInfo(Activity activity) {
        BaseField.SCREEN_HEIGHT = activity.getResources().getDisplayMetrics().heightPixels;
        BaseField.SCREEN_WIDHT = activity.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 将dp类型的尺寸转换成px类型的尺寸
     *
     * @param dipValue
     * @param context
     * @return
     */
    public static int dip2px(Context context, int dipValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px类型的尺寸转换成dp类型的尺寸
     *
     * @param pxValue
     * @param context
     * @return
     */
    public static int px2dip(Context context, int pxValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 获得hashmap中value的值,以List 返回
     *
     * @param hashMap
     * @return
     */
    public static List<Object> getListFromHashMap(HashMap<String, Object> hashMap) {
        List<Object> list = new ArrayList<Object>();
        Iterator<?> iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            list.add(entry.getValue());
        }

        return list;
    }

    /**
     * 将分转为元
     *
     * @param money 分--->元
     * @return
     */
    public static double getMoney(String money) {
        if (money != null && !money.equals("") && !money.equals("null")) {
            return Double.parseDouble(money) / 100.0;
        }

        return 0.00;
    }

    /**
     * 将时间戳转为字符串 到日
     *
     * @param cc_time
     * @return
     * @author Michael.Zhang 2013-08-05 14:09:17
     */
    public static String getStrDate(String cc_time) {
        return getStrTime(cc_time, "yyyy-MM-dd");
    }

    /**
     * 将时间戳转为字符串 到分
     *
     * @param cc_time
     * @return
     * @author Michael.Zhang 2013-08-05 14:09:23
     */
    public static String getStrTime(String cc_time) {
        return getStrTime(cc_time, "yyyy-MM-dd HH:mm:ss");
    }

    private static String getStrTime(String cc_time, String format) {
        String re_StrTime = null;
        if (cc_time == null) {
            cc_time = System.currentTimeMillis() + "";
        }

        if (cc_time.length() == 10) { // 单位 秒
            cc_time += "000";// 单位 毫秒
        }

        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time));

        return re_StrTime;
    }

    /**
     * 时间得到时间戳
     *
     * @param time
     * @return
     */
    public static long getLongTime(String time) {
        SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sDate.parse(time);
            return date.getTime();// date转成毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 将毫秒数转为时分秒
     *
     * @param cc_time 毫秒数
     * @return
     */
    public static String getStrHourMinute(long cc_time, boolean isHour) {
        int total_second = (int) (cc_time / 1000); // 总秒
        int second = total_second % 60;// 剩余秒
        int minute = total_second / 60; // 总分

        if (isHour) {
            int hour = minute / 60;// 剩余时
            minute = minute % 60; // 剩余分
            return ((hour < 10) ? "0" + hour : "" + hour) + ":" + ((minute < 10) ? "0" + minute : "" + minute) + ":"
                    + ((second < 10) ? "0" + second : "" + second);
        } else {
            return ((minute < 10) ? "0" + minute : "" + minute) + ":" + ((second < 10) ? "0" + second : "" + second);
        }
    }

    /**
     * 从assets 文件夹中获取文件并读取数据
     *
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(Context context, String fileName) {
        String result = null;
        try {
            InputStream in = context.getResources().getAssets().open(fileName);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
//            result = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 从res中的raw文件夹中获取文件并读取数据
     *
     * @param context
     * @param resId
     * @return
     */
    public static String getTextFromRaw(Context context, int resId) {
        String result = null;
        try {
            InputStream in = context.getResources().openRawResource(resId);
            // 获取文件的字节数
            int lenght = in.available();
            // 创建byte数组
            byte[] buffer = new byte[lenght];
            // 将文件中的数据读到byte数组中
            in.read(buffer);
            // result = EncodingUtils.getString(buffer, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取版本号
     *
     * @return
     */
    public static int getVersionCode(Activity activity) {
        int versionCode = 0;
        if (getPackageInfo(activity) != null) {
            versionCode = getPackageInfo(activity).versionCode;
        }

        return versionCode;
    }

    /**
     * 获取版本名称
     *
     * @return
     */
    public static String getVersionName(Activity activity) {
        String versionName = "0";
        if (getPackageInfo(activity) != null) {
            versionName = getPackageInfo(activity).versionName;
        }

        return versionName;
    }

    /**
     * 安装包信息
     *
     * @param activity
     * @return
     */
    public static PackageInfo getPackageInfo(Activity activity) {
        String packageName = activity.getPackageName();
        try {
            return activity.getPackageManager().getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 获取保存到View的Tag中的字符串
     *
     * @param v
     * @return
     */
    public static String getViewTagString(View v) {
        try {
            return v.getTag().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * double型四舍五入
     *
     * @param value 数值
     * @param scale 小数点后位数
     * @return
     */
    public static Double roundDouble(double value, int scale) {
        BigDecimal b = ((0 == value) ? new BigDecimal("0") : new BigDecimal(Double.toString(value)));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 弹出窗 Toast
     *
     * @param context
     * @param content
     */
    public static void Toast(Context context, String content) {
        if (!isNull(content)) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 收起软键盘
     */
    public static void hideSoftInput(Activity ac) {
        InputMethodManager imm = (InputMethodManager) ac.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(ac.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    /**
     * getDrawable过时方法处理
     *
     * @param id
     * @return
     */
    public static Drawable getDrawable(Context context, int id) {
        return ContextCompat.getDrawable(context, id);
    }

    /**
     * setBackgroundDrawable过时方法处理
     *
     * @param view
     * @param drawable
     */
    public static void setBackground(@NonNull View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }

    /**
     * Log.i
     *
     * @param s
     */
    public static void Log(String s) {
        if (s == null) {
            s = "传进来的是null";
        }

        Log.i("info", s);
    }

    /**
     * 参数：maxLines 要限制的最大行数
     * 参数：content  指TextView中要显示的内容
     */
    public void setMaxEcplise(final TextView mTextView, final int maxLines, final String content) {

        ViewTreeObserver observer = mTextView.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mTextView.setText(content);
                if (mTextView.getLineCount() > maxLines) {
                    int lineEndIndex = mTextView.getLayout().getLineEnd(maxLines - 1);
                    //下面这句代码中：我在项目中用数字3发现效果不好，改成1了
                    String text = content.subSequence(0, lineEndIndex - 3) + "...";
                    mTextView.setText(text);
                } else {
                    removeGlobalOnLayoutListener(mTextView.getViewTreeObserver(), this);
                }
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void removeGlobalOnLayoutListener(ViewTreeObserver obs, ViewTreeObserver.OnGlobalLayoutListener listener) {
        if (obs == null)
            return;
        if (Build.VERSION.SDK_INT < 16) {
            obs.removeGlobalOnLayoutListener(listener);
        } else {
            obs.removeOnGlobalLayoutListener(listener);
        }
    }
}
