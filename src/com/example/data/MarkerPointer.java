package com.example.data;

import android.content.Context;
import android.graphics.Bitmap;
import org.melonframwork.android.Pointer;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 00:06
 */
public class MarkerPointer extends Pointer {
    /** 对应xml中的id*/
    private int id=1;
    private int contentId;

    public MarkerPointer(){

    }

    public MarkerPointer(Context context, Bitmap _bitmap, float _immutableX, float _immutableY) {
        super(context, _bitmap, _immutableX, _immutableY);
    }
    public MarkerPointer(Context context, float _immutableX, float _immutableY, float _width, float _height) {
        super(context, _immutableX, _immutableY, _width, _height);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }
}
