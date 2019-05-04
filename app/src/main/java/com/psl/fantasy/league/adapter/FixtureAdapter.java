package com.psl.fantasy.league.adapter;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.fragment.ContestFragment;
import com.psl.fantasy.league.model.ui.MatchesBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FixtureAdapter extends ArrayAdapter<MatchesBean> {
    Context context;
    int resource;
    List<MatchesBean> list;
    public FixtureAdapter(Context context, int resource, List<MatchesBean> list) {
        super(context,resource,list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }



    @Override
    public long getItemId(int position) {
        return list.get(position).getMatchId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");

        convertView=LayoutInflater.from(context).inflate(resource,null);
        final MatchesBean bean=list.get(position);
        Log.e("MatchesBean",bean.toString());
        Drawable drawable = null,drawable2 = null;
        /*ImageView image_team_one = convertView.findViewById(R.id.image_team_one);
        ImageView image_team_two = convertView.findViewById(R.id.image_team_two);*/
        TextView txt_teamOne=convertView.findViewById(R.id.txt_teamOne);
        TextView txt_teamTwo=convertView.findViewById(R.id.txt_teamTwo);
        final TextView txt_time = convertView.findViewById(R.id.txt_time);

        try{
            txt_teamOne.setText(bean.getTeamOne());
            txt_teamTwo.setText(bean.getTeamTwo());
          /*  if(bean.getTeamOne().equalsIgnoreCase("pakistan")){
                drawable= context.getDrawable(R.drawable.drawable_pk);
            }if(bean.getTeamOne().equalsIgnoreCase("bangladesh")){
                drawable= context.getDrawable(R.drawable.drawable_bd);
            }if(bean.getTeamOne().equalsIgnoreCase("india")){
                drawable= context.getDrawable(R.drawable.drawable_in);
            }if(bean.getTeamOne().equalsIgnoreCase("srilanka")){
                drawable= context.getDrawable(R.drawable.drawable_lk);
            }if(bean.getTeamOne().equalsIgnoreCase("australia")){
                drawable= context.getDrawable(R.drawable.drawable_au);
            }
        *//*if(bean.getTeamOne().equalsIgnoreCase("za")){
                drawable= context.getDrawable(R.drawable.drawable_sa);
            }if(bean.getTeamOne().equalsIgnoreCase("zw")){
                drawable= context.getDrawable(R.drawable.drawable_zmb);
            }*//*

            if(bean.getTeamTwo().equalsIgnoreCase("pakistan")){
                drawable2= context.getDrawable(R.drawable.drawable_pk);
            }if(bean.getTeamTwo().equalsIgnoreCase("bbangladeshd")){
                drawable2= context.getDrawable(R.drawable.drawable_bd);
            }if(bean.getTeamTwo().equalsIgnoreCase("india")){
                drawable2= context.getDrawable(R.drawable.drawable_in);
            }if(bean.getTeamTwo().equalsIgnoreCase("srilanka")){
                drawable2= context.getDrawable(R.drawable.drawable_lk);
            }if(bean.getTeamTwo().equalsIgnoreCase("australia")){
                drawable2= context.getDrawable(R.drawable.drawable_au);
            }



            Glide.with(context).load(drawable).into(image_team_one);
            Glide.with(context).load(drawable2).into(image_team_two);*/
            txt_time.post(new Runnable() {
                @Override
                public void run() {
                    txt_time.setText(sdf.format(new Date()));
                }
            });
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Dashboard",bean.getTeam_id1()+","+bean.getTeam_id2());
                    //Helper.showAlertNetural(context,"Id",bean.getTeam_id1()+","+bean.getTeam_id2());
                    Fragment fragment=new ContestFragment();
                    Bundle bundle=new Bundle();
                    bundle.putInt("match_id",bean.getMatchId());
                    bundle.putInt("TeamId1",bean.getTeam_id1());
                    bundle.putInt("TeamId2",bean.getTeam_id2());
                    fragment.setArguments(bundle);
                    FragmentTransaction ft=((FragmentActivity)context).getFragmentManager().beginTransaction();
                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }
}
