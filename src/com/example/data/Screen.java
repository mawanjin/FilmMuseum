package com.example.data;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 15:33
 * 展映活动
 */
public class Screen {
    /** 两种页面排版 : 0是当前展映和展映计划 1是展映回顾 */
    private int type;
    /** 列表中显示的名称:当前展映、展映回顾、展映计划。。。*/
    private String title;

    private List<ScreenItem> screenItems;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ScreenItem> getScreenItems() {
        return screenItems;
    }

    public void setScreenItems(List<ScreenItem> screenItems) {
        this.screenItems = screenItems;
    }
}
