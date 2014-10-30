package com.example.navigation;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.*;
import com.example.arthighlights.AudioActivity;
import com.example.arthighlights.VideoActivity;
import com.example.data.Floor;
import com.example.data.MagicFactory;
import com.example.data.MarkerPointer;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.regional.PhotoViewAttacher.OnMatrixChangedListener;
import com.example.util.ArtMenu;
import org.melonframwork.android.Pointer;
import org.melonframwork.android.TouchImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 楼层图主页
 */
public class HighFloorActivity extends Activity  {
    private String TAG = HighFloorActivity.class.getName();

    private TextView tv;

    private ImageView iv1, iv2, iv3, iv4;
    private ImageView ivMenu;
    private ImageView ivReturn;
    private PopupWindow pop;
    private Bitmap bm;
    private LinearLayout layout;
    private LinearLayout menu;
    private FrameLayout container;
    private TouchImageView touchImageView;
    private List<ImageView> footFloors = new ArrayList<ImageView>(0);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.high_floor);
        SysApplication.getInstance().addActivity(this);

        container = (FrameLayout) findViewById(R.id.container);
        switchFloor(1);
        tv = (TextView) findViewById(R.id.tv_title);
        tv.setText("博物馆楼层图");

        ivReturn = (ImageView) findViewById(R.id.ivReturn);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
                System.gc();
                overridePendingTransition(R.anim.a2, R.anim.a1);
            }
        });

        menu = (LinearLayout) findViewById(R.id.menu);
        //动态生成底部楼层个数
        initFloor();
        if(footFloors!=null&&footFloors.size()>0)
        footFloors.get(0).performClick();

//        iv1 = (ImageView) findViewById(R.id.highfloor_iv1);
//        iv2 = (ImageView) findViewById(R.id.highfloor_iv2);
//        iv3 = (ImageView) findViewById(R.id.highfloor_iv3);
//        iv4 = (ImageView) findViewById(R.id.highfloor_iv4);
//        iv1.setImageResource(R.drawable.f1_2);
//        iv2.setImageResource(R.drawable.f2_1);
//        iv3.setImageResource(R.drawable.f3_1);
//        iv4.setImageResource(R.drawable.f4_1);
//        iv1.setOnClickListener(this);
//        iv2.setOnClickListener(this);
//        iv3.setOnClickListener(this);
//        iv4.setOnClickListener(this);

        ivMenu = (ImageView) findViewById(R.id.iv_menu);
        ivMenu.setVisibility(View.GONE);
    }

    private void initFloor() {
        List<Floor> floors = MagicFactory.getFloors(this);
        if (floors == null) return;

        for (int i=0;i<floors.size();i++) {
            final ImageView imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0,60);
            params.weight = 1;
            imageView.setImageResource(getFootFloor(i));
            imageView.setLayoutParams(params);
            menu.addView(imageView);
            footFloors.add(imageView);

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for(int i=0;i<footFloors.size();i++){
                        if(footFloors.get(i)==imageView){
                            footFloors.get(i).setImageResource(getFootFloorSelected(i));
                            switchFloor(i);
                        }else
                            footFloors.get(i).setImageResource(getFootFloor(i));
                    }

                }
            });

        }

    }

    private int getFootFloor(int i){
        switch (i){
            case 0:
                return R.drawable.f1_1;
            case 1:
                return R.drawable.f2_1;
            case 2:
                return R.drawable.f3_1;
            case 3:
                return R.drawable.f4_1;
        }
        return R.drawable.f1_1;
    }

    private int getFootFloorSelected(int i){
        switch (i){
            case 0:
                return R.drawable.f1_2;
            case 1:
                return R.drawable.f2_2;
            case 2:
                return R.drawable.f3_2;
            case 3:
                return R.drawable.f4_2;
        }
        return R.drawable.f1_2;
    }

    private void switchFloor(int index) {
        container.removeAllViews();

        Floor floor = MagicFactory.getFloors(this).get(index);
        touchImageView = new TouchImageView(this, MagicFactory.getBitmap(floor.getBackground()), floor.getPointers());

        touchImageView.setmActivity(this);
        touchImageView.setEventDispatcher(new TouchImageView.EventDispatcher() {

            public Pointer onClick(Pointer p,boolean isTip) {
                Log.d(TAG, "good");
                //不是tip,则只隐藏或显示
                if (!isTip) return null;

                Intent intent = new Intent();

                List<ArtMenu> menus = MagicFactory.getArtMenus();
                intent.setClass(getApplicationContext(), AudioActivity.class);
                for(ArtMenu menu1: menus){
                    if(menu1.getId() == ((MarkerPointer) p).getContentId()){
                        if(menu1.getType().equals("player")){
                            intent.setClass(getApplicationContext(), VideoActivity.class);
                        }
                        break;
                    }
                }


                Bundle bundle = new Bundle();
                bundle.putInt("id", ((MarkerPointer) p).getContentId());
                intent.putExtras(bundle);
                startActivity(intent);
                return null;
            }
        });
        container.addView(touchImageView);
    }

//    public void onClick(View view) {
//        if (pop != null && pop.isShowing()) {
//            pop.dismiss();
//        }
//        switch (view.getId()) {
//            case R.id.highfloor_iv1:
//                iv1.setImageResource(R.drawable.f1_2);
//                iv2.setImageResource(R.drawable.f2_1);
//                iv3.setImageResource(R.drawable.f3_1);
//                iv4.setImageResource(R.drawable.f4_1);
//                switchFloor(1);
//                break;
//            case R.id.highfloor_iv2:
//                iv1.setImageResource(R.drawable.f1_1);
//                iv2.setImageResource(R.drawable.f2_2);
//                iv3.setImageResource(R.drawable.f3_1);
//                iv4.setImageResource(R.drawable.f4_1);
//                switchFloor(2);
//                break;
//            case R.id.highfloor_iv3:
//                iv1.setImageResource(R.drawable.f1_1);
//                iv2.setImageResource(R.drawable.f2_1);
//                iv3.setImageResource(R.drawable.f3_2);
//                iv4.setImageResource(R.drawable.f4_1);
//                switchFloor(3);
//                break;
//            case R.id.highfloor_iv4:
//                iv1.setImageResource(R.drawable.f1_1);
//                iv2.setImageResource(R.drawable.f2_1);
//                iv3.setImageResource(R.drawable.f3_1);
//                iv4.setImageResource(R.drawable.f4_2);
//                switchFloor(4);
//                break;
//            default:
//                break;
//        }
//    }

    // 菜单键
    @SuppressWarnings("static-access")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Timer exit = null;
            if (isExit == false) {
                isExit = true;
                Toast.makeText(getApplicationContext(), "再按一次退出程序",
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
        Log.v("HTTWs", "highfloorActivity进入onstop");
        super.onStop();
    }

    protected void onDestroy() {
        Log.v("HTTWs", "highfloorActivity进入ondestroy");
        if (bm != null && bm.isRecycled() == false) {
            bm.recycle();
            bm = null;
            System.gc();
        }
        System.gc();
        super.onDestroy();
    }

    /**
     * @param v
     * @param id
     * @param i
     * @param scale
     */
    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    private void showPopUp(View v, final int id, int i, float scale) {
        layout = new LinearLayout(this);
        if (i == 1) {
            layout.setBackgroundResource(R.drawable.tab1);
        }
        if (i == 2) {
            layout.setBackgroundResource(R.drawable.tab2);
        }
        if (i == 3) {
            layout.setBackgroundResource(R.drawable.tab3);
        }
        if (i == 4) {
            layout.setBackgroundResource(R.drawable.tab4);
        }
        layout.setGravity(Gravity.CENTER);
        Button btn = new Button(this);
        btn.getBackground().setAlpha(0);
        btn.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
                LayoutParams.FILL_PARENT));
        btn.setText("灿烂金杯");
        btn.setTextColor(Color.WHITE);
        btn.setTextSize(25 * scale);
        layout.addView(btn);
        pop = new PopupWindow(layout, (int) (300 * scale), (int) (150 * scale));
        btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), AudioActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        pop.setOutsideTouchable(true);
        pop.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        v.getLocationOnScreen(location);

//		pop.showAtLocation(v, Gravity.NO_GRAVITY, popX, popY);
    }

    private class MatrixChangeListener implements OnMatrixChangedListener {

        public void onMatrixChanged(RectF rect) {
        }
    }

}
