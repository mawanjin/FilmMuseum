package com.example.navigation;

import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arthighlights.AudioActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.regional.PhotoViewAttacher;
import com.example.regional.PhotoViewAttacher.OnMatrixChangedListener;
import com.example.regional.PhotoViewAttacher.OnPhotoTapListener;

public class HighFloorActivity extends Activity implements View.OnClickListener {

	private TextView tv;

	private ImageView iv1, iv2, iv3, iv4;
	private ImageView zom;
	private ImageView ivMenu;
	private ImageView ivReturn;
	private int cnt = 1;
	private PhotoViewAttacher attacher;
	private PopupWindow pop;
	private int popX;
	private int popY;
	private Bitmap bm;
	private LinearLayout layout;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.high_floor);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("博物馆楼层图");
		zom = (ImageView) findViewById(R.id.iv_highfloor);
		bm = BitmapFactory.decodeResource(getResources(), R.drawable.f1);
		zom.setImageBitmap(bm);
		attacher = new PhotoViewAttacher(zom);
		Log.v("HTTWs", "---->1");
		attacher.setOnMatrixChangeListener(new MatrixChangeListener());
		attacher.setOnPhotoTapListener(new PhotoTapListener());
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				System.gc();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		iv1 = (ImageView) findViewById(R.id.highfloor_iv1);
		iv2 = (ImageView) findViewById(R.id.highfloor_iv2);
		iv3 = (ImageView) findViewById(R.id.highfloor_iv3);
		iv4 = (ImageView) findViewById(R.id.highfloor_iv4);
		iv1.setImageResource(R.drawable.f1_2);
		iv2.setImageResource(R.drawable.f2_1);
		iv3.setImageResource(R.drawable.f3_1);
		iv4.setImageResource(R.drawable.f4_1);
		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);

		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
	}

	public void onClick(View view) {
		if (pop != null && pop.isShowing()) {
			pop.dismiss();
		}
		switch (view.getId()) {
		case R.id.highfloor_iv1:
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.f1);
			zom.setImageBitmap(bm);
			cnt = 1;
			iv1.setImageResource(R.drawable.f1_2);
			iv2.setImageResource(R.drawable.f2_1);
			iv3.setImageResource(R.drawable.f3_1);
			iv4.setImageResource(R.drawable.f4_1);
			break;
		case R.id.highfloor_iv2:
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.f2);
			zom.setImageBitmap(bm);
			cnt = 2;
			iv1.setImageResource(R.drawable.f1_1);
			iv2.setImageResource(R.drawable.f2_2);
			iv3.setImageResource(R.drawable.f3_1);
			iv4.setImageResource(R.drawable.f4_1);

			break;
		case R.id.highfloor_iv3:
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.f3);
			zom.setImageBitmap(bm);
			cnt = 3;
			iv1.setImageResource(R.drawable.f1_1);
			iv2.setImageResource(R.drawable.f2_1);
			iv3.setImageResource(R.drawable.f3_2);
			iv4.setImageResource(R.drawable.f4_1);
			break;
		case R.id.highfloor_iv4:
			bm = BitmapFactory.decodeResource(getResources(), R.drawable.f4);
			zom.setImageBitmap(bm);
			cnt = 4;
			iv1.setImageResource(R.drawable.f1_1);
			iv2.setImageResource(R.drawable.f2_1);
			iv3.setImageResource(R.drawable.f3_1);
			iv4.setImageResource(R.drawable.f4_2);
			break;
		default:
			break;
		}
	}

	// 菜单键
	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
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
		Log.v("HTTWs", "highfloorActivity进入onstop");
		super.onStop();
	}

	protected void onDestroy() {
		Log.v("HTTWs", "highfloorActivity进入ondestroy");
		if (bm.isRecycled() == false) {
			bm.recycle();
			bm = null;
			System.gc();
		}
		zom = null;
		System.gc();
		super.onDestroy();
	}

	private class PhotoTapListener implements OnPhotoTapListener {

		public void onPhotoTap(View view, float x, float y) {

			float xPercentage = x * 100f;
			float yPercentage = y * 100f;
			float scale = attacher.getScale();
			if (cnt == 1) {
				if (xPercentage > 10 && xPercentage < 24 && yPercentage > 15
						&& yPercentage < 25) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						Log.v("HTTWs", " __ 1");
						popX = (int) (attacher.evX - 150+150*(1-scale));
						popY = (int) (attacher.evY - 70+160*(1-scale));
					}
					showPopUp(view, 1, 1, scale);
				}
				if (xPercentage > 60 && xPercentage < 70 && yPercentage > 15
						&& yPercentage < 25) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 1, scale);
				}
				if (xPercentage > 80 && xPercentage < 95 && yPercentage > 30
						&& yPercentage < 40) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 1, scale);
				}
				if (xPercentage > 30 && xPercentage < 40 && yPercentage > 50
						&& yPercentage < 60) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 1, scale);
				}
				if (xPercentage > 35 && xPercentage < 45 && yPercentage > 65
						&& yPercentage < 75) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 1, scale);
				}
			}
			if (cnt == 2) {
				if (xPercentage > 15 && xPercentage < 26 && yPercentage > 17
						&& yPercentage < 25) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 2, scale);
				}
				if (xPercentage > 75 && xPercentage < 85 && yPercentage > 24
						&& yPercentage < 32) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 2, scale);
				}
				if (xPercentage > 8 && xPercentage < 18 && yPercentage > 50
						&& yPercentage < 56) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 2, scale);
				}
				if (xPercentage > 30 && xPercentage < 41 && yPercentage > 60
						&& yPercentage < 70) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 2, scale);
				}
				if (xPercentage > 58 && xPercentage < 67 && yPercentage > 55
						&& yPercentage < 62) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 2, scale);
				}
				if (xPercentage > 22 && xPercentage < 33 && yPercentage > 80
						&& yPercentage < 88) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 2, scale);
				}
				if (xPercentage > 62 && xPercentage < 72 && yPercentage > 80
						&& yPercentage < 90) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 2, scale);
				}
			}
			if (cnt == 3) {
				if (xPercentage > 13 && xPercentage < 23 && yPercentage > 20
						&& yPercentage < 30) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 3, scale);
				}
				if (xPercentage > 40 && xPercentage < 50 && yPercentage > 22
						&& yPercentage < 32) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 3, scale);
				}
				if (xPercentage > 71 && xPercentage < 81 && yPercentage > 21
						&& yPercentage < 31) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 3, scale);
				}
				if (xPercentage > 22 && xPercentage < 32 && yPercentage > 47
						&& yPercentage < 57) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 3, scale);
				}
				if (xPercentage > 74 && xPercentage < 84 && yPercentage > 48
						&& yPercentage < 58) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 3, scale);
					;
				}
				if (xPercentage > 38 && xPercentage < 48 && yPercentage > 76
						&& yPercentage < 86) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 3, scale);
				}
			}
			if (cnt == 4) {
				if (xPercentage > 13 && xPercentage < 23 && yPercentage > 23
						&& yPercentage < 33) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 4, scale);
				}
				if (xPercentage > 76 && xPercentage < 86 && yPercentage > 34
						&& yPercentage < 44) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 4, scale);
				}
				if (xPercentage > 30 && xPercentage < 40 && yPercentage > 50
						&& yPercentage < 60) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 4, scale);
				}
				if (xPercentage > 52 && xPercentage < 62 && yPercentage > 72
						&& yPercentage < 82) {
					if (attacher.getMaxScale() == (int) attacher.getScale()) {
						popX = (int) (attacher.evX - 270);
						popY = (int) (attacher.evY - 300);
					} else if (attacher.getMinScale() == attacher.getScale()) {
						popX = (int) (attacher.evX - 120);
						popY = (int) (attacher.evY - 30);
					} else {
						popX = (int) (attacher.evX - 150);
						popY = (int) (attacher.evY - 70);
					}
					showPopUp(view, 1, 4, scale);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void showPopUp(View v, final int id, int i, float scale) {
		layout = new LinearLayout(this);
		if (i == 1) {
			layout.setBackgroundResource(R.drawable.tab1);
		}
		if (i == 2) {
			layout.setBackgroundResource(R.drawable.tab2);
		}
		if (i == 3) {
			layout.setBackgroundResource(R.drawable.tab3);
		}
		if (i == 4) {
			layout.setBackgroundResource(R.drawable.tab4);
		}
		layout.setGravity(Gravity.CENTER);
		Button btn = new Button(this);
		btn.getBackground().setAlpha(0);
		btn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		btn.setText("灿烂金杯");
		btn.setTextColor(Color.WHITE);
		btn.setTextSize(25 * scale);
		layout.addView(btn);
		pop = new PopupWindow(layout, (int) (300 * scale), (int) (150 * scale));
		btn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), AudioActivity.class);
				Bundle bundle = new Bundle();
				bundle.putInt("id", id);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
		pop.setOutsideTouchable(true);
		pop.setBackgroundDrawable(new BitmapDrawable());

		int[] location = new int[2];
		v.getLocationOnScreen(location);

		pop.showAtLocation(v, Gravity.NO_GRAVITY, popX, popY);
	}

	private class MatrixChangeListener implements OnMatrixChangedListener {

		public void onMatrixChanged(RectF rect) {
		}
	}

}
