package com.example.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.sax.Element;
import android.util.Log;

public class Download {

	public List<ArtMenu> readMenuXml(String path)
	{
		List<ArtMenu> list = null;
		ArtMenu menu = null;
		File xmlFile = new File(path);
		Element xmlElement = null;
		if(xmlFile.exists())
		{
			try {
				InputStream slideInputStream = new FileInputStream(path);
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(slideInputStream,"UTF-8");
				int eventType = xpp.getEventType();
				while(eventType!=XmlPullParser.END_DOCUMENT)
				{
					switch(eventType)
					{
					case XmlPullParser.START_DOCUMENT:
						list = new ArrayList<ArtMenu>();
						break;
					case XmlPullParser.START_TAG:
						if("menu".equals(xpp.getName()))
						{
							menu = new ArtMenu();
						}
						if("id".equals(xpp.getName()))
						{
							int id=Integer.valueOf(xpp.nextText());
							menu.setId(id);
						}
						if("type".equals(xpp.getName()))
						{
							String type=xpp.nextText();
							menu.setType(type);
						}
						if("src".equals(xpp.getName()))
						{
							String src=xpp.nextText();
							menu.setSrc(src);
						}
						if("title".equals(xpp.getName()))
						{
							String title = xpp.nextText();
							menu.setTitle(title);
						}
						if("text".equals(xpp.getName()))
						{
							String text=xpp.nextText();
							menu.setText(text);
						}
						
						break;
					case XmlPullParser.END_TAG:
						if("menu".equals(xpp.getName()))
						{
							list.add(menu);
							menu = null;
						}
						break;
					}
					eventType = xpp.next();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	public List<ArtContent> readConXml(String path)
	{
		List<ArtContent> list = null;
		ArtContent con = null;
		File xmlFile = new File(path);
		Element xmlElement = null;
		if(xmlFile.exists())
		{
			try {
				InputStream slideInputStream = new FileInputStream(path);
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(slideInputStream,"UTF-8");
				int eventType = xpp.getEventType();
				while(eventType!=XmlPullParser.END_DOCUMENT)
				{
					switch(eventType)
					{
					case XmlPullParser.START_DOCUMENT:
						list = new ArrayList<ArtContent>();
						break;
					case XmlPullParser.START_TAG:
						if("menu".equals(xpp.getName()))
						{
							con = new ArtContent();
						}
						if("id".equals(xpp.getName()))
						{
							int id=Integer.valueOf(xpp.nextText());
							con.setId(id);
						}
						if("src".equals(xpp.getName()))
						{
							String src=xpp.nextText();
							con.setSrc(src);
						}
						if("title".equals(xpp.getName()))
						{
							String title = xpp.nextText();
							con.setTitle(title);
						}
						if("content".equals(xpp.getName()))
						{
							String content=xpp.nextText();
							con.setContent(content);
						}
						break;
					case XmlPullParser.END_TAG:
						if("menu".equals(xpp.getName()))
						{
							list.add(con);
							con = null;
						}
						break;
					}
					eventType = xpp.next();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return list;
	}
	
	public List<ArtContentAudio> readConXmlAudio(String path)
	{
		List<ArtContentAudio> list = null;
		ArtContentAudio cons = null;
		File xmlFile = new File(path);
		Element xmlElement = null;
		if(xmlFile.exists())
		{
			try {
				InputStream slideInputStream = new FileInputStream(path);
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(slideInputStream,"UTF-8");
				int eventType = xpp.getEventType();
				while(eventType!=XmlPullParser.END_DOCUMENT)
				{
					switch(eventType)
					{
					case XmlPullParser.START_DOCUMENT:
						list = new ArrayList<ArtContentAudio>();
						break;
					case XmlPullParser.START_TAG:
						if("layer".equals(xpp.getName()))
						{
							cons = new ArtContentAudio();
						}
						if("id".equals(xpp.getName()))
						{
							int id=Integer.valueOf(xpp.nextText());
							cons.setId(id);
						}
						if("visible".equals(xpp.getName()))
						{
							int visible=Integer.valueOf(xpp.nextText());
							cons.setVisible(visible);
						}
						if("type".equals(xpp.getName()))
						{
							String type=xpp.nextText();
							cons.setType(type);
						}
						if("src".equals(xpp.getName()))
						{
							String src=xpp.nextText();
							cons.setSrc(src);
						}
						if("width".equals(xpp.getName()))
						{
							int width = Integer.valueOf(xpp.nextText());
							cons.setWidth(width);
						}
						if("height".equals(xpp.getName()))
						{
							int height = Integer.valueOf(xpp.nextText());
							cons.setHeight(height);
						}
						if("x".equals(xpp.getName()))
						{
							int x=Integer.valueOf(xpp.nextText());
							cons.setX(x);
						}
						if("y".equals(xpp.getName())) 
						{
							int y = Integer.valueOf(xpp.nextText());
							cons.setY(y);
						}
						if("title".equals(xpp.getName()))
						{
							String title=xpp.nextText();
							cons.setTitle(title);
						}
						if("textsize".equals(xpp.getName()))
						{
							float textsize = Float.valueOf(xpp.nextText());
							cons.setTextsize(textsize);
						}
						if("text".equals(xpp.getName()))
						{
							String text = xpp.nextText();
							cons.setText(text);
						}
						break;
					case XmlPullParser.END_TAG:
						if("layer".equals(xpp.getName()))
						{
							
							list.add(cons);
							
							cons = null;
						}
						break;
					}
					eventType = xpp.next();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	public List<ArtContentVideo> readConXmlVideo(String path)
	{
		List<ArtContentVideo> list = null;
		ArtContentVideo cons = null;
		File xmlFile = new File(path);
		Element xmlElement = null;
		if(xmlFile.exists())
		{
			try {
				InputStream slideInputStream = new FileInputStream(path);
				XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
				factory.setNamespaceAware(true);
				XmlPullParser xpp = factory.newPullParser();
				xpp.setInput(slideInputStream,"UTF-8");
				int eventType = xpp.getEventType();
				while(eventType!=XmlPullParser.END_DOCUMENT)
				{
					switch(eventType)
					{
					case XmlPullParser.START_DOCUMENT:
						list = new ArrayList<ArtContentVideo>();
						break;
					case XmlPullParser.START_TAG:
						if("layer".equals(xpp.getName()))
						{
							cons = new ArtContentVideo();
						}
						if("id".equals(xpp.getName()))
						{
							int id=Integer.valueOf(xpp.nextText());
							cons.setId(id);
						}
						if("visible".equals(xpp.getName()))
						{
							int visible=Integer.valueOf(xpp.nextText());
							cons.setVisible(visible);
						}
						if("type".equals(xpp.getName()))
						{
							String type=xpp.nextText();
							cons.setType(type);
						}
						if("src".equals(xpp.getName()))
						{
							String src=xpp.nextText();
							cons.setSrc(src);
						}
						if("width".equals(xpp.getName()))
						{
							int width = Integer.valueOf(xpp.nextText());
							cons.setWidth(width);
						}
						if("height".equals(xpp.getName()))
						{
							int height = Integer.valueOf(xpp.nextText());
							cons.setHeight(height);
						}
						if("x".equals(xpp.getName()))
						{
							int x=Integer.valueOf(xpp.nextText());
							cons.setX(x);
						}
						if("y".equals(xpp.getName())) 
						{
							int y = Integer.valueOf(xpp.nextText());
							cons.setY(y);
						}
						if("textsize".equals(xpp.getName()))
						{
							float textsize = Float.valueOf(xpp.nextText());
							cons.setTextsize(textsize);
						}
						break;
					case XmlPullParser.END_TAG:
						if("layer".equals(xpp.getName()))
						{
							list.add(cons);
							cons = null;
						}
						break;
					}
					eventType = xpp.next();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (XmlPullParserException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
}
