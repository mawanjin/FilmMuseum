package com.example.data;

import java.io.Serializable;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/11/1
 * Time: 22:06
 * 信息里排版二的内容
 */
public class InfoItem implements Serializable {
    private String name;
    private String image;
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
