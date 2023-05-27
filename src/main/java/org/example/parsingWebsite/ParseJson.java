package org.example.parsingWebsite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dto.LogDto;
import org.example.dto.MatchDto;
import org.example.dto.PlayerClassStatsDto;
import org.example.dto.PlayerMatchStatsDto;
import org.example.statisticsAnalysis.HeroClass;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParseJson {

    /**
     * Парсит jsonDoc по тегу "logs" и возвращает LogDto Obj
     * @param jsonDoc
     * @return
     */
    protected List<LogDto> getMatchIdsList(Document jsonDoc){

        String jsonString = jsonDoc.body().text();
        JSONObject obj = new JSONObject(jsonString);
        JSONArray jsonArray =  (JSONArray) obj.get("logs");

        ObjectMapper objectMapper = new ObjectMapper();
        List<LogDto> logDtoList;
        try {
            logDtoList = objectMapper.readValue(jsonArray.toString(), (new TypeReference<List<LogDto>>() {
            }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return logDtoList;
    }

    /**
     * Парсит jsonDoc по тегу "teams" и возвращает 2 MatchDto obj для команд Red и Blu
     * @param jsonMatch
     * @return
     */
    protected List<MatchDto> getMatchInfo(Document jsonMatch){
        String jsonString = jsonMatch.body().text();
        JSONObject obj = new JSONObject(jsonString);
        JSONObject teamsInfo = (JSONObject) obj.get("teams");
        Object redTeam = teamsInfo.get("Red");
        Object bluTeam = teamsInfo.get("Blue");

        ObjectMapper objectMapper = new ObjectMapper();
        MatchDto redTeamDto;
        MatchDto bluTeamDto;
        try {
            redTeamDto = objectMapper.readValue(redTeam.toString(), new TypeReference<MatchDto>() {
            });
            bluTeamDto = objectMapper.readValue(bluTeam.toString(), new TypeReference<MatchDto>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return List.of(redTeamDto, bluTeamDto);
    }

    protected List<List<MatchDto>> getMatchInfo(List<Document> jsonMatchList){
        List<List<MatchDto>> result = new ArrayList<>();
        for (Document doc: jsonMatchList
             ) {
            result.add(getMatchInfo(doc));
        }
        return result;
    }


    /**
     * Парсит jsonDoc по тегу "players", находит игрока с id - steamID,
     * возвращает статистику игрока PlayerMatchStatsDto
     * @param jsonMatch
     * @param steamID
     * @return
     */
    protected PlayerMatchStatsDto getStats(Document jsonMatch, String steamID){
        String jsonString = jsonMatch.body().text();
        JSONObject obj = new JSONObject(jsonString);
        JSONObject playersInfo = (JSONObject) obj.get("players");

        ObjectMapper objectMapper = new ObjectMapper();


        String steamID3;
        ConvertorSteamID convertorSteamID = new ConvertorSteamID();
        steamID3 = convertorSteamID.convertSteamID64toSteamID3(steamID);
        JSONObject playerStatsJson = (JSONObject) playersInfo.get(steamID3);
        PlayerMatchStatsDto playerResults;
        try {
            playerResults = objectMapper.readValue(playerStatsJson.toString(), new TypeReference<PlayerMatchStatsDto>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return playerResults;
    }

    protected List<PlayerMatchStatsDto> getStats(List<Document> jsonMaths, String steamID){
        List<PlayerMatchStatsDto> playerStats = new ArrayList<>();
        for (Document e: jsonMaths
             ) {
            playerStats.add(getStats(e, steamID));
        }
        return playerStats;
    }

    /**
     * Возвращает данные PlayerClassStatsDto из PlayerMatchStatsDto class_stats
     * @param jsonMatch
     * @param steamID
     * @param heroClass
     * @param considerOffclass
     * @return
     */
    protected PlayerClassStatsDto getStats(Document jsonMatch, String steamID, HeroClass heroClass, boolean considerOffclass){
        ObjectMapper objectMapper = new ObjectMapper();
        PlayerClassStatsDto playerClassStatsDto;

        PlayerMatchStatsDto playerMatchStatsDto = getStats(jsonMatch, steamID);
        List<Map<String, Object>> classStats = (ArrayList<Map<String, Object>>) playerMatchStatsDto.getClass_stats();
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
                    Object classStatsObjectArray = classStats.get(index);
                    Map classStatsMap = (Map) classStatsObjectArray;
                    JSONObject jsonObject = new JSONObject(classStatsMap);
                    playerClassStatsDto = objectMapper.readValue(jsonObject.toString(), new TypeReference<PlayerClassStatsDto>() {
                    });
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return playerClassStatsDto;
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
                        playerClassStatsDto = objectMapper.readValue(new JSONObject(classStatMap).toString(), new TypeReference<PlayerClassStatsDto>() {
                        });
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    return playerClassStatsDto;
                }
            }
        }
        return null;
    }

    protected List<PlayerClassStatsDto> getStats(List<Document> jsonMatchs, String steamID, HeroClass heroClass, boolean considerOffclass){
        List<PlayerClassStatsDto> playerStats = new ArrayList<>();
        for (Document doc: jsonMatchs
             ) {
            PlayerClassStatsDto playerClassStatsDto = getStats(doc, steamID, heroClass, considerOffclass);
            if(playerClassStatsDto != null) {
                playerStats.add(getStats(doc, steamID, heroClass, considerOffclass));
            }
        }
        return playerStats;
    }
}
