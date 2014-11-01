package com.example.data;

import java.io.Serializable;
import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 22:05
 */
public class Info implements Serializable{
    private int type;
    private String name;
    private String img;
    private String title;
    private String summary;

    //ÅÅ°æ¶þ
    private List<InfoItem> infoItems;


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<InfoItem> getInfoItems() {
        return infoItems;
    }

    public void setInfoItems(List<InfoItem> infoItems) {
        this.infoItems = infoItems;
    }
}
