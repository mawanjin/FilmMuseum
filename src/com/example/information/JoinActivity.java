package com.example.information;

import android.app.ActivityManager;
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

public class JoinActivity extends BaseActivity implements OnClickListener{

	private TextView tv;

	private ImageView ivReturn;
	private ListView lv;
	//加入我们
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.join);
		SysApplication.getInstance().addActivity(this);
		tv=(TextView) findViewById(R.id.tv_title);
		tv.setText("加入我们");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		lv=(ListView) findViewById(R.id.lv_join);
		SimpleAdapter adapter= new SimpleAdapter(getApplicationContext(), getList(), R.layout.item_information, new String[]{"title"}, new int[]{R.id.tv_information});
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent intent=new Intent();
				if(position==0)
				{
					intent.setClass(getApplicationContext(),ConstitutionActivity.class);
					startActivity(intent);
				}else 
				{
//					intent.setClass(getApplicationContext(), VolunteerActivity.class);
//					startActivity(intent);
				}
				
			}
		});
		initSlideMenu();

	}


	private List<Map<String, Object>> list;
	public List<Map<String , Object>> getList()
	{
		list = new ArrayList<Map<String,Object>>();
		Map<String , Object> map=new HashMap<String, Object>();
		map.put("title", "会员章程");
		list.add(map);
		map=new HashMap<String, Object>();
		map.put("title","加入志愿者");
		list.add(map);
		return list;
	}
	
	protected void onStop() {
		System.gc();
		list = new ArrayList<Map<String,Object>>();
		super.onStop();
	}
	protected void onDestroy() {
		Log.v("HTTWs", "JoinActivity进入ondestroy");
		final ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
		activityManager.getMemoryInfo(info);
		Log.v("HTTWss", "系统剩余内存:" + (info.availMem >> 10) / 1024 + "M");
		super.onDestroy();
	}
}
