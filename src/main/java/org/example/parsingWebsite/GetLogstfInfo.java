package org.example.parsingWebsite;

import org.example.dto.*;
import org.example.statisticsAnalysis.HeroClass;
import org.example.statisticsAnalysis.PlayerStats;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Интерфейс для пользователя.
 */
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

        ParseJson parseJson = new ParseJson();
        GetPlayerStats getPlayerStats = new GetPlayerStats();

        List<Document> jsonMatchInfoList = getMatchesInfo(steamID, matchesCount);
        if(jsonMatchInfoList == null){
            return null;
        }

        List<PlayerClassStats> playerClassStatsList = parseJson.getStats(jsonMatchInfoList, steamID, heroClass, offclass);
        List<PlayerMainStatistic> playerMainStatisticList = new ArrayList<>(playerClassStatsList);
        return getPlayerStats.getPlayerStatsAllRoles(playerMainStatisticList);
    }

    /**
     * Возвращает список результатов за последние matchesCount.
     */
    public List<PlayerStats> getPlayerStatsAllHero(String steamID, int matchesCount){
        ParseJson parseJson = new ParseJson();

        List<Document> jsonMatchInfoList = getMatchesInfo(steamID, matchesCount);
        if(jsonMatchInfoList == null){
            return null;
        }

        List<PlayerMatchStats> playerMatchStats = parseJson.getStats(jsonMatchInfoList, steamID);
        List<PlayerMainStatistic> playerMainStatistics = new ArrayList<>(playerMatchStats);

        GetPlayerStats getPlayerStats = new GetPlayerStats();
        return getPlayerStats.getPlayerStatsAllRoles(playerMainStatistics);
    }

    private List<Document> getMatchesInfo(String steamID, int matchesCount){
        CreateQueryLink createQueryLink = new CreateQueryLink();
        Connect connect = new Connect();
        ParseJson parseJson = new ParseJson();

        String queryLink = createQueryLink.getPlayerGames(steamID);
        Document jsonMatchesInfo;
        jsonMatchesInfo = connect.getData(queryLink);


        List<Log> logList = parseJson.getMatchIdsList(jsonMatchesInfo);
        if(logList.size() == 0){
            return null;
        }

        List<Long> matchIDs;
        if(ignoreTags.size() == 0){
            matchIDs = new ArrayList<>();

            for (Log log: logList
            ) {
                matchIDs.add(log.getId());
            }
        }else{
            matchIDs = getIdMatches(logList);
        }

        List<String> queryLinks = createQueryLink.getGame(matchIDs);

        List<Document> jsonMatchInfoList = new ArrayList<>();
        Document jsonMatchInfo;
        int logsLoses = 0;
        int matchCountCanChange = matchesCount;
        System.out.println("Looking for matches...");

        // TODO Перенести в Connect
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
        return jsonMatchInfoList;
    }

    /**
     * TODO
     * Выдает список матчей с учетом игнорируемых тегов.
     * ПЕРЕДЕЛАТЬ)))
     */
    private List<Long> getIdMatches(List<Log> logList){
        // TODO

        Map<Long, List<Integer>> dataEpoch = new HashMap<>();
        for(int i = 0; i < logList.size(); i++){
            long time = logList.get(i).getDate();
            time = (long)(time/1000);
            if(dataEpoch.containsKey(time)){
                dataEpoch.get(time).add(i);
            }
            else {
                dataEpoch.put(time, List.of(i));
            }
        }
        List<Integer> ignoreIndex = new ArrayList<>();
        for(int i = 0; i < logList.size(); i++){
            if(titleIsIgnoreTag(logList.get(i).getTitle())){
                long time = logList.get(i).getDate();
                time = (long)(time/1000);
                ignoreIndex.addAll(dataEpoch.get(time));
            }
        }

        List<Long> result = new ArrayList<>();
        for(int i = 0; i < logList.size(); i++){
            if(ignoreIndex.contains(i)){
                continue;
            }else {
                result.add(logList.get(i).getId());
            }
        }
        return result;
    }

    private boolean titleIsIgnoreTag(String title){
        String[] titleSplit = title.split(" ");
        IgnoreTags ignoreTag = IgnoreTags.valueOf(titleSplit[0].toUpperCase());
        return ignoreTags.contains(ignoreTag);
    }


    /**
     * TODO
     * Добавить игнорируемый тэг. Пока забросим.
     */
    public void addIgnoreTag(IgnoreTags ignoreTags){
        this.ignoreTags.add(ignoreTags);
    }
    public void addIgnoreTag(List<IgnoreTags> ignoreTags){
        this.ignoreTags.addAll(ignoreTags);
    }
}
