package com.example.util;

import java.io.Serializable;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/27
 * Time: 14:07
 */
public class ListItem implements Serializable{
    private String src;
    private String big;
    private String title;
    private String txt;

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getBig() {
        return big;
    }

    public void setBig(String big) {
        this.big = big;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }
}
