package com.example.util;

/**
 * 对应content2.xml中的数据。音频信息。
 */
public class ArtContentAudio {
	private int id;
	private int visible;
	private String type;
	private String src;
	private int width;
	private int height;
	private int x;
	private int y;
	private String title;
	private float textsize;
	private String text;
	
	public ArtContentAudio(){}
	public ArtContentAudio(int id,int visible, String type, String src, int width,
			int height, int x, int y,String title,float textsize,String text) {
		super();
		this.id=id;
		this.visible = visible;
		this.type = type;
		this.src = src;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
		this.title=title;
		this.textsize=textsize;
		this.text=text;
	}
	public float getTextsize() {
		return textsize;
	}
	public void setTextsize(float textsize) {
		this.textsize = textsize;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	
}
