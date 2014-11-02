package com.example.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.intelligent.Person;
import com.example.intelligent.PersonService;
import com.example.util.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.List;

/**
 * User: mawanjin@join-cn.com
 * Date: 14/10/28
 * Time: 20:43
 * ��ӭ����ħ����������������Է��֣����ǿ��԰����е�xml�е����ݣ��������ħ��ת��
 * �ɿ��ԳԵ������POJO��:).
 * ������Ƶ����(content2.xml)
 */
public class MagicFactory {

    private static List<ArtContentAudio> audios;
    private static List<ArtContent> artContents;
    private static List<ArtContentVideo> artContentVideos;
    private static List<ArtMenu> artMenus;
    private static List<Person> persons;
    private static List<Floor> floors;
    private static List<ArtContentVideo> videoLayers;
    private static List<ArtMenu> eagerness;
    private static List<Recommend> recommends;
    private static Glance glances;
    private static List<Screen> screens;
    private static List<Info> infos;
    private static Index index;
    private static List<Online> onlines;
    private static Version version;


    /**
     * ת����Ƶ����content2.xml
     *
     * @return
     */
    public static List<ArtContentAudio> getAudios() {
        if (audios == null) {
            Download dow = new Download();
            audios = dow
                    .readConXmlAudio(FileSysUtils.getExternalStoragePath()
                            + "/FilmMuseum/system/FilmMuseum/content2.xml");
        }
        return audios;
    }

    /**
     * ��/FilmMuseum/system/image/�ж�ȡͼƬ
     *
     * @param src
     * @return
     */
    public static Bitmap getBitmap(String src) {
        return BitmapFactory.decodeFile(FileSysUtils.getExternalStoragePath()
                + "/FilmMuseum/system/FilmMuseum/image/" + src);
    }

    /**
     * ת������content.xml
     *
     * @return
     */
    public static List<ArtContent> getArtContents() {
        if (artContents == null) {
            Download dow = new Download();
            artContents = dow.readConXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/content.xml");
        }
        return artContents;
    }


    /**
     * ת������content.xml
     *
     * @return
     */
    public static List<ArtMenu> getArtMenus() {
        if (artMenus == null) {
            Download dow = new Download();
            artMenus = dow.readMenuXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/menu.xml");
        }
        return artMenus;
    }


    public static List<Person> getPersons() {
        if (persons == null) {
            try {
                persons = PersonService
                        .getPersons(FileSysUtils.getExternalStoragePath()
                                + "/FilmMuseum/system/FilmMuseum/beacon.xml");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return persons;
    }


    /**
     * ת������content.xml
     *
     * @return
     */
    public static List<ArtContentVideo> getArtContentVideos() {
        if (artContentVideos == null) {
            Download dow = new Download();
            artContentVideos = dow
                    .readConXmlVideo(FileSysUtils.getExternalStoragePath()
                            + "/FilmMuseum/system/FilmMuseum/content3.xml");
        }
        return artContentVideos;
    }

    /**
     * ��ȡ����Դ����
     *
     * @return
     */
    public static ArtContent getPlay(int id) {
        MagicFactory.getArtContents();

        for (ArtContent c : artContents) {
            if (id == c.getId()) {
                return c;
            }
        }
        return null;
    }


    public static String getPlayUrl(String src) {
        return FileSysUtils.getExternalStoragePath() + "/FilmMuseum/system/FilmMuseum/image/" + src;
    }


    /**
     * ת������floor.xml.��ȡ¥����Ϣ
     *
     * @return
     */
    public static List<Floor> getFloors(Context context) {
        if (floors == null) {
            Download dow = new Download();
            floors = dow
                    .readFloorXml(context, FileSysUtils.getExternalStoragePath()
                            + "/FilmMuseum/system/FilmMuseum/floor.xml");
        }
        return floors;
    }

    /**
     * ͼ������
     * @param context
     * @param index
     * @return
     */
    public static Floor getFloor(Context context, int index) {
        getFloors(context);
        if (floors != null) {
            return floors.get(index);
        }
        return null;
    }

    /**
     * ��Ӧcontent4.xml ��ƵUI
     * @return
     */
    public static List<ArtContentVideo> getVideoLayer(){

        if(videoLayers==null){
            Download dow = new Download();
            videoLayers = dow.readConXmlVideo(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/content4.xml");
        }
        return videoLayers;
    }

    public static List<ArtMenu> getEagerness(){

        if(eagerness==null){
            Download dow = new Download();
            eagerness = dow.readMenuXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/eagerness.xml");
        }

        return eagerness;
    }

    public static Glance getGlance(){
        if(glances==null){
            Download dow = new Download();
            glances = dow.readGlanceXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/glance.xml");
        }
        return glances;
    }

    public static List<Recommend> getRecommends(){
        if(recommends==null){
            Download dow = new Download();
            recommends = dow.readRecommendXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/recommend.xml");
        }
        return recommends;
    }

    public static List<Screen> getScreens(){
        if(screens==null){
            Download dow = new Download();
            screens = dow.readScreenXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/screen.xml");
        }
        return screens;
    }

    public static List<Info> getInfos(){
        if(infos==null){
            Download dow = new Download();
            infos = dow.readInfoXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/info.xml");
        }
        return infos;
    }

    public static Index getIndex(){
        if(index==null){
            Download dow = new Download();
            index = dow.readIndexXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/indexdata.xml");
        }
        return index;
    }

    public static List<Online> getOnlines(){

        if(onlines==null){
            Download dow = new Download();
            onlines = dow.readOnlineXml(FileSysUtils.getExternalStoragePath()
                    + "/FilmMuseum/system/FilmMuseum/online.xml");
        }
        return onlines;
    }

    public static Version getVersion(Context context){
        if(version==null){
            version = new Version();
            InputStream slideInputStream = null;
            try {
                slideInputStream = context.getResources().getAssets().open("version.xml");
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
                            if ("areaId".equals(xpp.getName())) {
                                version.setAreaId(Integer.parseInt(xpp.nextText()));
                            } else if ("versionCode".equals(xpp.getName())) {
                                version.setVersionCode(Integer.parseInt(xpp.nextText()));
                            } else if ("checkUrl".equals(xpp.getName())) {
                                version.setCheckUrl(xpp.nextText());
                            } else if ("downloadUrl".equals(xpp.getName())) {
                                version.setDownloadUrl(xpp.nextText());
                            } else if ("fileSize".equals(xpp.getName())) {
                                version.setFileSize(xpp.nextText());
                            }
                            break;
                    }
                    eventType = xpp.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return version;
    }

    public static Version parseVersionXml(InputStream inputStream){
        try {
            Version version1 = new Version();
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(inputStream, "UTF-8");
            int eventType = xpp.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if ("areaId".equals(xpp.getName())) {
                            version1.setAreaId(Integer.parseInt(xpp.nextText()));
                        } else if ("versionCode".equals(xpp.getName())) {
                            version1.setVersionCode(Integer.parseInt(xpp.nextText()));
                        } else if ("checkUrl".equals(xpp.getName())) {
                            version1.setCheckUrl(xpp.nextText());
                        } else if ("downloadUrl".equals(xpp.getName())) {
                            version1.setDownloadUrl(xpp.nextText());
                        } else if ("fileSize".equals(xpp.getName())) {
                            version1.setFileSize(xpp.nextText());
                        }

                        break;
                }
                eventType = xpp.next();
            }
            return version1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
