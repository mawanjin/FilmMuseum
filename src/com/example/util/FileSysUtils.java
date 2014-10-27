package com.example.util;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 16:29
 */
public class FileSysUtils {
    // 获取sd卡的路径
    public static String getExternalStoragePath() {
        String state = android.os.Environment.getExternalStorageState();
        if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
            if (android.os.Environment.getExternalStorageDirectory().canRead()) {
                return android.os.Environment.getExternalStorageDirectory()
                        .getPath();
            }
        }
        return null;
    }
}
