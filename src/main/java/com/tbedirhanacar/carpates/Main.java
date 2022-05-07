package com.tbedirhanacar.carpates;

import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.emoji.Emoji;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.Timer;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        //Token.getToken() returns Discord bot token. Token class doesn't exist in this repository for safety reasons
        DiscordApi api = new DiscordApiBuilder().setToken(Token.getToken()).setAllIntents().login().join();
        System.out.println("Bot activated");

        TextChannel genelSohbet = api.getTextChannelById("966449251267776571").isPresent()?api.getTextChannelById("966449251267776571").get():null;

        api.updateActivity(ActivityType.PLAYING,"Kedisiyle");

        api.updateStatus(UserStatus.IDLE);

        //#aktivite
        api.addMessageCreateListener(event -> {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#aktivite ")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        ActivityType aktivite = ActivityType.getActivityTypeById(event.getMessageContent().charAt(10));
                        String aktiviteStr = event.getMessageContent().substring(12);
                        api.updateActivity(aktivite,aktiviteStr);
                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#durum
        api.addMessageCreateListener(event -> {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#durum ")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        UserStatus status = UserStatus.fromString(event.getMessageContent().toLowerCase(Locale.forLanguageTag("tr-TR")).substring(7));
                        api.updateStatus(status);
                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#ekle
        api.addMessageCreateListener(event -> {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#ekle")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        Set<String> linkSet = Utils.gatheredLinksList(event.getMessage());
                        int addedLinks = linkSet.size();
                        AtomicBoolean isDeleted = new AtomicBoolean(false);
                        if (addedLinks>0){
                            Message replyMessage = event.getMessage().reply(addedLinks+" link eklenecek onaylıyor musunuz?\n"+linkSet).get();
                            replyMessage.addReactions("✅","❌");
                            TimeUnit.SECONDS.sleep(1);
                            api.addReactionAddListener(reactionAddEvent -> {
                                if (reactionAddEvent.getMessage().get().equals(replyMessage)){
                                    Emoji emoji = reactionAddEvent.getEmoji();
                                    if (emoji.equalsEmoji("✅")){
                                        replyMessage.delete();
                                        isDeleted.set(true);
                                        boolean isSuccesful = Utils.addLinksToSource(linkSet) > 0;
                                        String reply = isSuccesful ? addedLinks+" link kaynaklara eklendi" : "Hata! Link eklenemedi";
                                        event.getMessage().reply(reply);
                                    }
                                    if (emoji.equalsEmoji("❌")){
                                        replyMessage.delete();
                                        isDeleted.set(true);
                                    }
                                }
                            }).removeAfter(11,TimeUnit.SECONDS);
                            Timer t = new Timer();
                            t.schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            if (!isDeleted.get()){
                                                replyMessage.delete();
                                                event.getMessage().reply("Onaylamadığınız için işlem iptal edildi!");
                                            }
                                            t.cancel();
                                        }
                                    },
                                    10000
                            );

                        }
                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#memesil
        api.addMessageCreateListener(event -> {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#msil")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        Set<String> linkSet = Utils.gatheredLinksList(event.getMessage());
                        int addedLinks = linkSet.size();
                        AtomicBoolean isDeleted = new AtomicBoolean(false);
                        if (addedLinks>0){
                            Message replyMessage = event.getMessage().reply(addedLinks+" link silinecek onaylıyor musunuz?\n"+linkSet).get();
                            replyMessage.addReactions("✅","❌");
                            TimeUnit.SECONDS.sleep(1);
                            api.addReactionAddListener(reactionAddEvent -> {
                                if (reactionAddEvent.getMessage().get().equals(replyMessage)){
                                    Emoji emoji = reactionAddEvent.getEmoji();
                                    if (emoji.equalsEmoji("✅")){
                                        replyMessage.delete();
                                        isDeleted.set(true);
                                        boolean isSuccesful = Utils.removeLinksFromSource(linkSet) > 0;
                                        String reply = isSuccesful ? addedLinks+" link kaynaklardan silindi" : "Hata! Link silinemedi";
                                        event.getMessage().reply(reply);
                                    }
                                    if (emoji.equalsEmoji("❌")){
                                        replyMessage.delete();
                                        isDeleted.set(true);
                                    }
                                }
                            }).removeAfter(11,TimeUnit.SECONDS);
                            Timer t = new Timer();
                            t.schedule(
                                    new java.util.TimerTask() {
                                        @Override
                                        public void run() {
                                            if (!isDeleted.get()){
                                                replyMessage.delete();
                                                event.getMessage().reply("Onaylamadığınız için işlem iptal edildi!");
                                            }
                                            t.cancel();
                                        }
                                    },
                                    10000
                            );

                        }
                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#duyuru
        api.addMessageCreateListener(event -> {
           if (event.isPrivateMessage()&&
                   !event.getMessageAuthor().isBotUser()&&
                   event.getMessageContent().startsWith("#duyuru ")){
               if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                   try{
                       EmbedBuilder embed = new EmbedBuilder().setTitle(event.getMessageContent().substring(27))
                               .setAuthor("Carpates",null,"https://cdn.discordapp.com/attachments/969320849209442334/969935335452737596/addon_uploads_1551561734_1740927513.jpg")
                               .setFooter("Carpates bot Duty tarafından özel olarak tasarlandı", "https://cdn.discordapp.com/attachments/969320849209442334/970035473626046514/Ekran_Resmi_2022-03-12_03.58.30.png");
                       api.getChannelById(event.getMessageContent().substring(8,26)).get().asServerTextChannel().get().sendMessage(embed);
                   }catch (Exception e){
                       event.getMessage().reply("Bir hata oluştu!");
                       e.printStackTrace();
                   }
               }else {
                   event.getChannel().sendMessage("Senle konuşma iznim yok!");
               }
           }
        });

        //#mesaj
        api.addMessageCreateListener(event -> {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#mesaj ")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try{
                        String mesaj = event.getMessageContent().substring(26);
                        api.getChannelById(event.getMessageContent().substring(7,25)).get().asServerTextChannel().get().sendMessage(mesaj);
                    }catch (Exception e){
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#carpatesmeme
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()&&event.getMessageContent().trim().equals("#carpatesmeme")){
                    User user = event.getMessageAuthor().asUser().get();
                    if (Utils.isUserInCooldown(user)&&!Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                        event.getMessage().reply("Yavaşla! Tekrar meme istemek için "+ Utils.userCoolDownRemainingSecondFinder(user) +" saniye beklemen lazım!");
                    }else {
                        event.getMessage().reply(Utils.returnRandomMeme());
                        UserCooldown.setNextUse(user);
                    }
            }
        });

        //#carpatesflood
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()&&event.getMessageContent().trim().equals("#carpatesflood")){

                    User user = event.getMessageAuthor().asUser().get();
                    if (Utils.isUserInCooldown(user)&&!Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                        event.getMessage().reply("Yavaşla! Tekrar flood istemek için "+ Utils.userCoolDownRemainingSecondFinder(user) +" saniye beklemen lazım!");
                    }else {
                        event.getMessage().reply(Utils.returnRandomFloodEmbed());
                        UserCooldown.setNextUse(user);
                    }
            }
        });

        //iyi geceler
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()){
                if(event.getMessageContent().toLowerCase(Locale.forLanguageTag("tr-TR")).contains("iyi geceler")||
                        event.getMessageContent().toLowerCase(Locale.forLanguageTag("tr-TR")).contains("ıyı geceler")
                ){
                        if (GoodNightMorningCooldown.isNextUse(event.getServer().get())){
                            event.getMessage().reply(Videos.getIyiGecelerVideo());
                            GoodNightMorningCooldown.setNextUse(event.getServer().get());
                        }

                }
            }
        });

        //günaydın
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()){
                if(event.getMessageContent().toLowerCase(Locale.forLanguageTag("tr-TR")).contains("gunaydin")||
                        event.getMessageContent().toLowerCase(Locale.forLanguageTag("tr-TR")).contains("gunaydın")||
                        event.getMessageContent().toLowerCase(Locale.forLanguageTag("tr-TR")).contains("günaydın")||
                        event.getMessageContent().toLowerCase(Locale.forLanguageTag("tr-TR")).contains("günaydin")
                ){
                        if (GoodNightMorningCooldown.isNextUse(event.getServer().get())){
                            event.getMessage().reply(Videos.getGunaydinVideo());
                            GoodNightMorningCooldown.setNextUse(event.getServer().get());
                        }

                }
            }
        });

        //#adminhelp
        api.addMessageCreateListener(event -> {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#adminhelp")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                   event.getMessage().reply(AdminHelp.getEmbed());
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#servers
        api.addMessageCreateListener(event -> {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#servers")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    String serversReply = "";
                    Set<Server> servers = api.getServers().stream().collect(Collectors.toSet());
                    for (Server s: servers) {
                        serversReply += s.toString()+"\n";
                    }
                    event.getMessage().reply(serversReply);
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#carpateshelp
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()&&event.getMessageContent().trim().equals("#carpateshelp")){

                    if(CarpatesHelp.isNextUse()){
                        event.getMessage().reply(CarpatesHelp.getEmbed());
                        CarpatesHelp.setNextUse();
                    }

            }
        });

        //Carpates mention
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()&&event.getMessageContent().contains(CarpatesMention.getCarpatesMention())){
                if(CarpatesMention.isNextUse()){
                    event.getMessage().reply(CarpatesMention.getCarpatesMentionMessage());
                    CarpatesMention.setNextUse();
                }
            }
        });

        //#sil
        api.addMessageCreateListener(event ->   {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#sil")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        String messageLink = event.getMessageContent().substring(5,90);
                        api.getMessageByLink(messageLink).get().get().delete();
                        event.getMessage().reply("Mesaj silindi!");
                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#iyigeceler
        api.addMessageCreateListener(event ->   {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#iyigeceler")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        String messageLink = event.getMessageContent().substring(12).trim();
                        Videos.setIyiGecelerVideo(messageLink);
                        event.getMessage().reply("İyi geceler linki değişti! "+Videos.getIyiGecelerVideo());
                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#günaydın
        api.addMessageCreateListener(event ->   {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#günaydın")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        String messageLink = event.getMessageContent().substring(10).trim();
                        Videos.setGunaydinVideo(messageLink);
                        event.getMessage().reply("Günaydın linki değişti! "+Videos.getGunaydinVideo());
                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#memeler
        api.addMessageCreateListener(event ->   {
            if (event.isPrivateMessage()&&
                    !event.getMessageAuthor().isBotUser()&&
                    event.getMessageContent().startsWith("#memeler")){
                if(Utils.isUserAdmin(event.getMessageAuthor().asUser().get())){
                    try {
                        String reply = "Listedeki tüm memeler\n===================\n";
                        List<String> memeler = CarpatesMemes.getMemes();
                        int start = 0;
                        int end = 9;
                        boolean finish = false;
                        while (!finish){
                            for (int i = start; i<=end;i++){
                                reply += memeler.get(i)+"\n";
                            }
                            event.getMessage().reply(reply);
                            reply = "";
                            start += 10;
                            if (end==memeler.size()-1){
                                finish=true;
                            }
                            if (end+10>=memeler.size()){
                                end=memeler.size()-1;
                            }else{
                                end += 10;
                            }
                        }

                    } catch (Exception e) {
                        event.getMessage().reply("Bir hata oluştu!");
                        e.printStackTrace();
                    }
                }else {
                    event.getChannel().sendMessage("Senle konuşma iznim yok!");
                }
            }
        });

        //#carpateskod
        api.addMessageCreateListener(event -> {
            String message = event.getMessageContent().trim();
            if (!event.getMessageAuthor().isBotUser()&&CarpatesGithub.getCarpatesGithubKod().contains(message)){
                if (CarpatesGithub.isNextUse()){
                    event.getMessage().reply(CarpatesGithub.githubLink());
                    CarpatesGithub.setNextUse();
                }
            }
        });

        //gityat
        api.addMessageCreateListener(event -> {
            if (!event.getMessageAuthor().isBotUser()){
                    if(GitYat.isBetweenGitYatTime()&&GitYat.isNextUse()&&event.getChannel().equals(genelSohbet)){
                        try {
                            event.getMessage().reply(GitYat.getGitYatMessage());
                            GitYat.setNextUse();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
            }
        });



    }
}
