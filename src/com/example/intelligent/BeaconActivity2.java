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
import com.example.arthighlights.ImgFragmentActivity;
import com.example.arthighlights.ListMainActivity;
import com.example.arthighlights.VideoFragmentActivity;
import com.example.data.BeaconExtra;
import com.example.data.Filter;
import com.example.data.Floor;
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
* 原来的。。搜索展示beacon列表
*/
public class BeaconActivity2 extends Activity {
    private static String TAG = BeaconActivity2.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 1234;
    public static final int ON_RESULT_EXIT = 1001;
    private static final Region ALL_BEACONS_REGION = new Region("apr", null,
            null, null);
//    private BeaconAdapter adapter;
    private BeaconManager beaconManager;
    private ArrayList<Beacon> myBeacons;

    private int currentBeacon = -1;
    private List<Integer> list;

    private Looper mainlooper = null;
    private Myhandler handler = null;

    private ImageView ivReturn, ivMenu;
    private TextView tv;
    //有效距离
    private float distance = 1;
    //切换最小时间间隔
    private long switchGap = 2000;
    private long lastSwitchTime;


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
        BeaconExtra beaconExtra = MagicFactory.getBeaconExtra();
        if(beaconExtra!=null)distance = beaconExtra.getAvailableDistance();
    }

    double lastDistance = 1;


    Beacon beaconPlay = null;
    Beacon beacon = null;
    int major;
    int minor;
    List<Person> persons = MagicFactory.getPersons();;
    Map<Integer,Double> beaconValid = new HashMap<Integer,Double>();

    private void init() {
        myBeacons = new ArrayList<Beacon>();
//        ListView lv = (ListView) findViewById(R.id.lv_beacon);
//        adapter = new BeaconAdapter(this);
//        lv.setAdapter(adapter);
        beaconManager = new BeaconManager(BeaconActivity2.this);
        beaconManager.setForegroundScanPeriod(100, 0);

        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {

            }

            @Override
            public void onExitedRegion(Region region) {

            }
        });

    int miniDistance;

        beaconManager.setRangingListener(new RangingListener() {

            public void onBeaconsDiscovered(Region region, final List<Beacon> beacons) {
                myBeacons.clear();
                myBeacons.addAll(beacons);

                for (int i = 0; i < beacons.size(); i++) {//取出距离最小的设备

                    beacon = beacons.get(i);
                    //监测所有设备状态，如果和上次的距离相差太多，则忽略
                    Double d = beaconValid.get(beacon.getMajor());

                    if(d==null){
                        beaconValid.put(beacon.getMajor(),beacon.getDistance());
                    }else{
//                        double dis = beacon.getDistance();

                        double gap = Math.abs(d-beacon.getDistance());
                        if(gap>0.8){
                            continue;
                        }
                    }
                    System.out.println("xxxxx="+beacon.getMajor()+";"+beacon.getDistance()+"");
                    beaconValid.put(beacon.getMajor(),beacon.getDistance());

                    if(beaconPlay!=null){//正在播放的
                        System.out.println("beacon.getDistance()="+beacon.getDistance()+"last beacon="+beaconPlay.getMajor()+";beaconPlay.getDistance()="+lastDistance);
                    }

                    if(beaconPlay!=null&&beaconPlay.getMajor()==beacon.getMajor()){//如果是正在播放的，则更新一下当前设备现在的距离
                        lastDistance = beacon.getDistance();
                        continue;
                    }

                    if(beaconPlay==null){//如果是第一个，则直接进行播放
                        beaconPlay = beacon;
                        lastDistance = beaconPlay.getDistance();
                    }else if(beacon.getDistance()<lastDistance){//如果不是当前正在播放的，并且当前距离小于正在播放的，则进行播放操作
                            beaconPlay = beacon;
                            lastDistance = beacon.getDistance();
                        }
                }


                if(beaconPlay==null||currentBeacon == beaconPlay.getMajor())return;//如果正在播放，则跳过

                if(beaconPlay.getDistance()<distance)
                for (Person person : persons) {
                    if (beaconPlay.getMajor() == person.getMajor()
                            && beaconPlay.getMinor() == person.getMinor()) {
                        currentBeacon = beaconPlay.getMajor();
                        Message msg = new Message();
                        msg.obj = person;
                        handler.sendMessage(msg);
                        break;
                    }
                }



//                for (int i = 0; i < beacons.size(); i++) {
//                    beacon = beacons.get(i);
//
//                    if(currentBeacon == beacon.getMajor()){//如果当前设备是正在展现的，则更新当前设备的距离
//                        lastDistance = beacon.getDistance();
//                        continue;
//                    }
//
//                    try {
//                        if (currentBeacon != beacon.getMajor()) {//如果当前beacon不是正在播放的，则进行判断
//                            double bDistance = beacon.getDistance();//当前设备距离
//                            if(bDistance==0)return;
//                            if ( bDistance<= distance) {
//                                if(lastDistance!=-1){
//                                    if(bDistance>=lastDistance)continue;//如果当前距离大于上次设备的距离
//                                }
//                                lastDistance = bDistance;
//                            }
//                        }else{
//                            continue;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }

//                if(currentBeacon==beacon.getMajor())return;



                runOnUiThread(new Runnable() {
                    @SuppressLint("NewApi")
                    public void run() {
//                        adapter.replaceWith(beacons);
                    }
                });
            }
        });
    }

    private Activity lastActivity = null;

    public Activity getLastActivity() {
        return lastActivity;
    }

    public void setLastActivity(Activity lastActivity) {
        this.lastActivity = lastActivity;
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
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivityForResult(intent,ON_RESULT_EXIT);
                        Intent intentBroadcast = new Intent(Filter.IntentFilter_ACTION_BEACON_SWITCH);
                        intentBroadcast.putExtra("id",m.getId());
                        sendBroadcast(intentBroadcast);


                    } else if (m.getType().equals("music")) {

                        intent.setClass(getApplicationContext(),
                                AudioFragmentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", m.getId());
                        bundle.putSerializable("person", person);
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivityForResult(intent, ON_RESULT_EXIT);
                        sendBroadcast(new Intent(Filter.IntentFilter_ACTION_BEACON_SWITCH));
                        Intent intentBroadcast = new Intent(Filter.IntentFilter_ACTION_BEACON_SWITCH);
                        intentBroadcast.putExtra("id",m.getId());
                        sendBroadcast(intentBroadcast);
                    }else if (m.getType().equals("img")) {

                        intent.setClass(getApplicationContext(),
                                ImgFragmentActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("id", m.getId());
                        bundle.putSerializable("person", person);
                        intent.putExtras(bundle);
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        startActivityForResult(intent, ON_RESULT_EXIT);
                    }
                    else if (m.getType().equals("list")) {

                        intent.setClass(getApplicationContext(),
                                ListMainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(ListMainActivity.EXTRA_ITEMS, m);
                        bundle.putInt("id", m.getId());
                        bundle.putSerializable("person",person);
                        intent.putExtras(bundle);
                        startActivityForResult(intent,ON_RESULT_EXIT);
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
//        adapter.replaceWith(Collections.<Beacon>emptyList());
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
//        connectToService();
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                connectToService();
            } else {
                getActionBar().setSubtitle("Bluetooth not enabled");
            }
        }else if(requestCode == ON_RESULT_EXIT){
            finish();
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
