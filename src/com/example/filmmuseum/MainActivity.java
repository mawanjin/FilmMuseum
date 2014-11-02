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
    //�������°汾
    private Version versionNew;
	// ͼƬ�ֲ���viewpager
	private ViewPager viewpager;

	private TestAdapter adapter;
	// СԲ��
	private LinearLayout viewGroup;
	// �洢СԲ��ͼƬ
	private ImageView logo, dot, dots[];

	private Runnable runnable;

	// ͼƬ�л���ʱ��
	private int autoChangeTime = 5000;

	private Button btn1, btn2;
	private TextView tv2;

	private int positions = 0;

	private String[] str;
    private Index index;
    public static boolean isUnzipCompleted=false;
    private Version version;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ���ر�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		// ��activityװ�뼯��
		SysApplication.getInstance().addActivity(this);
        logo = (ImageView) findViewById(R.id.imageView3);
		btn1 = (Button) findViewById(R.id.main_btn1);
		btn2 = (Button) findViewById(R.id.main_btn2);
		tv2 = (TextView) findViewById(R.id.main_tv2);
//		str = new String[3];
//		str[0] = "�Ϻ���Ӱ�������һ����չʾ�����ι�������Ϊһ�壬\n���������ղء�ѧ���о�����������\n����չʾ�ȹ��ܵ���ҵ�����";
//		str[1] = "�����չ��Ϊ�Ĵ�����չ���������Ӱ�Ｐһ������Ӱ����\n�Ϻ���Ӱ����ݳ����˰����Ϻ���Ӱ��������\n���������˵�Ӱ�ˡ���Ӱ�º͵�Ӱ����Ĺ���";
//		str[2] = "������ڵ�Ӱ�Ļ����������ʥ�\nҲ���Ϻ���Ӱ�����й���Ӱ��Ϊ��Ҫ��չʾ����֮һ";

//		tv2.setText(str[0]);
		btn1.setOnClickListener(this);
		btn2.setOnClickListener(this);
        if(!FileSysUtils.isExist()){
            FileSysUtils.initSysData(this);

            ZipExtractorTask task = new ZipExtractorTask(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/download/FilmMuseum.zip",
                    FileSysUtils.getExternalStoragePath() + "/FilmMuseum/system/", this, true,new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    index  = MagicFactory.getIndex();
                    logo.setImageBitmap(MagicFactory.getBitmap(index.getLogo()));
                    tv2.setText(Html.fromHtml(index.getIndexItems().get(0).getDescription()));
                    initViewPager();
                }
            });
            task.execute();
        }else{
            index  = MagicFactory.getIndex();
            logo.setImageBitmap(MagicFactory.getBitmap(index.getLogo()));
            tv2.setText(Html.fromHtml(index.getIndexItems().get(0).getDescription()));
            initViewPager();
        }

        //check update
        version = MagicFactory.getVersion(this);

	}

	// ����¼�
	public void onClick(View v) {

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

	// �˳����򣬵�����ؼ�֮���2�����ٵ��
	@SuppressWarnings("static-access")
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == event.KEYCODE_BACK) {
			Timer exit = null;
			if (isExit == false) {
				isExit = true;
				Toast.makeText(getApplicationContext(), "�ٰ�һ���Ƴ�����",
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
		// ���ر���ͼƬ�ֲ��Ķ���
		viewpager.setPageTransformer(true, new TabletTransformer());
		viewHandler.postDelayed(runnable, autoChangeTime);

	}

	// ��ʼ��СԲ��
	private void initDot() {
		viewGroup = (LinearLayout) findViewById(R.id.viewGroup);

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

	// ��ñ���ͼƬ
	private List<Integer> list;

	private List<Integer> getList() {
		list = new ArrayList<Integer>();
		list.add(R.drawable.bg1);
		list.add(R.drawable.bg2);
		list.add(R.drawable.bg3);
		return list;
	}

	// ����ͼƬ�л�
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
	// ʵ��СԲ��ĵ���¼�
	OnClickListener onClick = new OnClickListener() {
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			setCurView(position);
		}
	};

	/**
	 * ���õ�ǰ������ҳ
	 */
	private void setCurView(int position) {
		if (position < 0 || position > adapter.getCount()) {
			return;
		}
		viewpager.setCurrentItem(position);
	}

	/**
	 * ѡ�е�ǰ����С��
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
	 * ÿ���̶�ʱ���л������ͼƬ
	 */
	@SuppressLint("HandlerLeak")
	private final Handler viewHandler = new Handler() {

		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			setCurView(msg.what);
		}

	};

	protected void onStop() {
		Log.v("HTTWs", "MainActivity����onstop");
		super.onStop();
	};

	protected void onDestroy() {
		Log.v("HTTWs","---> main����destroy");
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
                    // �����ȡ����ֱ�ӷ���XmlPullParser�������
                    versionNew = MagicFactory.parseVersionXml(is);
                    if(versionNew.getVersionCode()>version.getVersionCode()){
                        return true;
                    }

                } else {
                    return null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if((Boolean)o){//todo �����°汾���������ش���

            }
        }
    }

}
