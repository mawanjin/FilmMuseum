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
 * Time: 18:58
 */
public class NowScreeningAdapter extends BaseAdapter{
    Context context;
    List<ScreenItem> items;


    public NowScreeningAdapter(Context context,List<ScreenItem> items){
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
        ScreenItem item = items.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.item_screening,null);
        ImageView micro = (ImageView) view.findViewById(R.id.iv_screening);
        micro.setImageBitmap(MagicFactory.getBitmap(item.getImg()));
        TextView title = (TextView) view.findViewById(R.id.tv_screening);
        title.setText(item.getName());
        TextView titleTime = (TextView) view.findViewById(R.id.tv1_screening);
        titleTime.setText(item.getTitleTime());
        TextView time = (TextView) view.findViewById(R.id.tv2_screening);
        time.setText(item.getTime());

        TextView titleExhi = (TextView) view.findViewById(R.id.tv3_screening);
        titleExhi.setText(item.getTitleExhiTime());

        TextView exhiTime = (TextView) view.findViewById(R.id.tv4_screening);
        exhiTime.setText(item.getExhiTime());

        return view;
    }
}
