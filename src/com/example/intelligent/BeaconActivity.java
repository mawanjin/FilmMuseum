package com.example.intelligent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aprilbrother.aprilbrothersdk.Beacon;
import com.aprilbrother.aprilbrothersdk.BeaconManager;
import com.aprilbrother.aprilbrothersdk.BeaconManager.RangingListener;
import com.aprilbrother.aprilbrothersdk.Region;
import com.example.arthighlights.AudioActivity;
import com.example.arthighlights.VideoActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtMenu;
import com.example.util.Download;

/**
 * 搜索展示beacon列表
 */
public class BeaconActivity extends Activity {
	private static final int REQUEST_ENABLE_BT = 1234;
	private static final Region ALL_BEACONS_REGION = new Region("apr", null,
			null, null);
	private BeaconAdapter adapter;
	private BeaconManager beaconManager;
	private ArrayList<Beacon> myBeacons;
	private int cnt = 0;
	private int cnts = 0;
	private int cntn = 0;
	private int remajor = 0;
	private List<Integer> list;

	private Looper mainlooper = null;
	private Myhandler handler = null;

	private ImageView ivReturn, ivMenu;
	private TextView tv;

	@SuppressLint("NewApi")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.beacon);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("智能导览模式");
		ivReturn = (ImageView) findViewById(R.id.ivReturn);
		ivReturn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				finish();
				overridePendingTransition(R.anim.a2, R.anim.a1);
			}
		});
		ivMenu = (ImageView) findViewById(R.id.iv_menu);
		ivMenu.setVisibility(View.GONE);
		list = new ArrayList<Integer>();
		mainlooper = this.getMainLooper();
		handler = new Myhandler(mainlooper);
		init();
	}

	private void init() {
		myBeacons = new ArrayList<Beacon>();
		ListView lv = (ListView) findViewById(R.id.lv_beacon);
		adapter = new BeaconAdapter(this);
		lv.setAdapter(adapter);
		beaconManager = new BeaconManager(BeaconActivity.this);
		beaconManager.setForegroundScanPeriod(100, 0);
		beaconManager.setRangingListener(new RangingListener() {
			public void onBeaconsDiscovered(Region region,
					final List<Beacon> beacons) {
				myBeacons.addAll(beacons);
				for (int i = 0; i < beacons.size(); i++) {
					Beacon beacon = beacons.get(i);
					int major = beacon.getMajor();
					int minor = beacon.getMinor();
					List<Person> persons;
					try {
						persons = PersonService
								.getPersons(getExternalStoragePath()
										+ "/FilmMuseum/system/beacon.xml");
						if (beacon.getDistance() <= 1) {
							if (remajor == 0) {
								remajor = major;
							}
							for (final Person person : persons) {
								if (major == person.getMajor()
										&& minor == person.getMinor()) {
									if (list.size() == 0) {
										// Log.v("HTTWs", "扫描到了一个设备");
										String url = person.getUrl();
										Message msg = new Message();
										msg.obj = url;
										handler.sendMessage(msg);
										list.add(major);
										break;
									}
									if (list.size() > 0 && remajor == 0) {
										list = removeList(list);
										for (int j = 0; j < list.size(); j++) {
											if (major == list.get(j)) {
												// Log.v("HTTWs",
												// "---->扫描到旧的设备");
												break;
											}
											if (major != list.get(list.size() - 1)) {

												Toast.makeText(
														getApplicationContext(),
														"扫描到新的设备" + remajor,
														Toast.LENGTH_SHORT)
														.show();
												list.add(major);
												Toast.makeText(
														getApplicationContext(),
														"eeee" + cntn,
														Toast.LENGTH_LONG)
														.show();
												String url = person.getUrl();
												Message msg = new Message();
												msg.obj = url;
												handler.sendMessage(msg);
												continue;
											}
										}
									}
								}
							}
						}
						if (remajor != 0) {
							if (major == remajor) {
								if (beacon.getDistance() > 2) {
									Toast.makeText(getApplicationContext(),
											remajor + "离开了两米",
											Toast.LENGTH_SHORT).show();
									deleteList(list, remajor);
									remajor = 0;
								}
								Log.v("HTTWs", "----->" + beacon.getDistance()
										+ "        " + remajor);
							}
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (Exception e) {
					}
				}
				runOnUiThread(new Runnable() {
					@SuppressLint("NewApi")
					public void run() {
						adapter.replaceWith(beacons);
					}
				});
			}
		});
	}

	public void deleteList(List<Integer> list, int major) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == remajor) {
				list.remove(i);
			}
		}
	}

	// 获取sd卡的路径
	public static String getExternalStoragePath() {
		String state = android.os.Environment.getExternalStorageState();
		if (android.os.Environment.MEDIA_MOUNTED.equals(state)) {
			if (android.os.Environment.getExternalStorageDirectory().canRead()) {
				return android.os.Environment.getExternalStorageDirectory()
						.getPath();
			}
		}
		return null;
	}

	public class Myhandler extends Handler {
		public Myhandler(Looper looper) {
			super(looper);
		}

		public void handleMessage(Message msg) {
			int url = Integer.parseInt((String) msg.obj);
			Intent intent = new Intent();
			Download dow = new Download();
			List<ArtMenu> menu = dow.readMenuXml(getExternalStoragePath()
					+ "/FilmMuseum/system/menu.xml");
			for (ArtMenu m : menu) {
				if (url == m.getId()) {
					if (m.getType().equals("player")) {
						Log.v("HTTWsa", "---->播放视频" + cnt);
						Toast.makeText(getApplicationContext(), "播放的是视频" + cnt,
								Toast.LENGTH_LONG).show();
						intent.setClass(getApplicationContext(),
								VideoActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("id", m.getId());
						intent.putExtras(bundle);
						startActivity(intent);
						cnt = cnt + 1;
						cntn = cntn + 1;
					}
					if (m.getType().equals("music")) {
						Log.v("HTTWsa", "---->播放音乐" + cnts);
						Toast.makeText(getApplicationContext(),
								"播放的是音乐" + cnts, Toast.LENGTH_LONG).show();
						intent.setClass(getApplicationContext(),
								AudioActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("id", m.getId());
						intent.putExtras(bundle);
						startActivity(intent);
						cnts = cnts + 1;
						cntn = cntn + 1;
					}
				}
			}
		}
	}

	public List<Integer> removeList(List<Integer> list) {
		HashSet<Integer> hashSet = new HashSet<Integer>();
		List<Integer> newList = new ArrayList<Integer>();
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			int element = (Integer) iterator.next();
			if (hashSet.add(element)) {
				newList.add(element);
			}
		}
		list.clear();
		list.addAll(newList);
		return list;
	}

	@SuppressLint("NewApi")
	private void connectToService() {
		adapter.replaceWith(Collections.<Beacon> emptyList());
		beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
			@Override
			public void onServiceReady() {
				try {
					beaconManager.startRanging(ALL_BEACONS_REGION);
				} catch (RemoteException e) {

				}
			}
		});
	}

	@SuppressLint("NewApi")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == REQUEST_ENABLE_BT) {
			if (resultCode == Activity.RESULT_OK) {
				connectToService();
			} else {
				Toast.makeText(this, "哈哈哈哈哈哈哈哈", Toast.LENGTH_LONG).show();
				getActionBar().setSubtitle("Bluetooth not enabled");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void onStart() {
		super.onStart();

		if (!beaconManager.hasBluetooth()) {
			Toast.makeText(this, "您的设备没有蓝牙或蓝牙版本太低，请确认后重试...", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (!beaconManager.isBluetoothEnabled()) {
			Log.v("HTTWs", "您的设备未开启蓝牙，请确认后重试... ");
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
		} else {
			connectToService();
		}
	}

	protected void onDestroy() {
		Log.v("HTTWs", "---->onDestroy");
		list = null;
		System.gc();
		super.onDestroy();
	}

	protected void onStop() {
		Log.v("HTTWs", "---->onStop");
		super.onStop();
	}

	protected void onPause() {
		Log.v("HTTWs", "---->onpause");
		super.onPause();
	}

	protected void onRestart() {
		Log.v("HTTWs", "---->onRestart");
		try {
			beaconManager.startRanging(ALL_BEACONS_REGION);
		} catch (Exception e) {
			e.printStackTrace();
		}
		super.onRestart();
	}

}
