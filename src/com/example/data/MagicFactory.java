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
 * 欢迎来到魔法工厂。这里你可以发现，我们可以把所有的xml中的数据，用神奇的魔法转变
 * 成可以吃的面包（POJO）:).
 * 包括音频数据(content2.xml)
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
     * 转化音频数据content2.xml
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
     * 从/FilmMuseum/system/image/中读取图片
     *
     * @param src
     * @return
     */
    public static Bitmap getBitmap(String src) {
        return BitmapFactory.decodeFile(FileSysUtils.getExternalStoragePath()
                + "/FilmMuseum/system/FilmMuseum/image/" + src);
    }

    /**
     * 转化内容content.xml
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
     * 转化内容content.xml
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
     * 转化内容content.xml
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
     * 读取播放源名称
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
     * 转化内容floor.xml.获取楼层信息
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
     * 图层数据
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
     * 对应content4.xml 视频UI
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
