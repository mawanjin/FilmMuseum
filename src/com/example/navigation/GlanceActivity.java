package com.example.navigation;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.BaseActivity;
import com.example.data.Glance;
import com.example.data.GlanceContent;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;

/**
 *  速览
 */
public class GlanceActivity extends BaseActivity implements View.OnClickListener {

	private TextView tv;
	private ImageView iv;
	private ImageView ivReturn;
	// 速览

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
		initSlideMenu();

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


	protected void onDestroy() {
		Log.v("HTTWs", "GlanceActivity进入ondestroy");

		super.onDestroy();
	}

}
