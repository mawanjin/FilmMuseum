package com.example.navigation;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.arthighlights.ArtHighlightsActivity;
import com.example.data.Glance;
import com.example.data.GlanceContent;
import com.example.data.MagicFactory;
import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.information.*;
import com.example.screening.FutureScreeningActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.slidingmenu.lib.SlidingMenu;

import java.util.Timer;
import java.util.TimerTask;

/**
 *  速览
 */
public class GlanceActivity extends Activity implements View.OnClickListener {

	private TextView tv;
	private ImageView iv;
	private ImageView ivReturn;
	// 速览
	private SlidingMenu menu;
	private ImageView ivMenu;
	private Bitmap bm;
    private Glance glance;
    private LinearLayout container;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.glance);
		SysApplication.getInstance().addActivity(this);

        glance = MagicFactory.getGlance();
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("速览");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});


        init();

		iv=(ImageView) findViewById(R.id.glance_iv);
		iv.setImageBitmap(MagicFactory.getBitmap(glance.getImg()));
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

    private void init() {
        container = (LinearLayout) findViewById(R.id.container);
        for(int i=0;i<glance.getContents().size();i++){
            GlanceContent content = glance.getContents().get(i);
            TextView title = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(40,30,0,0);
            title.setLayoutParams(params);
            title.setText(Html.fromHtml(content.getTitle()));
            title.setTextColor(getResources().getColor(R.color.glance_title));
            title.setTextSize(32);
            container.addView(title);

            TextView summary = new TextView(this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, 10, 0, 0);
            summary.setLayoutParams(params);
            summary.setText(Html.fromHtml(content.getSummary()));
            summary.setTextSize(27);
            container.addView(summary);

            if(i!=glance.getContents().size()-1){
                ImageView line = new ImageView(this);
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0,20,0,20);
                line.setLayoutParams(params);
                line.setScaleType(ImageView.ScaleType.FIT_XY);
                line.setImageDrawable(getResources().getDrawable(R.drawable.line5));
                container.addView(line);
            }

        }


    }

    public void onClick(View view) {
		Intent intent = new Intent();
		switch (view.getId()) {
		// 艺术亮点
		case R.id.btn_art:
			intent.setClass(getApplicationContext(),
					ArtHighlightsActivity.class);
			startActivity(intent);
			finish();
			break;
		// 先睹为快
		case R.id.btn_eag:
			intent.setClass(getApplicationContext(), EagernessActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展馆导航
		case R.id.btn_navigation:
			intent.setClass(getApplicationContext(), NavigationActivity.class);
			startActivity(intent);
			finish();
			break;
		// 博物馆楼层图
		case R.id.btn_flo:
			intent.setClass(getApplicationContext(), HighFloorActivity.class);
			startActivity(intent);
			finish();
			break;
		// 速览
		case R.id.btn_glance:
			intent.setClass(getApplicationContext(), GlanceActivity.class);
			startActivity(intent);
			finish();
			break;
		// 参观路线
		case R.id.btn_route:
			intent.setClass(getApplicationContext(), RouteActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展映活动
		case R.id.btn_screening:
			intent.setClass(getApplicationContext(), ScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 当前展映
		case R.id.btn_exhibition:
			intent.setClass(getApplicationContext(), NowScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展映回顾
		case R.id.btn_review:
			intent.setClass(getApplicationContext(),
					ReviewScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 展映计划
		case R.id.btn_program:
			intent.setClass(getApplicationContext(),
					FutureScreeningActivity.class);
			startActivity(intent);
			finish();
			break;
		// 参观资讯
		case R.id.btn_information:
			intent.setClass(getApplicationContext(), InformationActivity.class);
			startActivity(intent);
			finish();
			break;
		// 博物馆简介
		case R.id.btn_museum:
			intent.setClass(getApplicationContext(), IntroductionActivity.class);
			startActivity(intent);
			finish();
			break;
		// 开放时间
		case R.id.btn_business:
			intent.setClass(getApplicationContext(), BusinessActivity.class);
			startActivity(intent);
			finish();
			break;
		// 购票指南
		case R.id.btn_guide:
			intent.setClass(getApplicationContext(), TicketActivity.class);
			startActivity(intent);
			finish();
			break;
		// 配套服务
		case R.id.btn_supporting:
			intent.setClass(getApplicationContext(), SupServicesActivity.class);
			startActivity(intent);
			finish();
			break;
		// 参观须知
		case R.id.btn_notes:
			intent.setClass(getApplicationContext(), VisitActivity.class);
			startActivity(intent);
			finish();
			break;
		// 加入我们
		case R.id.btn_join:
			intent.setClass(getApplicationContext(), JoinActivity.class);
			startActivity(intent);
			finish();
			break;
		// 联系方式
		case R.id.btn_phone:
			intent.setClass(getApplicationContext(), ContactActivity.class);
			startActivity(intent);
			finish();
			break;
		default:
			break;
		}
	}

	// 菜单键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU)
		{
			menu.toggle();
			return true;
		}
		if(keyCode == event.KEYCODE_BACK)
		{
			Timer exit = null;
			if(isExit == false)
			{
				isExit = true;
				Toast.makeText(getApplicationContext(),"再按一次推出程序",Toast.LENGTH_SHORT).show();
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
	
	protected void onDestroy() {
		Log.v("HTTWs", "GlanceActivity进入ondestroy");
		if(bm!=null && bm.isRecycled() == false)
		{
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}

}
