package com.psl.fantasy.league.revamp.model.ui;

public class MyMatchesBean {
    private int userId;
    private int match_id;
    private int team1Id;
    private int team2Id;
    private String teamOne;
    private String teamTwo;
    private String matchStatus;
    private String numberOfContest;
    private String shortName1;
    private String shortName2;
    private String seriesName;

    public MyMatchesBean(int userId, int match_id, int team1Id, int team2Id, String teamOne, String teamTwo, String matchStatus, String numberOfContest, String shortName1, String shortName2, String seriesName) {
        this.userId = userId;
        this.match_id = match_id;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.teamOne = teamOne;
        this.teamTwo = teamTwo;
        this.matchStatus = matchStatus;
        this.numberOfContest = numberOfContest;
        this.shortName1 = shortName1;
        this.shortName2 = shortName2;
        this.seriesName = seriesName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getMatch_id() {
        return match_id;
    }

    public void setMatch_id(int match_id) {
        this.match_id = match_id;
    }

    public int getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(int team1Id) {
        this.team1Id = team1Id;
    }

    public int getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(int team2Id) {
        this.team2Id = team2Id;
    }

    public String getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(String teamOne) {
        this.teamOne = teamOne;
    }

    public String getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(String teamTwo) {
        this.teamTwo = teamTwo;
    }

    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    public String getNumberOfContest() {
        return numberOfContest;
    }

    public void setNumberOfContest(String numberOfContest) {
        this.numberOfContest = numberOfContest;
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

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }
}
