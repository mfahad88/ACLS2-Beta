package com.psl.fantasy.league.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.interfaces.PlayerInterface;
import com.psl.fantasy.league.model.ui.PlayerBean;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfoAdapter extends ArrayAdapter<PlayerBean> {
    Context context;
    int resource;
    int player_Type;
    List<PlayerBean> list=new ArrayList<>();
    int count_wk=0;
    int count_bat=0;
    int count_alrounder=0;
    int count_bowl=0;
    List<PlayerBean> beanList=new ArrayList<>();
    PlayerInterface playerInterface;
    DbHelper dbHelper;

    public PlayerInfoAdapter(Context context, int resource, List<PlayerBean> list, int player_Type, PlayerInterface playerInterface, DbHelper dbHelper) {
        super(context,resource,list);
        Log.e(this.getClass().getSimpleName(),"Instance: "+player_Type);
        this.context=context;
        this.resource=resource;
        this.list=list;
        this.player_Type=player_Type; //0=batting ,1=bowling,2=alrounder,3=keeper
        this.playerInterface=playerInterface;
        this.dbHelper =dbHelper;
    }



    @Override
    public PlayerBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getPosition(PlayerBean item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(resource, null);
        TextView txt_player_name = convertView.findViewById(R.id.txt_player_name);
        TextView txt_short_country = convertView.findViewById(R.id.txt_short_country);
//        TextView txt_skill_player = convertView.findViewById(R.id.txt_skill_player);
        TextView txt_points = convertView.findViewById(R.id.txt_points);
        TextView txt_credits = convertView.findViewById(R.id.txt_credits);
        /*CheckBox chk_vcaptain = convertView.findViewById(R.id.chk_vcaptain);

        CheckBox chk_captain = convertView.findViewById(R.id.chk_captain);*/

        PlayerBean bean = list.get(position);

        txt_player_name.setText(bean.getName());
        txt_points.setText(String.valueOf(bean.getPoints()));
        txt_credits.setText(String.valueOf(bean.getCredits()));
        txt_short_country.setText(bean.getShort_country());
//        txt_skill_player.setText(bean.getSkill());
        final View finalConvertView = convertView;


        if (player_Type == 3) {

            convertView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("NewApi")
                @Override
                public void onClick(View v) {

                    if (list.get(position).isChecked()) {
                        if (count_wk == 1) {
                            count_wk--;
                            txt_player_name.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_points.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_credits.setTextColor(Color.parseColor("#7b7a7a"));
                            playerInterface.playerCount(player_Type, count_wk);
                            playerInterface.count('-');
                            playerInterface.credit(bean.getCredits());
                            finalConvertView.setSelected(false);
                            finalConvertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            bean.setChecked(false);

                            dbHelper.deleteMyTeamById(bean.getId());
                        }
                    } else {
                        if(dbHelper.getMyTeamCount()<11){
                            if (count_wk == 0) {
                                count_wk++;
                                playerInterface.playerCount(player_Type, count_wk);
                                playerInterface.count('+');
                                playerInterface.credit(bean.getCredits());
                                finalConvertView.setSelected(true);
                                txt_player_name.setTextColor(Color.parseColor("#000000"));
                                txt_points.setTextColor(Color.parseColor("#000000"));
                                txt_credits.setTextColor(Color.parseColor("#000000"));
                                finalConvertView.setBackgroundColor(Color.parseColor("#FFF4DD"));

                                bean.setChecked(true);
                                dbHelper.saveMyTeam(bean);


                            }
                        }
                    }

                }

            });

        }
        if (player_Type == 0) {

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (list.get(position).isChecked()) {
                        if (count_bat > 0) {
                            //  Toast.makeText(context, bean.getName(), Toast.LENGTH_SHORT).show();

                            count_bat--;

                            playerInterface.playerCount(player_Type, count_bat);
                            playerInterface.count('-');
                            playerInterface.credit(bean.getCredits());
                            finalConvertView.setSelected(false);
                            txt_player_name.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_points.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_credits.setTextColor(Color.parseColor("#7b7a7a"));
                            finalConvertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            bean.setChecked(false);
                            dbHelper.deleteMyTeamById(bean.getId());
                            beanList.remove(bean);
                        }
                    } else {
                        if(dbHelper.getMyTeamCount()<11){
                            if (count_bat < 4) {
                                count_bat++;

                                playerInterface.playerCount(player_Type, count_bat);
                                playerInterface.count('+');
                                playerInterface.credit(bean.getCredits());
                                finalConvertView.setSelected(true);
                                txt_player_name.setTextColor(Color.parseColor("#000000"));
                                txt_points.setTextColor(Color.parseColor("#000000"));
                                txt_credits.setTextColor(Color.parseColor("#000000"));
                                finalConvertView.setBackgroundColor(Color.parseColor("#FFF4DD"));
                                bean.setChecked(true);
                                dbHelper.saveMyTeam(bean);

                            }

                        }
                    }


                }

            });

        }
        if (player_Type == 2) {

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  Toast.makeText(context, bean.getName(), Toast.LENGTH_SHORT).show();

                    if (list.get(position).isChecked()) {
                        if (count_alrounder > 0) {
                            count_alrounder--;

                            playerInterface.playerCount(player_Type, count_alrounder);
                            playerInterface.count('-');
                            playerInterface.credit(bean.getCredits());

                            finalConvertView.setSelected(false);
                            txt_player_name.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_points.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_credits.setTextColor(Color.parseColor("#7b7a7a"));
                            finalConvertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            bean.setChecked(false);
                            dbHelper.deleteMyTeamById(bean.getId());
                            beanList.remove(bean);
                        }
                    } else {
                        if(dbHelper.getMyTeamCount()<11){
                            if (count_alrounder < 2) {
                                count_alrounder++;

                                playerInterface.playerCount(player_Type, count_alrounder);
                                playerInterface.count('+');
                                playerInterface.credit(bean.getCredits());

                                finalConvertView.setSelected(true);
                                txt_player_name.setTextColor(Color.parseColor("#000000"));
                                txt_points.setTextColor(Color.parseColor("#000000"));
                                txt_credits.setTextColor(Color.parseColor("#000000"));
                                finalConvertView.setBackgroundColor(Color.parseColor("#FFF4DD"));
                                bean.setChecked(true);
                                dbHelper.saveMyTeam(bean);

                            }

                        }
                    }


                }

            });
        }
        if (player_Type == 1) {

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //  Toast.makeText(context, bean.getName(), Toast.LENGTH_SHORT).show();

                    if (list.get(position).isChecked()) {
                        if (count_bowl > 0) {
                            count_bowl--;

                            playerInterface.playerCount(player_Type, count_bowl);
                            playerInterface.count('-');
                            playerInterface.credit(bean.getCredits());

                            finalConvertView.setSelected(false);
                            txt_player_name.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_points.setTextColor(Color.parseColor("#7b7a7a"));
                            txt_credits.setTextColor(Color.parseColor("#7b7a7a"));
                            finalConvertView.setBackgroundColor(Color.parseColor("#FFFFFF"));
                            bean.setChecked(false);
                            dbHelper.deleteMyTeamById(bean.getId());
                            beanList.remove(bean);
                        }
                    } else {
                        if(dbHelper.getMyTeamCount()<11){
                            if (count_bowl < 4) {
                                count_bowl++;

                                playerInterface.playerCount(player_Type, count_bowl);
                                playerInterface.count('+');
                                playerInterface.credit(bean.getCredits());
                                finalConvertView.setSelected(true);
                                txt_player_name.setTextColor(Color.parseColor("#000000"));
                                txt_points.setTextColor(Color.parseColor("#000000"));
                                txt_credits.setTextColor(Color.parseColor("#000000"));
                                finalConvertView.setBackgroundColor(Color.parseColor("#FFF4DD"));
                                bean.setChecked(true);
                                dbHelper.saveMyTeam(bean);

                            }
                        }
                    }

                }

            });


        }


        if(list.get(position).isChecked()){
            convertView.setSelected(true);
            convertView.setBackgroundColor(Color.parseColor("#FFF4DD"));
        }
        return convertView;

    }


}
