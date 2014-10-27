package com.example.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.filmmuseum.R;
import com.example.util.FileSysUtils;
import com.example.util.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 14:36
 */
public class ListActivityAdapter extends BaseAdapter {

    private Context context;
    private List<ListItem> items = new ArrayList<ListItem>(0);

    public ListActivityAdapter(Context context, List<ListItem> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ListItem item = items.get(i);
        ViewHolder viewHolder = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.listview_list, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) view.findViewById(R.id.img);
            viewHolder.title = (TextView) view.findViewById(R.id.title);
            viewHolder.txt = (TextView) view.findViewById(R.id.txt);
            view.setTag(viewHolder);

        } else
            viewHolder = (ViewHolder) view.getTag();

        Bitmap bm = BitmapFactory.decodeFile(FileSysUtils.getExternalStoragePath()
                + "/FilmMuseum/system/image/" + item.getSrc());
        viewHolder.img.setImageBitmap(bm);
        viewHolder.title.setText(item.getTitle());
        viewHolder.txt.setText(item.getTxt());
        return view;
    }

    public class ViewHolder {
        ImageView img;
        TextView title;
        TextView txt;
    }

}
