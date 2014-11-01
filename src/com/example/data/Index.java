package com.example.data;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 23:49
 * 首页数据：LOGO、文字
 */
public class Index {
    private String logo;
    private List<IndexItem> indexItems;

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public List<IndexItem> getIndexItems() {
        return indexItems;
    }

    public void setIndexItems(List<IndexItem> indexItems) {
        this.indexItems = indexItems;
    }
}
