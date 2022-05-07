package com.tbedirhanacar.carpates;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class CarpatesHelp {

    private static EmbedBuilder embed = new EmbedBuilder()
            .setAuthor("Carpates",null,"https://cdn.discordapp.com/attachments/969320849209442334/969935335452737596/addon_uploads_1551561734_1740927513.jpg")
            .setFooter("Carpates bot Duty tarafından özel olarak tasarlandı", "https://cdn.discordapp.com/attachments/969320849209442334/970035473626046514/Ekran_Resmi_2022-03-12_03.58.30.png")
            .setTitle("Carpates bot komutları")
            .setDescription("Carpates botun tek işlevi '#carpatesmeme' yazdığınızda miim, '#carpatesflood' yazdığınızda flood atmaktır. Ayrıca bazı özel ifadelere cevap verebilir. " +
                    "Başka da hiçbi sikime yaramamaktadır.\n--\n#carpateskod <- 😉")
            .setImage("https://media.discordapp.net/attachments/969320849209442334/970381648401170512/Screenshot_96.png");

    public static EmbedBuilder getEmbed() {
        return embed;
    }
}
