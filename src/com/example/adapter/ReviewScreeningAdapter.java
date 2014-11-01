package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.data.MagicFactory;
import com.example.data.ScreenItem;
import com.example.filmmuseum.R;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 20:23
 */
public class ReviewScreeningAdapter extends BaseAdapter {
    Context context;
    List<ScreenItem> items;

    public ReviewScreeningAdapter(Context context,List<ScreenItem> items){
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
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
        ScreenItem screenItem = items.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.item_review_screening,null);
        ImageView micro = (ImageView) view.findViewById(R.id.iv1_review_screening);
        micro.setImageBitmap(MagicFactory.getBitmap(screenItem.getImg()));
        TextView title = (TextView) view.findViewById(R.id.tv1_review_screening);
        title.setText(screenItem.getName());

        TextView summary = (TextView) view.findViewById(R.id.tv2_review_screening);
        summary.setText(screenItem.getSummary());

        return view;
    }
}
