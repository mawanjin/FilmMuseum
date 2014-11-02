package com.example.async;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import com.example.data.MagicFactory;
import com.example.data.Version;
import com.example.filmmuseum.MainActivity;
import com.example.util.FileSysUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/2
 * Time: 22:11
 */
public class DownloadTask extends AsyncTask {
    private long fileSize;
    private Context context;
    private Handler handler;
    private String downloadPath = FileSysUtils.getExternalStoragePath() + "/FilmMuseum/download/FilmMuseum.zip";
    private boolean complete = false;

    public DownloadTask(Context context, Handler handler) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        Version version = MagicFactory.getVersion(context);
        URL url = null;
        try {
            url = new URL(version.getDownloadUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {

                fileSize = conn.getContentLength();
                InputStream myInput = conn.getInputStream();
                // 这里获取数据直接放在XmlPullParser里面解析
                File file = new File(downloadPath);
                if (file.exists()) {
                    long a = file.length();
                    file.delete();
                }

                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        File file = new File(downloadPath);
                        while (true) {

                            if (file.exists()) {
                                long b = file.length();
                                double percent = (double) b / fileSize;
                                String p = "0%";
                                if(percent==1){
                                    p = "100%";
                                }else if(percent==0){
                                    p = "0%";
                                }else {
                                    p = (percent * 100 + "").substring(0, 2);
                                    if (p.equals("0.")) p = "0%";
                                    else
                                        p += "%";
                                }



                                Message msg = new Message();
                                msg.obj = p;
                                handler.sendMessage(msg);
                                if (p.equals("100%"))break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }.start();

                OutputStream myOutputStream = new FileOutputStream(downloadPath);

                byte[] buffer = new byte[1024];
                int length = myInput.read(buffer);
                while (length > 0) {
                    myOutputStream.write(buffer, 0, length);
                    length = myInput.read(buffer);
                    myOutputStream.flush();
                }

                myInput.close();
                myOutputStream.close();

                //更新本地version.xml文件
                MagicFactory.updateVersion(context,((MainActivity)context).getVersionNew());

                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }


}
