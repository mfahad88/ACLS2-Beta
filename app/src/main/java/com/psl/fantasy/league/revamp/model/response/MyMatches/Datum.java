
package com.psl.fantasy.league.revamp.model.response.MyMatches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("match_id")
    @Expose
    private Integer matchId;
    @SerializedName("team_id1")
    @Expose
    private Integer teamId1;
    @SerializedName("team_name1")
    @Expose
    private String teamName1;
    @SerializedName("team_id2")
    @Expose
    private Integer teamId2;
    @SerializedName("team_name2")
    @Expose
    private String teamName2;
    @SerializedName("match_sts")
    @Expose
    private String matchSts;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getTeamId1() {
        return teamId1;
    }

    public void setTeamId1(Integer teamId1) {
        this.teamId1 = teamId1;
    }

    public String getTeamName1() {
        return teamName1;
    }

    public void setTeamName1(String teamName1) {
        this.teamName1 = teamName1;
    }

    public Integer getTeamId2() {
        return teamId2;
    }

    public void setTeamId2(Integer teamId2) {
        this.teamId2 = teamId2;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public void setTeamName2(String teamName2) {
        this.teamName2 = teamName2;
    }

    public String getMatchSts() {
        return matchSts;
    }

    public void setMatchSts(String matchSts) {
        this.matchSts = matchSts;
    }

}
