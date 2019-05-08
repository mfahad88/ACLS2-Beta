package com.psl.fantasy.league.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.adapter.PrizesAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Prizes.Datum;
import com.psl.fantasy.league.model.response.Prizes.PrizesResponse;
import com.psl.fantasy.league.model.ui.PrizesBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentPrizes extends Fragment {

    public FragmentPrizes() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView=inflater.inflate(R.layout.fragment_prizes, container, false);
        ListView list_prizes=mView.findViewById(R.id.list_prizes);
        List<PrizesBean> list=new ArrayList<>();

        ApiClient.getInstance().AllPrizes()
                .enqueue(new Callback<PrizesResponse>() {
                    @Override
                    public void onResponse(Call<PrizesResponse> call, Response<PrizesResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                for(Datum datum:response.body().getData()) {
                                    list.add(new PrizesBean(datum.getName(),datum.getDescription(),datum.getQuantity(),datum.getConsume()));
                                }
                                if(list.size()>0){
                                    PrizesAdapter adapter=new PrizesAdapter(mView.getContext(),R.layout.prizes_adapter,list);
                                    list_prizes.setAdapter(adapter);
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PrizesResponse> call, Throwable t) {

                    }
                });
        return mView;
    }
}
