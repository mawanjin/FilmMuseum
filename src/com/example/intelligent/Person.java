package com.example.intelligent;

import java.io.Serializable;

public class Person implements Serializable{
	private Integer major;
	private Integer minor;
    private Integer floor;
    private Integer pid;
	private String url;
    private int locationX;
    private int locationY;
    private String locationMarker;
    private boolean showMap;

	public Integer getMajor() {
		return major;
	}
	public void setMajor(Integer major) {
		this.major = major;
	}
	public Integer getMinor() {
		return minor;
	}
	public void setMinor(Integer minor) {
		this.minor = minor;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Person(Integer major, Integer minor,Integer floor, String url) {
		super();
		this.major = major;
		this.minor = minor;
		this.url = url;
        this.floor = floor;
	}
	public Person() {
		super();
	}
	
	public Integer toMajor()
	{
		return major;
	}
	
	public Integer toMinor()
	{
		return minor;
	}
	
	public String toUrl()
	{
		return url;
	}

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public String getLocationMarker() {
        return locationMarker;
    }

    public void setLocationMarker(String locationMarker) {
        this.locationMarker = locationMarker;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

	public boolean isShowMap() {
		return showMap;
	}

	public void setShowMap(boolean showMap) {
		this.showMap = showMap;
	}
}
