package com.tbedirhanacar.carpates;

public class Videos {

    private static String iyiGecelerVideo = "https://cdn.discordapp.com/attachments/969320849209442334/969934240022159400/good_night_sleep_well_sailors.mp4";
    private static String gunaydinVideo = "https://cdn.discordapp.com/attachments/969320849209442334/969934240366075935/GOOD_MORNING.mp4";

    public static void setIyiGecelerVideo(String iyiGecelerVideo) {
        Videos.iyiGecelerVideo = iyiGecelerVideo;
    }

    public static void setGunaydinVideo(String gunaydinVideo) {
        Videos.gunaydinVideo = gunaydinVideo;
    }

    public static String getIyiGecelerVideo() {
        return iyiGecelerVideo;
    }

    public static String getGunaydinVideo() {
        return gunaydinVideo;
    }
}
