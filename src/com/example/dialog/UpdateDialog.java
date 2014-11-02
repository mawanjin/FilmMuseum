package com.example.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.async.DownloadTask;
import com.example.filmmuseum.MainActivity;
import com.example.filmmuseum.R;
import com.example.util.FileSysUtils;
import com.example.util.ZipExtractorTask;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/2
 * Time: 19:35
 */
public class UpdateDialog extends AlertDialog {
    Context context;

    private String fileSize;
    private TextView percent;

    public UpdateDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_update);
        setCanceledOnTouchOutside(false);

        ((TextView)findViewById(R.id.fileSize)).setText(fileSize);

        percent = (TextView) findViewById(R.id.percent);

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        findViewById(R.id.download).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.content).setVisibility(View.GONE);
                findViewById(R.id.downloadView).setVisibility(View.VISIBLE);
                UpdateDialog.this.setCancelable(false);
                //开始下载
                new DownloadTask(context,new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        String p = (String)msg.obj;
                        percent.setText(p);
                        if(p.equals("100%")){
                            findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);
                            percent.setText("下载完成，正在解压");
                            ZipExtractorTask task = new ZipExtractorTask(FileSysUtils.getExternalStoragePath()
                                    + "/FilmMuseum/download/FilmMuseum.zip",
                                    FileSysUtils.getExternalStoragePath() + "/FilmMuseum/system/", context, true,new Handler(){
                                @Override
                                public void handleMessage(Message msg) {
                                    super.handleMessage(msg);
                                    Toast.makeText(context,"更新成功",1000).show();
                                    UpdateDialog.this.dismiss();
                                    ((MainActivity)context).reloadAfterUpgrade();
                                }
                            });
                            task.execute();
                        }

                    }
                }).execute();

            }
        });
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
