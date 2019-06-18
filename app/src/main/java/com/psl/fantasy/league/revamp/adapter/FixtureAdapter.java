package com.psl.fantasy.league.revamp.adapter;


import android.Manifest;
import android.annotation.SuppressLint;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;


import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.fragment.ContestFragment;
import com.psl.fantasy.league.revamp.fragment.DashboardFragment;
import com.psl.fantasy.league.revamp.model.ui.MatchesBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class FixtureAdapter extends ArrayAdapter<MatchesBean>{
    Context context;
    int resource;
    List<MatchesBean> list;
    View convertView;
    @SuppressLint("MissingPermission")
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
        return Long.parseLong(list.get(position).getMatchId());
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        convertView=LayoutInflater.from(context).inflate(resource,null);
        final MatchesBean bean=list.get(position);

        //Drawable drawable = null;
        CardView card_view_match=convertView.findViewById(R.id.card_view_match);
        int resource=0;
        ImageView image_team_one = convertView.findViewById(R.id.image_team_one);
        ImageView image_team_two = convertView.findViewById(R.id.image_team_two);

        TextView txt_short_team_one=convertView.findViewById(R.id.txt_short_team_one);
        TextView txt_short_team_two=convertView.findViewById(R.id.txt_short_team_two);
        TextView txt_series=convertView.findViewById(R.id.txt_series);
        final TextView txt_time = convertView.findViewById(R.id.txt_time);


        try{
            txt_series.setText(bean.getTxt_series());
            txt_short_team_one.setText(bean.getShortName1());
            txt_short_team_two.setText(bean.getShortName2());

            if(bean.getTeamOne().trim().equals("Pakistan")){
//                drawable= context.getDrawable(R.drawable.pakistan);
                resource=R.drawable.pakistan;
//                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("Bangladesh")){
//                drawable= context.getDrawable(R.drawable.bangladesh);
                resource=R.drawable.bangladesh;
//                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamOne().trim().equals("India")){
//                drawable= context.getDrawable(R.drawable.india);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.india;
            }if(bean.getTeamOne().trim().equals("Sri Lanka")){
//                drawable= context.getDrawable(R.drawable.srilanka);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.srilanka;
            }if(bean.getTeamOne().trim().equals("Australia")){
//                drawable= context.getDrawable(R.drawable.australia);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.australia;
            }if(bean.getTeamOne().trim().equals("Afghanistan")){
//                drawable= context.getDrawable(R.drawable.afghanistan);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.afghanistan;
            }if(bean.getTeamOne().trim().equals("South Africa")){
//                drawable= context.getDrawable(R.drawable.southafrica);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.southafrica;
            }if(bean.getTeamOne().trim().equals("England")){
//                drawable= context.getDrawable(R.drawable.england);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.england;
            }if(bean.getTeamOne().trim().equals("New Zealand")){
//                drawable= context.getDrawable(R.drawable.newzealand);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.newzealand;
            }if(bean.getTeamOne().trim().equals("West Indies")){
//                drawable= context.getDrawable(R.drawable.westindies);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.westindies;
            }
            image_team_one.setImageResource(resource);

            if(bean.getTeamTwo().trim().equals("Pakistan")){
//                drawable= context.getDrawable(R.drawable.pakistan);
                resource=R.drawable.pakistan;
//                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("Bangladesh")){
//                drawable= context.getDrawable(R.drawable.bangladesh);
                resource=R.drawable.bangladesh;
//                image_team_one.setImageDrawable(drawable);
            }if(bean.getTeamTwo().trim().equals("India")){
//                drawable= context.getDrawable(R.drawable.india);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.india;
            }if(bean.getTeamTwo().trim().equals("Sri Lanka")){
//                drawable= context.getDrawable(R.drawable.srilanka);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.srilanka;
            }if(bean.getTeamTwo().trim().equals("Australia")){
//                drawable= context.getDrawable(R.drawable.australia);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.australia;
            }if(bean.getTeamTwo().trim().equals("Afghanistan")){
//                drawable= context.getDrawable(R.drawable.afghanistan);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.afghanistan;
            }if(bean.getTeamTwo().trim().equals("South Africa")){
//                drawable= context.getDrawable(R.drawable.southafrica);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.southafrica;
            }if(bean.getTeamTwo().trim().equals("England")){
//                drawable= context.getDrawable(R.drawable.england);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.england;
            }if(bean.getTeamTwo().trim().equals("New Zealand")){
//                drawable= context.getDrawable(R.drawable.newzealand);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.newzealand;
            }if(bean.getTeamTwo().trim().equals("West Indies")){
//                drawable= context.getDrawable(R.drawable.westindies);
//                image_team_one.setImageDrawable(drawable);
                resource=R.drawable.westindies;
            }
            image_team_two.setImageResource(resource);
            Log.e("Matches",bean.toString());
            Timer timer=new Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    txt_time.post(new Runnable() {
                        @Override
                        public void run() {

                            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            txt_time.setText( showDifference(sdf.format(bean.getTime()),card_view_match,bean));

                        }
                    });
                }
            },1000,100);
            /*new Thread(new Runnable() {
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

                                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ssZ");

                                    txt_time.setText( showDifference(sdf.format(bean.getTime()),card_view_match,bean));

                                }
                            });


                    }
                }
            }).start();*/

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Dashboard",bean.getTeam_id1()+","+bean.getTeam_id2());
                    //Helper.showAlertNetural(context,"Id",bean.getTeam_id1()+","+bean.getTeam_id2());
                    Fragment fragment=new ContestFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("match_id",bean.getMatchId());
                    bundle.putInt("TeamId1",bean.getTeam_id1());
                    bundle.putInt("TeamId2",bean.getTeam_id2());
                    bundle.putString("TeamOne",bean.getTeamOne().trim());
                    bundle.putString("TeamTwo",bean.getTeamTwo().trim());
                    fragment.setArguments(bundle);
                    AppCompatActivity activity=(AppCompatActivity)context;
                    //FragmentTransaction ft=((FragmentActivity)context).getFragmentManager().beginTransaction();
                    android.support.v4.app.FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main_content,fragment);
                    ft.addToBackStack(null);

                    ft.commit();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        /*if(bean.isChecked()){
            card_view_match.setCardBackgroundColor(Color.parseColor("#FFF4DD"));
        }*/
        return convertView;
    }


    public String showDifference(String date1, CardView cardView, MatchesBean bean){

        String dateStop = date1;
        String dateFinal = "";
        //TimeZone pstTime = TimeZone.getTimeZone("GMT");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateStart = format.format(new Date());

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);


            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");

            Log.e("Time",diffDays + " days, "+diffHours + " hours, "+diffMinutes + " minutes, "+diffSeconds + " seconds.");

            //New Format By FT
            long hrs=diffDays*24;
            hrs+=diffHours;
            /*if(hrs<=72){
                cardView.setCardBackgroundColor(Color.parseColor("#FFF4DD"));
                bean.setChecked(true);
            }*/
            if(hrs<=48 && hrs>5){
                dateFinal=hrs+" Hr(s) Left" ;
            }

            if(hrs>48){
                dateFinal=diffDays+" Day(s) "+diffHours+" Hr(s) Left";
            }

            // if(hrs<=5){
            if(hrs<=5 && hrs>0){ // change once data is updated
                dateFinal = diffHours+" Hr(s) "+diffMinutes+" Min(s) "+diffSeconds+" Sec(s) Left";
            }

            // dateFinal=diffDays + " days, "+diffHours + " hours, "+diffMinutes + " minutes, "+diffSeconds + " seconds.";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateFinal;
    }



}
