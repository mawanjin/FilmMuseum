package com.example.util;

public class ArtContent {
	private int id;
	private String title;
	private String src;
	private String content;
	
	public ArtContent() {
		super();
	}
	public ArtContent(int id, String title, String src, String content) {
		super();
		this.id = id;
		this.title = title;
		this.src = src;
		this.content = content;
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
	public String getSrc() {
		return src;
	}
	public void setSrc(String src) {
		this.src = src;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
