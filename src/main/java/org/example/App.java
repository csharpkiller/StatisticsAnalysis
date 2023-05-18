package org.example;

import org.example.parsingWebsite.GetLogstfInfo;
import org.example.parsingWebsite.SteamId;
import org.example.statisticsAnalysis.PlayerStats;
import org.jsoup.nodes.Document;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GetLogstfInfo getLogstfInfo = new GetLogstfInfo();
        getLogstfInfo.getPlayerStatsAllHero("76561197987681768", 3);
    }
}
