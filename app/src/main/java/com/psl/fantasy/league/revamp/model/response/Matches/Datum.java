package com.psl.fantasy.league.revamp.model.response.Matches;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


public class Datum {

    @SerializedName("match_id")
    @Expose
    private Integer matchId;
    @SerializedName("team_id1_name")
    @Expose
    private String teamId1Name;
    @SerializedName("team_id1")
    @Expose
    private Integer teamId1;
    @SerializedName("team_id2")
    @Expose
    private Integer teamId2;
    @SerializedName("team_id2_name")
    @Expose
    private String teamId2Name;
    @SerializedName("vernue")
    @Expose
    private String vernue;
    @SerializedName("start_date")
    @Expose
    private Date startDate;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("match_sts")
    @Expose
    private String matchSts;
    @SerializedName("contest_joining_mins")
    @Expose
    private Integer contestJoiningMins;
    @SerializedName("team_creation_mins")
    @Expose
    private Integer teamCreationMins;
    @SerializedName("team_id1_logo_path_enable")
    @Expose
    private String teamId1LogoPathEnable;
    @SerializedName("team_id1_logo_path_disble")
    @Expose
    private String teamId1LogoPathDisble;
    @SerializedName("team_id2_logo_path_enable")
    @Expose
    private String teamId2LogoPathEnable;
    @SerializedName("team_id2_logo_path_disble")
    @Expose
    private String teamId2LogoPathDisble;
    @SerializedName("team_id1_shortName")
    @Expose
    private String team_id1_shortName;
    @SerializedName("team_id2_shortName")
    @Expose
    private String team_id2_shortName;
    @SerializedName("series_name")
    @Expose
    private String series_name;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public String getTeamId1Name() {
        return teamId1Name;
    }

    public void setTeamId1Name(String teamId1Name) {
        this.teamId1Name = teamId1Name;
    }

    public Integer getTeamId1() {
        return teamId1;
    }

    public void setTeamId1(Integer teamId1) {
        this.teamId1 = teamId1;
    }

    public Integer getTeamId2() {
        return teamId2;
    }

    public void setTeamId2(Integer teamId2) {
        this.teamId2 = teamId2;
    }

    public String getTeamId2Name() {
        return teamId2Name;
    }

    public void setTeamId2Name(String teamId2Name) {
        this.teamId2Name = teamId2Name;
    }

    public String getVernue() {
        return vernue;
    }

    public void setVernue(String vernue) {
        this.vernue = vernue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getMatchSts() {
        return matchSts;
    }

    public void setMatchSts(String matchSts) {
        this.matchSts = matchSts;
    }

    public Integer getContestJoiningMins() {
        return contestJoiningMins;
    }

    public void setContestJoiningMins(Integer contestJoiningMins) {
        this.contestJoiningMins = contestJoiningMins;
    }

    public Integer getTeamCreationMins() {
        return teamCreationMins;
    }

    public void setTeamCreationMins(Integer teamCreationMins) {
        this.teamCreationMins = teamCreationMins;
    }

    public String getTeamId1LogoPathEnable() {
        return teamId1LogoPathEnable;
    }

    public void setTeamId1LogoPathEnable(String teamId1LogoPathEnable) {
        this.teamId1LogoPathEnable = teamId1LogoPathEnable;
    }

    public String getTeamId1LogoPathDisble() {
        return teamId1LogoPathDisble;
    }

    public void setTeamId1LogoPathDisble(String teamId1LogoPathDisble) {
        this.teamId1LogoPathDisble = teamId1LogoPathDisble;
    }

    public String getTeamId2LogoPathEnable() {
        return teamId2LogoPathEnable;
    }

    public void setTeamId2LogoPathEnable(String teamId2LogoPathEnable) {
        this.teamId2LogoPathEnable = teamId2LogoPathEnable;
    }

    public String getTeamId2LogoPathDisble() {
        return teamId2LogoPathDisble;
    }

    public void setTeamId2LogoPathDisble(String teamId2LogoPathDisble) {
        this.teamId2LogoPathDisble = teamId2LogoPathDisble;
    }

    public String getTeam_id1_shortName() {
        return team_id1_shortName;
    }

    public void setTeam_id1_shortName(String team_id1_shortName) {
        this.team_id1_shortName = team_id1_shortName;
    }

    public String getTeam_id2_shortName() {
        return team_id2_shortName;
    }

    public void setTeam_id2_shortName(String team_id2_shortName) {
        this.team_id2_shortName = team_id2_shortName;
    }

    public String getSeries_name() {
        return series_name;
    }

    public void setSeries_name(String series_name) {
        this.series_name = series_name;
    }
}