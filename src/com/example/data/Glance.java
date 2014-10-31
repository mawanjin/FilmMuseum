package com.example.data;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 01:11
 */
public class Glance {
    private String img;
    private List<GlanceContent> contents;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public List<GlanceContent> getContents() {
        return contents;
    }

    public void setContents(List<GlanceContent> contents) {
        this.contents = contents;
    }
}
