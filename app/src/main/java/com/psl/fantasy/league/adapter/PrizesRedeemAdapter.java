package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.fragment.PrizesFragment;
import com.psl.fantasy.league.fragment.PrizesRedeemFragment;
import com.psl.fantasy.league.model.response.Redeem.GetRedeem;
import com.psl.fantasy.league.model.response.Redeem.TeamWiseRedeem;
import com.psl.fantasy.league.model.ui.RedeemBean;

import org.json.JSONObject;

import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrizesRedeemAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<RedeemBean> list;
    int user_id;
    public PrizesRedeemAdapter(Context context, int resource, List objects, int user_id) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
        this.user_id=user_id;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_team_name=convertView.findViewById(R.id.txt_team_name);
        TextView txt_match_name=convertView.findViewById(R.id.txt_match_name);
        TextView txt_earned_points=convertView.findViewById(R.id.txt_earned_points);
        Button btn_redeem=convertView.findViewById(R.id.btn_redeem);
        RedeemBean bean=list.get(position);
        txt_match_name.setText(bean.getMatchName());
        txt_team_name.setText(bean.getTeamName());
        txt_earned_points.setText(bean.getEarnPoints());
        btn_redeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    JSONObject object=new JSONObject();
                    object.put("team_id",bean.getTeamId());
                    object.put("user_id",user_id);

                    ApiClient.getInstance().RedeemPointTeamWise(Helper.encrypt(object.toString()))
                            .enqueue(new Callback<TeamWiseRedeem>() {
                                @Override
                                public void onResponse(Call<TeamWiseRedeem> call, Response<TeamWiseRedeem> response) {
                                    if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Helper.showAlertNetural(context,"Success",response.body().getMessage());

                                            }
                                        },1000);
                                        AppCompatActivity activity=(AppCompatActivity)context;
                                        FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
                                        ft.replace(R.id.main_content,new PrizesRedeemFragment());
                                        ft.commit();
                                    }else{
                                        Helper.showAlertNetural(context,"Error",response.body().getMessage());
                                    }
                                }

                                @Override
                                public void onFailure(Call<TeamWiseRedeem> call, Throwable t) {
                                    t.printStackTrace();
                                    Helper.showAlertNetural(context,"Error",t.getMessage());
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        return convertView;
    }
}
