package com.example.filmmuseum;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.BaseActivity;
@Deprecated
public class SukiActivity extends BaseActivity {

	private TextView tv;
	private ImageView ivReturn;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.suki);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		tv.setText("我的收藏");
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		initSlideMenu();

	}

}
