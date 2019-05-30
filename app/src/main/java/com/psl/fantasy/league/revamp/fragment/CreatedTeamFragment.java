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
import com.psl.fantasy.league.revamp.adapter.PlayerAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.PlayerInfo.Datum;
import com.psl.fantasy.league.revamp.model.response.PlayerInfo.PlayerInfoResponse;
import com.psl.fantasy.league.revamp.model.ui.PlayerInfoBean;

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
public class CreatedTeamFragment extends Fragment {

    int teamId;
    public CreatedTeamFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_created_team, container, false);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        ListView list_player=mView.findViewById(R.id.list_player);
        List<PlayerInfoBean> list=new ArrayList<>();
        if(getArguments()!=null){
            teamId=getArguments().getInt("teamId");
        }
        JSONObject object=new JSONObject();
        try {
            object.put("team_id",teamId);
            ApiClient.getInstance().getTeamPlayerInfoByTeamId(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<PlayerInfoResponse>() {
                        @Override
                        public void onResponse(Call<PlayerInfoResponse> call, Response<PlayerInfoResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            if(response.isSuccessful()){
                                list_player.setVisibility(View.VISIBLE);
                                if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                    /*for(Datum datum:response.body().getData()){
                                        list.add(new PlayerInfoBean(datum.getPlayerId().intValue(),datum.getPlayer_name(),String.valueOf(datum.getPlayerPoint()),Integer.parseInt(datum.getIsCaptan()),Integer.parseInt(datum.getIsViceCaptan())));
                                    }*/
                                    PlayerAdapter adapter=new PlayerAdapter(mView.getContext(),R.layout.player_adapter,response.body().getData());
                                    list_player.setAdapter(adapter);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PlayerInfoResponse> call, Throwable t) {
                            t.printStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                        }
                    });
        }catch (JSONException e){
            e.printStackTrace();
        }
        return mView;
    }

}
