package com.example;

import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.arthighlights.ArtHighlightsActivity;
import com.example.data.Info;
import com.example.data.MagicFactory;
import com.example.data.Screen;
import com.example.eagerness.EagernessActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.information.*;
import com.example.navigation.GlanceActivity;
import com.example.navigation.HighFloorActivity;
import com.example.navigation.NavigationActivity;
import com.example.navigation.RouteActivity;
import com.example.screening.NowScreeningActivity;
import com.example.screening.ReviewScreeningActivity;
import com.example.screening.ScreeningActivity;
import com.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/2
 * Time: 01:32
 */
public class BaseActivity extends Activity implements View.OnClickListener{
    private static boolean isExit = false;

     public SlidingMenu menu;
     public ImageView ivMenu;
    private List<Button> buttons = new ArrayList<Button>(0);
    /** �ι���Ѷ*/
    private List<Button> button1s = new ArrayList<Button>(0);

    public BaseActivity(){}
    public void initSlideMenu(){
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

        //��ʼ�����˵�����
        ((Button)view.findViewById(R.id.btn_art)).setText(MagicFactory.getOnlines().get(0).getName());
        ((Button)view.findViewById(R.id.btn_eag)).setText(MagicFactory.getOnlines().get(1).getName());
        ((Button)view.findViewById(R.id.btn_navigation)).setText(MagicFactory.getOnlines().get(2).getName());
        ((Button)view.findViewById(R.id.btn_screening)).setText(MagicFactory.getOnlines().get(3).getName());
        ((Button)view.findViewById(R.id.btn_information)).setText(MagicFactory.getOnlines().get(4).getName());

        //չӳ�
        LinearLayout screeningContainer = (LinearLayout) view.findViewById(R.id.screeningContainer);
        int i=0;
        for(final Screen screen: MagicFactory.getScreens()){
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.gravity = Gravity.LEFT;
            params.setMargins(-20,10,0,0);
            button.setGravity(Gravity.LEFT);
            button.setLayoutParams(params);
            button.setText(screen.getTitle());
            button.setTextSize(32);
            button.setTextColor(getResources().getColor(R.color.gray));
            button.setBackgroundColor(getResources().getColor(R.color.clear));
            button.setAlpha(0.3f);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = 0;
                    for(int i=0;i<buttons.size();i++){
                        if(view == buttons.get(i))index = i;
                    }

                    Intent intent = new Intent();
                    if(screen.getType()==0){
                        intent.putExtra("index", index);
                        intent.setClass(BaseActivity.this,
                                NowScreeningActivity.class);
                        startActivity(intent);
                    }else{
                        intent.putExtra("index",index);
                        intent.setClass(BaseActivity.this,
                                ReviewScreeningActivity.class);
                        startActivity(intent);
                    }
                }
            });
            buttons.add(button);
            screeningContainer.addView(button);
            if(i!=MagicFactory.getScreens().size()-1){
                ImageView imageView = new ImageView(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.line2));
                imageView.setLayoutParams(params);
                screeningContainer.addView(imageView);
            }else{
                ImageView imageView = new ImageView(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.line1));
                imageView.setLayoutParams(params);
                screeningContainer.addView(imageView);
            }
            i++;
        }
        //�ι���Ѷ
        LinearLayout infoContainer = (LinearLayout) view.findViewById(R.id.infoContainer);
        int k=0;
        for(final Info info: MagicFactory.getInfos()){
            Button button = new Button(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(-20,10,0,0);
            button.setLayoutParams(params);
            button.setText(info.getName());
            button.setTextSize(32);
            button.setTextColor(getResources().getColor(R.color.gray));
            button.setBackgroundColor(getResources().getColor(R.color.clear));
            button.setAlpha(0.3f);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int index = 0;
                    for(int j=0;j<button1s.size();j++){
                        if(view == button1s.get(j))index = j;
                    }

                    Intent intent = new Intent();
                    intent.putExtra("info", MagicFactory.getInfos().get(index));
                    if(info.getType()==0){
                        intent.setClass(BaseActivity.this,
                                IntroductionActivity.class);
                    }else{
                        intent.setClass(BaseActivity.this,
                                SupServicesActivity.class);
                    }
                    startActivity(intent);
                }
            });
            button1s.add(button);
            infoContainer.addView(button);
            if(k!=MagicFactory.getScreens().size()-1){
                ImageView imageView = new ImageView(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.line2));
                imageView.setLayoutParams(params);
                screeningContainer.addView(imageView);
            }else{
                ImageView imageView = new ImageView(this);
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.line1));
                imageView.setLayoutParams(params);
                infoContainer.addView(imageView);
            }
            k++;
        }


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
//        // ��ǰչӳ
//        view.findViewById(R.id.btn_exhibition).setOnClickListener(this);
//        // չӳ�ع�
//        view.findViewById(R.id.btn_review).setOnClickListener(this);
//        // չӳ�ƻ�
//        view.findViewById(R.id.btn_program).setOnClickListener(this);
        // �ι���Ѷ
        view.findViewById(R.id.btn_information).setOnClickListener(this);
//        // ����ݼ��
//        view.findViewById(R.id.btn_museum).setOnClickListener(this);
//        // ����ʱ��
//        view.findViewById(R.id.btn_business).setOnClickListener(this);
//        // ��Ʊָ��
//        view.findViewById(R.id.btn_guide).setOnClickListener(this);
//        // ���׷���
//        view.findViewById(R.id.btn_supporting).setOnClickListener(this);
//        // �ι���֪
//        view.findViewById(R.id.btn_notes).setOnClickListener(this);
//        // ��������
//        view.findViewById(R.id.btn_join).setOnClickListener(this);
//        // ��ϵ��ʽ
//        view.findViewById(R.id.btn_phone).setOnClickListener(this);
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
//            // ��ǰչӳ
//            case R.id.btn_exhibition:
//                intent.setClass(getApplicationContext(), NowScreeningActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // չӳ�ع�
//            case R.id.btn_review:
//                intent.setClass(getApplicationContext(),
//                        ReviewScreeningActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // չӳ�ƻ�
//            case R.id.btn_program:
//                intent.setClass(getApplicationContext(),
//                        FutureScreeningActivity.class);
//                startActivity(intent);
//                finish();
//                break;
            // �ι���Ѷ
            case R.id.btn_information:
                intent.setClass(getApplicationContext(), InformationActivity.class);
                startActivity(intent);
                finish();
                break;
//            // ����ݼ��
//            case R.id.btn_museum:
//                intent.setClass(getApplicationContext(), IntroductionActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // ����ʱ��
//            case R.id.btn_business:
//                intent.setClass(getApplicationContext(), BusinessActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // ��Ʊָ��
//            case R.id.btn_guide:
//                intent.setClass(getApplicationContext(), TicketActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // ���׷���
//            case R.id.btn_supporting:
//                intent.setClass(getApplicationContext(), SupServicesActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // �ι���֪
//            case R.id.btn_notes:
//                intent.setClass(getApplicationContext(), VisitActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // ��������
//            case R.id.btn_join:
//                intent.setClass(getApplicationContext(), JoinActivity.class);
//                startActivity(intent);
//                finish();
//                break;
//            // ��ϵ��ʽ
//            case R.id.btn_phone:
//                intent.setClass(getApplicationContext(), ContactActivity.class);
//                startActivity(intent);
//                finish();
//                break;
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
                Toast.makeText(getApplicationContext(), "�ٰ�һ���Ƴ�����", Toast.LENGTH_SHORT).show();
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
}
