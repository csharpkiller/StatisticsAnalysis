package org.example.statisticsAnalysis;

import java.util.Objects;

public class PlayerStats {
    private int kills;
    private int deaths;
    private boolean win;
    private int damage;
    /**
     * Результаты одного матча.
     */
    public PlayerStats(int kills, int deaths, int damage, boolean win){
        this.kills = kills;
        this.deaths = deaths;
        this.damage = damage;
        this.win = win;
    }

    /**
     * Использовать после анализа статистики.
     */
    /*public PlayerStats(double averageKD, double averageDamage,
                       int matchCount, double winPercent,
                       double bestKD, double worstKD){
        //TODO
    }*/

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public boolean isWin() {
        return win;
    }

    public int getDamage(){
        return damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStats that = (PlayerStats) o;
        return kills == that.kills && deaths == that.deaths && win == that.win && damage == that.damage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(kills, deaths, win, damage);
    }
}
