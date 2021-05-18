package com.ciscu.SpotifyStats.utils;

import java.util.Map;

public class Utils {
    /**
     * Function that compares if the apikey is correct
     * @param headers header where the apikey comes from
     * @return true or false based on if it's right or not
     */
    public static boolean apiKey(Map<String, String> headers){
        boolean resultado = false;
        
        if(headers.get("apikey")!=null && headers.get("apikey").equals("c3BvdGlmeXN0YXRzYXBpa2V5")){
            resultado = true;
        }
        
        return resultado;
    }
}
