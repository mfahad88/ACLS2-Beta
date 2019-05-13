package com.psl.fantasy.league.model.ui;


import java.util.Date;

public class MatchesBean {
    private int MatchId;
    private String TeamOne;
    private String TeamTwo;
    private Date Time;
    private int team_id1;
    private int team_id2;

    public MatchesBean(int matchId, String teamOne, String teamTwo, Date time, int team_id1, int team_id2) {
        MatchId = matchId;
        TeamOne = teamOne;
        TeamTwo = teamTwo;
        Time = time;
        this.team_id1 = team_id1;
        this.team_id2 = team_id2;
    }

    public int getMatchId() {
        return MatchId;
    }

    public void setMatchId(int matchId) {
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

    @Override
    public String toString() {
        return "MatchesBean{" +
                "MatchId=" + MatchId +
                ", TeamOne='" + TeamOne + '\'' +
                ", TeamTwo='" + TeamTwo + '\'' +
                ", Time='" + Time + '\'' +
                ", team_id1=" + team_id1 +
                ", team_id2=" + team_id2 +
                '}';
    }
}
