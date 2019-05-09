package com.psl.fantasy.league.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.PrizesAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Prizes.Datum;
import com.psl.fantasy.league.model.response.Prizes.PrizesResponse;
import com.psl.fantasy.league.model.ui.PrizesBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPrizes extends Fragment {
    int userId;
    public FragmentPrizes() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.fragment_prizes, container, false);
        ListView list_prizes=mView.findViewById(R.id.list_prizes);
        List<PrizesBean> list=new ArrayList<>();
        SharedPreferences preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);

        if(Helper.getUserSession(preferences,"MyUser")==null) {
            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"ACL","user.txt");
            if(file.exists()) {
                if (Helper.getUserIdFromText() != null) {

                    try {
                        userId= Integer.parseInt(Helper.getUserIdFromText());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }else{
            try{
                JSONObject jsonObject = new JSONObject(Helper.getUserSession(preferences, "MyUser").toString());
                userId=jsonObject.getInt("user_id");
            }catch (JSONException e){
                e.printStackTrace();
            }
        }


        try{
            ApiClient.getInstance().AllPrizes()
                    .enqueue(new Callback<PrizesResponse>() {
                        @Override
                        public void onResponse(Call<PrizesResponse> call, Response<PrizesResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                    for(Datum datum:response.body().getData()) {
                                        list.add(new PrizesBean(datum.getPrizeId(),userId,datum.getPrizeCatId(),datum.getName(),datum.getDescription(),datum.getPoints(),datum.getQuantity(),datum.getConsume(),datum.getAmt()));
                                    }
                                    if(list.size()>0){
                                        PrizesAdapter adapter=new PrizesAdapter(mView.getContext(),R.layout.prizes_adapter,list);
                                        list_prizes.setAdapter(adapter);
                                    }
                                }else{
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                }
                            }else{
                                try {
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<PrizesResponse> call, Throwable t) {
                            t.printStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }
}
