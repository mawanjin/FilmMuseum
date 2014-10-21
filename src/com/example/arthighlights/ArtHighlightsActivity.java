package com.example.arthighlights;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SukiActivity;
import com.example.filmmuseum.SysApplication;
import com.example.information.BusinessActivity;
import com.example.information.ContactActivity;
import com.example.information.InformationActivity;
import com.example.information.IntroductionActivity;
import com.example.information.JoinActivity;
import com.example.information.SupServicesActivity;
import com.example.information.TicketActivity;
import com.example.information.VisitActivity;
import com.example.navigation.GlanceActivity;
import com.example.navigation.HighFloorActivity;
import com.example.navigation.NavigationActivity;
import com.example.navigation.RouteActivity;
import com.example.screening.FutureScreeningActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.example.util.ArtMenu;
import com.example.util.Download;
import com.slidingmenu.lib.SlidingMenu;

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
		tv.setText("��������");
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				menu = null;
				list = null;
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		// ��ʾ����
		gv = (GridView) findViewById(R.id.gv_highlights);
		DishAdapter adp = new DishAdapter(getApplicationContext(), getData());
		gv.setAdapter(adp);
		// �����ת
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
					Log.v("HTTWs", "���ŵ�����Ƶ");
					startActivity(intent);
					overridePendingTransition(R.anim.a2, R.anim.a1);
				}
				if ((type.get(position)).equals("music")) {
					intent.setClass(ArtHighlightsActivity.this,
							AudioActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("id", id.get(position));
					intent.putExtras(bundle);
					Log.v("HTTWs", "���ŵ�������");
					startActivity(intent);
					overridePendingTransition(R.anim.a2, R.anim.a1);
				}
			}
		});

		// ���ò˵���ť
		menu = new SlidingMenu(this);
		menu.setMode(SlidingMenu.RIGHT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setShadowWidthRes(R.dimen.shadow_width);
		// ���ò˵����ֺ���Ļʣ�µľ���
		menu.setBehindOffsetRes(R.dimen.setBehindOffsetRes);

		menu.setFadeDegree(0.35f);

		menu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
		// ���ز˵�
		View view = LayoutInflater.from(this)
				.inflate(R.layout.menu_right, null);
		view.findViewById(R.id.textView1).getWidth();
		menu.setMenu(view);
		// �����ʾ
		ivMenu.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				menu.toggle();
			}
		});

		// ��ȡ�˵��ؼ���ID
		view.findViewById(R.id.btn_right).setOnClickListener(this);
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

	// �˵�����Ŀؼ�����¼�
	public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		// �ҵ��ղ�
		case R.id.btn_right:
			intent.setClass(getApplicationContext(), SukiActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ��������
		case R.id.btn_art:
			intent.setClass(getApplicationContext(),
					ArtHighlightsActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// �ȶ�Ϊ��
		case R.id.btn_eag:
			intent.setClass(getApplicationContext(), EagernessActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// չ�ݵ���
		case R.id.btn_navigation:
			intent.setClass(getApplicationContext(), NavigationActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// �����¥��ͼ
		case R.id.btn_flo:
			intent.setClass(getApplicationContext(), HighFloorActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ����
		case R.id.btn_glance:
			intent.setClass(getApplicationContext(), GlanceActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// �ι�·��
		case R.id.btn_route:
			intent.setClass(getApplicationContext(), RouteActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// չӳ�
		case R.id.btn_screening:
			intent.setClass(getApplicationContext(), ScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ��ǰչӳ
		case R.id.btn_exhibition:
			intent.setClass(getApplicationContext(), NowScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// չӳ�ع�
		case R.id.btn_review:
			intent.setClass(getApplicationContext(),
					ReviewScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// չӳ�ƻ�
		case R.id.btn_program:
			intent.setClass(getApplicationContext(),
					FutureScreeningActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// �ι���Ѷ
		case R.id.btn_information:
			intent.setClass(getApplicationContext(), InformationActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ����ݼ��
		case R.id.btn_museum:
			intent.setClass(getApplicationContext(), IntroductionActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ����ʱ��
		case R.id.btn_business:
			intent.setClass(getApplicationContext(), BusinessActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ��Ʊָ��
		case R.id.btn_guide:
			intent.setClass(getApplicationContext(), TicketActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ���׷���
		case R.id.btn_supporting:
			intent.setClass(getApplicationContext(), SupServicesActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// �ι���֪
		case R.id.btn_notes:
			intent.setClass(getApplicationContext(), VisitActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ��������
		case R.id.btn_join:
			intent.setClass(getApplicationContext(), JoinActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		// ��ϵ��ʽ
		case R.id.btn_phone:
			intent.setClass(getApplicationContext(), ContactActivity.class);
			startActivity(intent);
			finish();
			overridePendingTransition(R.anim.a2, R.anim.a1);
			break;
		}
	}

	// ��������
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
				Toast.makeText(getApplication(), "�ٰ�һ���Ƴ�����", Toast.LENGTH_SHORT)
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

	// ��ȡsd����·��
	public static String getExternalStoragePath() {
		String state = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (android.os.Environment.getExternalStorageDirectory().canRead()) {
				return android.os.Environment.getExternalStorageDirectory()
						.getPath();
			}
		}
		return null;
	}

	private List<Map<String, Object>> list;
	private Bitmap bm;

	private List<Map<String, Object>> getData() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		Download dow = new Download();
		List<ArtMenu> menu = dow.readMenuXml(getExternalStoragePath()
				+ "/FilmMuseum/system/menu.xml");
		for (ArtMenu art : menu) {
			bm = BitmapFactory.decodeFile(getExternalStoragePath()
					+ "/FilmMuseum/system/image/" + art.getSrc());
			map.put("image", bm);
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
		if (bm.isRecycled() == false && bm != null) {
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}

	protected void onStop() {
		super.onStop();
	}
}
