package com.example.screening;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
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

public class FutureScreeningActivity extends BaseActivity implements OnClickListener {

	private ListView lv;

	private TextView tv;

	private ImageView ivReturn;

	// 展映计划
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.future_screening);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("展映计划");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_future);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				getList(), R.layout.item_screening, new String[] { "image",
						"tv5", "tv1", "tv2", "tv3", "tv4" }, new int[] {
						R.id.iv_screening, R.id.tv_screening,
						R.id.tv1_screening, R.id.tv2_screening,
						R.id.tv3_screening, R.id.tv4_screening });

		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
				switch (position) {
				case 0:
					intent.setClass(FutureScreeningActivity.this,
							DragonActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent.setClass(FutureScreeningActivity.this,
							HeavenActivity.class);
					startActivity(intent);
					break;
				default:
					break;
				}
			}
		});
		initSlideMenu();

	}


	private List<Map<String, Object>> list;
	public List<Map<String, Object>> getList() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", R.drawable.dragon);
		map.put("tv5", "4D影片《鱼龙勇士》");
		map.put("tv1", "活动时间");
		map.put("tv2", "2014/06/04至2014/12/31");
		map.put("tv3", "展映时间");
		map.put("tv4", "10:30、11:30、14:15、15:45");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("image", R.drawable.heaven);
		map.put("tv5", "《大闹天宫》4D版");
		map.put("tv1", "活动时间");
		map.put("tv2", "2014/06/17至2014/12/31");
		map.put("tv3", "展映时间");
		map.put("tv4", "10:00、11:00、13:30、15:00");
		list.add(map);
		return list;
	}
	
	
	protected void onStop() {
		Log.v("HTTWs", "FutureScreeningActivity进入onstop");
		super.onStop();
	}
	
	protected void onDestroy() {
		Log.v("HTTWs", "FutureScreeningActivity进入ondestroy");
		if(list.size()  != 0)
		{
			list = null;
			System.gc();
		}
		super.onDestroy();
	}
}
