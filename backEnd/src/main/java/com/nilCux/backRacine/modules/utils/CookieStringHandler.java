package com.nilCux.backRacine.modules.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
@Slf4j
public class CookieStringHandler {
    public static HashMap<String, String> parseCookie(String rawCookieString) throws Exception{
        if(rawCookieString == null || rawCookieString.length() == 0)
            return null;
        HashMap<String, String> target = new HashMap<>();
        log.info(rawCookieString);
        String[] rawCookieArray = rawCookieString.split(";");
        for (String element: rawCookieArray) {
            String[] pair = element.split("=");
            if (pair.length != 2)
                throw new Exception("'=' Symbol appeared in cookie content");
            target.put(pair[0].trim(), pair[1].trim());
        }
        return target;
    }
}
