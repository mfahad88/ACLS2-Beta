package com.psl.fantasy.league.interfaces;

import com.psl.fantasy.league.model.response.Insert.InsertResponse;
import com.psl.fantasy.league.model.response.JoinContest.JoinContenstResponse;
import com.psl.fantasy.league.model.request.TestBeanRequest;
import com.psl.fantasy.league.model.response.Config.ConfigBeanResponse;
import com.psl.fantasy.league.model.response.Contest.ContestResponse;
import com.psl.fantasy.league.model.response.JoinedContest.JoinedContestResponse;
import com.psl.fantasy.league.model.response.Login.LoginResponse;
import com.psl.fantasy.league.model.response.Matches.MatchesResponse;
import com.psl.fantasy.league.model.response.MyLeaderboardTab.MyLeaderboardTabResponse;
import com.psl.fantasy.league.model.response.MyMatches.MyMatchesResponse;
import com.psl.fantasy.league.model.response.MyMatchesTab.MyMatchesTabResponse;
import com.psl.fantasy.league.model.response.Player.PlayerResponse;
import com.psl.fantasy.league.model.response.PlayerInfo.PlayerInfoResponse;
import com.psl.fantasy.league.model.response.PrizeClaim.PrizeClaimResponse;
import com.psl.fantasy.league.model.response.Prizes.PrizesResponse;
import com.psl.fantasy.league.model.ui.PlayerBean;
import com.psl.fantasy.league.model.ui.PlayerInfoBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("matches")
    Call<MatchesResponse> matches(@Body TestBeanRequest beanRequest);


    @POST("getAllContest")
    Call<ContestResponse> getAllContest(@Body TestBeanRequest beanRequest);

    @POST("getPlayersMatches")
    Call<PlayerResponse> getPlayersMatches();

    @POST("config")
    Call<ConfigBeanResponse> getConfig(@Body TestBeanRequest beanRequest);

    @POST("login")
    Call<LoginResponse> login(@Body TestBeanRequest beanRequest);

    @POST("JoinContest")
    Call<JoinContenstResponse> JoinContest(@Body TestBeanRequest beanRequest);

    @POST("insertUser")
    Call<InsertResponse> insertUser(@Body TestBeanRequest beanRequest);

    @POST("getAllMatchByUserId")
    Call<MyMatchesResponse> getAllMatchByUserId(@Body TestBeanRequest beanRequest);

    @POST("getAllMatchesContestByUserIdTeamId")
    Call<JoinedContestResponse> getAllMatchesContestByUserIdTeamId(@Body TestBeanRequest beanRequest);

    @POST("getAllTeamsByUserId")
    Call<MyMatchesTabResponse> getAllTeamsByUserId(@Body TestBeanRequest beanRequest);

    @POST("getAllContestTeamsByUserIdUnion")
    Call<MyLeaderboardTabResponse> getAllContestTeamsByUserIdUnion(@Body TestBeanRequest beanRequest);

    @POST("getTeamPlayerInfoByTeamId")
    Call<PlayerInfoResponse> getTeamPlayerInfoByTeamId(@Body TestBeanRequest beanRequest);

    @POST("AllPrizes")
    Call<PrizesResponse> AllPrizes();

    @POST("insertPrizeClaim")
    Call<PrizeClaimResponse> insertPrizeClaim(@Body TestBeanRequest beanRequest);
}
