package com.example.screening;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.arthighlights.ArtHighlightsActivity;
import com.example.data.MagicFactory;
import com.example.data.Screen;
import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.information.*;
import com.example.navigation.GlanceActivity;
import com.example.navigation.HighFloorActivity;
import com.example.navigation.NavigationActivity;
import com.example.navigation.RouteActivity;
import com.slidingmenu.lib.SlidingMenu;

import java.util.*;

/**
 * չӰ�
 */
public class ScreeningActivity extends Activity implements OnClickListener {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;

	private SlidingMenu menu;
	private ImageView ivMenu;
    private List<Screen> screens;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.screening);
		SysApplication.getInstance().addActivity(this);

        screens = MagicFactory.getScreens();

		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("չӳ�");
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
				switch (position) {
				case 0:
					intent.setClass(ScreeningActivity.this,
							NowScreeningActivity.class);
					startActivity(intent);
					break;
				case 1:
					intent.setClass(ScreeningActivity.this,
							ReviewScreeningActivity.class);
					startActivity(intent);
					break;
				case 2:
					intent.setClass(ScreeningActivity.this,
							FutureScreeningActivity.class);
					startActivity(intent);
					break;
				}
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		menu.setBehindOffsetRes(R.dimen.setBehindOffsetRes);
		menu.setFadeDegree(0.35f);
		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		View view = LayoutInflater.from(this)
				.inflate(R.layout.menu_right, null);
		view.findViewById(R.id.textView1).getWidth();
		menu.setMenu(view);
		ivMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				menu.toggle();
			}
		});
		// ��������
		view.findViewById(R.id.btn_art).setOnClickListener(this);
		// �ȶ�Ϊ��
		view.findViewById(R.id.btn_eag).setOnClickListener(this);
		// չ�ݵ���
		view.findViewById(R.id.btn_navigation).setOnClickListener(this);
		// �����¥��ͼ
		view.findViewById(R.id.btn_flo).setOnClickListener(this);
		// ����
		view.findViewById(R.id.btn_glance).setOnClickListener(this);
		// �ι�·��
		view.findViewById(R.id.btn_route).setOnClickListener(this);
		// չӳ�
		view.findViewById(R.id.btn_screening).setOnClickListener(this);
		// ��ǰչӳ
		view.findViewById(R.id.btn_exhibition).setOnClickListener(this);
		// չӳ�ع�
		view.findViewById(R.id.btn_review).setOnClickListener(this);
		// չӳ�ƻ�
		view.findViewById(R.id.btn_program).setOnClickListener(this);
		// �ι���Ѷ
		view.findViewById(R.id.btn_information).setOnClickListener(this);
		// ����ݼ��
		view.findViewById(R.id.btn_museum).setOnClickListener(this);
		// ����ʱ��
		view.findViewById(R.id.btn_business).setOnClickListener(this);
		// ��Ʊָ��
		view.findViewById(R.id.btn_guide).setOnClickListener(this);
		// ���׷���
		view.findViewById(R.id.btn_supporting).setOnClickListener(this);
		// �ι���֪
		view.findViewById(R.id.btn_notes).setOnClickListener(this);
		// ��������
		view.findViewById(R.id.btn_join).setOnClickListener(this);
		// ��ϵ��ʽ
		view.findViewById(R.id.btn_phone).setOnClickListener(this);

	}

	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		// ��������
		case R.id.btn_art:
			intent.setClass(getApplicationContext(),
					ArtHighlightsActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ȶ�Ϊ��
		case R.id.btn_eag:
			intent.setClass(getApplicationContext(), EagernessActivity.class);
			startActivity(intent);
			finish();
			break;
		// չ�ݵ���
		case R.id.btn_navigation:
			intent.setClass(getApplicationContext(), NavigationActivity.class);
			startActivity(intent);
			finish();
			break;
		// �����¥��ͼ
		case R.id.btn_flo:
			intent.setClass(getApplicationContext(), HighFloorActivity.class);
			startActivity(intent);
			finish();
			break;
		// ����
		case R.id.btn_glance:
			intent.setClass(getApplicationContext(), GlanceActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ι�·��
		case R.id.btn_route:
			intent.setClass(getApplicationContext(), RouteActivity.class);
			startActivity(intent);
			finish();
			break;
		// չӳ�
		case R.id.btn_screening:
			intent.setClass(getApplicationContext(), ScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��ǰչӳ
		case R.id.btn_exhibition:
			intent.setClass(getApplicationContext(), NowScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// չӳ�ع�
		case R.id.btn_review:
			intent.setClass(getApplicationContext(),
					ReviewScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// չӳ�ƻ�
		case R.id.btn_program:
			intent.setClass(getApplicationContext(),
					FutureScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ι���Ѷ
		case R.id.btn_information:
			intent.setClass(getApplicationContext(), InformationActivity.class);
			startActivity(intent);
			finish();
			break;
		// ����ݼ��
		case R.id.btn_museum:
			intent.setClass(getApplicationContext(), IntroductionActivity.class);
			startActivity(intent);
			finish();
			break;
		// ����ʱ��
		case R.id.btn_business:
			intent.setClass(getApplicationContext(), BusinessActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��Ʊָ��
		case R.id.btn_guide:
			intent.setClass(getApplicationContext(), TicketActivity.class);
			startActivity(intent);
			finish();
			break;
		// ���׷���
		case R.id.btn_supporting:
			intent.setClass(getApplicationContext(), SupServicesActivity.class);
			startActivity(intent);
			finish();
			break;
		// �ι���֪
		case R.id.btn_notes:
			intent.setClass(getApplicationContext(), VisitActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��������
		case R.id.btn_join:
			intent.setClass(getApplicationContext(), JoinActivity.class);
			startActivity(intent);
			finish();
			break;
		// ��ϵ��ʽ
		case R.id.btn_phone:
			intent.setClass(getApplicationContext(), ContactActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	// �˵���
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			menu.toggle();
			return true;
		}
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "�ٰ�һ���Ƴ�����",
						Toast.LENGTH_SHORT).show();
				exit = new Timer();
				exit.schedule(new TimerTask() {
					public void run() {
						isExit = false;
					}
				}, 2000);
			} else {
				finish();
				SysApplication.getInstance().exit();
			}
		}
		return false;
	}

	private static boolean isExit = false;
	private List<Map<String, Object>> list;

	public List<Map<String, Object>> getList() {
		list = new ArrayList<Map<String, Object>>();

        for(Screen screen:screens){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("title", screen.getTitle());
            list.add(map);
        }

//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("title", "��ǰչӳ");
//		list.add(map);
//		map = new HashMap<String, Object>();
//		map.put("title", "չӳ�ع�");
//		list.add(map);
//		map = new HashMap<String, Object>();
//		map.put("title", "չӳ�ƻ�");
//		list.add(map);
		return list;
	}

	protected void onStop() {
		super.onStop();
	}

	protected void onDestroy() {
		Log.v("HTTWs", "screeningActivity����ondestroy");
		list = null;
		menu = null;
		System.gc();
		super.onDestroy();
	}

}
