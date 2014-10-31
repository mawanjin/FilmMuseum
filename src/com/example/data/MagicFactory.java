package com.example.data;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.example.intelligent.Person;
import com.example.intelligent.PersonService;
import com.example.util.*;

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
    private static Glance glances;


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


}
