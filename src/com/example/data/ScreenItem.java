package com.example.data;

import java.io.Serializable;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 15:35
 * չӳ�ƻ��е���Ŀ����
 */
public class ScreenItem implements Serializable{
    private int id;
    /** �������ͼ*/
    private String img;
    private String name;
    /** �ʱ��*/
    private String titleTime;
    /** ʱ��*/
    private String time;
    /** չӳʱ��*/
    private String titleExhiTime;
    /** չӳ����ʱ��*/
    private String exhiTime;

    /** typeΪ0������ */
    private ScreenItemContent content;

    /** typeΪ1*/
    private String title;
    private String summary;
    private String detailTitle;
    private String detail;
    private String detailImg;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitleTime() {
        return titleTime;
    }

    public void setTitleTime(String titleTime) {
        this.titleTime = titleTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitleExhiTime() {
        return titleExhiTime;
    }

    public void setTitleExhiTime(String titleExhiTime) {
        this.titleExhiTime = titleExhiTime;
    }

    public String getExhiTime() {
        return exhiTime;
    }

    public void setExhiTime(String exhiTime) {
        this.exhiTime = exhiTime;
    }

    public ScreenItemContent getContent() {
        return content;
    }

    public void setContent(ScreenItemContent content) {
        this.content = content;
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

    public String getDetailTitle() {
        return detailTitle;
    }

    public void setDetailTitle(String detailTitle) {
        this.detailTitle = detailTitle;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getDetailImg() {
        return detailImg;
    }

    public void setDetailImg(String detailImg) {
        this.detailImg = detailImg;
    }
}
