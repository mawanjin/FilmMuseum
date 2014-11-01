package com.example.information;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.BaseActivity;
import com.example.data.Info;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参观资讯主页
 */
public class InformationActivity extends BaseActivity implements OnClickListener {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;
	
    private List<Info> infos;

	// 参观资讯
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.information);
		SysApplication.getInstance().addActivity(this);
        infos = MagicFactory.getInfos();
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("参观资讯");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				menu = null;
				list = null;
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_information);
		SimpleAdapter adp = new SimpleAdapter(getApplicationContext(),
				getDate(), R.layout.item_information, new String[] { "title" },
				new int[] { R.id.tv_information });
		lv.setAdapter(adp);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {

				Intent intent=new Intent();
                intent.putExtra("info",infos.get(position));
                if(infos.get(position).getType()==0){
                    intent.setClass(InformationActivity.this, IntroductionActivity.class);
                    startActivity(intent);
                }else{
                    intent.setClass(InformationActivity.this, SupServicesActivity.class);
                    startActivity(intent);
                }
			}
		});
		initSlideMenu();

	}

	private List<Map<String, Object>> list;
	public List<Map<String, Object>> getDate() {
		list = new ArrayList<Map<String, Object>>();
        for(Info info:infos){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", info.getName());
            list.add(map);
        }
		return list;
	}
	protected void onStop() {
		super.onStop();
	}
	
	protected void onDestroy() {
		list = null;
		menu = null;
		System.gc();
		super.onDestroy();
	}
}	
