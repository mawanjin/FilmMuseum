package com.example.data;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 15:33
 * չӳ�
 */
public class Screen {
    /** ����ҳ���Ű� : 0�ǵ�ǰչӳ��չӳ�ƻ� 1��չӳ�ع� */
    private int type;
    /** �б�����ʾ������:��ǰչӳ��չӳ�عˡ�չӳ�ƻ�������*/
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
