package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.fragment.LeaderboardFragment;
import com.psl.fantasy.league.model.ui.JoinedContestBean;
import com.psl.fantasy.league.model.ui.MyMatchesBean;

import java.util.List;

public class JoinedContestAdapter extends ArrayAdapter<JoinedContestBean> {
    Context context;
    int resource;
    List<JoinedContestBean> list;

    public JoinedContestAdapter(Context context, int resource, List<JoinedContestBean> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public JoinedContestBean getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(JoinedContestBean item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        JoinedContestBean bean=list.get(position);

        TextView txt_contest_name=convertView.findViewById(R.id.txt_contest_name);
        TextView txt_contest_desc=convertView.findViewById(R.id.txt_contest_desc);
        Button btn_pay=convertView.findViewById(R.id.btn_pay);
        TextView txt_team_name=convertView.findViewById(R.id.txt_team_name);
        TextView txt_points=convertView.findViewById(R.id.txt_points);
        txt_contest_name.setText(bean.getContestName());
        txt_contest_desc.setText(bean.getContestDesc());

        txt_team_name.setText(bean.getTeamName());
        txt_points.setText(String.valueOf(bean.getPoints()));

        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new LeaderboardFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("contestId",bean.getContestId());
                bundle.putInt("userId",bean.getUserId());
                AppCompatActivity activity=(AppCompatActivity)context;
                FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
//                FragmentTransaction ft=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                fragment.setArguments(bundle);
                ft.replace(R.id.main_content,fragment);
                ft.commit();
            }
        });
        return convertView;
    }
}
