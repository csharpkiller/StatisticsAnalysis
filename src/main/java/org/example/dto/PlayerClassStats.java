package org.example.dto;

import java.util.List;

public class PlayerClassStats implements PlayerMainStatistic{
    private int kills;
    private Object weapon;
    private int assists;
    private String type;
    private int total_time;
    private int deaths;
    private int dmg;
    private List<Match> matchInfo;
    private String team;

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public Object getWeapon() {
        return weapon;
    }

    public void setWeapon(Object weapon) {
        this.weapon = weapon;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getTotal_time() {
        return total_time;
    }

    public void setTotal_time(int total_time) {
        this.total_time = total_time;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public int getDmg() {
        return dmg;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }

    @Override
    public List<Match> getMatchInfo() {
        return matchInfo;
    }

    public void setMatchInfo(List<Match> matchInfo) {
        this.matchInfo = matchInfo;
    }

    @Override
    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
