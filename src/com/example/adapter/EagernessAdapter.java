package com.example.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.util.ArtMenu;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 00:36
 */
public class EagernessAdapter extends BaseAdapter{

    List<ArtMenu> datas;
    Context context;

    public EagernessAdapter(Context context,List<ArtMenu> datas){
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
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
        ArtMenu data = datas.get(i);

        view = LayoutInflater.from(context).inflate(R.layout.item_eagerness,null);
        ImageView banner = (ImageView) view.findViewById(R.id.iv_eagerness);
        ImageView iv3_eagerness = (ImageView) view.findViewById(R.id.iv3_eagerness);
        ImageView iv2_eagerness = (ImageView) view.findViewById(R.id.iv2_eagerness);
        TextView title = (TextView) view.findViewById(R.id.tv_eagerness);

        iv3_eagerness.setImageDrawable(context.getResources().getDrawable(R.drawable.play));
        banner.setImageBitmap(MagicFactory.getBitmap(data.getImg()));
        title.setText(data.getTitle());

        return view;
    }
}
