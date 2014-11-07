package com.example.arthighlights;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.data.MagicFactory;
import com.example.data.MarkerPointer;
import com.example.filmmuseum.R;
import com.example.intelligent.Person;
import org.melonframwork.android.TouchImageView;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 22:25
 * »¬¶¯ÏÔÊ¾Â¥²ãÍ¼
 */
public class FloorFragment extends Fragment {

    private TouchImageView touchImageView;
    private List<MarkerPointer> pointers;
    private Person person;

    public FloorFragment(List<MarkerPointer> pointers,Person person){
        this.pointers = pointers;
        this.person = person;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.floor, null);
        FrameLayout floorContainer = (FrameLayout) view.findViewById(R.id.container);

        for (MarkerPointer pointer : pointers) {
            pointer.clickable = false;
        }

        touchImageView = new TouchImageView(getActivity(), MagicFactory.getBitmap( MagicFactory.getFloor(getActivity(),person.getFloor()).getBackground()), pointers);
        touchImageView.setmActivity(getActivity());

        floorContainer.removeAllViews();
        floorContainer.addView(touchImageView);

        return view;
    }

    public void showLocation(){
        touchImageView.invalidate();
    }
}
