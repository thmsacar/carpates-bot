package com.tbedirhanacar.carpates;

import org.javacord.api.entity.server.Server;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GoodNightMorningCooldown {

    public static Map<Server,LocalDateTime> serverCooldown = new HashMap<>();

    private static LocalDateTime getNextUse(Server server) {
        LocalDateTime serverNextUse = serverCooldown.get(server);
        if (serverNextUse == null){
            return LocalDateTime.now().minusMinutes(10);
        }
        return serverCooldown.get(server);
    }

    public static void setNextUse(Server server) {
        if (serverCooldown.containsKey(server)){
            serverCooldown.replace(server, LocalDateTime.now().plusMinutes(15));
        }else{
            serverCooldown.put(server, LocalDateTime.now().plusMinutes(15));
        }

    }
    public static boolean isNextUse(Server server) {
        return LocalDateTime.now().isAfter(getNextUse(server));
    }
}
