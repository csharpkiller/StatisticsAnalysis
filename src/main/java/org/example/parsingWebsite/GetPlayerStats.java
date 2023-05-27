package org.example.parsingWebsite;

import org.example.dto.MatchDto;
import org.example.dto.PlayerMatchStatsDto;
import org.example.statisticsAnalysis.PlayerStats;

import java.util.ArrayList;
import java.util.List;

public class GetPlayerStats {
    /**
     * Выбирает из playerMatchStatsDto нужную статистику и определяет выиграл ли игрок.
     * @param playerMatchStatsDto
     * @param matchDtos
     * @return
     */
    protected PlayerStats getPlayerStatsAllRoles(PlayerMatchStatsDto playerMatchStatsDto, List<MatchDto> matchDtos){
        TeamWinner teamWinner;

        if(matchDtos.get(0).getScore() == matchDtos.get(1).getScore()){
            teamWinner = TeamWinner.DRAW;
        }else{
            if(matchDtos.get(0).getScore() > matchDtos.get(1).getScore()){
                teamWinner = TeamWinner.RED;
            }
            else {
                teamWinner = TeamWinner.BLU;
            }
        }
        boolean win;
        String playerTeam = playerMatchStatsDto.getTeam();
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

        return new PlayerStats(playerMatchStatsDto.getKills(), playerMatchStatsDto.getDeaths(), playerMatchStatsDto.getDmg(), win);
    }

    protected List<PlayerStats> getPlayerStatsAllRoles(List<PlayerMatchStatsDto> playerMatchStatsDtos, List<List<MatchDto>> matchDtosList){
        List<PlayerStats> result = new ArrayList<>();
        for(int i = 0; i < playerMatchStatsDtos.size(); i++){
            result.add(getPlayerStatsAllRoles(playerMatchStatsDtos.get(i), matchDtosList.get(i)));
        }
        return result;
    }
}
