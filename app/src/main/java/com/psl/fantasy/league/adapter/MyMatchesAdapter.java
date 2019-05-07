package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.model.ui.MyMatchesBean;

import java.util.List;

public class MyMatchesAdapter extends ArrayAdapter<MyMatchesBean> {
    Context context;
    int resource;
    List<MyMatchesBean> list;
    public MyMatchesAdapter(Context context, int resource, List<MyMatchesBean> list) {
        super(context, resource,list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public MyMatchesBean getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(MyMatchesBean item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_teamOne=convertView.findViewById(R.id.txt_teamOne);
        TextView txt_teamTwo=convertView.findViewById(R.id.txt_teamTwo);
        TextView txt_match_status=convertView.findViewById(R.id.txt_match_status);
        TextView txt_contest_joined=convertView.findViewById(R.id.txt_contest_joined);
        MyMatchesBean bean= list.get(position);
        txt_teamOne.setText(bean.getTeamOne());
        txt_teamTwo.setText(bean.getTeamTwo());
        txt_match_status.setText(bean.getMatchStatus());
        txt_contest_joined.setText(bean.getNumberOfContest());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,String.valueOf(bean.getMatch_id()),Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }
}
