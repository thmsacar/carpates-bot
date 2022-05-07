package com.tbedirhanacar.carpates;

import org.javacord.api.entity.user.User;

import java.util.HashMap;
import java.util.Map;

public class UserCooldown {

    public static Map<User,Long> userCooldown = new HashMap<>();

    private static long cooldownTime = 10000;

    public static void setNextUse(User user) {
        if (UserCooldown.userCooldown.containsKey(user)){
            userCooldown.replace(user, System.currentTimeMillis()+cooldownTime);
        }else{
            userCooldown.put(user, System.currentTimeMillis()+cooldownTime);
        }
    }


}
