package org.example.parsingWebsite;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import netscape.javascript.JSObject;
import org.example.dto.LogDto;
import org.example.dto.MatchDto;
import org.example.dto.PlayerMatchStatsDto;
import org.example.statisticsAnalysis.PlayerStats;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;

public class ParseJson {
    /**
     * Возвращает лист id матчей
     *
     * Поменять, должен возвращать Log DTO
     */
    protected List<Long> getMatchIdsList(Document jsonDoc){

        String jsonString = jsonDoc.body().text();
        JSONObject obj = new JSONObject(jsonString);
        JSONArray jsonArray =  (JSONArray) obj.get("logs");
        /*Object some = jsonArray.get(0);
        String map = some.toString();*/

        ObjectMapper objectMapper = new ObjectMapper();
        List<LogDto> logDtoList;
        try {
            logDtoList = objectMapper.readValue(jsonArray.toString(), (new TypeReference<List<LogDto>>() {
            }));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        List<Long> idList = new ArrayList<>();
        for (LogDto e: logDtoList
        ) {
            idList.add(e.getId());
        }
        return idList;
    }

    /**
     * Читает json матча и возвращает статистику игрока.
     *
     * ПОМЕНЯТЬ ДОЛЖЕН ВОЗВРАЩАТЬ PlayerMatchStatsDto + выразить MatchDto в другой метод
     */
    protected PlayerStats getStats(Document jsonMatch, String steamID){
        String jsonString = jsonMatch.body().text();
        JSONObject obj = new JSONObject(jsonString);
        JSONObject teamsInfo = (JSONObject) obj.get("teams");
        JSONObject playersInfo = (JSONObject) obj.get("players");
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

        String steamID3;
        SteamId steamId = new SteamId();
        steamID3 = steamId.convertSteamID64toSteamID3(steamID);
        JSONObject playerStatsJson = (JSONObject) playersInfo.get(steamID3);
        PlayerMatchStatsDto playerResults;
        String str = playerStatsJson.toString();

        try {
            playerResults = objectMapper.readValue(playerStatsJson.toString(), new TypeReference<PlayerMatchStatsDto>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    protected MatchDto getMatchInfo(Document jsonMatch){
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
    }

    protected List<PlayerStats> getStats(List<Document> jsonMaths, String steamID){
        List<PlayerStats> playerStats = new ArrayList<>();
        for (Document e: jsonMaths
             ) {
            playerStats.add(getStats(e, steamID));
        }
        return playerStats;
    }
}
