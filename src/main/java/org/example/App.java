package org.example;

import org.example.parsingWebsite.GetLogstfInfo;
import org.example.parsingWebsite.GetPlayerStats;
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
        List<PlayerStats> playerStatsList = getLogstfInfo.getPlayerStatsAllHero("76561198017212697", 10);
        System.out.println("DONE");
    }
}
