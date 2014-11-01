package com.example.data;

import java.io.Serializable;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 15:37
 * 展映活动的详情
 */
public class ScreenItemContent implements Serializable {
    private String title;
    private String img;
    private String summary;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
