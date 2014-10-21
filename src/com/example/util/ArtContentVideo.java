package com.example.util;

public class ArtContentVideo {
	private int id;
	private int visible;
	private String type;
	private String src;
	private int width;
	private int height;
	private int x;
	private int y;
	private float textsize;
	
	public ArtContentVideo(){}
	public ArtContentVideo(int id, int visible, String type, String src,
			int width, int height, int x, int y,float textsize) {
		super();
		this.id = id;
		this.visible = visible;
		this.type = type;
		this.src = src;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.textsize = textsize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getVisible() {
		return visible;
	}
	public void setVisible(int visible) {
		this.visible = visible;
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
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public float getTextsize() {
		return textsize;
	}
	public void setTextsize(float textsize) {
		this.textsize = textsize;
	}
	
}
