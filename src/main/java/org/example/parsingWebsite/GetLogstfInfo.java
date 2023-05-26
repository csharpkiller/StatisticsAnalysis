package org.example.parsingWebsite;

import org.example.dto.*;
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
     * Возвращает список результатов за последние matchCount игр, играя за определенного HeroClass "персонажа".
     * offclass - во время игры можно менять персонажей, как правело это происходит краткосрочно: примерно 5 - 10 минут от
     * матча в длиной в 30 минут. Все вторичные персонажи, которые использовались называются offclass.
     * boolean offclass указывает учитывать ли этих "сторонних персонажей" для учета статистики.
     */
    public List<PlayerStats> getPlayerStatsSpecialHero(String steamID, HeroClass heroClass, int matchesCount, boolean offclass){
        CreateQueryLink createQueryLink = new CreateQueryLink();
        Connect connect = new Connect();
        ParseJson parseJson = new ParseJson();
        GetPlayerStats getPlayerStats = new GetPlayerStats();

        String matchesInfoQueryLink = createQueryLink.getPlayerGames(steamID);
        Document jsonMatchesInfo = connect.getData(matchesInfoQueryLink);
        List<Log> logList = parseJson.getMatchIdsList(jsonMatchesInfo);
        List<Long> matchIDs = new ArrayList<>();

        for (Log log: logList
             ) {
            matchIDs.add(log.getId());
        }

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

        List<PlayerClassStats> playerClassStatsList = parseJson.getStats(jsonMatchInfoList, steamID, heroClass, offclass);
        List<PlayerMainStatistic> playerMainStatisticList = new ArrayList<>(playerClassStatsList);
        return getPlayerStats.getPlayerStatsAllRoles(playerMainStatisticList);
    }

    /**
     * Возвращает список результатов за последние matchesCount.
     */
    public List<PlayerStats> getPlayerStatsAllHero(String steamID, int matchesCount){
        CreateQueryLink createQueryLink = new CreateQueryLink();
        Connect connect = new Connect();
        ParseJson parseJson = new ParseJson();

        String queryLink = createQueryLink.getPlayerGames(steamID);
        Document jsonMatchesInfo;
        jsonMatchesInfo = connect.getData(queryLink);


        List<Long> matchIDs = new ArrayList<>();
        List<Log> logList = parseJson.getMatchIdsList(jsonMatchesInfo);
        for (Log logs: logList
             ) {
            matchIDs.add(logs.getId());
        }
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

        List<PlayerMatchStats> playerMatchStats = parseJson.getStats(jsonMatchInfoList, steamID);
        List<PlayerMainStatistic> playerMainStatistics = new ArrayList<>(playerMatchStats);

        GetPlayerStats getPlayerStats = new GetPlayerStats();
        return getPlayerStats.getPlayerStatsAllRoles(playerMainStatistics);
    }


    /**
     * Добавить игнорируемый тэг
     */
    public void addIgnoreTag(IgnoreTags ignoreTags){
        this.ignoreTags.add(ignoreTags);
    }
    public void addIgnoreTag(List<IgnoreTags> ignoreTags){
        this.ignoreTags.addAll(ignoreTags);
    }
}
