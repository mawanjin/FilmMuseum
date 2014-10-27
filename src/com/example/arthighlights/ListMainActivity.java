package com.example.arthighlights;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.Window;
import com.example.data.FloorLegendFactory;
import com.example.filmmuseum.R;
import com.example.util.ArtMenu;
import com.example.view.ViewPagerFixed;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 22:08
 */
public class ListMainActivity extends FragmentActivity {
    public static String EXTRA_ITEMS = ListMainActivity.class.getName() + "_items";

    private ViewPagerFixed viewpager;
    private List<Fragment> fragments = new ArrayList<Fragment>(0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ���ر�����
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.list_main_activity_layout);
        viewpager = (ViewPagerFixed) findViewById(R.id.viewpager);
        ListFragment listFragment = new ListFragment((ArtMenu) getIntent().getExtras().getSerializable(EXTRA_ITEMS));

        //todo ���ݸ�idȥ�ҵ���Ӧ��¥��ͼ����
        int id = getIntent().getIntExtra("id",0);

        FloorFragment floorFragment = new FloorFragment(FloorLegendFactory.getInstance(this).getViewItemsWithLocation());
        fragments.add(floorFragment);
        fragments.add(listFragment);


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
