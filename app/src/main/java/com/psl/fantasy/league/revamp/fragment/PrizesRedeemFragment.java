package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.PrizesRedeemAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.Redeem.Datum;
import com.psl.fantasy.league.revamp.model.response.Redeem.GetRedeem;
import com.psl.fantasy.league.revamp.model.ui.RedeemBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrizesRedeemFragment extends Fragment {


    public PrizesRedeemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_prizes_redeem, container, false);
        int user_id;
        ListView list_redeem_prizes=view.findViewById(R.id.list_redeem_prizes);
        SharedPreferences preferences=view.getContext().getSharedPreferences(Helper.MY_USER,Context.MODE_PRIVATE);
        List<RedeemBean> list=new ArrayList<>();

        if(Helper.getUserSession(preferences,Helper.MY_USER)!=null){
            try{
                JSONObject object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");

                /////Request//////////
                if(Helper.isConnectedToNetwork(getActivity())){
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("user_id",user_id);
                    ApiClient.getInstance().getAllRedeemTeams(Helper.encrypt(jsonObject.toString()))
                            .enqueue(new Callback<GetRedeem>() {
                                @Override
                                public void onResponse(Call<GetRedeem> call, Response<GetRedeem> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                            for(Datum datum:response.body().getData()){
                                                list.add(new RedeemBean(datum.getContestId(),datum.getTeamName(),datum.getContestName(),datum.getTotal_point(),datum.getMyUserTeamId()));
                                                PrizesRedeemAdapter adapter=new PrizesRedeemAdapter(view.getContext(),R.layout.adapter_redeem_prizes,list,user_id);
                                                list_redeem_prizes.setAdapter(adapter);
                                            }
                                        }else{
                                            Helper.showAlertNetural(view.getContext(),"Error",response.body().getMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<GetRedeem> call, Throwable t) {
                                    t.printStackTrace();
                                    Helper.showAlertNetural(view.getContext(),"Error","Communication Error");
                                }
                            });
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return view;
    }

}
