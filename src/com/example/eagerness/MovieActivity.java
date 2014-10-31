package com.example.eagerness;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.*;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.*;
import android.view.SurfaceHolder.Callback;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtContentVideo;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 先睹为快的视频播放
 */
public class MovieActivity extends Activity implements Callback,
        OnCompletionListener, OnPreparedListener, OnErrorListener,
        OnSeekCompleteListener, OnVideoSizeChangedListener {

    private TextView tv;

    private ImageView ivReturn, ivMenu, iv1, iv2, iv3;

    private SeekBar seekBar;

    private upDateSeekBar update; // 更新进度条用

    private Display currentDisplay;
    private SurfaceView surface;
    private SurfaceHolder holder;
    // 使用media播放视频
    private MediaPlayer mediaPlayer;
    // 视频的宽高
    private int videoWidth = 0;
    private int videoHeight = 0;

    private boolean flag = true; // 用于判断视频是否在播放中

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.movie);
        SysApplication.getInstance().addActivity(this);
        ivMenu = (ImageView) findViewById(R.id.iv_menu);
        ivMenu.setVisibility(View.GONE);
        tv = (TextView) findViewById(R.id.tv_title);

        iv1 = (ImageView) findViewById(R.id.movie_player);
        iv2 = (ImageView) findViewById(R.id.movie_down);
        iv3 = (ImageView) findViewById(R.id.movie_suki);

        seekBar = (SeekBar) findViewById(R.id.movie_seekbar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                seekBar.setProgress(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int value = seekBar.getProgress() * mediaPlayer.getDuration() // 计算进度条需要前进的位置数据大小
                        / seekBar.getMax();
                mediaPlayer.seekTo(value);
            }
        });
        tv.setText("微电影");
        ivReturn = (ImageView) findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
                overridePendingTransition(R.anim.a2, R.anim.a1);
            }
        });

        List<ArtContentVideo> video = MagicFactory.getArtContentVideos();
        for (ArtContentVideo vi : video) {
            if (vi.getType().equals("image")) {
                if (vi.getId() == 1) {
                    Bitmap bm = MagicFactory.getBitmap(vi.getSrc());
                    LayoutParams params = new LayoutParams(vi.getWidth(),
                            vi.getHeight());
                    iv1.setImageBitmap(bm);
                    iv1.setLayoutParams(params);
                }
            }
        }
        surface = (SurfaceView) findViewById(R.id.surface);
        holder = surface.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnVideoSizeChangedListener(this);
        currentDisplay = getWindowManager().getDefaultDisplay();
        Bundle bundle = getIntent().getExtras();
        String src = bundle.getString("src");

        String path = MagicFactory.getPlayUrl(src);
        try {
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            update = new upDateSeekBar();
            new Thread(update).start();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.setOnCompletionListener(new OnCompletionListener() {
            public void onCompletion(MediaPlayer arg0) {
                iv1.setImageResource(R.drawable.player);
                seekBar.setProgress(0);
            }
        });
        iv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    new Thread(update).start();
                    iv1.setImageResource(R.drawable.pause);
                    flag = true;
                } else {
                    mediaPlayer.pause();
                    new Thread(update).start();
                    iv1.setImageResource(R.drawable.player);
                    flag = false;
                }
            }
        });

    }


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (mediaPlayer == null) {
                flag = false;
            } else if (mediaPlayer.isPlaying()) {
                flag = true;
                int position = mediaPlayer.getCurrentPosition();
                int mMax = mediaPlayer.getDuration();
                int sMax = seekBar.getMax();
                seekBar.setProgress(position * sMax / mMax);
            } else {
                return;
            }
        }

        ;
    };

    class upDateSeekBar implements Runnable {
        public void run() {
            mHandler.sendMessage(Message.obtain());
            if (flag) {
                mHandler.postDelayed(update, 1000);
            }
        }
    }

    @SuppressWarnings("static-access")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Timer exit = null;
            if (isExit == false) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次推出程序",
                        Toast.LENGTH_SHORT).show();
                exit = new Timer();
                exit.schedule(new TimerTask() {
                    public void run() {
                        isExit = false;
                    }
                }, 2000);
            } else {
                finish();
                SysApplication.getInstance().exit();
            }
        }
        return false;
    }

    private static boolean isExit = false;

    protected void onStop() {
        Log.v("HTTWs", "MovieActivity进入onstop");
        finish();
        super.onStop();
    }

    public void onVideoSizeChanged(MediaPlayer arg0, int arg1, int arg2) {
    }

    public void onSeekComplete(MediaPlayer arg0) {
    }

    public boolean onError(MediaPlayer arg0, int arg1, int arg2) {
        return false;
    }

    public void onPrepared(MediaPlayer media) {
        videoWidth = currentDisplay.getWidth();
        videoHeight = currentDisplay.getHeight();
//        if (videoWidth > currentDisplay.getWidth()
//                || videoHeight > currentDisplay.getHeight()) {
//            float heightRatio = (float) videoHeight
//                    / (float) currentDisplay.getHeight();
//            float widthRatio = (float) videoWidth
//                    / (float) currentDisplay.getWidth();
//            if (heightRatio > 1 || widthRatio > 1) {
//                if (heightRatio > widthRatio) {
//                    videoHeight = (int) Math.ceil((float) videoHeight
//                            / (float) heightRatio);
//                    videoWidth = (int) Math.ceil((float) videoWidth
//                            / (float) heightRatio);
//                } else {
//                    videoHeight = (int) Math.ceil((float) videoHeight
//                            / (float) widthRatio);
//                    videoWidth = (int) Math.ceil((float) videoWidth
//                            / (float) widthRatio);
//                }
//            }
//        }
        seekBar.setX(100);
        seekBar.setY(videoHeight - 130);
        iv1.setX(10);
        iv1.setY(videoHeight - 160);
        surface.setLayoutParams(new RelativeLayout.LayoutParams(videoWidth,
                videoHeight));
        media.start();
    }

    public void onCompletion(MediaPlayer arg0) {
    }

    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
    }

    public void surfaceCreated(SurfaceHolder arg0) {
        mediaPlayer.setDisplay(holder);
    }

    public void surfaceDestroyed(SurfaceHolder arg0) {
    }

    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer = null;
        }
        super.onDestroy();
    }
}
