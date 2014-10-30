package com.example.data;

import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/29
 * Time: 14:33
 */
public class Floor {
    private int id;
    private String background;
    private List<MarkerPointer> pointers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public List<MarkerPointer> getPointers() {
        return pointers;
    }

    public void setPointers(List<MarkerPointer> pointers) {
        this.pointers = pointers;
    }
}
