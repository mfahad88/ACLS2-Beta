package com.psl.fantasy.league.revamp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.model.ui.MyLeaderboardTabBean;

import java.util.List;

public class MyLeaderboardTabAdapter extends ArrayAdapter<MyLeaderboardTabBean> {
    Context context;
    int resource;
    List<MyLeaderboardTabBean> list;
    int userId;
    public MyLeaderboardTabAdapter(Context context, int resource, List<MyLeaderboardTabBean> objects,int userId) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.userId=userId;
        this.list=objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    
    @Override
    public MyLeaderboardTabBean getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(MyLeaderboardTabBean item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_team_name=convertView.findViewById(R.id.txt_team_name);
        TextView txt_credit=convertView.findViewById(R.id.txt_credit);
        TextView txt_rank=convertView.findViewById(R.id.txt_rank);
        CardView card_view=convertView.findViewById(R.id.card_view);
        MyLeaderboardTabBean bean=list.get(position);
        if(bean.getUserId()==userId){
            card_view.setBackgroundColor(Color.parseColor("#FFF4DD"));
        }
        txt_team_name.setText(bean.getTeamName());
        txt_credit.setText(bean.getCredit());
        txt_rank.setText(String.valueOf(bean.getNum()));
        return convertView;
    }
}
