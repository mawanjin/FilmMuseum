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

import com.example.arthighlights.AudioFragmentActivity;
import com.example.arthighlights.ImgFragmentActivity;
import com.example.arthighlights.ListMainActivity;
import com.example.arthighlights.VideoFragmentActivity;
import com.example.data.BeaconExtra;
import com.example.data.Filter;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.util.ArtMenu;
import org.altbeacon.beacon.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 搜索展示beacon列表
 */
public class BeaconActivity extends Activity implements BeaconConsumer {
    private String TAG = getClass().getSimpleName();
    public static final int ON_RESULT_EXIT = 1001;
    private BeaconManager beaconManager = BeaconManager.getInstanceForApplication(this);
    private ImageView ivReturn, ivMenu;
    private TextView tv;

    @SuppressLint("NewApi")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.beacon);
        SysApplication.getInstance().addActivity(this);

        beaconManager.bind(this);
        beaconManager.setDebug(true);

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
    }


    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                if (beacons.size() > 0) {
                    Log.i(TAG, "The first beacon I see is about "+beacons.iterator().next().getDistance()+" meters away.");
                }
            }
        });


        try {
            beaconManager.startRangingBeaconsInRegion(new Region("apr", null, null, null));
        } catch (Exception e) {  e.printStackTrace(); }

    }


    public class Myhandler extends Handler {

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



    @Override
    protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }

}
