package org.example.parsingWebsite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.Log;
import org.example.dto.Match;
import org.example.dto.PlayerClassStats;
import org.example.dto.PlayerMatchStats;
import org.example.statisticsAnalysis.HeroClass;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Переносит информацию из JSONObject (текста) в реальные объекты см package dto
 */
public class ParseJson {

    /**
     * Парсит jsonDoc по тегу "logs" и возвращает список объектов Log Obj.
     */
    protected List<Log> getMatchIdsList(Document jsonDoc){

        String jsonString = jsonDoc.body().text();
        JSONObject obj;

        // TODO
        /**
         * ПАДАЕТ ОШИБКА ДЛЯ МОЕГО STEAMID 76561198146466689 надо очень сильно вникнуть и пофиксить..
         */
        try {
            obj = new JSONObject(jsonString);
        }
        catch (Exception e){
            return null;
        }
        JSONArray jsonArray =  (JSONArray) obj.get("logs");

        ObjectMapper objectMapper = new ObjectMapper();
        List<Log> logList;
        try {
            logList = objectMapper.readValue(jsonArray.toString(), (new TypeReference<List<Log>>() {
            }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return logList;
    }

    /**
     * Парсит jsonDoc по тегу "teams" и возвращает 2 объекта Match.
     *  1 элемент листа информация команды Red
     *  2 элемент листа информация команды Blu
     *  Можно сделать отдельный класс по типу Pair для более очевидного вывода результата.
     */
    protected List<Match> getMatchInfo(Document jsonMatch){
        JSONObject obj;
        String jsonString = jsonMatch.body().text();
        try {
            obj = new JSONObject(jsonString);
        }catch (Exception e){
            return null;
        }
        JSONObject teamsInfo = (JSONObject) obj.get("teams");
        Object redTeam = teamsInfo.get("Red");
        Object bluTeam = teamsInfo.get("Blue");

        ObjectMapper objectMapper = new ObjectMapper();
        Match redTeamDto;
        Match bluTeamDto;
        try {
            redTeamDto = objectMapper.readValue(redTeam.toString(), new TypeReference<>() {
            });
            bluTeamDto = objectMapper.readValue(bluTeam.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return List.of(redTeamDto, bluTeamDto);
    }

    /**
     * Парсит список jsonDoc по тегу "teams" и возвращает список из 2 объекта Match.
     *  1 элемент листа информация команды Red
     *  2 элемент листа информация команды Blu
     *  Можно сделать отдельный класс по типу Pair для более очевидного вывода результата.
     */
    protected List<List<Match>> getMatchInfo(List<Document> jsonMatchList){
        List<List<Match>> result = new ArrayList<>();
        for (Document doc: jsonMatchList
             ) {
            result.add(getMatchInfo(doc));
        }
        return result;
    }


    /**
     * Парсит jsonDoc по тегу "players", находит игрока с id - steamID,
     * возвращает статистику игрока в объект PlayerMatchStats
     */
    protected PlayerMatchStats getStats(Document jsonMatch, String steamID){
        List<Match> match = getMatchInfo(jsonMatch);
        if(match == null){
            return null;
        }
        String jsonString = jsonMatch.body().text();
        JSONObject obj = new JSONObject(jsonString);
        JSONObject playersInfo = (JSONObject) obj.get("players");

        ObjectMapper objectMapper = new ObjectMapper();


        String steamID3;
        ConvertorSteamID convertorSteamID = new ConvertorSteamID();
        steamID3 = convertorSteamID.convertSteamID64toSteamID3(steamID);
        JSONObject playerStatsJson = (JSONObject) playersInfo.get(steamID3);
        PlayerMatchStats playerResults;
        try {
            playerResults = objectMapper.readValue(playerStatsJson.toString(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        playerResults.setMatchInfo(match);
        return playerResults;
    }

    /**
     * Парсит список jsonDoc по тегу "players", находит игрока с id - steamID,
     * возвращает статистику игрока списком объектов PlayerMatchStats
     */
    protected List<PlayerMatchStats> getStats(List<Document> jsonMaths, String steamID){
        List<PlayerMatchStats> playerStats = new ArrayList<>();
        for (Document e: jsonMaths
             ) {
            if(e !=null) {
                playerStats.add(getStats(e, steamID));
            }
        }
        return playerStats;
    }

    /**
     * PlayerMatchStats содержит Object class_stats.
     * Метод парсит jsonMatch по тегу "players", находит игрока с id - steamID,
     * после получает PlayerMatchStats и парсит поле class_stats в PlayerClassStats.
     */
    protected PlayerClassStats getStats(Document jsonMatch, String steamID, HeroClass heroClass, boolean considerOffclass){
        List<Match> match = getMatchInfo(jsonMatch);
        if(match == null){
            return null;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        PlayerClassStats playerClassStats;

        PlayerMatchStats playerMatchStats = getStats(jsonMatch, steamID);
        List<Map<String, Object>> classStats = (ArrayList<Map<String, Object>>) playerMatchStats.getClass_stats();
        if(!considerOffclass){
            int index = 0;
            int value = 0;
            for(int i = 0; i < classStats.size(); i++){
                Map totalTIme = (Map)classStats.get(i);
                int totalTime = (int)totalTIme.get("total_time");
                if(value < totalTime){
                    value = totalTime;
                    index = i;
                }
            }
            HeroClass mainClass = HeroClass.valueOf(((Map)classStats.get(index)).get("type").toString().toUpperCase());
            if(heroClass.equals(mainClass)){
                try {
                    Object classStatsObjectMap = classStats.get(index);
                    Map classStatsMap = (Map) classStatsObjectMap;
                    JSONObject jsonObject = new JSONObject(classStatsMap);
                    playerClassStats = objectMapper.readValue(jsonObject.toString(), new TypeReference<PlayerClassStats>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                playerClassStats.setTeam(playerMatchStats.getTeam());
                playerClassStats.setMatchInfo(match);
                return playerClassStats;
            }
            else{
                return null;
            }
        }
        else{
            for (Map classStatMap: classStats
                 ) {
                HeroClass heroClass1 = HeroClass.valueOf(classStatMap.get("type").toString().toUpperCase());
                if(heroClass1.equals(heroClass)){
                    try {
                        playerClassStats = objectMapper.readValue(new JSONObject(classStatMap).toString(), new TypeReference<PlayerClassStats>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    playerClassStats.setTeam(playerMatchStats.getTeam());
                    playerClassStats.setMatchInfo(match);
                    return playerClassStats;
                }
            }
        }
        return null;
    }

    /**
     * PlayerMatchStats содержит Object class_stats.
     * Метод парсит список jsonMatch по тегу "players", находит игрока с id - steamID,
     * после получает список PlayerMatchStats и парсит поле class_stats в список PlayerClassStats.
     */
    protected List<PlayerClassStats> getStats(List<Document> jsonMatches, String steamID, HeroClass heroClass, boolean considerOffclass){
        List<PlayerClassStats> playerStats = new ArrayList<>();
        for (Document doc: jsonMatches
             ) {
            PlayerClassStats playerClassStats = getStats(doc, steamID, heroClass, considerOffclass);
            if(playerClassStats != null) {
                playerStats.add(getStats(doc, steamID, heroClass, considerOffclass));
            }
        }
        return playerStats;
    }
}
