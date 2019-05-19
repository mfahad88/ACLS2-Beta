package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.model.ui.RedeemBean;

import java.util.List;

public class PrizesRedeemAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<RedeemBean> list;
    public PrizesRedeemAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_team_name=convertView.findViewById(R.id.txt_team_name);
        TextView txt_match_name=convertView.findViewById(R.id.txt_match_name);
        TextView txt_earned_points=convertView.findViewById(R.id.txt_earned_points);
        Button btn_claim=convertView.findViewById(R.id.btn_claim);
        RedeemBean bean=list.get(position);
        txt_match_name.setText(bean.getMatchName());
        txt_team_name.setText(bean.getTeamName());
        txt_earned_points.setText(bean.getEarnPoints());
        return convertView;
    }
}
