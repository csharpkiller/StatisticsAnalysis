package org.example.parsingWebsite;

import junit.framework.TestCase;
import org.example.statisticsAnalysis.HeroClass;
import org.example.statisticsAnalysis.PlayerStats;
import org.junit.Test;

import java.util.List;

public class GetLogstfInfoTest extends TestCase {

    /**
     * SpecialHero, проверяем корректность полученных данных. Должны игнорироваться все результаты, на персонажах отличные от SCOUT
     */
    @Test
    public void testGetPlayerStatsSpecialHero() {
        PlayerStats game1 = new PlayerStats(28, 23, 8081, true);
        GetLogstfInfo getLogstfInfo = new GetLogstfInfo();
        List<PlayerStats> getStats = getLogstfInfo.getPlayerStatsSpecialHero("76561197987681768", HeroClass.SCOUT, 1, false);
        assertEquals(game1, getStats.get(0));
    }

    /**
     *  AllHero, проверяем корректность полученных данных
     */
    @Test
    public void correctData() {
        PlayerStats game1 = new PlayerStats(17,  14, 7541, true);
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
     * Проверка игнорирования тэга.
     */
    @Test
    public void ignoreTag(){
        PlayerStats game1 = new PlayerStats(21, 24, 7391, true);

        GetLogstfInfo getLogstfInfo = new GetLogstfInfo(List.of(IgnoreTags.PUGCHAMP));
        List<PlayerStats> getStats = getLogstfInfo.getPlayerStatsAllHero("76561197987681768", 1);
        assertEquals(game1, getStats.get(0));

        GetLogstfInfo getLogstfInfo1 = new GetLogstfInfo();
        getLogstfInfo1.addIgnoreTag(IgnoreTags.PUGCHAMP);
        List<PlayerStats> getStats1 = getLogstfInfo.getPlayerStatsAllHero("76561197987681768", 1);
        assertEquals(game1, getStats1.get(0));
    }
}