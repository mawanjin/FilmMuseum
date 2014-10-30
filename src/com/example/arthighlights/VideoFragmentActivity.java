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
import com.example.filmmuseum.SysApplication;
import com.example.intelligent.Person;
import com.example.view.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

/**
 * ���ܵ�����Ƶ����
 */
public class VideoFragmentActivity extends FragmentActivity {
    ViewPagerFixed viewpager;
    private List<Fragment> fragments = new ArrayList<Fragment>(0);
    VideoFragment videoFragment;
    Person person;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���ر�����
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.audio_fragment_activity_layout);
        viewpager = (ViewPagerFixed) findViewById(R.id.viewpager);
        videoFragment = new VideoFragment(getIntent().getIntExtra("id",0));
        //todo �������idȥ���Ҷ�Ӧ��¥������
        person  = (Person) getIntent().getSerializableExtra("person");
        FloorFragment floorFragment = new FloorFragment(FloorLegendFactory.getInstance(this).getViewItemsWithLocation(this,person),person);
        fragments.add(floorFragment);
        fragments.add(videoFragment);

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
    protected void onStart() {
        super.onStart();
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //�л���ƵԴ
                videoFragment.switchURL(intent.getIntExtra("id",0));
            }
        },new IntentFilter(Filter.IntentFilter_ACTION_BEACON_SWITCH));
    }
}
