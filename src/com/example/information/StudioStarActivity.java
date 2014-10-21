package com.example.information;

import java.util.Timer;
import java.util.TimerTask;

import com.example.arthighlights.ArtHighlightsActivity;
import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.filmmuseum.R.id;
import com.example.filmmuseum.R.layout;
import com.example.filmmuseum.R.menu;
import com.example.navigation.GlanceActivity;
import com.example.navigation.HighFloorActivity;
import com.example.navigation.NavigationActivity;
import com.example.navigation.RouteActivity;
import com.example.screening.FutureScreeningActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class StudioStarActivity extends Activity{

	private TextView tv;

	private ImageView ivReturn;
	private ImageView ivMenu,iv;
	private Bitmap bm;
	//片场星吧
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.studio_star);
		SysApplication.getInstance().addActivity(this);
		tv=(TextView) findViewById(R.id.tv_title);
		tv.setText("片场星吧");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		
		iv=(ImageView) findViewById(R.id.star_iv);
		bm=BitmapFactory.decodeResource(getResources(), R.drawable.star);
		iv.setImageBitmap(bm);
	}

	// 菜单键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK)
		{
			Timer exit = null;
			if(isExit == false)
			{
				isExit = true;
				Toast.makeText(getApplicationContext(),"再按一次推出程序",Toast.LENGTH_SHORT).show();
				exit=new Timer();
				exit.schedule(new TimerTask() {
					public void run() {
						isExit = false;
					}
				}, 2000);
			}else{
				finish();
				SysApplication.getInstance().exit();
			}
		}
		return false;
	}
	private static boolean isExit = false;
	
	protected void onDestroy() {
		Log.v("HTTWs", "studiostarActivity进入ondestroy");
		if(bm.isRecycled() == false)
		{
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}
}
