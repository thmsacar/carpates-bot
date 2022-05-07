package com.tbedirhanacar.carpates;

import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    private static int lastFlood = -1;

    private static int lastMeme = -1;

    private static List<String> returnAdmins(){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get("carpates-source/whitelist-users.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public static boolean isUserAdmin(User user){
       return returnAdmins().contains(user.getIdAsString());
    }

    private static List<String> returnFloods(){
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get("carpates-source/carpates-floods.txt"), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    private static String returnFlood(int idx){
        return returnFloods().get(idx);
    }

    private static String returnRandomFlood(){
        String flood;
        int i;
        do {
             i=randomGenerator(returnFloods().size());
            flood = returnFlood(i);
        }while(flood.isEmpty()||(i==lastFlood));
        lastFlood=i;
        return flood;
    }

    public static EmbedBuilder returnRandomFloodEmbed(){
        return new EmbedBuilder()
                .setAuthor("Carpates",null,"https://cdn.discordapp.com/attachments/969320849209442334/969935335452737596/addon_uploads_1551561734_1740927513.jpg")
                .setFooter("Carpates bot Duty tarafından özel olarak tasarlandı", "https://cdn.discordapp.com/attachments/969320849209442334/970035473626046514/Ekran_Resmi_2022-03-12_03.58.30.png")
                .setDescription(returnRandomFlood());
    }

    private static int randomGenerator(int size){
        int min = 0; int max = size-1;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return random_int;
    }

    private static String returnMeme(int idx){
        return CarpatesMemes.getMemes().get(idx);
    }

    public static String returnRandomMeme(){
        String meme;
        int i;
        do {
            i=randomGenerator(CarpatesMemes.getMemes().size());
            meme = returnMeme(i);
        }while(meme.isEmpty()||(i==lastMeme));
        lastMeme = i;
        return meme;
    }

    private static long userCooldownEndFinder(User user){
        return UserCooldown.userCooldown.get(user);
    }

    public static long userCoolDownRemainingSecondFinder(User user){
        return (userCooldownEndFinder(user)-System.currentTimeMillis())/1000;
    }

    public static boolean isUserInCooldown(User user){
        if (UserCooldown.userCooldown.containsKey(user)){
            return System.currentTimeMillis()<userCooldownEndFinder(user);
        }else{
            return false;
        }
    }

    private static List<String> gatherLinksFromMessage(Message message) {
        List<String> links = new ArrayList<String>(Arrays.asList(message.getContent().trim().split(" ")));
        links.remove(0);
        return links;
    }

    public static Set<String> gatheredLinksList(Message message){
        List<String> links = gatherLinksFromMessage(message).stream().filter(t->t.startsWith("http")).collect(Collectors.toList());
        List<String> corruptedLinks = links.stream().filter(t->t.endsWith("#ekle")).collect(Collectors.toList());
        links.removeAll(corruptedLinks);
        Set<String> linkSet = new HashSet<>(links);
        return linkSet;
    }

    public static int addLinksToSource(Set<String> linkSet){
        try {
            linkSet.stream().forEach(CarpatesMemes::addLinks);
            return linkSet.size();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    public static int removeLinksFromSource(Set<String> linkSet){
        try {
            linkSet.stream().forEach(CarpatesMemes::removeLink);
            return linkSet.size();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }





}
