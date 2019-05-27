package com.psl.fantasy.league.revamp.model.ui;


import java.util.Date;

public class MatchesBean {
    private String MatchId;
    private String TeamOne;
    private String TeamTwo;
    private String shortName1;
    private String shortName2;
    private Date Time;
    private int team_id1;
    private int team_id2;
    private String txt_series;

    public MatchesBean(String matchId, String teamOne, String teamTwo, String shortName1, String shortName2, Date time, int team_id1, int team_id2, String txt_series) {
        MatchId = matchId;
        TeamOne = teamOne;
        TeamTwo = teamTwo;
        this.shortName1 = shortName1;
        this.shortName2 = shortName2;
        Time = time;
        this.team_id1 = team_id1;
        this.team_id2 = team_id2;
        this.txt_series = txt_series;
    }

    public String getMatchId() {
        return MatchId;
    }

    public void setMatchId(String matchId) {
        MatchId = matchId;
    }

    public String getTeamOne() {
        return TeamOne;
    }

    public void setTeamOne(String teamOne) {
        TeamOne = teamOne;
    }

    public String getTeamTwo() {
        return TeamTwo;
    }

    public void setTeamTwo(String teamTwo) {
        TeamTwo = teamTwo;
    }

    public String getShortName1() {
        return shortName1;
    }

    public void setShortName1(String shortName1) {
        this.shortName1 = shortName1;
    }

    public String getShortName2() {
        return shortName2;
    }

    public void setShortName2(String shortName2) {
        this.shortName2 = shortName2;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    public int getTeam_id1() {
        return team_id1;
    }

    public void setTeam_id1(int team_id1) {
        this.team_id1 = team_id1;
    }

    public int getTeam_id2() {
        return team_id2;
    }

    public void setTeam_id2(int team_id2) {
        this.team_id2 = team_id2;
    }

    public String getTxt_series() {
        return txt_series;
    }

    public void setTxt_series(String txt_series) {
        this.txt_series = txt_series;
    }

    @Override
    public String toString() {
        return "MatchesBean{" +
                "MatchId=" + MatchId +
                ", TeamOne='" + TeamOne + '\'' +
                ", TeamTwo='" + TeamTwo + '\'' +
                ", shortName1='" + shortName1 + '\'' +
                ", shortName2='" + shortName2 + '\'' +
                ", Time=" + Time +
                ", team_id1=" + team_id1 +
                ", team_id2=" + team_id2 +
                ", txt_series='" + txt_series + '\'' +
                '}';
    }
}
