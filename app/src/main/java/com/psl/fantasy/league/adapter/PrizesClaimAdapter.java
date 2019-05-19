package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.PrizeClaim.PrizeClaimResponse;
import com.psl.fantasy.league.model.ui.PrizesBean;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrizesClaimAdapter extends ArrayAdapter<PrizesBean> {
    Context context;
    int resource;
    List<PrizesBean> list;
    public PrizesClaimAdapter( Context context, int resource, List<PrizesBean> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_desc=convertView.findViewById(R.id.txt_desc);
        TextView txt_prize_name=convertView.findViewById(R.id.txt_price_name);
        TextView txt_price_amount=convertView.findViewById(R.id.txt_price_amount);
        TextView txt_remaining=convertView.findViewById(R.id.txt_remaining);
        ProgressBar progressBar=convertView.findViewById(R.id.progressBar);
        Button btn_claim=convertView.findViewById(R.id.btn_claim);
        PrizesBean bean=list.get(position);
        txt_desc.setText(bean.getPrizeDesc());
        txt_prize_name.setText(bean.getPrizeName());
        txt_price_amount.setText(bean.getAmount());
        txt_remaining.setText("Remaining: "+String.valueOf(bean.getConsumed())+"/"+String.valueOf(bean.getQuantity()));
        btn_claim.setText("Rs. "+bean.getCash());
        int percent=(bean.getConsumed()*bean.getQuantity())/100;
        progressBar.setProgress(percent);
        btn_claim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(v.getId()==R.id.btn_claim){
                    JSONObject object=new JSONObject();
                    try{
                        object.put("user_id",bean.getUserId());
                        object.put("prize_id",bean.getPrizeId());
                        ApiClient.getInstance().insertPrizeClaim(Helper.encrypt(object.toString()))
                                .enqueue(new Callback<PrizeClaimResponse>() {
                                    @Override
                                    public void onResponse(Call<PrizeClaimResponse> call, Response<PrizeClaimResponse> response) {
                                        if(response.isSuccessful()){
                                            if(response.body().getResponseCode().equals("1001")){
                                                Helper.showAlertNetural(context,"Success",response.body().getMessage());
                                            }else {
                                                Helper.showAlertNetural(context,"Error",response.body().getMessage());
                                            }
                                        }else{
                                            try {
                                                Helper.showAlertNetural(context,"Error",response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PrizeClaimResponse> call, Throwable t) {
                                        t.printStackTrace();
                                        Helper.showAlertNetural(context,"Error",t.getMessage());
                                    }
                                });
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
        return convertView;
    }


}
