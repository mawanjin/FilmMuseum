package com.example.screening;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.data.MagicFactory;
import com.example.data.ScreenItem;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

/**
 * 展映回顾详情
 */
public class LovelyActivity extends Activity {

	private TextView tv;

	private ImageView ivReturn,iv,ivMenu;
	private Bitmap bm;
    private ScreenItem screenItem;
    private TextView detailTitle,detail;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lovely);
		SysApplication.getInstance().addActivity(this);

        screenItem = (ScreenItem) getIntent().getSerializableExtra("screenItem");
        detail = (TextView) findViewById(R.id.lovely_tv4);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText(screenItem.getName());
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		iv=(ImageView) findViewById(R.id.lovely_iv);
		ivMenu=(ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);

		bm=BitmapFactory.decodeResource(getResources(), R.drawable.lovely);
		iv.setImageBitmap(MagicFactory.getBitmap(screenItem.getDetailImg()));

        detailTitle = (TextView) findViewById(R.id.lovely_tv1);
        detailTitle.setText(screenItem.getDetailTitle());
        detail.setText(Html.fromHtml(screenItem.getDetail()));
		
	}

	protected void onDestroy() {
		Log.v("HTTWs","Lovely进入ondestroy");
		if(bm != null && bm.isRecycled() == false)
		{
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}

}
