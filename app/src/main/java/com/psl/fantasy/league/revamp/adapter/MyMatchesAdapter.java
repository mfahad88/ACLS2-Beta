package com.psl.fantasy.league.revamp.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.fragment.JoinedContestFragment;
import com.psl.fantasy.league.revamp.model.ui.MyMatchesBean;

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
        int resId=0;
        ImageView image_team_one=convertView.findViewById(R.id.image_team_one);
        ImageView image_team_two=convertView.findViewById(R.id.image_team_two);
        TextView txt_short_team_one=convertView.findViewById(R.id.txt_short_team_one);
        TextView txt_short_team_two=convertView.findViewById(R.id.txt_short_team_two);
        TextView txt_series=convertView.findViewById(R.id.txt_series);
        TextView txt_contest_joined=convertView.findViewById(R.id.txt_contest_joined);
        MyMatchesBean bean= list.get(position);

        if(bean.getTeamOne().trim().equals("Pakistan")){
//                drawable= context.getDrawable(R.drawable.pakistan);
            resId=R.drawable.pakistan;
//                image_team_one.setImageDrawable(drawable);
        }if(bean.getTeamOne().trim().equals("Bangladesh")){
//                drawable= context.getDrawable(R.drawable.bangladesh);
            resId=R.drawable.bangladesh;
//                image_team_one.setImageDrawable(drawable);
        }if(bean.getTeamOne().trim().equals("India")){
//                drawable= context.getDrawable(R.drawable.india);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.india;
        }if(bean.getTeamOne().trim().equals("Sri Lanka")){
//                drawable= context.getDrawable(R.drawable.srilanka);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.srilanka;
        }if(bean.getTeamOne().trim().equals("Australia")){
//                drawable= context.getDrawable(R.drawable.australia);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.australia;
        }if(bean.getTeamOne().trim().equals("Afghanistan")){
//                drawable= context.getDrawable(R.drawable.afghanistan);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.afghanistan;
        }if(bean.getTeamOne().trim().equals("South Africa")){
//                drawable= context.getDrawable(R.drawable.southafrica);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.southafrica;
        }if(bean.getTeamOne().trim().equals("England")){
//                drawable= context.getDrawable(R.drawable.england);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.england;
        }if(bean.getTeamOne().trim().equals("New Zealand")){
//                drawable= context.getDrawable(R.drawable.newzealand);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.newzealand;
        }if(bean.getTeamOne().trim().equals("West Indies")){
//                drawable= context.getDrawable(R.drawable.westindies);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.westindies;
        }
        image_team_one.setImageResource(resId);

        if(bean.getTeamTwo().trim().equals("Pakistan")){
//                drawable= context.getDrawable(R.drawable.pakistan);
            resId=R.drawable.pakistan;
//                image_team_one.setImageDrawable(drawable);
        }if(bean.getTeamTwo().trim().equals("Bangladesh")){
//                drawable= context.getDrawable(R.drawable.bangladesh);
            resId=R.drawable.bangladesh;
//                image_team_one.setImageDrawable(drawable);
        }if(bean.getTeamTwo().trim().equals("India")){
//                drawable= context.getDrawable(R.drawable.india);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.india;
        }if(bean.getTeamTwo().trim().equals("Sri Lanka")){
//                drawable= context.getDrawable(R.drawable.srilanka);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.srilanka;
        }if(bean.getTeamTwo().trim().equals("Australia")){
//                drawable= context.getDrawable(R.drawable.australia);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.australia;
        }if(bean.getTeamTwo().trim().equals("Afghanistan")){
//                drawable= context.getDrawable(R.drawable.afghanistan);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.afghanistan;
        }if(bean.getTeamTwo().trim().equals("South Africa")){
//                drawable= context.getDrawable(R.drawable.southafrica);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.southafrica;
        }if(bean.getTeamTwo().trim().equals("England")){
//                drawable= context.getDrawable(R.drawable.england);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.england;
        }if(bean.getTeamTwo().trim().equals("New Zealand")){
//                drawable= context.getDrawable(R.drawable.newzealand);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.newzealand;
        }if(bean.getTeamTwo().trim().equals("West Indies")){
//                drawable= context.getDrawable(R.drawable.westindies);
//                image_team_one.setImageDrawable(drawable);
            resId=R.drawable.westindies;
        }
        image_team_two.setImageResource(resId);
        txt_short_team_one.setText(bean.getShortName1());
        txt_short_team_two.setText(bean.getShortName2());
        txt_series.setText(bean.getSeriesName());
        txt_contest_joined.setText(bean.getMatchStatus());
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,String.valueOf(bean.getMatch_id()),Toast.LENGTH_SHORT).show();
                Fragment fragment=new JoinedContestFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("userId",bean.getUserId());
                bundle.putInt("matchId",bean.getMatch_id());
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
