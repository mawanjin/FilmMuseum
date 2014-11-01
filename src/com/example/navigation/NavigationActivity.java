package com.example.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.BaseActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展馆导航主页
 */
public class NavigationActivity extends BaseActivity implements
		View.OnClickListener {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.navigation);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("展馆导航");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				menu=null;
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lvFloor);
		SimpleAdapter adp = new SimpleAdapter(this,
				(List<? extends Map<String, ?>>) getData(), R.layout.item,
				new String[] { "image", "text" }, new int[] { R.id.menuIv,
						R.id.menu_tv });
		lv.setAdapter(adp);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				switch (position) {
				// 博物馆楼层图
				case 0:
					intent.setClass(NavigationActivity.this,
							HighFloorActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2, R.anim.a1);
					break;
				// 速览
				case 1:
					 intent.setClass(NavigationActivity.this,
					 GlanceActivity.class);
					 startActivity(intent);
					 overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				// 参观路线
				case 2:
					 intent.setClass(NavigationActivity.this,
					 RecommendActivity.class);
					 startActivity(intent);
					 overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				default:
					break;
				}
			}
		});
		initSlideMenu();

	}

	private List<Map<String, Object>> list;

	private List<Map<String, Object>> getData() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", R.drawable.floor_high1);
		map.put("text", "博物馆楼层图");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.floor_high2);
		map.put("text", "速览");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.floor_high3);
		map.put("text", "参观路线");
		list.add(map);
		return list;
	}
	
	protected void onDestroy() {
		Log.v("HTTWs","navigation进入ondestroy");
		if (list.size() != 0) {
			list = null;
		}
		menu = null;
		System.gc();
		super.onDestroy();
	}

	protected void onStop() {
		Log.v("HTTWs","navigation进入onstop");
		super.onStop();
	}

}
