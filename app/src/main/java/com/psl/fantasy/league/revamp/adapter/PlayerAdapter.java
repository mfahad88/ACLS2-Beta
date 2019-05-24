package com.psl.fantasy.league.revamp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;

import com.psl.fantasy.league.revamp.model.ui.PlayerInfoBean;

import java.util.List;

public class PlayerAdapter extends ArrayAdapter<PlayerInfoBean> {
    Context context;
    int resource;
    List<PlayerInfoBean> list;
    public PlayerAdapter(Context context, int resource, List<PlayerInfoBean> list) {
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
        TextView txt_captain=convertView.findViewById(R.id.txt_captain);
        TextView txt_vice_captain=convertView.findViewById(R.id.txt_vice_captain);
        PlayerInfoBean bean=list.get(position);
        txt_team_name.setText(bean.getPlayerName());
        txt_credit.setText(bean.getCredit());
        txt_captain.setText(String.valueOf(bean.getCaptain()));
        txt_vice_captain.setText(String.valueOf(bean.getVice_captain()));
        return convertView;
    }
}
