package com.example.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.sax.Element;
import com.example.data.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Download {

    public Glance readGlanceXml(String path) {
        Glance glance = new Glance();
        List<GlanceContent> contents = new ArrayList<GlanceContent>(0);
        glance.setContents(contents);
        GlanceContent content = null;

        File xmlFile = new File(path);
        if (xmlFile.exists()) {
            InputStream slideInputStream = null;
            try {
                slideInputStream = new FileInputStream(path);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(slideInputStream, "UTF-8");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if ("img".equals(xpp.getName())) {
                                glance.setImg(xpp.nextText());
                            } else if ("glance".equals(xpp.getName())) {
                                content = new GlanceContent();
                                contents.add(content);
                            } else if ("title".equals(xpp.getName())) {
                                content.setTitle(xpp.nextText());
                            } else if ("summary".equals(xpp.getName())) {
                                content.setSummary(xpp.nextText());
                            }
                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return glance;
    }

    public List<ArtMenu> readMenuXml(String path) {
        List<ArtMenu> list = null;
        List<ListItem> items = null;
        ArtMenu menu = null;
        ListItem item = null;
        File xmlFile = new File(path);
        Element xmlElement = null;
        if (xmlFile.exists()) {
            try {
                InputStream slideInputStream = new FileInputStream(path);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(slideInputStream, "UTF-8");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<ArtMenu>();
                            break;
                        case XmlPullParser.START_TAG:
                            if ("menu".equals(xpp.getName())) {
                                menu = new ArtMenu();
                            }
                            if ("id".equals(xpp.getName())) {
                                int id = Integer.valueOf(xpp.nextText());
                                menu.setId(id);
                            }
                            if ("type".equals(xpp.getName())) {
                                String type = xpp.nextText();
                                menu.setType(type);
                            }
                            if ("src".equals(xpp.getName())) {
                                String src = xpp.nextText();
                                menu.setSrc(src);
                            }
                            if ("title".equals(xpp.getName())) {
                                String title = xpp.nextText();
                                menu.setTitle(title);
                            }
                            if ("text".equals(xpp.getName())) {
                                String text = xpp.nextText();
                                menu.setText(text);
                            }

                            if ("items".equals(xpp.getName())) {
                                items = new ArrayList<ListItem>(0);
                                menu.setItems(items);
                            }

                            if ("item".equals(xpp.getName())) {
                                item = new ListItem();
                                items.add(item);
                            }

                            if ("isrc".equals(xpp.getName())) {
                                item.setSrc(xpp.nextText());
                            }

                            if ("ibig".equals(xpp.getName())) {
                                item.setBig(xpp.nextText());
                            }

                            if ("ititle".equals(xpp.getName())) {
                                item.setTitle(xpp.nextText());
                            }

                            if ("itxt".equals(xpp.getName())) {
                                item.setTxt(xpp.nextText());
                            }

                            if ("img".equals(xpp.getName())) {
                                menu.setImg(xpp.nextText());
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("menu".equals(xpp.getName())) {
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

    public List<ArtContent> readConXml(String path) {
        List<ArtContent> list = null;
        ArtContent con = null;
        File xmlFile = new File(path);
        Element xmlElement = null;
        if (xmlFile.exists()) {
            try {
                InputStream slideInputStream = new FileInputStream(path);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(slideInputStream, "UTF-8");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<ArtContent>();
                            break;
                        case XmlPullParser.START_TAG:
                            if ("menu".equals(xpp.getName())) {
                                con = new ArtContent();
                            }
                            if ("id".equals(xpp.getName())) {
                                int id = Integer.valueOf(xpp.nextText());
                                con.setId(id);
                            }
                            if ("src".equals(xpp.getName())) {
                                String src = xpp.nextText();
                                con.setSrc(src);
                            }
                            if ("title".equals(xpp.getName())) {
                                String title = xpp.nextText();
                                con.setTitle(title);
                            }
                            if ("content".equals(xpp.getName())) {
                                String content = xpp.nextText();
                                con.setContent(content);
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("menu".equals(xpp.getName())) {
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

    public List<ArtContentAudio> readConXmlAudio(String path) {
        List<ArtContentAudio> list = null;
        ArtContentAudio cons = null;
        File xmlFile = new File(path);
        Element xmlElement = null;
        if (xmlFile.exists()) {
            try {
                InputStream slideInputStream = new FileInputStream(path);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(slideInputStream, "UTF-8");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<ArtContentAudio>();
                            break;
                        case XmlPullParser.START_TAG:
                            if ("layer".equals(xpp.getName())) {
                                cons = new ArtContentAudio();
                            }
                            if ("id".equals(xpp.getName())) {
                                int id = Integer.valueOf(xpp.nextText());
                                cons.setId(id);
                            }
                            if ("visible".equals(xpp.getName())) {
                                int visible = Integer.valueOf(xpp.nextText());
                                cons.setVisible(visible);
                            }
                            if ("type".equals(xpp.getName())) {
                                String type = xpp.nextText();
                                cons.setType(type);
                            }
                            if ("src".equals(xpp.getName())) {
                                String src = xpp.nextText();
                                cons.setSrc(src);
                            }
                            if ("width".equals(xpp.getName())) {
                                int width = Integer.valueOf(xpp.nextText());
                                cons.setWidth(width);
                            }
                            if ("height".equals(xpp.getName())) {
                                int height = Integer.valueOf(xpp.nextText());
                                cons.setHeight(height);
                            }
                            if ("x".equals(xpp.getName())) {
                                int x = Integer.valueOf(xpp.nextText());
                                cons.setX(x);
                            }
                            if ("y".equals(xpp.getName())) {
                                int y = Integer.valueOf(xpp.nextText());
                                cons.setY(y);
                            }
                            if ("title".equals(xpp.getName())) {
                                String title = xpp.nextText();
                                cons.setTitle(title);
                            }
                            if ("textsize".equals(xpp.getName())) {
                                float textsize = Float.valueOf(xpp.nextText());
                                cons.setTextsize(textsize);
                            }
                            if ("text".equals(xpp.getName())) {
                                String text = xpp.nextText();
                                cons.setText(text);
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("layer".equals(xpp.getName())) {

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


    public List<ArtContentVideo> readConXmlVideo(String path) {
        List<ArtContentVideo> list = null;
        ArtContentVideo cons = null;
        File xmlFile = new File(path);
        Element xmlElement = null;
        if (xmlFile.exists()) {
            try {
                InputStream slideInputStream = new FileInputStream(path);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(slideInputStream, "UTF-8");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<ArtContentVideo>();
                            break;
                        case XmlPullParser.START_TAG:
                            if ("layer".equals(xpp.getName())) {
                                cons = new ArtContentVideo();
                            }
                            if ("id".equals(xpp.getName())) {
                                int id = Integer.valueOf(xpp.nextText());
                                cons.setId(id);
                            }
                            if ("visible".equals(xpp.getName())) {
                                int visible = Integer.valueOf(xpp.nextText());
                                cons.setVisible(visible);
                            }
                            if ("type".equals(xpp.getName())) {
                                String type = xpp.nextText();
                                cons.setType(type);
                            }
                            if ("src".equals(xpp.getName())) {
                                String src = xpp.nextText();
                                cons.setSrc(src);
                            }
                            if ("width".equals(xpp.getName())) {
                                int width = Integer.valueOf(xpp.nextText());
                                cons.setWidth(width);
                            }
                            if ("height".equals(xpp.getName())) {
                                int height = Integer.valueOf(xpp.nextText());
                                cons.setHeight(height);
                            }
                            if ("x".equals(xpp.getName())) {
                                int x = Integer.valueOf(xpp.nextText());
                                cons.setX(x);
                            }
                            if ("y".equals(xpp.getName())) {
                                int y = Integer.valueOf(xpp.nextText());
                                cons.setY(y);
                            }
                            if ("textsize".equals(xpp.getName())) {
                                float textsize = Float.valueOf(xpp.nextText());
                                cons.setTextsize(textsize);
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if ("layer".equals(xpp.getName())) {
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


    public List<Floor> readFloorXml(Context context, String path) {
        List<Floor> list = null;
        List<MarkerPointer> pointers = null;
        Floor floor = null;
        MarkerPointer pointer = null;
        File xmlFile = new File(path);
        Element xmlElement = null;
        if (xmlFile.exists()) {
            try {
                InputStream slideInputStream = new FileInputStream(path);
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(slideInputStream, "UTF-8");
                int eventType = xpp.getEventType();
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    switch (eventType) {
                        case XmlPullParser.START_DOCUMENT:
                            list = new ArrayList<Floor>();
                            break;
                        case XmlPullParser.START_TAG:
                            if ("floor".equals(xpp.getName())) {
                                floor = new Floor();
                                list.add(floor);
                            } else if ("id".equals(xpp.getName())) {
                                floor.setId(Integer.valueOf(xpp.nextText()));
                            } else if ("background".equals(xpp.getName())) {
                                floor.setBackground(xpp.nextText());
                            } else if ("pointers".equals(xpp.getName())) {
                                pointers = new ArrayList<MarkerPointer>(0);
                                floor.setPointers(pointers);
                            } else if ("pointer".equals(xpp.getName())) {
                                pointer = new MarkerPointer();
                                pointers.add(pointer);
                            } else if ("src".equals(xpp.getName())) {
                                pointer.bitmap = MagicFactory.getBitmap(xpp.nextText());
                                pointer.width = pointer.bitmap.getWidth();
                                pointer.height = pointer.bitmap.getHeight();
                            } else if ("x".equals(xpp.getName())) {
                                pointer.immutableX = Float.parseFloat(xpp.nextText());
                            } else if ("y".equals(xpp.getName())) {
                                pointer.immutableY = Float.parseFloat(xpp.nextText());
                            } else if ("txt".equals(xpp.getName())) {
                                pointer.txt = xpp.nextText();
                            } else if ("content_id".equals(xpp.getName())) {
                                pointer.setContentId(Integer.parseInt(xpp.nextText()));
                            } else if ("tip".equals(xpp.getName())) {
                                Bitmap bitmapTip = MagicFactory.getBitmap(xpp.nextText());
                                pointer.tip = new MarkerPointer(context, bitmapTip, (pointer.immutableX + pointer.width) - pointer.width / 2 - bitmapTip.getWidth() / 2, pointer.immutableY - pointer.height);
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            if ("menu".equals(xpp.getName())) {

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
