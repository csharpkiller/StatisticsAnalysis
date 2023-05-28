package org.example.parsingWebsite;

import java.util.ArrayList;
import java.util.List;

/**
 * Генерирует ссылки для запроса
 */
public class CreateQueryLink {
    private final String basePlayerGamesLink = "http://logs.tf/api/v1/log?";
    private final String baseGameLink = "http://logs.tf/json/";
    private final int maxLimit = 10000;

    /**
     * Возвращает ссылку для запроса последних игр игрока.
     * maxLimit - кол-во последних игр, задаем пока сами.
     */
    public String getPlayerGames(String steamID){
        // оставим буфер, про потоки еще нужно будет подумать
        StringBuffer stringBuffer = new StringBuffer(basePlayerGamesLink);
        stringBuffer.append("player=");
        stringBuffer.append(steamID);
        stringBuffer.append("&limit=");
        stringBuffer.append(maxLimit);
        return stringBuffer.toString();
    }

    /**
     * Возвращает ссылку для запроса конкретной игры.
     */
    public String getGame(long matchId){
        StringBuffer stringBuffer = new StringBuffer(baseGameLink);
        stringBuffer.append(matchId);
        return stringBuffer.toString();
    }

    public List<String> getGame(List<Long> matchIds){
        List<String> queryLinksMatchID = new ArrayList<>();
        for (long e: matchIds
             ) {
            queryLinksMatchID.add(getGame(e));
        }
        return queryLinksMatchID;
    }
}
