package com.example.eagerness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.BaseActivity;
import com.example.adapter.EagernessAdapter;
import com.example.arthighlights.ArtHighlightsActivity;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 先睹为快
 */
public class EagernessActivity extends BaseActivity {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;
    List<ArtMenu> datas;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.eagerness);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("先睹为快");
        datas = MagicFactory.getEagerness();
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		lv = (ListView) findViewById(R.id.lv_eagerness);

		lv.setAdapter(new EagernessAdapter(this,datas));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {

				Intent intent = new Intent();
					Bundle bundle = new Bundle();
					bundle.putString("src", datas.get(position).getSrc());
					intent.putExtras(bundle);
					intent.setClass(EagernessActivity.this,MovieActivity.class);
					startActivity(intent);
			}
		});
		initSlideMenu();

	}

	private List<Map<String, Object>> list;
	public List<Map<String, Object>> getList() {
		list = new ArrayList<Map<String, Object>>();


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("image", R.drawable.banner1);
		map.put("title", "微电影");
		map.put("image2", R.drawable.play);
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("image", R.drawable.banner2);
		map.put("title", "微电影");
		map.put("image2", R.drawable.play);
		list.add(map);
		return list;
	}

	// 叠放图片的方法
	@SuppressWarnings("deprecation")
	public LayerDrawable initBitmap(Bitmap b1, Bitmap b2) {
		Drawable[] array = new Drawable[2];
		array[0] = new BitmapDrawable(b1);
		array[1] = new BitmapDrawable(b2);
		LayerDrawable la = new LayerDrawable(array);
		la.setLayerInset(0, 0, 0, 0, 0);
		la.setLayerInset(1, 20, 20, 20, 20);
		return la;
	}
	
	protected void onDestroy() {
		Log.v("HTTWs","--->Eagerness进入ondestroy");
		super.onDestroy();
	}
	
	protected void onStop() {
		Log.v("HTTWs","--->Eagerness进入onstop");
		super.onStop();
	}
}
