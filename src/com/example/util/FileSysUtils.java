package com.example.util;

import android.content.Context;

import java.io.*;

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

    /**
     * 提到存放本地图片的路径
     *
     * @return
     */
    public static String getImagePath() {
        return FileSysUtils.getExternalStoragePath()
                + "/FilmMuseum/system/FilmMuseum/image/";
    }

    // 拷贝文件到SD卡
    public static void copyBigDataToSD(Context context,String strOutFileName) throws IOException {
        InputStream myInput;
        OutputStream myOutputStream = new FileOutputStream(strOutFileName);
        myInput = context.getAssets().open("FilmMuseum.zip");
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutputStream.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutputStream.flush();
        myInput.close();
        myOutputStream.close();
    }


    public static void initSysData(Context context){
        File destDir = new File(FileSysUtils.getExternalStoragePath()
                + "/FilmMuseum/download");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        destDir = new File(FileSysUtils.getExternalStoragePath() + "/FilmMuseum/system");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        destDir = new File(FileSysUtils.getExternalStoragePath() + "/FilmMuseum/collection");
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        String path = FileSysUtils.getExternalStoragePath()
                + "/FilmMuseum/download/FilmMuseum.zip";
        try {
            FileSysUtils.copyBigDataToSD(context,path);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public static boolean isExist(){
        String path = FileSysUtils.getExternalStoragePath()
                + "/FilmMuseum/download/FilmMuseum.zip";
        File file = new File(path);
        return file.exists();
    }

}
