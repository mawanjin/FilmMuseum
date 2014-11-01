package com.example.information;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.example.data.InfoItem;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.FileSysUtils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 配套服务--》第二种排版的详情
 */
public class ServiceCentreActivity extends Activity {

	private TextView tv;

	private ImageView ivReturn;

	private ListView lv;
	private ImageView ivMenu,iv;
	private Bitmap bm;
    private InfoItem infoItem;
    private TextView content;
	// 服务中心
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.service_centre);
		SysApplication.getInstance().addActivity(this);

        infoItem = (InfoItem) getIntent().getSerializableExtra("infoItem");
        content = (TextView) findViewById(R.id.container);
        String summary = infoItem.getContent();

        if(summary.contains("<img")){
            summary = summary.replaceAll("<img src=\"", "<img src=\""+ FileSysUtils.getImagePath());
        }
        final Html.ImageGetter imageGetter = new Html.ImageGetter() {

            public Drawable getDrawable(String source) {
                Drawable drawable=null;
                drawable=Drawable.createFromPath(source);
                if(drawable!=null){
                    int width = drawable.getIntrinsicWidth()*2;
                    int height = drawable.getIntrinsicHeight()*2;
                    drawable.setBounds(0, 0, width, height);
                }

                return drawable;};
        };
        content.setText(Html.fromHtml(summary,imageGetter,null));
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText(infoItem.getName());
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		iv=(ImageView) findViewById(R.id.center_iv);
		bm=BitmapFactory.decodeResource(getResources(), R.drawable.servicecen1);
		iv.setImageBitmap(MagicFactory.getBitmap(infoItem.getImage()));

	}

	// 菜单键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			return true;
		}
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "再按一次推出程序",
						Toast.LENGTH_SHORT).show();
				exit = new Timer();
				exit.schedule(new TimerTask() {
					public void run() {
						isExit = false;
					}
				}, 2000);
			} else {
				finish();
				SysApplication.getInstance().exit();
			}
		}
		return false;
	}

	private static boolean isExit = false;

	protected void onStop() {
		Log.v("HTTWs","ServiceCentreActivity进入onstop");
		super.onStop();
	}

	protected void onDestroy() {
		Log.v("HTTWs", "ServiceCentreActivity进入ondestroy");
		if(bm.isRecycled() == false)
		{
			bm.recycle();
			System.gc();
		}
		super.onDestroy();
	}
}
