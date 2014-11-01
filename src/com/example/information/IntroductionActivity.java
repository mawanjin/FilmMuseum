package com.example.information;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.data.Info;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.FileSysUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 第一种排版
 */
public class IntroductionActivity extends Activity{

	private TextView tv;

	private ImageView ivReturn;
	private ImageView ivMenu,iv;
    private Info info;
    private TextView title,detail;
	//博物馆简介
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.introduction);
		SysApplication.getInstance().addActivity(this);
        info = (Info) getIntent().getSerializableExtra("info");

		tv=(TextView) findViewById(R.id.tv_title);

		tv.setText(info.getName());

        title = (TextView) findViewById(R.id.introduction_tv1);
        title.setText(info.getTitle());

        detail = (TextView) findViewById(R.id.introduction_tv2);
        String content = info.getSummary();
        if(content.contains("<img")){
            content = content.replaceAll("<img src=\"", "<img src=\""+FileSysUtils.getImagePath());
        }
        final Html.ImageGetter imageGetter = new Html.ImageGetter() {

            public Drawable getDrawable(String source) {
                Drawable drawable=null;
                drawable=Drawable.createFromPath(source);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                return drawable;};
        };

        detail.setText(Html.fromHtml(content, imageGetter, null));

		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2,R.anim.a1);
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		iv=(ImageView) findViewById(R.id.introduction_iv);
//		bm=BitmapFactory.decodeResource(getResources(), R.drawable.introduction);

		if(info.getImg()==null||info.getImg().equals("")){
            iv.setVisibility(View.GONE);
        }else
		    iv.setImageBitmap(MagicFactory.getBitmap(info.getImg()));

	}

	// 菜单键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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
	protected void onStop() {
		Log.v("HTTWs", "introductionActivity进入onstop");
		super.onStop();
	}
	
	protected void onDestroy() {
		Log.v("HTTWs", "introductionActivity进入ondestroy");
		super.onDestroy();
	}
}
