package org.example.parsingWebsite;

import java.util.ArrayList;
import java.util.List;

public class CreateQueryLink {
    /*
    Log data API
Access raw JSON data of logs: http://logs.tf/api/v1/log/<log_id> OR http://logs.tf/json/<log_id>.

Log search API
Access JSON list of logs: http://logs.tf/api/v1/log?title=X&uploader=Y&player=Z&limit=N&offset=N

Any of these fields can be combined together or omitted.

Results are always ordered by log id descending.

Example of comma-separated SteamIDs: 76561197987681768,76561197996199110

title	Optional Title text search (min. 2 characters)
map	Optional Exact name of a map
uploader	Optional Uploader SteamID64
player	Optional One or more player SteamID64 values, comma-separated
limit	Optional Limit results (default 1000, maximum 10000)
offset	Optional Offset results (default 0)
     */
    private final String basePlayerGamesLink = "http://logs.tf/api/v1/log?";
    private final String baseGameLink = "http://logs.tf/json/";
    private final int maxLimit = 10000;

    /**
     * Возвращает ссылку для запроса последних игр игрока.
     * @param steamID
     * @return
     */
    public String getPlayerGames(String steamID){
        StringBuffer stringBuffer = new StringBuffer(basePlayerGamesLink);
        stringBuffer.append("player=");
        stringBuffer.append(steamID);
        stringBuffer.append("&limit=");
        stringBuffer.append(maxLimit/5);
        return stringBuffer.toString();
    }

    /**
     * Возвращает ссылку для запроса конкретной игры.
     * @param matchId
     * @return
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
