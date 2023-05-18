package org.example.parsingWebsite;

import org.example.statisticsAnalysis.HeroClass;
import org.example.statisticsAnalysis.PlayerStats;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetLogstfInfo {
    private final List<IgnoreTags> ignoreTags;
    public GetLogstfInfo(List<IgnoreTags> ignoreTags){
        this.ignoreTags = ignoreTags;
    }

    public GetLogstfInfo(){
        this.ignoreTags = new ArrayList<>();
    }

    /**
     * Возвращает список результатов за последние matchesCount матчей для определенного персонажа heroClass
     */
    public List<PlayerStats> getPlayerStatsSpecialHero(String steamID, HeroClass heroClass, int matchesCount){
        // TODO
        return null;
    }

    /**
     * Возвращает список результатов за последние matchesCount
     */
    public List<PlayerStats> getPlayerStatsAllHero(String steamID, int matchesCount){
        CreateQueryLink createQueryLink = new CreateQueryLink();
        String queryLink = createQueryLink.getPlayerGames(steamID);
        /*Document jsonMatchesInfo;
        try {
            System.out.println("Connecting...");
            jsonMatchesInfo = Jsoup.connect(queryLink).ignoreContentType(true).get();
        } catch (IOException e) {
            System.out.println("Error connecting");
            return null;
        }*/
        Connect connect = new Connect();
        Document jsonMatchesInfo;
        jsonMatchesInfo = connect.getData(queryLink);

        ParseJson parseJson = new ParseJson();
        List<Long> matchIDs = parseJson.getMatchIdsList(jsonMatchesInfo);
        List<String> queryLinks = createQueryLink.getGame(matchIDs);

        List<Document> jsonMatchInfoList = new ArrayList<>();
        Document jsonMatchInfo;
        int logsLoses = 0;
        int matchCountCanChange = matchesCount;
        System.out.println("Looking for matches...");
        for(int i = 0; i < matchCountCanChange; i++){
            try {
                jsonMatchInfo = Jsoup.connect(queryLinks.get(i)).ignoreContentType(true).get();
                jsonMatchInfoList.add(jsonMatchInfo);
            } catch (IOException e) {
                logsLoses++;
                if(matchCountCanChange < 5000){
                    matchCountCanChange++;
                }
            }
        }
        System.out.println("Match loses: " + logsLoses);
        List<PlayerStats> playerStats = parseJson.getStats(jsonMatchInfoList, steamID);
        return null;
    }


    /**
     * Добавить игнорируемый тэг
     * @param ignoreTags
     */
    public void addIgnoreTag(IgnoreTags ignoreTags){
        this.ignoreTags.add(ignoreTags);
    }
    public void addIgnoreTag(List<IgnoreTags> ignoreTags){
        this.ignoreTags.addAll(ignoreTags);
    }
}
