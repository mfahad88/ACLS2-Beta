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
import com.psl.fantasy.league.model.ui.PrizesBean;

import java.util.List;

public class PrizesAdapter extends ArrayAdapter<PrizesBean> {
    Context context;
    int resource;
    List<PrizesBean> list;
    public PrizesAdapter( Context context, int resource, List<PrizesBean> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_prize_desc=convertView.findViewById(R.id.txt_prize_pool);
        TextView txt_prize_name=convertView.findViewById(R.id.txt_price);
        TextView txt_remaining=convertView.findViewById(R.id.txt_spots_left);
        ProgressBar progressBar=convertView.findViewById(R.id.progressBar);
        Button btn_pay=convertView.findViewById(R.id.btn_pay);
        PrizesBean bean=list.get(position);
        txt_prize_desc.setText(bean.getPrizeDesc());
        txt_prize_name.setText(bean.getPrizeName());
        txt_remaining.setText("Remaining: "+String.valueOf(bean.getConsumed())+"/"+String.valueOf(bean.getQuantity()));
        int percent=(bean.getConsumed()*bean.getQuantity())/100;
        progressBar.setProgress(percent);
        return convertView;
    }
}
