package utils;

import org.jsoup.Jsoup;

/**
 * Created by akorovin on 22.02.2017.
 */
public final class SlideHelper {
    public final static String htmlToText(String html) {
        return Jsoup.parse(html).text();
    }

    public static void main(String[] args) {
        String vikings = "";
        String hanukkah = "";
        String greenhouse = "";
        String shakespear = "";
        String soccer = "";
        // TODO: convert html to string
    }
}
