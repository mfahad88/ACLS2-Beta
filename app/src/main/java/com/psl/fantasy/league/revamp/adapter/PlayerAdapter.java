package com.psl.fantasy.league.revamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;

import com.psl.fantasy.league.revamp.model.response.PlayerInfo.Datum;
import com.psl.fantasy.league.revamp.model.ui.PlayerInfoBean;

import java.util.List;

public class PlayerAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<Datum> list;
    public PlayerAdapter(Context context, int resource, List<Datum> list) {
        super(context, resource, list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_team_name=convertView.findViewById(R.id.txt_team_name);
        TextView txt_credit=convertView.findViewById(R.id.txt_credit);
        /*TextView txt_captain=convertView.findViewById(R.id.txt_captain);
        TextView txt_vice_captain=convertView.findViewById(R.id.txt_vice_captain);*/
        TextView txt_earned_points=convertView.findViewById(R.id.txt_earned_points);
        Datum bean=list.get(position);
        txt_team_name.setText(bean.getPlayer_name());
        txt_credit.setText(bean.getPlayerPoint().toString());
        /*txt_captain.setText(String.valueOf(bean.getCaptain()));
        txt_vice_captain.setText(String.valueOf(bean.getVice_captain()));*/
        txt_earned_points.setText(bean.getUserPoint().toString());
        return convertView;
    }
}
