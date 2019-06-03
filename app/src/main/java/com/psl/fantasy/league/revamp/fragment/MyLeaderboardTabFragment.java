package com.psl.fantasy.league.revamp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.MyLeaderboardTabAdapter;
import com.psl.fantasy.league.revamp.adapter.MyMatchesTabAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.MyLeaderboardTab.Datum;
import com.psl.fantasy.league.revamp.model.response.MyLeaderboardTab.MyLeaderboardTabResponse;
import com.psl.fantasy.league.revamp.model.ui.MyLeaderboardTabBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyLeaderboardTabFragment extends Fragment {
    private int userId; private int contestId;

    public MyLeaderboardTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_my_leaderboard, container, false);
        ListView list_my_leaderboard=mView.findViewById(R.id.list_my_leaderboard);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        List<MyLeaderboardTabBean> list=new ArrayList<>();
        if(getArguments()!=null){
            userId=getArguments().getInt("userId");
            contestId=getArguments().getInt("contestId");
        }

        if(Helper.isConnectedToNetwork(getActivity())){
            try {
                JSONObject object=new JSONObject();
                object.put("user_id",userId);
                object.put("contest_id",contestId);
                ApiClient.getInstance().getAllContestTeamsByUserIdUnion(Helper.encrypt(object.toString()))
                        .enqueue(new Callback<MyLeaderboardTabResponse>() {
                            @Override
                            public void onResponse(Call<MyLeaderboardTabResponse> call, Response<MyLeaderboardTabResponse> response) {
                                progressBar.setVisibility(View.GONE);
                                if(response.isSuccessful()) {
                                    if (response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                        for (Datum datum : response.body().getData()) {
                                            list.add(new MyLeaderboardTabBean(datum.getMyUserTeamId(),datum.getUserId(), datum.getTeamName(), String.valueOf(datum.getTotalPoint()),datum.getPosition().intValue()));
                                        }
                                        if (list.size() > 0) {
                                            list_my_leaderboard.setVisibility(View.VISIBLE);
                                            MyLeaderboardTabAdapter adapter = new MyLeaderboardTabAdapter(mView.getContext(), R.layout.my_leaderboard_tab_adapter, list,userId);
                                            list_my_leaderboard.setAdapter(adapter);
                                        }
                                    } else {
                                        Helper.showAlertNetural(mView.getContext(), "Error", response.body().getMessage());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<MyLeaderboardTabResponse> call, Throwable t) {
                                t.printStackTrace();
                                Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                            }
                        });
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return mView;
    }

}
