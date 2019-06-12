package com.psl.fantasy.league.revamp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.JoinedContestAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.JoinedContest.Datum;
import com.psl.fantasy.league.revamp.model.response.JoinedContest.JoinedContestResponse;
import com.psl.fantasy.league.revamp.model.ui.JoinedContestBean;

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

        if(Helper.isConnectedToNetwork(getActivity())){
            try{
                JSONObject object=new JSONObject();
                object.put("user_id",userId);
                object.put("match_id",matchId);

                ApiClient.getInstance().getAllMatchesContestByUserIdTeamId(Helper.encrypt(object.toString())).enqueue(new Callback<JoinedContestResponse>() {
                    @Override
                    public void onResponse(Call<JoinedContestResponse> call, Response<JoinedContestResponse> response) {
                        if(response.isSuccessful()){
                            progressBar.setVisibility(View.GONE);
                            list_matches.setVisibility(View.VISIBLE);
                            if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                for (Datum datum : response.body().getData()) {
                                    list.add(new JoinedContestBean(datum.getContest_id(),userId,datum.getContestName(), datum.getDescription(), datum.getEntryFee(), datum.getTeamName(), datum.getTotalPoint()));
                                    JoinedContestAdapter adapter = new JoinedContestAdapter(mView.getContext(), R.layout.joined_contest_adapter, list);
                                    list_matches.setAdapter(adapter);
                                }
                            }else {
                                Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<JoinedContestResponse> call, Throwable t) {
                        call.cancel();
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
