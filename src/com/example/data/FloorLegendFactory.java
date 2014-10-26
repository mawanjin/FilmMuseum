package com.example.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.filmmuseum.R;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 12/19/13
 * Time: 8:53 PM
 * ¥��ͼ����Դ.�������Ǵ�xml���н���������
 */
public class FloorLegendFactory {
    private Context context;
    private static FloorLegendFactory floorLegendFactory;
    private List<MarkerPointer> viewItems;
    private List<MarkerPointer> viewItems2;
    private List<MarkerPointer> viewItems3;
    private List<MarkerPointer> viewItems4;

    public static FloorLegendFactory getInstance(Context context) {
        if (floorLegendFactory == null) floorLegendFactory = new FloorLegendFactory(context);
        return floorLegendFactory;
    }

    public FloorLegendFactory(Context context) {
        this.context = context;
        init();
    }

    /**
     * ��һ��
     */
    public void init() {
        viewItems = new ArrayList<MarkerPointer>(0);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film);
        MarkerPointer p = new MarkerPointer(context, bitmap, 136, 800);
        p.txt = "����Ӱ��";

        Bitmap bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film);
        p = new MarkerPointer(context, bitmap, 900, 860);
        p.txt = "��ѻ����ȸ";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film);
        p = new MarkerPointer(context, bitmap, 500, 1202);
        p.txt = "���赮��";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 1319, 1040);
        p.txt = "�����Ӱ��";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_pic);
        p = new MarkerPointer(context, bitmap, 290, 1125);
        p.txt = "Ӱʷ��һ";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 420, 1150);
        p.txt = "���ý�";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 260, 1269);
        p.txt = "����Ի�";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 510, 1352);
        p.txt = "��ҫ˲��";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 500, 1616);
        p.txt = "��Ӱ֮Ϸ";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);


    }

    public List<MarkerPointer> getF2Items() {
        if (viewItems2 == null) {

            viewItems2 = new ArrayList<MarkerPointer>(0);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film_purple);
            MarkerPointer p = new MarkerPointer(context, bitmap, 136, 800);
            p.txt = "һ��¼����";

            Bitmap bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame_purple);
            p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
            viewItems2.add(p);
        }
        return viewItems2;
    }

    public List<MarkerPointer> getF3Items() {
        if (viewItems3 == null) {

            viewItems3 = new ArrayList<MarkerPointer>(0);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_pic_atrovirens);
            MarkerPointer p = new MarkerPointer(context, bitmap, 136, 800);
            p.txt = "Ӱ����Դ";

            Bitmap bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame_atrovirens);
            p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
            viewItems3.add(p);
        }
        return viewItems3;
    }

    public List<MarkerPointer> getF4Items() {
        if (viewItems4 == null) {

            viewItems4 = new ArrayList<MarkerPointer>(0);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_pic_yellow);
            MarkerPointer p = new MarkerPointer(context, bitmap, 136, 800);
            p.txt = "ˮ�����µ��Ͼ�·";

            Bitmap bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame_yellow);
            p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
            viewItems4.add(p);
        }
        return viewItems4;
    }

    public List<MarkerPointer> getViewItems() {
        return viewItems;
    }


}
