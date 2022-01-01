package com.upc.gzq.utils;

import java.util.Random;

public class KeyUtil {
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        //10000-99999
        Integer a = random.nextInt(90000)+10000;
        return System.currentTimeMillis()+String.valueOf(a);
    }
}
