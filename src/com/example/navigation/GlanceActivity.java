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
 *  ����
 */
public class GlanceActivity extends Activity implements View.OnClickListener {

	private TextView tv;
	private ImageView iv;
	private ImageView ivReturn;
	// ����
	private SlidingMenu menu;
	private ImageView ivMenu;
	private Bitmap bm;
    private Glance glance;
    private LinearLayout container;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.glance);
		SysApplication.getInstance().addActivity(this);

        glance = MagicFactory.getGlance();
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("����");
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
	
	protected void onDestroy() {
		Log.v("HTTWs", "GlanceActivity����ondestroy");
		if(bm!=null && bm.isRecycled() == false)
		{
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}

}
