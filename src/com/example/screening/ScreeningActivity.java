package com.example.screening;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.BaseActivity;
import com.example.data.MagicFactory;
import com.example.data.Screen;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展影活动
 */
public class ScreeningActivity extends BaseActivity {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;

    private List<Screen> screens;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.screening);
		SysApplication.getInstance().addActivity(this);

        screens = MagicFactory.getScreens();

		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("展映活动");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_screening);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				getList(), R.layout.item_information, new String[] { "title" },
				new int[] { R.id.tv_information });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
                Intent intent = new Intent();
                Screen screen = screens.get(position);
                if(screen.getType()==0){
                    intent.putExtra("index", position);
                    intent.setClass(ScreeningActivity.this,
                            NowScreeningActivity.class);
                    startActivity(intent);
                }else{
                    intent.putExtra("index",position);
                    intent.setClass(ScreeningActivity.this,
                            ReviewScreeningActivity.class);
                    startActivity(intent);
                }
			}
		});
	initSlideMenu();

	}

	private List<Map<String, Object>> list;

	public List<Map<String, Object>> getList() {
		list = new ArrayList<Map<String, Object>>();

        for(Screen screen:screens){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", screen.getTitle());
            list.add(map);
        }
		return list;
	}

	protected void onStop() {
		super.onStop();
	}

	protected void onDestroy() {
		Log.v("HTTWs", "screeningActivity进入ondestroy");
		list = null;
		menu = null;
		System.gc();
		super.onDestroy();
	}

}
