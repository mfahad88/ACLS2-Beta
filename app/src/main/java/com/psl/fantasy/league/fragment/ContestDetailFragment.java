package com.psl.fantasy.league.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.ContestAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Contest.ContestResponse;
import com.psl.fantasy.league.model.response.Contest.Datum;
import com.psl.fantasy.league.model.ui.ContestBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContestDetailFragment extends Fragment {
    int match_id;
    int contest_type; //1 mega, 2 expert,3 beginner
    int TeamId1;int TeamId2;
    private List<ContestBean> list;
    private String teamOne,teamTwo;
    private ProgressBar progressBar;
    public ContestDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_contest_detail, container, false);
        ListView list_contest=mView.findViewById(R.id.list_contest);
        progressBar=mView.findViewById(R.id.progressBar);
        if(getArguments()!=null){
            match_id=getArguments().getInt("match_id");
            contest_type=getArguments().getInt("contest_type");
            TeamId1=getArguments().getInt("TeamId1");
            TeamId2=getArguments().getInt("TeamId2");
            teamOne=getArguments().getString("TeamOne");
            teamTwo=getArguments().getString("TeamTwo");
        }
        list=new ArrayList<>();

        try {
            progressBar.setVisibility(View.VISIBLE);
            JSONObject object=new JSONObject();
            object.put("match_id",match_id);
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            ApiClient.getInstance().getAllContest(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<ContestResponse>() {
                        @Override
                        public void onResponse(Call<ContestResponse> call, Response<ContestResponse> response) {
                            if(response.isSuccessful()) {
                                progressBar.setVisibility(View.GONE);
                                list_contest.setVisibility(View.VISIBLE);
                                if (response.body().getResponseCode().equalsIgnoreCase("1001")){
                                    for (Datum datum : response.body().getData()) {
//                                if(datum.getPoolConsumed()>0){
                                        if (datum.getContestType().equalsIgnoreCase(String.valueOf(contest_type))) {
                                            Log.e("ContestId--->", String.valueOf(datum.getContestId().intValue()));
                                            Log.e("PoolConsumed---->", String.valueOf(datum.getPoolConsumed().intValue()));
                                            Log.e("Pool---->",datum.getPool().toString());
                                            float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                            int percent = Math.round(perc);
                                            list.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                    , String.valueOf(datum.getPool()), datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));
                                            ContestAdapter adapter = new ContestAdapter(mView.getContext(), R.layout.list_contest, list, TeamId1, TeamId2, teamOne, teamTwo);
                                            list_contest.setAdapter(adapter);
                                        }
//                                }
                                    }
                                }else{
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ContestResponse> call, Throwable t) {
                            t.printStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mView;
    }

}
