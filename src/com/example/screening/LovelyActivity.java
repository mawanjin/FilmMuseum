package com.example.screening;

import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.filmmuseum.R.layout;
import com.example.filmmuseum.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

public class LovelyActivity extends Activity {

	private TextView tv;

	private ImageView ivReturn,iv,ivMenu;
	private Bitmap bm;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lovely);
		SysApplication.getInstance().addActivity(this);
//		获取屏幕分辨率
//		Display display=getWindowManager().getDefaultDisplay();
//		Log.v("HTTWs","---width="+display.getWidth()+"  "+"---height="+display.getHeight());
		
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("《恋爱与义务》");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		iv=(ImageView) findViewById(R.id.lovely_iv);
		ivMenu=(ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		bm=BitmapFactory.decodeResource(getResources(), R.drawable.lovely);
		iv.setImageBitmap(bm);
		
	}

	protected void onDestroy() {
		Log.v("HTTWs","Lovely进入ondestroy");
		if(bm != null && bm.isRecycled() == false)
		{
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}

}
