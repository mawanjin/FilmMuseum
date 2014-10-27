package com.example.util;

import java.io.Serializable;
import java.util.List;

public class ArtMenu implements Serializable{
	private int id;
	private String type;
	private String src;
	private String title;
	private String text;

    private List<ListItem> items;

	public ArtMenu(){}
	public ArtMenu(int id, String type, String src, String title, String text) {
		super();
		this.id = id;
		this.type = type;
		this.src = src;
		this.title = title;
		this.text = text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

    public List<ListItem> getItems() {
        return items;
    }

    public void setItems(List<ListItem> items) {
        this.items = items;
    }
}
