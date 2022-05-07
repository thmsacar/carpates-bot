package com.tbedirhanacar.carpates;

import org.javacord.api.entity.message.embed.EmbedBuilder;

public class AdminHelp {

    private static EmbedBuilder embed = new EmbedBuilder()
            .setAuthor("Carpates",null,"https://cdn.discordapp.com/attachments/969320849209442334/969935335452737596/addon_uploads_1551561734_1740927513.jpg")
            .setFooter("Carpates bot Duty tarafından özel olarak tasarlandı", "https://cdn.discordapp.com/attachments/969320849209442334/970035473626046514/Ekran_Resmi_2022-03-12_03.58.30.png")
            .setTitle("Carpates admin komutları")
            .setDescription("Bu komutlar sadece admin yetkisi olan kişiler tarafından botla DM üzerinden kullanılabilir")
            .addField("#mesaj","Mesaj komutunun kullanılışı aşağıdaki gibidir\n#mesaj {kanal_id} {mesaj}")
            .addField("#duyuru","Duyuru komutunun kullanılışı aşağıdaki gibidir\n#duyuru {kanal_id} {duyuru-mesaji}")
            .addField("#ekle", "Ekle komutunun kullanılışı aşağıdaki gibidir\n#ekle {linkler}\n=============\n!!!DİKKAT!!! Birden fazla link " +
                    "eklerken aralarına boşluk BIRAKMAYA azami özen gösteriniz!")
            .addField("#msil", "Meme sil komutunun kullanılışı aşağıdaki gibidir\n#msil {linkler}\n=============\n!!!DİKKAT!!! Birden fazla link " +
            "silerken aralarına boşluk BIRAKMAYA azami özen gösteriniz!")
            .addInlineField("#durum", "Durum komutunun kullanılışı aşağıdaki gibidir\n#durum {durum}")
            .addInlineField("{durum} kısmına yazılabilecekler", "●online\n○idle\n●dnd\n○invisible")
            .addField("#aktivite", "Aktivite komutunun kullanılışı aşağıdaki gibidir\n#aktivite {aktivite-tipi} {metin}")
            .addInlineField("{aktivite-tipi} kısmına yazılabilecekler", "0 = PLAYING\n1 = STREAMING\n3 = WATCHING\n5 = COMPETING")
            .addField("#servers","Bu komut ile botun bulunduğu sunucular görülebilir")
            .addField("#sil", "Sil komutunun kullanılışı aşağıdaki gibidir\n#sil {silinecek-mesaj-linki}")
            .addField("#iyigeceler", "Bu komut iyi geceler videosunu günceller\n#iyigeceler {link}")
            .addField("#günaydın", "Bu komut günaydın videosunu günceller\n#günayın {link}");


    public static EmbedBuilder getEmbed() {
        return embed;
    }
}
