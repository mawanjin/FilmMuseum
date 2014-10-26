package com.example.filmmuseum;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.arthighlights.ArtHighlightsActivity;
import com.example.eagerness.EagernessActivity;
import com.example.information.InformationActivity;
import com.example.navigation.NavigationActivity;
import com.example.screening.ScreeningActivity;

import java.util.*;

/**
 * ����������б���ҳ
 */
public class MenuInterfaceActivity extends Activity {

	private ListView lv;
	//�˵���ť�����ذ�ť
	private ImageView ivMenu,ivReturn;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.menurface);
		//��activityװ�뼯��
		SysApplication.getInstance().addActivity(this);
		ivMenu=(ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		
		ivReturn=(ImageView) findViewById(R.id.ivReturn);
		
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				//������ذ�ťʱ���ٽ���
				finish();
				//�����л�����
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		
		lv = (ListView) findViewById(R.id.lv);
		
		//��listview��ֵ
		SimpleAdapter adp=new SimpleAdapter(getApplicationContext(), getData(), R.layout.item, new String[]{"image","text"}, new int[]{R.id.menuIv,R.id.menu_tv});
		lv.setAdapter(adp);
		
		//listview�ĵ���¼�
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent=new Intent();
				switch (position) {
				//��������
				case 0:
					intent.setClass(MenuInterfaceActivity.this, ArtHighlightsActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//�ȶ�Ϊ��
				case 1:
					intent.setClass(MenuInterfaceActivity.this, EagernessActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//չ�ݵ���
				case 2:
					intent.setClass(MenuInterfaceActivity.this, NavigationActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//չӳ�
				case 3:
					intent.setClass(MenuInterfaceActivity.this, ScreeningActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.a2,R.anim.a1);
					break;
				//�ι���Ѷ
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
		map.put("text", "��������");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn2);
		map.put("text", "�ȶ�Ϊ��");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn3);
		map.put("text", "չ�ݵ���");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn4);
		map.put("text", "չӳ�");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("image", R.drawable.btn5);
		map.put("text", "�ι���Ѷ");
		list.add(map);
		return list;
	}
	
	//���ؼ�
	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == event.KEYCODE_BACK)
		{
			Timer exit = null;
			if(isExit == false)
			{
				isExit = true;
				Toast.makeText(getApplicationContext(),"�ٰ�һ���Ƴ�����",Toast.LENGTH_SHORT).show();
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
		Log.v("HTTWs","menuinterface����onstop");
		super.onStop();
	}
	protected void onDestroy() {
		Log.v("HTTWs","menuinterface����ondestroy");
		if(list.size() != 0)
		{
			list=null;
			System.gc();
		}
		super.onDestroy();
	}

}
