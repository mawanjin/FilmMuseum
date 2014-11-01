package com.example.navigation;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.data.MagicFactory;
import com.example.data.Recommend;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 推荐路线
 */
public class RecommendActivity extends Activity  {

	private TextView tv;

	private ImageView ivReturn;

	private ImageView iv1, iv2, iv3, iv4;

	private ImageView ivCon;

	private SlidingMenu menu;
	private ImageView ivMenu;
	private Bitmap bm;

    private List<Recommend> recommends;
    private LinearLayout menuContainer;
    private List<ImageView> imageViews = new ArrayList<ImageView>(0);
    private ImageView imageView;

    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.recommend);
		SysApplication.getInstance().addActivity(this);
        recommends = MagicFactory.getRecommends();

		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("推荐路线");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
        menuContainer = (LinearLayout) findViewById(R.id.menuContainer);

        ivCon = (ImageView) findViewById(R.id.iv_recommend);
        init();
        if(imageViews!=null&&imageViews.size()>0)
        imageViews.get(0).performClick();

		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
	}

    private void init() {
       for(int i=0;i<recommends.size();i++){
           final Recommend recommend = recommends.get(i);
           imageView = new ImageView(this);
           LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
           params.weight = 1;
           imageView.setLayoutParams(params);
           imageView.setImageBitmap(MagicFactory.getBitmap(recommend.getIndicator()));
           imageViews.add(imageView);
           menuContainer.addView(imageView);
           imageView.setOnClickListener(new OnClickListener() {
               @Override
               public void onClick(View view) {
                   for(int j=0;j<imageViews.size();j++){
                       ImageView imageView1 =imageViews.get(j);
                       if(view==imageView1){
                           imageView1.setImageBitmap(MagicFactory.getBitmap(recommends.get(j).getIndicator_selected()));
                           Bitmap bitmap = MagicFactory.getBitmap(recommends.get(j).getImg());
                           ivCon.setImageBitmap(bitmap);
                                   }else
                           imageView1.setImageBitmap(MagicFactory.getBitmap(recommends.get(j).getIndicator()));
                   }

               }
           });


       }
    }


	// 菜单键
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			menu.toggle();
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
	
	protected void onDestroy() {
		
		super.onDestroy();
	}

}
