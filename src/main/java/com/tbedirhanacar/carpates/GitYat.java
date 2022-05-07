package com.tbedirhanacar.carpates;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;

public class GitYat {

    private static final LocalTime oneAm = LocalTime.of(1,0).atOffset(ZoneOffset.ofHours(3)).toLocalTime();

    private static final LocalTime fiveAm = LocalTime.of(5,0).atOffset(ZoneOffset.ofHours(3)).toLocalTime();

    public static boolean isBetweenGitYatTime(){
        if (LocalTime.now(ZoneOffset.ofHours(3)).isAfter(oneAm)&&LocalTime.now(ZoneOffset.ofHours(3)).isBefore(fiveAm)){
            return true;
        }return false;
    };

    private static LocalDateTime nextUse = LocalDateTime.now(ZoneOffset.UTC);

    private static LocalDateTime getNextUse() {
        return nextUse;
    }

    public static void setNextUse() {
        nextUse = LocalDateTime.now().plusMinutes(59);
    }

    public static boolean isNextUse() {
        return LocalDateTime.now().isAfter(getNextUse());
    }

    public static String saat(){
        String saat = LocalTime.now(ZoneOffset.ofHours(3)).getHour() + ":";
        String dakika = LocalDateTime.now().getMinute()<10
                ?"0"+LocalDateTime.now().getMinute()
                :""+LocalDateTime.now().getMinute();
        return saat+dakika;
    }

    public static String getGitYatMessage() {
        return "Saat " + saat() + ", git yat!\n " +
                "https://cdn.discordapp.com/attachments/969320849209442334/970470790984241202/videoplayback_2.mp4";
    }
}
