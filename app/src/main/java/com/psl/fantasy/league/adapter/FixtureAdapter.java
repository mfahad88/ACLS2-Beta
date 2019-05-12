package com.psl.fantasy.league.adapter;


import android.annotation.SuppressLint;

import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final SimpleDateFormat sdf=new SimpleDateFormat("HH:mm:ss");

        convertView=LayoutInflater.from(context).inflate(resource,null);
        final MatchesBean bean=list.get(position);
        Log.e("MatchesBean",bean.toString());
        Drawable drawable = null,drawable2 = null;
        ImageView image_team_one = convertView.findViewById(R.id.image_team_one);
        ImageView image_team_two = convertView.findViewById(R.id.image_team_two);
        /*TextView txt_teamOne=convertView.findViewById(R.id.txt_teamOne);
        TextView txt_teamTwo=convertView.findViewById(R.id.txt_teamTwo);*/
        TextView txt_short_team_one=convertView.findViewById(R.id.txt_short_team_one);
        TextView txt_short_team_two=convertView.findViewById(R.id.txt_short_team_two);
        TextView txt_series=convertView.findViewById(R.id.txt_series);
        final TextView txt_time = convertView.findViewById(R.id.txt_time);

        try{
            txt_short_team_one.setText(bean.getTeamOne());
            txt_short_team_two.setText(bean.getTeamTwo());
            /*txt_teamOne.setText(bean.getTeamOne());
            txt_teamTwo.setText(bean.getTeamTwo());*/
            if(bean.getTeamOne().trim().equals("Pakistan")){
                drawable= context.getDrawable(R.drawable.pakistan);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("Bangladesh")){
                drawable= context.getDrawable(R.drawable.bangladesh);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("India")){
                drawable= context.getDrawable(R.drawable.india);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("Sri Lanka")){
                drawable= context.getDrawable(R.drawable.srilanka);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("Australia")){
                drawable= context.getDrawable(R.drawable.australia);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("Afghanistan")){
                drawable= context.getDrawable(R.drawable.afghanistan);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("South Africa")){
                drawable= context.getDrawable(R.drawable.southafrica);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("England")){
                drawable= context.getDrawable(R.drawable.england);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("India")){
                drawable= context.getDrawable(R.drawable.india);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("New Zealand")){
                drawable= context.getDrawable(R.drawable.newzealand);
                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("West Indies")){
                drawable= context.getDrawable(R.drawable.westindies);
                image_team_one.setImageDrawable(drawable);
            }


            if(bean.getTeamTwo().trim().equals("Pakistan")){
                drawable= context.getDrawable(R.drawable.pakistan);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("Bangladesh")){
                drawable= context.getDrawable(R.drawable.bangladesh);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("India")){
                drawable= context.getDrawable(R.drawable.india);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("Sri Lanka")){
                drawable= context.getDrawable(R.drawable.srilanka);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("Australia")){
                drawable= context.getDrawable(R.drawable.australia);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("Afghanistan")){
                drawable= context.getDrawable(R.drawable.afghanistan);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("South Africa")){
                drawable= context.getDrawable(R.drawable.southafrica);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("England")){
                drawable= context.getDrawable(R.drawable.england);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("India")){
                drawable= context.getDrawable(R.drawable.india);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("New Zealand")){
                drawable= context.getDrawable(R.drawable.newzealand);
                image_team_two.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("West Indies")){
                drawable= context.getDrawable(R.drawable.westindies);
                image_team_two.setImageDrawable(drawable);
            }
            Log.e("West", String.valueOf(bean.getTeamOne()));

//            Glide.with(context).load(drawable).into(image_team_one);
//            Glide.with(context).load(drawable2).into(image_team_two);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        txt_time.post(new Runnable() {
                            @Override
                            public void run() {
                                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
                                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.ENGLISH);

                                LocalDateTime date = LocalDateTime.parse(bean.getTime().replace("+0000","Z"), inputFormatter);
                                String formattedDate = outputFormatter.format(date);
                                try {
                                    Date date1=sdf.parse(formattedDate);
                                    /*Long diff=date1.getTime()-new Date().getTime();
                                    long diffSeconds = diff / 1000 % 60;
                                    long diffMinutes = diff / (60 * 1000) % 60;
                                    long diffHours = diff / (60 * 60 * 1000) % 24;
                                    txt_time.setText(diffHours+":"+diffMinutes+":"+diffSeconds+" Left");*/
                                    txt_time.setText(date1.toString()+" Left");
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }
                }
            }).start();

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
                    AppCompatActivity activity=(AppCompatActivity)context;
                    //FragmentTransaction ft=((FragmentActivity)context).getFragmentManager().beginTransaction();
                    android.support.v4.app.FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
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
