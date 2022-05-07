package com.tbedirhanacar.carpates;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CarpatesGithub {

    private static List<String> carpatesGithubKod = new ArrayList<>(Arrays.asList("#carpateskod","#carpatesgithub","#carpateskaynak"));

    public static List<String> getCarpatesGithubKod() {
        return carpatesGithubKod;
    }

    private static LocalDateTime nextUse = LocalDateTime.now();

    public static String githubLink(){ return "https://github.com/tbedirhanacar/carpates-bot";}

    private static LocalDateTime getNextUse() {
        return nextUse;
    }

    public static void setNextUse(){
        nextUse = LocalDateTime.now().plusMinutes(3);
    }

    public static boolean isNextUse(){
        return LocalDateTime.now().isAfter(getNextUse());
    }

}
