package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.data.MagicFactory;
import com.example.data.Online;
import com.example.filmmuseum.R;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/2
 * Time: 01:10
 */
public class OnlineAdapter extends BaseAdapter {

    List<Online> onlines;
    Context context;

    public OnlineAdapter(Context context,List<Online> onlines){
        this.context = context;
        this.onlines = onlines;
    }

    @Override
    public int getCount() {
        return onlines.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Online online = onlines.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.item,null);
        ImageView imageView = (ImageView) view.findViewById(R.id.menuIv);
        imageView.setImageBitmap(MagicFactory.getBitmap(online.getImg()));

        TextView name = (TextView) view.findViewById(R.id.menu_tv);
        name.setText(online.getName());

        return view;
    }
}
