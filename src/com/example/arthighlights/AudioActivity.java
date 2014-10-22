package com.example.arthighlights;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtContent;
import com.example.util.ArtContentAudio;
import com.example.util.Download;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AudioActivity extends Activity implements OnSeekBarChangeListener {
	private TextView tv, tv2, tv3;
	@SuppressWarnings("unused")
	private ImageView ivReturn, ivMenu, iv, iv2,iv3;
	private MediaPlayer player = null;
	private SeekBar seekbar;
	private boolean flag = true;
	private upDateSeekBar update; // 更新进度条用
	private String path = null;
	private Bitmap bm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.audio);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);

		tv2 = (TextView) findViewById(R.id.tv_Audio);
		tv3 = (TextView) findViewById(R.id.tv2_Audio);

		iv = (ImageView) findViewById(R.id.audio_iv);
		iv2 = (ImageView) findViewById(R.id.audio_iv2);
		iv3 = (ImageView) findViewById(R.id.audio_iv3);

		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		player = new MediaPlayer();
		seekbar = (SeekBar) findViewById(R.id.audio_seekbar);
		Download dow = new Download();
		List<ArtContentAudio> cons = dow
				.readConXmlAudio(getExternalStoragePath()
						+ "/FilmMuseum/system/content2.xml");
		Log.v("HTTWs", "---->" + getExternalStoragePath()
				+ "/FilmMuseum/system/content2.xml");
		for (ArtContentAudio arts : cons) {
			if (arts.getVisible() == 0) {
				iv.setVisibility(View.GONE);
			} else {
				if (arts.getType().equals("image")) {
					if (arts.getId() == 1) {
						bm = BitmapFactory.decodeFile(getExternalStoragePath()
								+ "/FilmMuseum/system/image/" + arts.getSrc());
						iv.setImageBitmap(bm);
						iv.setX(arts.getX());
						iv.setY(arts.getY());
					}
					if (arts.getId() == 3) {
						bm = BitmapFactory.decodeFile(getExternalStoragePath()
								+ "/FilmMuseum/system/image/" + arts.getSrc());
						LayoutParams params = new LayoutParams(arts.getWidth(),
								arts.getHeight());
						iv2.setImageBitmap(bm);
						iv2.setX(arts.getX());
						iv2.setY(arts.getY());
						iv2.setLayoutParams(params);
					}
					if (arts.getId() == 6) {
						bm = BitmapFactory.decodeFile(getExternalStoragePath()
								+ "/FilmMuseum/system/image/" + arts.getSrc());
						iv3.setImageBitmap(bm);
						iv3.setX(arts.getX());
						iv3.setY(arts.getY());
					}
				} else if (arts.getType().equals("text")) {
					if (arts.getId() == 7) {
						tv2.setX(arts.getX());
						tv2.setY(arts.getY());
						tv2.setTextSize(arts.getTextsize());
					}
					if (arts.getId() == 8) {
						tv3.setX(arts.getX());
						tv3.setY(arts.getY());
						tv3.setTextSize(arts.getTextsize());
					}
				} else if (arts.getType().equals("seekbar")) {
					seekbar.setX(arts.getX());
					seekbar.setY(arts.getY());
				}
			}
		}
		
		List<ArtContent> con = dow.readConXml(getExternalStoragePath()
				+ "/FilmMuseum/system/content.xml");
		Bundle bundle = getIntent().getExtras();
		int id = bundle.getInt("id");
		for (ArtContent c : con) {
			if (id == c.getId()) {
				tv.setText(c.getTitle());
				tv2.setText(c.getTitle());
				tv3.setText(c.getContent());
				path = getExternalStoragePath() + "/FilmMuseum/system/image/"
						+ c.getSrc();
			}
		}
		try {
			player.setDataSource(path);
			player.prepare();
			player.start();
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
		player.setOnCompletionListener(new OnCompletionListener() {
			public void onCompletion(MediaPlayer arg0) {
				iv2.setImageResource(R.drawable.player);
				seekbar.setProgress(0);
			}
		});
		iv2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				if (!player.isPlaying()) {
					player.start();
					new Thread(update).start();
					iv2.setImageResource(R.drawable.pause);
					flag = true;
				} else {
					player.pause();
					new Thread(update).start();
					iv2.setImageResource(R.drawable.player);
					flag = false;
				}
			}
		});
		Log.v("HTTWs", "-------------->3 " + player.isPlaying());
	}

	@SuppressLint("HandlerLeak")
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (player == null) {
				flag = false;
			} else if (player.isPlaying()) {
				flag = true;
				int position = player.getCurrentPosition();
				int mMax = player.getDuration();
				int sMax = seekbar.getMax();
				seekbar.setProgress(position * sMax / mMax);
			} else {
				return;
			}
		};
	};

	// 每秒更新一次进度条
	class upDateSeekBar implements Runnable {
		public void run() {
			mHandler.sendMessage(Message.obtain());
			if (flag) {
				mHandler.postDelayed(update, 1000);
			}
		}
	}

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

	private static boolean isExit = false;

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

	protected void onDestroy() {
		player.release();
		player = null;
		if (bm.isRecycled() == false) {
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}

	protected void onStop() {
		Log.v("HTTWs", "Audio 进入onstop");
		player.stop();
		finish();
		super.onStop();
	}
	public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) {
		Log.v("HTTWs", "----------> " + progress);
	}

	public void onStartTrackingTouch(SeekBar arg0) {

	}

	public void onStopTrackingTouch(SeekBar arg0) {

	}

}
