package com.example.intelligent;

public class Person {
	private Integer major;
	private Integer minor;
	private String url;
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
	public Person(Integer major, Integer minor, String url) {
		super();
		this.major = major;
		this.minor = minor;
		this.url = url;
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
	
	
	
}
