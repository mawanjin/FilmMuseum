package com.example.arthighlights;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.adapter.ListActivityAdapter;
import com.example.data.MagicFactory;
import com.example.filmmuseum.R;
import com.example.util.ArtMenu;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 14:23
 * 智能导航中显示如果类型是list的时候，显示该列表页
 */
public class ListFragment  extends Fragment{



    private ListActivityAdapter mAdapter;

    private ImageView ivReturn;
    private ImageView iv_menu;
    private TextView tv;
    private ListView listView;

    ArtMenu menu = null;

    public ListFragment(ArtMenu menu){
        this.menu = menu;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);

//        menu = (ArtMenu)  getIntent().getExtras().getSerializable(EXTRA_ITEMS);

        View view = inflater.inflate(R.layout.layout_listactivity,null);
        mAdapter = new ListActivityAdapter(getActivity(),menu.getItems());
        listView = (ListView) view.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

        ivReturn = (ImageView) view.findViewById(R.id.ivReturn);
        iv_menu = (ImageView) view.findViewById(R.id.iv_menu);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        iv_menu.setVisibility(View.GONE);
        tv = (TextView) view.findViewById(R.id.tv_title);
        tv.setText(R.string.inteli_navi);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_list_big_img,null);
                dialog.setContentView(view1);
                final ImageView img = (android.widget.ImageView) view1.findViewById(R.id.img);
                img.setImageBitmap(MagicFactory.getBitmap(menu.getItems().get(position).getBig()));
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

        return view;
    }
}
