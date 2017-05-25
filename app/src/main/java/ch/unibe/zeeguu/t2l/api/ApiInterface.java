package ch.unibe.zeeguu.t2l.api;

import java.util.Arrays;

/**
 * Created by LeveX on 19/05/2017.
 */

public class ApiInterface {
    public final static String URL_LIST_LANGUAGES = "https://zeeguu.unibe.ch/api/available_languages";

    public static void setLanguages(String list) {
        list.replace("[", "");
        list.replace("]", "");
        list.replace("\"", "");


        String[] languages = list.split(",");


        System.out.println("Got languages: " + list);
    }
}
