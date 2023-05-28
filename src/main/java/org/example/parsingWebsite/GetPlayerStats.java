package org.example.parsingWebsite;

import org.example.dto.PlayerMainStatistic;
import org.example.statisticsAnalysis.PlayerStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Дополняет и конвертирует статистику в нужный нам объект.
 */
public class GetPlayerStats {
    /**
     *  Определяет выиграл ли игрок в матче и возвращает нужную информацию для анализа.
     */
    protected PlayerStats getPlayerStatsAllRoles(PlayerMainStatistic playerMainStatistic){
        TeamWinner teamWinner;

        if(playerMainStatistic.getMatchInfo().get(0).getScore() == playerMainStatistic.getMatchInfo().get(1).getScore()){
            teamWinner = TeamWinner.DRAW;
        }else{
            if(playerMainStatistic.getMatchInfo().get(0).getScore() > playerMainStatistic.getMatchInfo().get(1).getScore()){
                teamWinner = TeamWinner.RED;
            }
            else {
                teamWinner = TeamWinner.BLU;
            }
        }

        // заменить на Enum.valueOf... TODO
        boolean win;
        String playerTeam = playerMainStatistic.getTeam();
        if(teamWinner.equals(TeamWinner.DRAW)){
            win = true;
        }
        else if(playerTeam.equals("Red") && teamWinner.equals(TeamWinner.RED)){
            win = true;
        }
        else if(playerTeam.equals("Blue") && teamWinner.equals(TeamWinner.BLU)){
            win = true;
        }
        else {
            win = false;
        }

        return new PlayerStats(playerMainStatistic.getKills(), playerMainStatistic.getDeaths(), playerMainStatistic.getDmg(), win);
    }

    /**
     *  Определяет выиграл ли игрок в матче и возвращает нужную информацию для анализа.
     */
    protected List<PlayerStats> getPlayerStatsAllRoles(List<PlayerMainStatistic> playerMainStatistics){
        List<PlayerStats> result = new ArrayList<>();
        for (PlayerMainStatistic playerMainStatistic : playerMainStatistics) {
            result.add(getPlayerStatsAllRoles(playerMainStatistic));
        }
        return result;
    }
}
