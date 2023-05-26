package org.example.dto;

import java.util.List;

public class PlayerMatchStatsDto implements PlayerMainStatistic{
    private int kills;
    private int backstabs;
    private int headshots_hit;
    private int hr;
    private Object ubertypes;
    private Object class_stats;
    private int dmg_real;
    private int dt;
    private int drops;
    private int headshots;
    private int dapm;
    private int assists;
    private int lks;
    private int cpc;
    private int heal;
    private int ic;
    private Object medicstats;
    private int deaths;
    private double kpd;
    private int dapd;
    private int medkits_hp;
    private int ubers;
    private String team;
    private int as;
    private int suicides;
    private int sentries;
    private double kapd;
    private int dt_real;
    private int medkits;
    private int dmg;
    private List<MatchDto> matchInfo;

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getBackstabs() {
        return backstabs;
    }

    public void setBackstabs(int backstabs) {
        this.backstabs = backstabs;
    }

    public int getHeadshots_hit() {
        return headshots_hit;
    }

    public void setHeadshots_hit(int headshots_hit) {
        this.headshots_hit = headshots_hit;
    }

    public int getHr() {
        return hr;
    }

    public void setHr(int hr) {
        this.hr = hr;
    }

    public Object getUbertypes() {
        return ubertypes;
    }

    public void setUbertypes(Object ubertypes) {
        this.ubertypes = ubertypes;
    }

    public Object getClass_stats() {
        return class_stats;
    }

    public void setClass_stats(Object class_stats) {
        this.class_stats = class_stats;
    }

    public int getDmg_real() {
        return dmg_real;
    }

    public void setDmg_real(int dmg_real) {
        this.dmg_real = dmg_real;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public int getDrops() {
        return drops;
    }

    public void setDrops(int drops) {
        this.drops = drops;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    public int getDapm() {
        return dapm;
    }

    public void setDapm(int dapm) {
        this.dapm = dapm;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getLks() {
        return lks;
    }

    public void setLks(int lks) {
        this.lks = lks;
    }

    public int getCpc() {
        return cpc;
    }

    public void setCpc(int cpc) {
        this.cpc = cpc;
    }

    public int getHeal() {
        return heal;
    }

    public void setHeal(int heal) {
        this.heal = heal;
    }

    public int getIc() {
        return ic;
    }

    public void setIc(int ic) {
        this.ic = ic;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public double getKpd() {
        return kpd;
    }

    public void setKpd(double kpd) {
        this.kpd = kpd;
    }

    public int getDapd() {
        return dapd;
    }

    public void setDapd(int dapd) {
        this.dapd = dapd;
    }

    public int getMedkits_hp() {
        return medkits_hp;
    }

    public void setMedkits_hp(int medkits_hp) {
        this.medkits_hp = medkits_hp;
    }

    public int getUbers() {
        return ubers;
    }

    public void setUbers(int ubers) {
        this.ubers = ubers;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public int getAs() {
        return as;
    }

    public void setAs(int as) {
        this.as = as;
    }

    public int getSuicides() {
        return suicides;
    }

    public void setSuicides(int suicides) {
        this.suicides = suicides;
    }

    public int getSentries() {
        return sentries;
    }

    public void setSentries(int sentries) {
        this.sentries = sentries;
    }

    public double getKapd() {
        return kapd;
    }

    public void setKapd(double kapd) {
        this.kapd = kapd;
    }

    public int getDt_real() {
        return dt_real;
    }

    public void setDt_real(int dt_real) {
        this.dt_real = dt_real;
    }

    public int getMedkits() {
        return medkits;
    }

    public void setMedkits(int medkits) {
        this.medkits = medkits;
    }

    public int getDmg() {
        return dmg;
    }

    public void setMatchInfo(List<MatchDto> matchInfo) {
        this.matchInfo = matchInfo;
    }

    public Object getMedicstats() {
        return medicstats;
    }

    public void setMedicstats(Object medicstats) {
        this.medicstats = medicstats;
    }

    public List<MatchDto> getMatchInfo() {
        return matchInfo;
    }

    public void setDmg(int dmg) {
        this.dmg = dmg;
    }
}
