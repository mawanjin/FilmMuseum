package com.example.arthighlights;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;
import com.example.BaseActivity;
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
public class ArtHighlightsActivity extends BaseActivity implements
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

		initSlideMenu();

	}


	private List<Map<String, Object>> list;

	private List<Map<String, Object>> getData() {
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		List<ArtMenu> menu = MagicFactory.getArtMenus();
		for (ArtMenu art : menu) {
            if(art.getType().equals("list"))continue;
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
