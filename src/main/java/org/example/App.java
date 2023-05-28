package org.example;

import org.example.parsingWebsite.GetLogstfInfo;
import org.example.parsingWebsite.GetPlayerStats;
import org.example.parsingWebsite.IgnoreTags;
import org.example.statisticsAnalysis.HeroClass;
import org.example.statisticsAnalysis.PlayerStats;

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
        //getLogstfInfo.addIgnoreTag(IgnoreTags.PUGCHAMP);
        //List<PlayerStats> playerStatsList = getLogstfInfo.getPlayerStatsAllHero("76561197987681768", 3);
        List<PlayerStats> playerStatsList1 = getLogstfInfo.getPlayerStatsSpecialHero("76561197987681768", HeroClass.SCOUT, 3, false);
        System.out.println("done");
    }

}
