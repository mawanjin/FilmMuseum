package com.example.navigation;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.BaseActivity;

public class OptionalActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv;

    private ImageView ivReturn;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.optional);
//        SysApplication.getInstance().addActivity(this);
//        tv = (TextView) findViewById(R.id.tv_title);
//        tv.setText("自选路线");
//        ivReturn = (ImageView) findViewById(R.id.ivReturn);
//        ivReturn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View arg0) {
//                finish();
//                overridePendingTransition(R.anim.a2, R.anim.a1);
//            }
//        });
//        initSlideMenu();

    }
}
