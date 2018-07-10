package com.cc.xsl.coolweather.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.Window;
import android.view.WindowManager;

import com.cc.xsl.coolweather.R;


@TargetApi(Build.VERSION_CODES.KITKAT)
public class Utilty {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 通用Activity调整
     *
     * @param context
     * @param targetActivity
     * @param param          intent参数
     */
    public static void go2Activity(Context context, Class<?> targetActivity,
                                   HashMap<String, String> param) {
        Intent intent = new Intent(context, targetActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (param != null) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        Activity activity = (Activity) context;
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slid_in_right,
                R.anim.slid_out_left);
    }

    /**
     * 判断字符串是否为空为null
     *
     * @param str 字符串
     * @return true：为空 fasle：不为空
     */
    public static boolean isNull(String str) {
        if (str != null && !str.equals("")) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, double dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 判断摸个服务是否启动
     *
     * @param mContext
     * @param className :服务的名称
     * @return
     */
    public static boolean isServiceRunning(Context mContext, String className) {

        final ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        final List<RunningServiceInfo> services = activityManager
                .getRunningServices(Integer.MAX_VALUE);

        for (RunningServiceInfo runningServiceInfo : services) {
            if (runningServiceInfo.service.getClassName().equals(className)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 得到IP地址
     *
     * @return
     */
    public static String getIpAddress(Context context) {
        String ipAddress = null;
        SharedPreferences pre = context.getSharedPreferences("NewEast",
                Context.MODE_PRIVATE);
        ipAddress = pre.getString("IpAddress",
                "http://192.168.0.116:8002/WebService/MobileService.asmx");
        return ipAddress;
    }

    /**
     * 保存IP地址
     *
     * @param context   ：上下文
     * @param IpAddress ：IP地址
     */
    public static void setIpAddress(Context context, String IpAddress) {
        SharedPreferences pre = context.getSharedPreferences("NewEast",
                Context.MODE_PRIVATE);
        Editor editor = pre.edit();
        editor.putString("IpAddress", IpAddress);
        editor.commit();
    }

    /**
     * 设置学生信息
     *
     * @param context
     * @param value   ：学生信息
     */
    public static void setStudentInfo(Context context, String key, String value) {
        setValueToShare(context, key, value);
    }

    /**
     * 得到学生信息
     *
     * @param context
     * @return
     */
    public static String getStudentInfo(Context context, String key) {
        return getValueFromShare(context, key, "");
    }

    /**
     * 得到url地址
     *
     * @param context
     * @return
     */
    public static String getUrl(Context context) {
        return getValueFromShare(context, "url", "");
    }

    /**
     * 设置url地址
     *
     * @param context
     * @param value
     */
    public static void setUrl(Context context, String value) {
        setValueToShare(context, "url", value);
    }

    // 得到用户名
    public static String getUsername(Context context) {
        return getValueFromShare(context, "username", "");
    }

    /**
     * 封装用户名
     *
     * @param context
     * @param username
     */
    public static void setUsername(Context context, String username) {
        setValueToShare(context, "username", username);
    }

    // 得到密码
    public static String getPassword(Context context) {
        return getValueFromShare(context, "password", "");
    }

    /**
     * 封装密码
     *
     * @param context
     * @param password
     */
    public static void setPassword(Context context, String password) {
        setValueToShare(context, "password", password);
    }

    /**
     * 封装Token
     *
     * @param context
     * @param token
     */
    public static void setToken(Context context, String token) {
        setValueToShare(context, "token", token);
    }

    /**
     * 得到token
     *
     * @param context
     * @return
     */
    public static String getToken(Context context) {
        return getValueFromShare(context, "token", "");
    }

    /**
     * 得到未读的条数
     *
     * @param context
     * @return
     */
    public static String getUnNumber(Context context) {
        return getValueFromShare(context, "unNumber", "0");
    }

    /**
     * 设置未读的条数
     *
     * @param context
     * @param count
     */
    public static void setUnNumber(Context context, String count) {
        setValueToShare(context, "unNumber", count);
    }

    /**
     * 得到已读的条数
     *
     * @param context
     * @return
     */
    public static String getNumber(Context context) {
        return getValueFromShare(context, "number", "0");
    }

    /**
     * @param context
     * @param count
     */
    public static void setNumber(Context context, String count) {
        setValueToShare(context, "number", count);
    }

    /**
     * 设置消息标记
     *
     * @param context
     * @param flag
     */
    public static void setMessageFlag(Context context, String flag) {
        setValueToShare(context, "messageFlag", flag);
    }

    /**
     * 得到提醒的开关状态
     *
     * @return
     */
    public static String getTipState(Context context) {
        return getValueFromShare(context, "tipState", "open");
    }

    /**
     * 设置提醒的开关状态
     *
     * @param context
     * @param value
     */
    public static void setTipState(Context context, String value) {
        setValueToShare(context, "tipState", value);
    }

    /**
     * 得到消息标志
     *
     * @param context
     * @return
     */
    public static String getMessageFlag(Context context) {
        return getValueFromShare(context, "messageFlag", "");
    }

    /**
     * 封装值进入key
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setValueToShare(Context context, String key, String value) {
        SharedPreferences pre = context.getSharedPreferences("NewEast",
                Context.MODE_PRIVATE);
        Editor editor = pre.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 封装值进入key
     *
     * @param context
     * @param key
     * @param value
     */
    public static void setIntValueToShare(Context context, String key, int value) {
        SharedPreferences pre = context.getSharedPreferences("NewEast",
                Context.MODE_PRIVATE);
        Editor editor = pre.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 得到
     *
     * @param context
     * @param key
     * @return
     */
    public static String getValueFromShare(Context context, String key,
                                           String defaultValue) {
        String str = null;
        SharedPreferences pre = context.getSharedPreferences("NewEast",
                Context.MODE_PRIVATE);
        str = pre.getString(key, defaultValue);
        return str;
    }

    /**
     * 得到int型的分享
     *
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getIntValueFromShare(Context context, String key,
                                           int defaultValue) {
        int str = 0;
        SharedPreferences pre = context.getSharedPreferences("NewEast",
                Context.MODE_PRIVATE);
        str = pre.getInt(key, defaultValue);
        return str;
    }

    /**
     * 时间格式化
     *
     * @param date        时间
     * @param formateType 格式类型
     * @return 时间字符串
     */
    public static String getFormateTime(Date date, String formateType) {

        SimpleDateFormat format = new SimpleDateFormat(formateType);
        return format.format(date);
    }

    /**
     * 时间格式化
     *
     * @param time        long类型时间
     * @param formateType 格式化类型
     * @return 时间字符串
     */
    public static String getFormateTime(long time, String formateType) {
        SimpleDateFormat format = new SimpleDateFormat(formateType);
        Date date = new Date(time);
        return format.format(date);
    }

    /**
     * 得到次之间之外的时间
     *
     * @param date 当前时间
     * @param day  提前或者延后的时间
     * @return 处理过的时间
     */
    public static Date getOtherExtreaDate(Date date, int day) {
        Calendar calendar = Calendar.getInstance();// 日历对象
        calendar.setTime(date);// 设置当前日期
        calendar.add(Calendar.DATE, day);
        return calendar.getTime();
    }

    /**
     * 得到访问地址
     *
     * @param str
     * @return
     */
    // public static String getProcessURL(String str) {
    // return Constant.url + str + ".ashx";
    // }

    /**
     * 得到所有的数字
     *
     * @param context
     * @return
     */
    public static int getAllNumber(Context context) {
        int count1 = 0;
        int count2 = 0;

        String countStr1 = getNumber(context);
        if (countStr1 != null && !countStr1.equals("")) {
            count1 = Integer.parseInt(countStr1);
        }

        String countStr2 = getUnNumber(context);
        if (countStr2 != null && !countStr2.equals("")) {
            count2 = Integer.parseInt(countStr2);
        }

        return count1 + count2;
    }

    /**
     * 得到时间
     *
     * @param type  类型 1：月初 2：月末
     * @param month 月份 0 ：当月 -1：上一月 1：下一月
     * @return
     */
    public static Date getMonthDate(int type, int month) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        if (type == 1) {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month);
            calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
        } else {
            calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + month
                    + 1);
            calendar.set(Calendar.DATE, calendar.getMinimum(Calendar.DATE));
            calendar.add(Calendar.DATE, -1);
        }
        return calendar.getTime();
    }

    /**
     * 得到当月的第一天
     *
     * @param jump ：差的月份
     * @return
     */
    public static String getMonthBeginString(int year, int month) {
        String time = null;
        Calendar c = Calendar.getInstance();
        // c.add(Calendar.MONTH, 0);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        time = format.format(c.getTime());
        return time;
    }

    /**
     * 得到当月的最后一天
     *
     * @param jump ：差的月份
     * @return
     */
    public static String getMonthEndString(int year, int month) {
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month - 1);
        ca.set(Calendar.DAY_OF_MONTH,
                ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        return last;
    }

    /**
     * 从字符串转成date
     *
     * @param dateStr   时间字符串
     * @param formatStr 字符串格式
     * @return
     */
    public static Date StringToDate(String dateStr, String formatStr) {
        SimpleDateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 去除标题栏
     *
     * @param mActivity
     */
    public static void requestNotTitleBar(final Activity mActivity) {
        mActivity.requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 全屏
     *
     * @param mActivity
     */
    public static void requestFullscreen(final Activity mActivity) {
        final Window window = mActivity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        window.requestFeature(Window.FEATURE_NO_TITLE);
    }

    /**
     * 得到签名
     *
     * @return
     */
//    public static String getSine(HashMap<String, String> param) {
//        String sign = "";
//        String str = checkBigCode(getValueFromMap(transMap(param))) + "SIGNKEY";
//        String upStr = str.toUpperCase();
//        sign = exChange(AESX3.md5(upStr));
//        return sign;
//    }

    /**
     * 删除目录下的所有的文件
     *
     * @param root
     */
    public static void deleteAllFiles(File root) {
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.isDirectory()) { // 判断是否为文件夹
                    deleteAllFiles(f);
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                } else {
                    if (f.exists()) { // 判断是否存在
                        deleteAllFiles(f);
                        try {
                            f.delete();
                        } catch (Exception e) {
                        }
                    }
                }
            }
    }

    /**
     * 将小写的全都改成大写的
     *
     * @param str
     * @return
     */
    public static String exChange(String str) {
        StringBuffer sb = new StringBuffer();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (Character.isLowerCase(c)) {
                    sb.append(Character.toUpperCase(c));
                } else {
                    sb.append(c);
                }

            }
        }

        return sb.toString();
    }

    /**
     * 将hashmap进行升序排列
     *
     * @param param
     * @return
     */
    public static Map<String, String> transMap(HashMap<String, String> param) {
        Map<String, String> paramMap = new TreeMap<String, String>();
        for (String key : param.keySet()) {
            if (param.get(key) != null) {
                String value = param.get(key);
                paramMap.put(key, value);
            }
        }
        return paramMap;
    }

    /**
     * 获取屏幕的高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 将hashMap封装成字符串
     *
     * @param param
     * @return
     */
    public static String getValueFromMap(Map<String, String> param) {
        StringBuffer buffer = new StringBuffer();
        for (String key : param.keySet()) {
            if (param.get(key) != null) {
                buffer.append(param.get(key));
            }
        }
        return buffer.toString();
    }

    /**
     * 检查是否是大写的，不是大写的改成大写的
     *
     * @param param
     * @return
     */
    public static String checkBigCode(String param) {

        return param;
    }

    /**
     * 验证手机号
     *
     * @param mobiles ：手机号
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,\\D])|(17[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证邮箱
     *
     * @param email ：邮箱
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "(?=^[\\w.@]{6,50}$)\\w+@\\w+(?:\\.[\\w]{2,3}){1,2}";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 讲drawable转化成bitmap
     *
     * @param drawable
     * @return h
     * @author 王海啸
     */
    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(

                drawable.getIntrinsicWidth(),

                drawable.getIntrinsicHeight(),

                drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                        : Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);

        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());

        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 将图片变成四周圆角
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {

        Bitmap output = Bitmap.createBitmap(90, 90, Config.ARGB_8888);

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;

        final Paint paint = new Paint();

        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        final RectF rectF = new RectF(rect);

        final float roundPx = pixels;

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);

        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));

        canvas.drawBitmap(bitmap, rect, rect, paint);

        bitmap.recycle();

        return output;

    }

    /**
     * 将图片变成圆形的
     *
     * @param context
     * @param bitmap       ：图片
     * @param STROKE_WIDTH ：圆角的宽度
     * @return
     */
    public static Bitmap toRoundBitmap(Context context, Bitmap bitmap,
                                       int STROKE_WIDTH) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left, top, right, bottom, dst_left, dst_top, dst_right, dst_bottom;
        if (width <= height) {
            roundPx = width / 2;
            top = 0;
            left = 0;
            bottom = width;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int) left, (int) top, (int) right,
                (int) bottom);
        final Rect dst = new Rect((int) dst_left, (int) dst_top,
                (int) dst_right, (int) dst_bottom);
        final RectF rectF = new RectF(dst);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(4);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);

        // 画白色圆圈
        paint.reset();
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(STROKE_WIDTH);
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, width / 2, width / 2 - STROKE_WIDTH / 2,
                paint);
        return output;
    }

    /**
     * 从4.4上得到路径
     *
     * @param context
     * @param data
     * @return
     */
    public static String getImagePathFromKitKat(Context context, Intent data) {
        Uri selectedImage = data.getData();

        String picturePath = getPathFromKitKat(context, selectedImage);
        return picturePath;
    }

    /**
     * 从4.4的相机获取图片
     *
     * @param context
     * @param data
     * @return
     */
    public static String getPhotoPathFromKitKat(Context context, Intent data) {
        String path = null;

        new DateFormat();
        String name = DateFormat.format("yyyyMMdd_hhmmss",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";
        Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

        FileOutputStream b = null;
        String pathStr = Environment.getExternalStorageDirectory().getPath();
        File file = new File(pathStr + "/DCIM/Camera/");
        if (file.exists()) {
            file.mkdirs();// 创建文件夹
        }
        String fileName = pathStr + "/DCIM/Camera/" + name;
        path = fileName;
        try {
            b = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bitmap.recycle();
                b.flush();
                b.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path;
    }

    /**
     * 从相机获取照片
     *
     * @param context
     * @param data
     * @return
     */
    public static String getPhotoPath(Context context, Intent data) {
        String path = null;

        Uri uri = data.getData();
        Cursor cur = context.getContentResolver().query(uri, null, null, null,
                null);
        if (cur.moveToFirst()) {
            path = cur.getString(cur.getColumnIndex("_data"));// 获取绝对路径
        }
        return path;

    }

//    /**
//     * 获取设备号
//     *
//     * @param context
//     * @return
//     */
//    public static String getDeviceId(Context context) {
//        String deviceId = DeviceInfo.getDeviceId(context);
//        if (isNull(deviceId)) {
//            // 拼装DeviceId
//            deviceId = "";
//        }
//        return deviceId;
//    }

    /**
     * @param context
     * @return
     */
    public static final float getHeightInPx(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        return height;
    }

    public static final float getWidthInPx(Context context) {
        final float width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }

    public static final int getHeightInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int heightInDp = px2dip(context, height);
        return heightInDp;
    }

    public static final int getWidthInDp(Context context) {
        final float height = context.getResources().getDisplayMetrics().heightPixels;
        int widthInDp = px2dip(context, height);
        return widthInDp;
    }

    /**
     * 在android4.4上获取资源的路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromKitKat(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri
                .getAuthority());
    }

    /**
     * 得到大写汉子
     *
     * @param number
     * @return
     */
    public static String getUpcaseNumber(int number) {
        String part = null;
        switch (number) {
            case 0:
                part = "一";
                break;
            case 1:
                part = "二";
                break;
            case 2:
                part = "三";
                break;
            case 3:
                part = "四";
                break;
            case 4:
                part = "五";
                break;
            case 5:
                part = "六";
                break;
            case 6:
                part = "七";
                break;
            case 7:
                part = "八";
                break;
            case 8:
                part = "九";
                break;
            case 9:
                part = "十";
                break;
            case 10:
                part = "十一";
                break;
            case 11:
                part = "十二";
                break;
            case 12:
                part = "十三";
                break;
            case 13:
                part = "十四";
                break;
            case 14:
                part = "十五";
                break;
            case 15:
                part = "十六";
                break;
            case 16:
                part = "十七";
                break;
            case 17:
                part = "十八";
                break;
            case 18:
                part = "十九";
                break;
            case 19:
                part = "二十";
                break;
            case 20:
                part = "二十一";
                break;
            case 21:
                part = "二十二";
                break;
            case 22:
                part = "二十四";
                break;
            case 23:
                part = "二十五";
                break;
            case 24:
                part = "二十六";
                break;
            case 25:
                part = "二十七";
                break;

            default:
                break;
        }
        return part;
    }

}
