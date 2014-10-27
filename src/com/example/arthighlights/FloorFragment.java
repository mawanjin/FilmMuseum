package com.example.arthighlights;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.data.MarkerPointer;
import com.example.filmmuseum.R;
import org.melonframwork.android.TouchImageView;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 22:25
 * 滑动显示楼层图
 */
public class FloorFragment extends Fragment {

    private TouchImageView touchImageView;
    private List<MarkerPointer> pointers;

    public FloorFragment(List<MarkerPointer> pointers){
        this.pointers = pointers;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.floor, null);
        FrameLayout floorContainer = (FrameLayout) view.findViewById(R.id.container);

        //读取beacon获取对应的数据，这里是ListMainActivity传进来的
//        List<MarkerPointer> pointers = FloorLegendFactory.getInstance(getActivity()).getViewItemsWithLocation();

        for (MarkerPointer pointer : pointers) {
            pointer.clickable = false;
        }

        touchImageView = new TouchImageView(getActivity(), R.drawable.f1, pointers);
        touchImageView.setmActivity(getActivity());
//        touchImageView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//
//                return false;
//            }
//        });


        floorContainer.removeAllViews();
        floorContainer.addView(touchImageView);

        return view;
    }
}
