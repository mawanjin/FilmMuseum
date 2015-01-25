package com.example.arthighlights;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Window;
import com.example.data.Filter;
import com.example.data.FloorLegendFactory;
import com.example.filmmuseum.R;
import com.example.intelligent.BeaconActivity;
import com.example.intelligent.Person;
import com.example.view.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能导览MUSIC播放
 */
public class AudioFragmentActivity extends FragmentActivity {
    ViewPagerFixed viewpager;
    private List<Fragment> fragments = new ArrayList<Fragment>(0);
    private AudioFragment audioFragment;
    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.audio_fragment_activity_layout);
        viewpager = (ViewPagerFixed) findViewById(R.id.viewpager);
        audioFragment = new AudioFragment(getIntent().getIntExtra("id",0));
        //todo 这里根据id去查找对应的楼层数据
        person  = (Person) getIntent().getSerializableExtra("person");
        FloorFragment floorFragment = new FloorFragment(FloorLegendFactory.getInstance(this).getViewItemsWithLocation(this,person),person);
//        fragments.add(floorFragment);
        if(person.isShowMap())
            fragments.add(floorFragment);
        fragments.add(audioFragment);

        PagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        };

        viewpager.setAdapter(mAdapter);
        viewpager.setCurrentItem(1);

    }

    @Override
    protected void onStop() {
        setResult(BeaconActivity.ON_RESULT_EXIT);
        finish();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //切换视频源
                audioFragment.switchURL(intent.getIntExtra("id",0));
            }
        },new IntentFilter(Filter.IntentFilter_ACTION_BEACON_SWITCH));
    }
}
