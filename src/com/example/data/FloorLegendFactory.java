package com.example.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.filmmuseum.R;
import com.example.intelligent.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 12/19/13
 * Time: 8:53 PM
 * 楼层图数据源.理论上是从xml包中解析出来。
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
     * 第一层
     */
    public void init() {
        viewItems = new ArrayList<MarkerPointer>(0);

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film);
        MarkerPointer p = new MarkerPointer(context, bitmap, 136, 800);
        p.txt = "艺术影厅";

        Bitmap bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film);
        p = new MarkerPointer(context, bitmap, 900, 860);
        p.txt = "乌鸦与麻雀";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film);
        p = new MarkerPointer(context, bitmap, 500, 1202);
        p.txt = "国歌诞生";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 1319, 1040);
        p.txt = "五号摄影棚";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_pic);
        p = new MarkerPointer(context, bitmap, 290, 1125);
        p.txt = "影史第一";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 420, 1150);
        p.txt = "灿烂金杯";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 260, 1269);
        p.txt = "百年辉煌";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 510, 1352);
        p.txt = "荣耀瞬间";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);

        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_ppt);
        p = new MarkerPointer(context, bitmap, 500, 1616);
        p.txt = "光影之戏";

        bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame);
        p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
        viewItems.add(p);


    }

    public List<MarkerPointer> getF2Items() {
        if (viewItems2 == null) {

            viewItems2 = new ArrayList<MarkerPointer>(0);
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_film_purple);
            MarkerPointer p = new MarkerPointer(context, bitmap, 136, 800);
            p.txt = "一号录音棚";

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
            p.txt = "影海溯源";

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
            p.txt = "水银灯下的南京路";

            Bitmap bitmapTip = BitmapFactory.decodeResource(context.getResources(), R.drawable.tip_frame_yellow);
            p.tip = new MarkerPointer(context, bitmapTip, (p.immutableX + p.width) - p.width / 2 - bitmapTip.getWidth() / 2, p.immutableY - p.height);
            viewItems4.add(p);
        }
        return viewItems4;
    }

    public List<MarkerPointer> getViewItems() {
        return viewItems;
    }

    public List<MarkerPointer> getViewItemsWithLocation(Context context,Person person) {

        List<MarkerPointer> pointers = null;

        List<Floor> floors = MagicFactory.getFloors(context);
        for(Floor floor:floors){
            if(floor.getId()==person.getFloor()){
                pointers = floor.getPointers();
                for(MarkerPointer pointer: pointers){
                    if(person.getPid()==pointer.getId()){
                        pointer.showing = true;
                        break;
                    }
                }
                break;
            }
        }

        return pointers;
    }


}
