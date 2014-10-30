package com.example.arthighlights;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.data.MagicFactory;
import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SukiActivity;
import com.example.filmmuseum.SysApplication;
import com.example.information.*;
import com.example.navigation.GlanceActivity;
import com.example.navigation.HighFloorActivity;
import com.example.navigation.NavigationActivity;
import com.example.navigation.RouteActivity;
import com.example.screening.FutureScreeningActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.example.util.ArtMenu;
import com.slidingmenu.lib.SlidingMenu;

import java.util.*;

/**
 * 艺术亮点
 */
public class ArtHighlightsActivity extends Activity implements
		View.OnClickListener {

	private ImageView ivReturn;

	private TextView tv;

	private GridView gv;

	private ImageView ivMenu;

	private SlidingMenu menu;
	private List<Integer> id;
	private List<String> type;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.art_highlights);
		SysApplication.getInstance().addActivity(this);
		id = new ArrayList<Integer>();
		type = new ArrayList<String>();
		tv = (TextView) findViewById(R.id.tv_title);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		tv.setText("艺术亮点");
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				menu = null;
				list = null;
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		// 显示数据
		gv = (GridView) findViewById(R.id.gv_highlights);
		DishAdapter adp = new DishAdapter(getApplicationContext(), getData());
		gv.setAdapter(adp);
		// 点击跳转
		gv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long ids) {
				Intent intent = new Intent();
				if ((type.get(position)).equals("player")) {
					intent.setClass(ArtHighlightsActivity.this,
							VideoActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("id", id.get(position));
					intent.putExtras(bundle);
					Log.v("HTTWs", "播放的是视频");
					startActivity(intent);
					overridePendingTransition(R.anim.a2, R.anim.a1);
				}
				if ((type.get(position)).equals("music")) {
					intent.setClass(ArtHighlightsActivity.this,
							AudioActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("id", id.get(position));
					intent.putExtras(bundle);
					Log.v("HTTWs", "播放的是音乐");
					startActivity(intent);
					overridePendingTransition(R.anim.a2, R.anim.a1);
				}
			}
		});

		// 设置菜单按钮
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// 设置菜单出现后屏幕剩下的距离
		menu.setBehindOffsetRes(R.dimen.setBehindOffsetRes);

		menu.setFadeDegree(0.35f);

		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// 加载菜单
		View view = LayoutInflater.from(this)
				.inflate(R.layout.menu_right, null);
		view.findViewById(R.id.textView1).getWidth();
		menu.setMenu(view);
		// 点击显示
		ivMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				menu.toggle();
			}
		});

		// 获取菜单控件的ID
		view.findViewById(R.id.btn_right).setOnClickListener(this);
		// 艺术亮点
		view.findViewById(R.id.btn_art).setOnClickListener(this);
		// 先睹为快
		view.findViewById(R.id.btn_eag).setOnClickListener(this);
		// 展馆导航
		view.findViewById(R.id.btn_navigation).setOnClickListener(this);
		// 博物馆楼层图
		view.findViewById(R.id.btn_flo).setOnClickListener(this);
		// 速览
		view.findViewById(R.id.btn_glance).setOnClickListener(this);
		// 参观路线
		view.findViewById(R.id.btn_route).setOnClickListener(this);
		// 展映活动
		view.findViewById(R.id.btn_screening).setOnClickListener(this);
		// 当前展映
		view.findViewById(R.id.btn_exhibition).setOnClickListener(this);
		// 展映回顾
		view.findViewById(R.id.btn_review).setOnClickListener(this);
		// 展映计划
		view.findViewById(R.id.btn_program).setOnClickListener(this);
		// 参观资讯
		view.findViewById(R.id.btn_information).setOnClickListener(this);
		// 博物馆简介
		view.findViewById(R.id.btn_museum).setOnClickListener(this);
		// 开放时间
		view.findViewById(R.id.btn_business).setOnClickListener(this);
		// 购票指南
		view.findViewById(R.id.btn_guide).setOnClickListener(this);
		// 配套服务
		view.findViewById(R.id.btn_supporting).setOnClickListener(this);
		// 参观须知
		view.findViewById(R.id.btn_notes).setOnClickListener(this);
		// 加入我们
		view.findViewById(R.id.btn_join).setOnClickListener(this);
		// 联系方式
		view.findViewById(R.id.btn_phone).setOnClickListener(this);

	}

	// 菜单里面的控件点击事件
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		// 我的收藏
		case R.id.btn_right:
			intent.setClass(getApplicationContext(), SukiActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 艺术亮点
		case R.id.btn_art:
			intent.setClass(getApplicationContext(),
					ArtHighlightsActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 先睹为快
		case R.id.btn_eag:
			intent.setClass(getApplicationContext(), EagernessActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 展馆导航
		case R.id.btn_navigation:
			intent.setClass(getApplicationContext(), NavigationActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 博物馆楼层图
		case R.id.btn_flo:
			intent.setClass(getApplicationContext(), HighFloorActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 速览
		case R.id.btn_glance:
			intent.setClass(getApplicationContext(), GlanceActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 参观路线
		case R.id.btn_route:
			intent.setClass(getApplicationContext(), RouteActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 展映活动
		case R.id.btn_screening:
			intent.setClass(getApplicationContext(), ScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 当前展映
		case R.id.btn_exhibition:
			intent.setClass(getApplicationContext(), NowScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 展映回顾
		case R.id.btn_review:
			intent.setClass(getApplicationContext(),
					ReviewScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 展映计划
		case R.id.btn_program:
			intent.setClass(getApplicationContext(),
					FutureScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 参观资讯
		case R.id.btn_information:
			intent.setClass(getApplicationContext(), InformationActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 博物馆简介
		case R.id.btn_museum:
			intent.setClass(getApplicationContext(), IntroductionActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 开放时间
		case R.id.btn_business:
			intent.setClass(getApplicationContext(), BusinessActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 购票指南
		case R.id.btn_guide:
			intent.setClass(getApplicationContext(), TicketActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 配套服务
		case R.id.btn_supporting:
			intent.setClass(getApplicationContext(), SupServicesActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 参观须知
		case R.id.btn_notes:
			intent.setClass(getApplicationContext(), VisitActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 加入我们
		case R.id.btn_join:
			intent.setClass(getApplicationContext(), JoinActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// 联系方式
		case R.id.btn_phone:
			intent.setClass(getApplicationContext(), ContactActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		}
	}

	// 按键操作
	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			menu.toggle();
			return true;
		}
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getApplication(), "再按一次推出程序", Toast.LENGTH_SHORT)
						.show();
				exit = new Timer();
				exit.schedule(new TimerTask() {
					public void run() {
						isExit = false;
					}
				}, 2000);
			} else {
				// finish();
				android.os.Process.killProcess(android.os.Process.myPid());
				System.exit(0);
			}
		}
		return false;
	}

	private static boolean isExit = false;


	private List<Map<String, Object>> list;

	private List<Map<String, Object>> getData() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<ArtMenu> menu = MagicFactory.getArtMenus();
		for (ArtMenu art : menu) {
			map.put("image", MagicFactory.getBitmap(art.getSrc()));
			map.put("title", art.getTitle());
			id.add(art.getId());
			type.add(art.getType());
			map.put("content", art.getText());
			list.add(map);
			map = new HashMap<String, Object>();
		}
		return list;
	}

	public class DishAdapter extends BaseAdapter {

		private Context context;
		private List<Map<String, Object>> list;

		public DishAdapter(Context context, List<Map<String, Object>> list) {
			this.context = context;
			this.list = list;
		}

		public int getCount() {
			return list.size();
		}

		public Object getItem(int position) {
			return list.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View view, ViewGroup arg2) {
			if (view == null) {
				view = LayoutInflater.from(context).inflate(
						R.layout.item_highlights, null);
			}
			Bitmap bitmap = ((Bitmap) list.get(position).get("image"));
			String title = ((String) list.get(position).get("title"));
			String content = (String) list.get(position).get("content");
			ImageView iv = ((ImageView) view.findViewById(R.id.high_image));
			iv.setImageBitmap(bitmap);
			((TextView) view.findViewById(R.id.high_title)).setText(title);
			((TextView) view.findViewById(R.id.high_content)).setText(content);
			return view;
		}

	}

	protected void onRestart() {
		super.onRestart();
	}

	protected void onDestroy() {
		list = null;
		super.onDestroy();
	}

	protected void onStop() {
		super.onStop();
	}
}
