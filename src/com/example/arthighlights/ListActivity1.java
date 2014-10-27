package com.example.arthighlights;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.example.adapter.ListActivityAdapter;
import com.example.filmmuseum.R;
import com.example.util.ArtMenu;
import com.example.util.FileSysUtils;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 14:23
 * 智能导航中显示如果类型是list的时候，显示该列表页
 */
public class ListActivity1 extends Activity{

    public static String EXTRA_ITEMS = ListActivity1.class.getName()+"_items";

    private ListActivityAdapter mAdapter;

    private ImageView ivReturn;
    private ImageView iv_menu;
    private TextView tv;
    private ListView listView;

    ArtMenu menu = null;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        // 隐藏标题栏
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        menu = (ArtMenu)  getIntent().getExtras().getSerializable(EXTRA_ITEMS);
        setContentView(R.layout.layout_listactivity);
        mAdapter = new ListActivityAdapter(this,menu.getItems());
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

        ivReturn = (ImageView) findViewById(R.id.ivReturn);
        iv_menu = (ImageView) findViewById(R.id.iv_menu);
        ivReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        iv_menu.setVisibility(View.GONE);
        tv = (TextView) findViewById(R.id.tv_title);
        tv.setText(R.string.inteli_navi);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                final AlertDialog dialog = new AlertDialog.Builder(ListActivity1.this).create();
                dialog.setCancelable(true);
                dialog.setCanceledOnTouchOutside(true);
                dialog.show();
                View view1 = LayoutInflater.from(getBaseContext()).inflate(R.layout.dialog_list_big_img,null);
                dialog.setContentView(view1);
                final ImageView img = (ImageView) view1.findViewById(R.id.img);
                Bitmap bm = BitmapFactory.decodeFile(FileSysUtils.getExternalStoragePath()
                        + "/FilmMuseum/system/image/" + menu.getItems().get(position).getBig());
                img.setImageBitmap(bm);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });

    }
}
