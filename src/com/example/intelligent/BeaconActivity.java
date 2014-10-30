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
import com.example.data.Filter;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtMenu;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

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

    private int currentBeacon = 0;
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
        //13


    }

    private void init() {
        myBeacons = new ArrayList<Beacon>();
        ListView lv = (ListView) findViewById(R.id.lv_beacon);
        adapter = new BeaconAdapter(this);
        lv.setAdapter(adapter);
        beaconManager = new BeaconManager(BeaconActivity.this);
        beaconManager.setForegroundScanPeriod(100, 0);
        beaconManager.setRangingListener(new RangingListener() {
            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                myBeacons.addAll(beacons);
                for (int i = 0; i < beacons.size(); i++) {
                    Beacon beacon = beacons.get(i);
                    int major = beacon.getMajor();
                    int minor = beacon.getMinor();
                    List<Person> persons;
                    try {
                        persons = MagicFactory.getPersons();

                        if (currentBeacon != major) {//如果当前beacon不是正在播放的，则进行播放操作。
                            if (beacon.getDistance() <= 1) {
                                for (Person person : persons) {
                                    if (major == person.getMajor()
                                            && minor == person.getMinor()) {
                                        Message msg = new Message();
//                                        msg.obj = person.getUrl();
                                        msg.obj = person;
                                        handler.sendMessage(msg);
                                        currentBeacon = major;
                                        break;
                                    }
                                }
                            }
                        }else{//如果是当前播放的而且已经离开了两米了
                            if (beacon.getDistance() > 2) {
//                                Toast.makeText(getApplicationContext(),
//                                        currentBeacon + "离开了两米",
//                                        Toast.LENGTH_SHORT).show();
                            }
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
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


    public class Myhandler extends Handler {
        public Myhandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message msg) {
            Person person = (Person) msg.obj;
            Intent intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            List<ArtMenu> menu = MagicFactory.getArtMenus();
            for (ArtMenu m : menu) {
                if (Integer.parseInt(person.getUrl()) == m.getId()) {

                    if (m.getType().equals("player")) {
                        intent.setClass(getBaseContext(),
                                VideoFragmentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", m.getId());
                        bundle.putSerializable("person",person);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        Intent intentBroadcast = new Intent(Filter.IntentFilter_ACTION_BEACON_SWITCH);
                        intentBroadcast.putExtra("id",m.getId());
                        sendBroadcast(intentBroadcast);
                    } else if (m.getType().equals("music")) {

                        intent.setClass(getApplicationContext(),
                                AudioFragmentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", m.getId());
                        bundle.putSerializable("person",person);
                        intent.putExtras(bundle);
                        intent.putExtras(bundle);
                        startActivity(intent);
                        sendBroadcast(new Intent(Filter.IntentFilter_ACTION_BEACON_SWITCH));
                        Intent intentBroadcast = new Intent(Filter.IntentFilter_ACTION_BEACON_SWITCH);
                        intentBroadcast.putExtra("id",m.getId());
                        sendBroadcast(intentBroadcast);
                    } else if (m.getType().equals("list")) {
                        Toast.makeText(getApplicationContext(),
                                "显示列表", Toast.LENGTH_LONG).show();
                        intent.setClass(getApplicationContext(),
                                ListMainActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ListMainActivity.EXTRA_ITEMS, m);
                        bundle.putInt("id", m.getId());
                        bundle.putSerializable("person",person);
                        intent.putExtras(bundle);
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
        Iterator iterator = list.iterator();
        while (iterator.hasNext()) {
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
        adapter.replaceWith(Collections.<Beacon>emptyList());
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
                getActionBar().setSubtitle("Bluetooth not enabled");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void onStart() {
        super.onStart();
        if (beaconManager == null) return;
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


    // 拷贝文件到SD卡
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


}
