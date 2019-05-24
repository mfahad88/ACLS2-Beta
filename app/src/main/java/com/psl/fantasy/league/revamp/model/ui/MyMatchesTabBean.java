package com.psl.fantasy.league.revamp.model.ui;

public class MyMatchesTabBean {
    private int teamId;
    private String teamName;
    private String credit;
    private String point;

    public MyMatchesTabBean(int teamId, String teamName, String credit, String point) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.credit = credit;
        this.point = point;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public String getPoint() {
        return point;
    }

    public void setPoint(String point) {
        this.point = point;
    }

    @Override
    public String toString() {
        return "MyMatchesTabBean{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", credit='" + credit + '\'' +
                ", point='" + point + '\'' +
                '}';
    }
}
