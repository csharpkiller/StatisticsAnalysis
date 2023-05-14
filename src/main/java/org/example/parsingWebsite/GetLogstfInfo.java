package org.example.parsingWebsite;

import org.example.statisticsAnalysis.HeroClass;
import org.example.statisticsAnalysis.PlayerStats;

import java.util.List;

public class GetLogstfInfo {
    public GetLogstfInfo(IgnoreTags ignoreTags){
        // TODO
    }

    public GetLogstfInfo(){

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
        //TODO
        return null;
    }

    /**
     * Добавить игнорируемый тэг
     * @param ignoreTags
     */
    public void addIgnoreTag(IgnoreTags ignoreTags){
        //TODO
    }
}
