package org.example.parsingWebsite;

import junit.framework.TestCase;
import org.example.statisticsAnalysis.HeroClass;
import org.example.statisticsAnalysis.PlayerStats;
import org.junit.Test;

import java.util.List;

public class GetLogstfInfoTest extends TestCase {

    /**
     *  AllHero, проверяем корректность полученных данных
     */
    @Test
    public void testGetPlayerStatsAllHero() {
        PlayerStats game1 = new PlayerStats(17,  24, 7541, true);
        PlayerStats game2 = new PlayerStats(14,  22, 6389, false);
        PlayerStats game3 = new PlayerStats(28,  23, 8081, true);
        List<PlayerStats> correctStats = List.of(game1, game2, game3);

        GetLogstfInfo getLogstfInfo = new GetLogstfInfo();
        List<PlayerStats> getStats = getLogstfInfo.getPlayerStatsAllHero("76561197987681768", 3);
        for(int i = 0; i < 3; i++){
            assertEquals(correctStats.get(i), getStats.get(i));
        }
    }

    /**
     * Неверный steamId
     */
    @Test
    public void testWrongSteamId(){
        GetLogstfInfo getLogstfInfo = new GetLogstfInfo();
        List<PlayerStats> getStats = getLogstfInfo.getPlayerStatsAllHero("76", 3);
        List<PlayerStats> getStats1 = getLogstfInfo.getPlayerStatsSpecialHero("7651218", HeroClass.SCOUT, 5, false);
        assertNull(getStats);
        assertNull(getStats1);
    }

    /**
     * SpecialHero, проверяем корректность полученных данных. Должны игнорироваться все результаты, на персонажах отличные от SCOUT
     */
    @Test
    public void testGetPlayerStatsSpecialHero() {
        PlayerStats game1 = new PlayerStats(28, 23, 8081, true);
        GetLogstfInfo getLogstfInfo = new GetLogstfInfo();
        List<PlayerStats> getStats = getLogstfInfo.getPlayerStatsSpecialHero("76561197987681768", HeroClass.SCOUT, 5, false);
        assertEquals(game1, getStats.get(0));
    }

}