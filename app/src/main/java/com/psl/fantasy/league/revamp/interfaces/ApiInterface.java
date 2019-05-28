package com.psl.fantasy.league.revamp.interfaces;

import com.psl.fantasy.league.model.response.UserNotification.GetUserNotificationBean;
import com.psl.fantasy.league.revamp.model.response.AppVersion.AppVersionBean;
import com.psl.fantasy.league.revamp.model.response.AccountLinking.BankInfo;
import com.psl.fantasy.league.revamp.model.response.AccountLinking.LinkingBean;
import com.psl.fantasy.league.revamp.model.response.AccountLinking.OTPBean;
import com.psl.fantasy.league.revamp.model.response.Insert.InsertResponse;
import com.psl.fantasy.league.revamp.model.response.JoinContest.JoinContenstResponse;
import com.psl.fantasy.league.revamp.model.request.TestBeanRequest;
import com.psl.fantasy.league.revamp.model.response.Config.ConfigBeanResponse;
import com.psl.fantasy.league.revamp.model.response.Contest.ContestResponse;
import com.psl.fantasy.league.revamp.model.response.JoinedContest.JoinedContestResponse;
import com.psl.fantasy.league.revamp.model.response.Login.LoginResponse;
import com.psl.fantasy.league.revamp.model.response.Matches.MatchesResponse;
import com.psl.fantasy.league.revamp.model.response.MyLeaderboardTab.MyLeaderboardTabResponse;
import com.psl.fantasy.league.revamp.model.response.MyMatches.MyMatchesResponse;
import com.psl.fantasy.league.revamp.model.response.MyMatchesTab.MyMatchesTabResponse;
import com.psl.fantasy.league.revamp.model.response.Player.PlayerResponse;
import com.psl.fantasy.league.revamp.model.response.PlayerInfo.PlayerInfoResponse;
import com.psl.fantasy.league.revamp.model.response.PrizeClaim.PrizeClaimResponse;
import com.psl.fantasy.league.revamp.model.response.PrizeDistribution.PrizeDistributionBean;
import com.psl.fantasy.league.revamp.model.response.Prizes.PrizesResponse;
import com.psl.fantasy.league.revamp.model.response.Redeem.GetRedeem;
import com.psl.fantasy.league.revamp.model.response.Redeem.TeamWiseRedeem;
import com.psl.fantasy.league.revamp.model.response.SelectUser.SelectUserBean;
import com.psl.fantasy.league.revamp.model.response.SimPaisa.SimPaisaResponse;
import com.psl.fantasy.league.revamp.model.response.SimPaisaOTP.SimPaisaOTPResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiInterface {


    @POST("matches")
    Call<MatchesResponse> matches(@Body TestBeanRequest beanRequest);


    @POST("getAllContest")
    Call<ContestResponse> getAllContest(@Body TestBeanRequest beanRequest);

    @POST("getPlayersMatches")
    Call<PlayerResponse> getPlayersMatches(@Body TestBeanRequest beanRequest);

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

    @POST("makePaymentSimPaisa")
    Call<SimPaisaResponse> makePaymentSimPaisa(@Body TestBeanRequest beanRequest);

    @POST("verifyPaymentSimPaisa")
    Call<SimPaisaOTPResponse> verifyPaymentSimPaisa(@Body TestBeanRequest beanRequest);

    @POST("verifyAccount")
    Call<LinkingBean> verifyAccount(@Body TestBeanRequest beanRequest);

    @POST("verifyOtp")
    Call<OTPBean> verifyOtp(@Body TestBeanRequest beanRequest);

    @POST("updateBankTitle")
    Call<BankInfo> updateBankTitle(@Body TestBeanRequest beanRequest);

    @POST("getAllRedeemTeams")
    Call<GetRedeem> getAllRedeemTeams(@Body TestBeanRequest beanRequest);

    @POST("RedeemPointTeamWise")
    Call<TeamWiseRedeem> RedeemPointTeamWise(@Body TestBeanRequest beanRequest);

    @POST("SelectUser")
    Call<SelectUserBean> SelectUser(@Body TestBeanRequest beanRequest);

    @POST("getAllContestWinningDist")
    Call<PrizeDistributionBean> getAllContestWinningDist();

    @POST("updateAppVersion")
    Call<AppVersionBean> updateAppVersion(@Body TestBeanRequest beanRequest);

    @POST("getUserNotif")
    Call<GetUserNotificationBean> getUserNotif(@Body TestBeanRequest beanRequest);
}
