package com.example.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.BaseActivity;
import com.example.arthighlights.ArtHighlightsActivity;
import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.information.*;
import com.example.screening.FutureScreeningActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.slidingmenu.lib.SlidingMenu;

import java.util.*;

public class RouteActivity extends BaseActivity implements View.OnClickListener {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;

	// 参观路线
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.route);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("参观路线");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_route);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				getList(), R.layout.item_route, new String[] { "title" },
				new int[] { R.id.tv_route });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
				if (position == 0) {
					intent.setClass(getApplicationContext(),
							RecommendActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2, R.anim.a1);
				} else {
					intent.setClass(getApplicationContext(),
							OptionalActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2, R.anim.a1);
				}

			}
		});
		initSlideMenu();

	}


	public List<Map<String, Object>> getList() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "推荐路线");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("title", "自选路线");
		list.add(map);
		return list;
	}
}
