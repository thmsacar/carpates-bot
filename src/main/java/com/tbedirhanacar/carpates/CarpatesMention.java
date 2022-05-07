package com.tbedirhanacar.carpates;

import java.time.LocalDateTime;

public class CarpatesMention {

    private static String carpatesMention = "<@969989588296822867>";

    private static String carpatesMentionMessage = "Carpates'in kodları <@363757842676580352> tarafından özel olarak yazıldı. Daha fazla bilgi için '#carpateshelp' komutunu kullanın.";

    private static LocalDateTime nextUse = LocalDateTime.now();

    private static LocalDateTime getNextUse() {
        return nextUse;
    }

    public static void setNextUse() {
        nextUse = LocalDateTime.now().plusMinutes(1);
    }

    public static boolean isNextUse() {
        return LocalDateTime.now().isAfter(getNextUse());
    }

    public static String getCarpatesMention() {
        return carpatesMention;
    }

    public static String getCarpatesMentionMessage() {
        return carpatesMentionMessage;
    }
}
