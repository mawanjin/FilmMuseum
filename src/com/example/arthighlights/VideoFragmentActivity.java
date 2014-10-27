package com.example.arthighlights;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Window;
import com.example.data.FloorLegendFactory;
import com.example.filmmuseum.R;
import com.example.filmmuseum.SysApplication;
import com.example.view.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

/**
 * 智能导览视频播放
 */
public class VideoFragmentActivity extends FragmentActivity {
    ViewPagerFixed viewpager;
    private List<Fragment> fragments = new ArrayList<Fragment>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        SysApplication.getInstance().addActivity(this);
        setContentView(R.layout.audio_fragment_activity_layout);
        viewpager = (ViewPagerFixed) findViewById(R.id.viewpager);
        VideoFragment videoFragment = new VideoFragment(getIntent().getIntExtra("id",0));
        //todo 这里根据id去查找对应的楼层数据
        FloorFragment floorFragment = new FloorFragment(FloorLegendFactory.getInstance(this).getViewItemsWithLocation());
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
}
