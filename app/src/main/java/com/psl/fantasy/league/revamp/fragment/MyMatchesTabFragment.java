package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.MyMatchesTabAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.MyMatchesTab.Datum;
import com.psl.fantasy.league.revamp.model.response.MyMatchesTab.MyMatchesTabResponse;
import com.psl.fantasy.league.revamp.model.ui.MyMatchesTabBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMatchesTabFragment extends Fragment {
    private int userId; private int contestId;

    public MyMatchesTabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_my_matches_tab, container, false);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        List<MyMatchesTabBean> list=new ArrayList<>();
        if(getArguments()!=null){
            userId=getArguments().getInt("userId");
            contestId=getArguments().getInt("contestId");
        }

       if(Helper.isConnectedToNetwork(getActivity())){
           try {
               JSONObject object=new JSONObject();
               object.put("user_id",userId);
               object.put("contest_id",contestId);
               ListView list_my_teams=mView.findViewById(R.id.list_my_teams);
               ApiClient.getInstance().getAllTeamsByUserId(Helper.encrypt(object.toString()))
                       .enqueue(new Callback<MyMatchesTabResponse>() {
                           @Override
                           public void onResponse(Call<MyMatchesTabResponse> call, Response<MyMatchesTabResponse> response) {
                               progressBar.setVisibility(View.GONE);
                               if(response.isSuccessful()){
                                   if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                       for(Datum datum:response.body().getData()) {
                                           list.add(new MyMatchesTabBean(datum.getMyUserTeamId(),datum.getTeamName(),String.valueOf(datum.getRemBudget()),String.valueOf(datum.getTotalPoint())));
                                       }
                                       list_my_teams.setVisibility(View.VISIBLE);
                                       MyMatchesTabAdapter adapter = new MyMatchesTabAdapter(mView.getContext(),R.layout.my_matches_tab_adapter,list);
                                       list_my_teams.setAdapter(adapter);
                                   }else {
                                       Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                   }
                               }
                           }

                           @Override
                           public void onFailure(Call<MyMatchesTabResponse> call, Throwable t) {
                               t.printStackTrace();
                               progressBar.setVisibility(View.GONE);
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
