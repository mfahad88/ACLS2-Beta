package com.psl.fantasy.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.JoinedContestAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.JoinedContest.Datum;
import com.psl.fantasy.league.model.response.JoinedContest.JoinedContestResponse;
import com.psl.fantasy.league.model.ui.JoinedContestBean;

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
public class JoinedContestFragment extends Fragment {
    private int userId,matchId;

    public JoinedContestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_joined_contest, container, false);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        ListView list_matches=mView.findViewById(R.id.list_matches);
        List<JoinedContestBean> list=new ArrayList<>();
        if(getArguments()!=null) {
            userId = getArguments().getInt("userId");
            matchId = getArguments().getInt("matchId");
        }
        JSONObject object=new JSONObject();
        try{
            object.put("user_id",userId);
            object.put("match_id",matchId);
        }catch (JSONException e){
            e.printStackTrace();
        }
        ApiClient.getInstance().getAllMatchesContestByUserIdTeamId(Helper.encrypt(object.toString())).enqueue(new Callback<JoinedContestResponse>() {
            @Override
            public void onResponse(Call<JoinedContestResponse> call, Response<JoinedContestResponse> response) {
                if(response.isSuccessful()){
                    progressBar.setVisibility(View.GONE);
                    list_matches.setVisibility(View.VISIBLE);
                    if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                        for (Datum datum : response.body().getData()) {
                            list.add(new JoinedContestBean(userId,datum.getContest_id(),datum.getContestName(), datum.getDescription(), datum.getEntryFee(), datum.getTeamName(), datum.getTotalPoint()));
                            JoinedContestAdapter adapter = new JoinedContestAdapter(mView.getContext(), R.layout.joined_contest_adapter, list);
                            list_matches.setAdapter(adapter);
                        }
                    }else {
                        Toast.makeText(mView.getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Toast.makeText(mView.getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JoinedContestResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(mView.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return mView;
    }

}
