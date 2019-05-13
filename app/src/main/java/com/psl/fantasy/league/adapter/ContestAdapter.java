package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.psl.fantasy.league.fragment.TeamFragment;
import com.psl.fantasy.league.model.ui.ContestBean;
import com.psl.fantasy.league.R;

import java.util.List;

public class ContestAdapter extends BaseAdapter {

    Context context; int resource; List<ContestBean> list; int teamId1; int teamId2;
    public ContestAdapter(Context context, int resource, List<ContestBean> list, int teamId1, int teamId2) {
        this.context=context;
        this.resource=resource;
        this.list=list;
        this.teamId1=teamId1;
        this.teamId2=teamId2;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        ContestBean bean=list.get(position);
        TextView txt_price=convertView.findViewById(R.id.txt_price);
        ProgressBar progressBar=convertView.findViewById(R.id.progressBar);
        TextView txt_spots_left=convertView.findViewById(R.id.txt_spots_left);
        TextView txt_discount=convertView.findViewById(R.id.txt_discount);
        TextView btn_pay=convertView.findViewById(R.id.btn_pay);
        TextView txt_winners=convertView.findViewById(R.id.txt_winners);
        TextView txt_contest_type_one=convertView.findViewById(R.id.txt_contest_type_one);
        TextView txt_confirm_winning=convertView.findViewById(R.id.txt_confirm_winning);
        TextView txt_multi=convertView.findViewById(R.id.txt_multi);
        TextView txt_pool=convertView.findViewById(R.id.txt_pool);
        if(Integer.parseInt(bean.getDiscount())>0){
            txt_discount.setVisibility(View.VISIBLE);
            StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
            txt_discount.setText(bean.getActual_price(),TextView.BufferType.SPANNABLE);
            Spannable spannable = (Spannable) txt_discount.getText();
            spannable.setSpan(strikethroughSpan, 0, txt_discount.getText().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            btn_pay.setText(bean.getDiscount());
        }else{
            btn_pay.setText(bean.getActual_price());
        }
        txt_pool.setText(bean.getSpots()+" Spots");
        progressBar.setProgress(bean.getProgress());
        txt_spots_left.setText(bean.getSpots_left()+" spots left");
        txt_price.setText(bean.getPrice());
        txt_winners.setText(bean.getWinners()+" Winners");
        txt_contest_type_one.setText(bean.getContest_type());
        if(bean.getMultiple().equalsIgnoreCase("1")) {
            txt_multi.setText("M");
        }else{
            txt_multi.setText("S");
        }
        if(bean.getConfirm_winning().equalsIgnoreCase("1")){
            txt_confirm_winning.setVisibility(View.VISIBLE);
            txt_confirm_winning.setText("C");
        }
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new TeamFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("contestId",bean.getContestId());
                bundle.putInt("teamId1",teamId1);
                bundle.putInt("teamId2",teamId2);
                fragment.setArguments(bundle);
                AppCompatActivity activity=(AppCompatActivity)context;
                FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
//                FragmentTransaction ft=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_content,fragment);
                ft.commit();
            }
        });
        return convertView;
    }
}
