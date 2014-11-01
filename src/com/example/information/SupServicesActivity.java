package com.example.information;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.BaseActivity;
import com.example.data.Info;
import com.example.data.InfoItem;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第二种排版
 */
public class SupServicesActivity extends BaseActivity{

	private TextView tv;
	private ListView lv;
	private ImageView ivReturn;

    private Info info;

	// 配套服务
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.sup_services);
		SysApplication.getInstance().addActivity(this);
        info = (Info) getIntent().getSerializableExtra("info");
        if(info==null){//从侧边menu直接点击进来的
            info = MagicFactory.getInfos().get(3);
        }
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText(info.getName());
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});

		lv = (ListView) findViewById(R.id.lv_sup_services);
		SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
				getList(), R.layout.item_information, new String[] { "title" },
				new int[] { R.id.tv_information });
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent intent = new Intent();

                intent.setClass(getApplicationContext(), ServiceCentreActivity.class);
                intent.putExtra("infoItem",info.getInfoItems().get(position));
                startActivity(intent);
			}
		});

        initSlideMenu();

	}



	private List<Map<String, Object>> list;
	public List<Map<String, Object>> getList() {
		list = new ArrayList<Map<String, Object>>();
        List<InfoItem> infoItems = info.getInfoItems();
        for(InfoItem infoItem:infoItems){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", infoItem.getName());
            list.add(map);
        }
		return list;
	}
	protected void onDestroy() {
		Log.v("HTTWs", "supservicesActivity进入ondestroy");
		list = null;
		System.gc();
		super.onDestroy();
	}
}
