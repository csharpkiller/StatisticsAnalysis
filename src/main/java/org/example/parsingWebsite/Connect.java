package org.example.parsingWebsite;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Connect {
    private int connectCount = 0;

    public Document getData (String query){
        Document jsonMatchesInfo;
        try {
            System.out.println("Connecting...");
            jsonMatchesInfo = Jsoup.connect(query).ignoreContentType(true).get();
        } catch (IOException e) {
            System.out.println("Error connecting");
            if(connectCount != 3){
                connectCount++;
                System.out.println("Reconnection, try number " + connectCount);
                getData(query);
            }
            return null;
        }
        return jsonMatchesInfo;
    }
}
