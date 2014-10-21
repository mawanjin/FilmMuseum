package com.example.screening;

import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.filmmuseum.R.layout;
import com.example.filmmuseum.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class GoddessActivity extends Activity {

	private TextView tv;

	private ImageView ivReturn,ivMenu;
	private ImageView iv;
	private Bitmap bm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.goddess);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("《神女》");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu=(ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		iv=(ImageView) findViewById(R.id.goddess_iv);
		bm=BitmapFactory.decodeResource(getResources(), R.drawable.goddess);
		iv.setImageBitmap(bm);
	}

	protected void onDestroy() {
		Log.v("HTTWs", "GoddessActivity进入ondestroy");
		if(bm != null && bm.isRecycled() == false)
		{
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}

}
