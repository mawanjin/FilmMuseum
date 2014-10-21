package com.example.filmmuseum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.example.arthighlights.ArtHighlightsActivity;
import com.example.eagerness.EagernessActivity;
import com.example.information.InformationActivity;
import com.example.navigation.NavigationActivity;
import com.example.screening.ScreeningActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MenuInterfaceActivity extends Activity {

	private ListView lv;
	//菜单按钮，返回按钮
	private ImageView ivMenu,ivReturn;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menurface);
		//将activity装入集合
		SysApplication.getInstance().addActivity(this);
		ivMenu=(ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		
		ivReturn=(ImageView) findViewById(R.id.ivReturn);
		
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				//点击返回按钮时销毁界面
				finish();
				//界面切换动画
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		
		lv = (ListView) findViewById(R.id.lv);
		
		//给listview赋值
		SimpleAdapter adp=new SimpleAdapter(getApplicationContext(), getData(), R.layout.item, new String[]{"image","text"}, new int[]{R.id.menuIv,R.id.menu_tv});
		lv.setAdapter(adp);
		
		//listview的点击事件
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent();
				switch (position) {
				//艺术亮点
				case 0:
					intent.setClass(MenuInterfaceActivity.this, ArtHighlightsActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//先睹为快
				case 1:
					intent.setClass(MenuInterfaceActivity.this, EagernessActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//展馆导航
				case 2:
					intent.setClass(MenuInterfaceActivity.this, NavigationActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//展映活动
				case 3:
					intent.setClass(MenuInterfaceActivity.this, ScreeningActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//参观资讯
				case 4:
					intent.setClass(MenuInterfaceActivity.this, InformationActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				default:
					break;
				}
			}
		});
	}
	
	private List<Map<String, Object>> list;
	private List<Map<String, Object>> getData() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("image", R.drawable.btn1);
		map.put("text", "艺术亮点");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn2);
		map.put("text", "先睹为快");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn3);
		map.put("text", "展馆导航");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn4);
		map.put("text", "展映活动");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn5);
		map.put("text", "参观资讯");
		list.add(map);
		return list;
	}
	
	//返回键
	@SuppressWarnings("static-access")
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
	
	protected void onStop() {
		Log.v("HTTWs","menuinterface进入onstop");
		super.onStop();
	}
	protected void onDestroy() {
		Log.v("HTTWs","menuinterface进入ondestroy");
		if(list.size() != 0)
		{
			list=null;
			System.gc();
		}
		super.onDestroy();
	}

}
