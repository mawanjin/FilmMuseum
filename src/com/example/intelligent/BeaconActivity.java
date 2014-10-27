package com.example.intelligent;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.*;
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
import com.example.arthighlights.AudioFragmentActivity;
import com.example.arthighlights.ListMainActivity;
import com.example.arthighlights.VideoFragmentActivity;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtMenu;
import com.example.util.Download;
import com.example.util.ZipExtractorTask;

import java.io.*;
import java.util.*;

/**
 * ����չʾbeacon�б�
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
		// ���ر�����
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.beacon);
		SysApplication.getInstance().addActivity(this);
		tv = (TextView) findViewById(R.id.tv_title);
		tv.setText("���ܵ���ģʽ");
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
        //test begin
//        init();
//        File destDir = new File(getExternalStoragePath()
//                + "/FilmMuseum/download");
//        if (!destDir.exists()) {
//            destDir.mkdirs();
//        }
//        destDir = new File(getExternalStoragePath() + "/FilmMuseum/system");
//        if (!destDir.exists()) {
//            destDir.mkdirs();
//        }
//        destDir = new File(getExternalStoragePath() + "/FilmMuseum/collection");
//        if (!destDir.exists()) {
//            destDir.mkdirs();
//        }
//        String path = getExternalStoragePath()
//                + "/FilmMuseum/download/FilmMuseum.zip";
//        try {
//            copyBigDataToSD(path);
//            doZipExtractorWork();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        Message msg = new Message();
//        msg.obj = "2";
//        handler.sendMessage(msg);
        //test end



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
										// Log.v("HTTWs", "ɨ�赽��һ���豸");
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
												// "---->ɨ�赽�ɵ��豸");
												break;
											}
											if (major != list.get(list.size() - 1)) {

												Toast.makeText(
														getApplicationContext(),
														"ɨ�赽�µ��豸" + remajor,
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
											remajor + "�뿪������",
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

	// ��ȡsd����·��
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
						Log.v("HTTWsa", "---->������Ƶ" + cnt);
						Toast.makeText(getBaseContext(), "���ŵ�����Ƶ" + cnt,
								Toast.LENGTH_LONG).show();
						intent.setClass(getBaseContext(),
                                VideoFragmentActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("id", m.getId());
						intent.putExtras(bundle);
						startActivity(intent);
						cnt = cnt + 1;
						cntn = cntn + 1;
					}else if (m.getType().equals("music")) {
						Log.v("HTTWsa", "---->��������" + cnts);
						Toast.makeText(getApplicationContext(),
								"���ŵ�������" + cnts, Toast.LENGTH_LONG).show();
						intent.setClass(getApplicationContext(),
								AudioFragmentActivity.class);
						Bundle bundle = new Bundle();
						bundle.putInt("id", m.getId());
						intent.putExtras(bundle);
						startActivity(intent);
						cnts = cnts + 1;
						cntn = cntn + 1;
					}else if(m.getType().equals("list")){
                        Log.v("HTTWsa", "---->��ʾ�б�" + cnts);
                        Toast.makeText(getApplicationContext(),
                                "��ʾ�б�" , Toast.LENGTH_LONG).show();
                        intent.setClass(getApplicationContext(),
                                ListMainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ListMainActivity.EXTRA_ITEMS,m);
                        bundle.putInt("id", m.getId());
                        intent.putExtras(bundle);
                        startActivity(intent);
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
				Toast.makeText(this, "����������������", Toast.LENGTH_LONG).show();
				getActionBar().setSubtitle("Bluetooth not enabled");
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	protected void onStart() {
		super.onStart();
        if(beaconManager==null)return;
		if (!beaconManager.hasBluetooth()) {
			Toast.makeText(this, "�����豸û�������������汾̫�ͣ���ȷ�Ϻ�����...", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (!beaconManager.isBluetoothEnabled()) {
			Log.v("HTTWs", "�����豸δ������������ȷ�Ϻ�����... ");
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





    // �����ļ���SD��
    private void copyBigDataToSD(String strOutFileName) throws IOException {
        InputStream myInput;
        OutputStream myOutputStream = new FileOutputStream(strOutFileName);
        myInput = this.getAssets().open("FilmMuseum.zip");
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while (length > 0) {
            myOutputStream.write(buffer, 0, length);
            length = myInput.read(buffer);
        }
        myOutputStream.flush();
        myInput.close();
        myOutputStream.close();
    }

    // ��ѹzip
    public void doZipExtractorWork() {
        ZipExtractorTask task = new ZipExtractorTask(getExternalStoragePath()
                + "/FilmMuseum/download/FilmMuseum.zip",
                getExternalStoragePath() + "/FilmMuseum/system/", this, true);
        task.execute();
    }

}
