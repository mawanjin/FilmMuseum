package com.example.screening;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.BaseActivity;
import com.example.adapter.ReviewScreeningAdapter;
import com.example.data.MagicFactory;
import com.example.data.Screen;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

public class ReviewScreeningActivity extends BaseActivity implements
		OnClickListener {

	private ListView lv;
	private TextView tv;
	private ImageView ivReturn;
    private int index;
    Screen screen;

	// 展映回顾
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.review_screening);
		SysApplication.getInstance().addActivity(this);

        index =  getIntent().getIntExtra("index",-1);
        if(index==-1){//从slidemenu中直接点击过来的
            index = 1;
        }

        screen = MagicFactory.getScreens().get(index);

		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("展映回顾");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.a2, R.anim.a1);
            }
        });

		lv = (ListView) findViewById(R.id.lv_review_screening);

		lv.setAdapter(new ReviewScreeningAdapter(this,screen.getScreenItems()));
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
                Intent intent = new Intent();
                intent.setClass(ReviewScreeningActivity.this,
                        LovelyActivity.class);
                intent.putExtra("screenItem",screen.getScreenItems().get(position));
                startActivity(intent);
                overridePendingTransition(R.anim.a2, R.anim.a1);

			}
		});
		initSlideMenu();
	}

	protected void onStop() {
		Log.v("HTTWs", "ReviewScreeningActivity进入onstop");
		super.onStop();
	}
	protected void onDestroy() {
		Log.v("HTTWs", "ReviewScreeningActivity进入ondestroy");
		System.gc();
		super.onDestroy();
	}
}
