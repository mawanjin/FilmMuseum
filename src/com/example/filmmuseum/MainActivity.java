package com.example.filmmuseum;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.*;
import com.example.data.Index;
import com.example.data.MagicFactory;
import com.example.data.Version;
import com.example.dialog.UpdateDialog;
import com.example.intelligent.BeaconActivity;
import com.example.util.FileSysUtils;
import com.example.util.ZipExtractorTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends Activity implements OnClickListener {
    //网上最新版本
    private Version versionNew;
	// 图片轮播的viewpager
	private ViewPager viewpager;

	private TestAdapter adapter;
	// 小圆点
	private LinearLayout viewGroup;
	// 存储小圆点图片
	private ImageView logo, dot, dots[];

	private Runnable runnable;

	// 图片切换的时间
	private int autoChangeTime = 5000;

	private Button btn1, btn2;
	private TextView tv2;

	private int positions = 0;

	private String[] str;
    private Index index;
    public static boolean isUnzipCompleted=false;
    private Version version;
    private UpdateDialog updateDialog;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		// 将activity装入集合
		SysApplication.getInstance().addActivity(this);
        logo = (ImageView) findViewById(R.id.imageView3);
		btn1 = (Button) findViewById(R.id.main_btn1);
		btn2 = (Button) findViewById(R.id.main_btn2);
		tv2 = (TextView) findViewById(R.id.main_tv2);
//		str = new String[3];
//		str[0] = "上海电影博物馆是一座融展示与活动、参观与体验为一体，\n涵盖文物收藏、学术研究、社会教育、\n陈列展示等功能的行业博物馆";
//		str[1] = "博物馆展分为四大主题展区，五号摄影棚及一座艺术影厅。\n上海电影博物馆呈现了百年上海电影的魅力，\n生动演绎了电影人、电影事和电影背后的故事";
//		str[2] = "满足大众电影文化需求的艺术圣殿，\n也是上海电影乃至中国电影最为重要的展示窗口之一";

//		tv2.setText(str[0]);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
        if(!FileSysUtils.isExist()){
			isUnzipCompleted = false;
            FileSysUtils.initSysData(this);

            ZipExtractorTask task = new ZipExtractorTask(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/download/FilmMuseum.zip",
                    FileSysUtils.getExternalStoragePath() + "/FilmMuseum/system/", this, true,new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
					isUnzipCompleted = true;
                    index  = MagicFactory.getIndex();
                    logo.setImageBitmap(MagicFactory.getBitmap(index.getLogo()));
                    tv2.setText(Html.fromHtml(index.getIndexItems().get(0).getDescription()));
                    initViewPager();
                }
            });
            task.execute();
        }else{
			isUnzipCompleted = true;
            index  = MagicFactory.getIndex();
            logo.setImageBitmap(MagicFactory.getBitmap(index.getLogo()));
            tv2.setText(Html.fromHtml(index.getIndexItems().get(0).getDescription()));
            initViewPager();
        }

        //check update
        version = MagicFactory.getVersion(this);
        new CheckUpdateTask().execute();

	}

	// 点击事件
	public void onClick(View v) {
		if(!isUnzipCompleted){
			Toast.makeText(this,"初始化中,请等待!",Toast.LENGTH_SHORT).show();
			return;
		}
		if (v.getId() == R.id.main_btn1) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, BeaconActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.a2, R.anim.a1);
		} else if (v.getId() == R.id.main_btn2) {
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, MenuInterfaceActivity.class);
			startActivity(intent);
			overridePendingTransition(R.anim.a2, R.anim.a1);
		}
	}

    public void reloadAfterUpgrade(){
        index  = MagicFactory.getIndex();
        logo.setImageBitmap(MagicFactory.getBitmap(index.getLogo()));
        tv2.setText(Html.fromHtml(index.getIndexItems().get(0).getDescription()));
        initViewPager();
    }

	// 退出程序，点击返回键之后的2秒内再点击
	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
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

	public void setScrollerAnimation(ABaseTransformer animation) {
		viewpager.setPageTransformer(true, animation);
	}

	private void initViewPager() {
		adapter = new TestAdapter(this);
		adapter.change(index.getIndexItems());
		
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		viewpager.setAdapter(adapter);
		viewpager.setOnPageChangeListener(myOnPageChangeListener);

		initDot();

		runnable = new Runnable() {
			public void run() {
				int next = viewpager.getCurrentItem() + 1;
				if (next >= adapter.getCount()) {
					next = 0;
				}
				viewHandler.sendEmptyMessage(next);
			}
		};
		// 加载背景图片轮播的动画
		viewpager.setPageTransformer(true, new TabletTransformer());
		viewHandler.postDelayed(runnable, autoChangeTime);

	}

	// 初始化小圆点
	private void initDot() {

		viewGroup = (LinearLayout) findViewById(R.id.viewGroup);
        viewGroup.removeAllViews();

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
				20, 20);
		layoutParams.setMargins(4, 3, 4, 3);

		dots = new ImageView[adapter.getCount()];
		for (int i = 0; i < adapter.getCount(); i++) {
			dot = new ImageView(this);

			dot.setLayoutParams(layoutParams);
			dots[i] = dot;
			dots[i].setTag(i);
			dots[i].setOnClickListener(onClick);

			if (i == 0) {
				dots[i].setBackgroundResource(R.drawable.madoka1);
			} else {
				dots[i].setBackgroundResource(R.drawable.madoka2);
			}
			viewGroup.addView(dots[i]);

		}
	}

	// 获得背景图片
	private List<Integer> list;

	private List<Integer> getList() {
		list = new ArrayList<Integer>();
		list.add(R.drawable.bg1);
		list.add(R.drawable.bg2);
		list.add(R.drawable.bg3);
		return list;
	}

	// 监听图片切换
	OnPageChangeListener myOnPageChangeListener = new OnPageChangeListener() {

		public void onPageScrollStateChanged(int arg0) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			setCurDot(arg0);
			positions = arg0;

			tv2.setText(Html.fromHtml(index.getIndexItems().get(positions).getDescription()));
			viewHandler.removeCallbacks(runnable);
			viewHandler.postDelayed(runnable, autoChangeTime);
		}
	};
	// 实现小圆点的点击事件
	OnClickListener onClick = new OnClickListener() {
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			setCurView(position);
		}
	};

	/**
	 * 设置当前的引导页
	 */
	private void setCurView(int position) {
		if (position < 0 || position > adapter.getCount()) {
			return;
		}
		viewpager.setCurrentItem(position);
	}

	/**
	 * 选中当前引导小点
	 */
	private void setCurDot(int position) {

		for (int i = 0; i < dots.length; i++) {
			if (position == i) {
				dots[i].setBackgroundResource(R.drawable.madoka1);
			} else {
				dots[i].setBackgroundResource(R.drawable.madoka2);
			}
		}
	}

	/**
	 * 每隔固定时间切换广告栏图片
	 */
	@SuppressLint("HandlerLeak")
	private final Handler viewHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setCurView(msg.what);
		}

	};

	protected void onStop() {
		Log.v("HTTWs", "MainActivity进入onstop");
		super.onStop();
	};

	protected void onDestroy() {
		Log.v("HTTWs","---> main进入destroy");
		if(list!=null)
		if (list.size() != 0) {
			list = null;
			System.gc();
		}
		super.onDestroy();
	}

    public class CheckUpdateTask extends AsyncTask{

        @Override
        protected Boolean doInBackground(Object[] objects) {
            URL url = null;
            try {
                url = new URL(version.getCheckUrl());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(3000);
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    // 这里获取数据直接放在XmlPullParser里面解析
                    versionNew = MagicFactory.parseVersionXml(is);
                    if(versionNew.getVersionCode()>version.getVersionCode()){
                        return true;
                    }

                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return false;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if((Boolean)o){//todo 发现新版本，弹出下载窗口
                updateDialog = new UpdateDialog(MainActivity.this);
                updateDialog.setFileSize(versionNew.getFileSize());
                updateDialog.show();
            }
        }
    }

    public Version getVersionNew() {
        return versionNew;
    }

    public void setVersionNew(Version versionNew) {
        this.versionNew = versionNew;
    }
}
