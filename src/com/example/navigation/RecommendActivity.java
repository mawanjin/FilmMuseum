package com.example.navigation;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.slidingmenu.lib.SlidingMenu;

public class RecommendActivity extends Activity implements OnClickListener {

	private TextView tv;

	private ImageView ivReturn;

	private ImageView iv1, iv2, iv3, iv4;

	private ImageView ivCon;

	private SlidingMenu menu;
	private ImageView ivMenu;
	private Bitmap bm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recommend);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("推荐路线");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivCon = (ImageView) findViewById(R.id.iv_recommend);
		iv1 = (ImageView) findViewById(R.id.iv1_recommend);
		iv2 = (ImageView) findViewById(R.id.iv2_recommend);
		iv3 = (ImageView) findViewById(R.id.iv3_recommend);
		iv4 = (ImageView) findViewById(R.id.iv4_recommend);
		iv1.setImageResource(R.drawable.submenu1_2);
		iv2.setImageResource(R.drawable.submenu2_1);
		iv3.setImageResource(R.drawable.submenu3_1);
		iv4.setImageResource(R.drawable.submenu4_1);
		iv1.setOnClickListener(this);
		iv2.setOnClickListener(this);
		iv3.setOnClickListener(this);
		iv4.setOnClickListener(this);
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.iv1_recommend:
			ivCon.setImageResource(R.drawable.main1);
			iv1.setImageResource(R.drawable.submenu1_2);
			iv2.setImageResource(R.drawable.submenu2_1);
			iv3.setImageResource(R.drawable.submenu3_1);
			iv4.setImageResource(R.drawable.submenu4_1);
			break;
		// 三小时路线
		case R.id.iv2_recommend:
			ivCon.setImageResource(R.drawable.main2);
			iv2.setImageResource(R.drawable.submenu2_2);
			iv1.setImageResource(R.drawable.submenu1_1);
			iv3.setImageResource(R.drawable.submenu3_1);
			iv4.setImageResource(R.drawable.submenu4_1);
			break;
		// 亲子游路线
		case R.id.iv3_recommend:
			ivCon.setImageResource(R.drawable.main3);
			iv3.setImageResource(R.drawable.submenu3_2);
			iv2.setImageResource(R.drawable.submenu2_1);
			iv1.setImageResource(R.drawable.submenu1_1);
			iv4.setImageResource(R.drawable.submenu4_1);
			break;
		// 老年游路线
		case R.id.iv4_recommend:
			ivCon.setImageResource(R.drawable.main4);
			iv4.setImageResource(R.drawable.submenu4_2);
			iv2.setImageResource(R.drawable.submenu2_1);
			iv3.setImageResource(R.drawable.submenu3_1);
			iv1.setImageResource(R.drawable.submenu1_1);
			break;
		default:
			break;
		}
	}

	// 菜单键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			menu.toggle();
			return true;
		}
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
	
	protected void onDestroy() {
		
		super.onDestroy();
	}

}
