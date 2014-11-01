package com.example.screening;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.BaseActivity;
import com.example.adapter.NowScreeningAdapter;
import com.example.data.MagicFactory;
import com.example.data.Screen;
import com.example.data.ScreenItem;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NowScreeningActivity extends BaseActivity implements OnClickListener {

	private ListView lv;
	private TextView tv;
	private ImageView ivReturn;
    private Screen screen;
    private int index;

	// 当前展映
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.now_screening);
		SysApplication.getInstance().addActivity(this);
        index =  getIntent().getIntExtra("index",0);
        screen = MagicFactory.getScreens().get(index);

		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText(screen.getTitle());
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_now);

		lv.setAdapter(new NowScreeningAdapter(this,screen.getScreenItems()));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();
                intent.setClass(NowScreeningActivity.this,
                        DragonActivity.class);
                intent.putExtra("screenItem", screen.getScreenItems().get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		initSlideMenu();

	}


	private List<Map<String, Object>> list;
	public List<Map<String, Object>> getList() {
		list = new ArrayList<Map<String, Object>>();
        for(ScreenItem item:screen.getScreenItems()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", item.getImg());
            map.put("tv5", "4D影片《鱼龙勇士》");
            map.put("tv1", "活动时间");
            map.put("tv2", "2014/06/04至2014/12/31");
            map.put("tv3", "展映时间");
            map.put("tv4", "10:30、11:30、14:15、15:45");
            list.add(map);
        }
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
		super.onStop();
	}
	
	protected void onDestroy() {
		Log.v("HTTWs", "NowScreeningActivity进入ondestroy");
			
		super.onDestroy();
	}
}
