package com.psl.fantasy.league.revamp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.interfaces.CaptainInterface;
import com.psl.fantasy.league.revamp.model.ui.PlayerBean;

import java.util.List;

public class CaptainAdapter extends ArrayAdapter<PlayerBean> {
    Context context;
    int resource;
    List<PlayerBean> list;
    DbHelper dbHelper;
    int captain_count; int vice_captain_count;
    CaptainInterface captainInterface;
    public CaptainAdapter(Context context, int resource, List<PlayerBean> objects, DbHelper dbHelper, CaptainInterface captainInterface) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
        this.dbHelper=dbHelper;
        this.captainInterface=captainInterface;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(resource,null);
        PlayerBean bean=list.get(position);
        TextView txt_player_name=convertView.findViewById(R.id.txt_player_name);
        TextView txt_short_country=convertView.findViewById(R.id.txt_short_country);
        TextView txt_points=convertView.findViewById(R.id.txt_points);
        TextView txt_captain=convertView.findViewById(R.id.txt_captain);
        TextView txt_vice_captain=convertView.findViewById(R.id.txt_vice_captain);
        txt_player_name.setText(bean.getName());
        txt_short_country.setText(bean.getShort_country());
        if(bean.getPoints()>0) {
            txt_points.setText(String.valueOf(bean.getPoints()));
        }
        txt_captain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.get(position).isCheckedCaptain()){
                    if(captain_count==1) {
                        captain_count--;
                        txt_captain.setSelected(false);
                        bean.setCheckedCaptain(false);
                        captainInterface.captain(false);
                        txt_captain.setTextColor(Color.parseColor("#9b9b9b"));
                        dbHelper.updateCaptainMyTeam(bean.getId(), true, 0);
                    }
                } else {
                        if(captain_count==0) {
                            captain_count++;
                            txt_captain.setSelected(true);
                            txt_captain.setTextColor(Color.parseColor("#ffffff"));
                            bean.setCheckedCaptain(true);
                            captainInterface.captain(true);
                            dbHelper.updateCaptainMyTeam(bean.getId(), true, 1);
                        }
                }
            }
        });

        txt_vice_captain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(list.get(position).isCheckedViceCaptain()){
                    if(vice_captain_count==1) {
                        vice_captain_count--;
                        txt_vice_captain.setSelected(false);
                        bean.setCheckedViceCaptain(false);
                        captainInterface.vice_captain(false);
                        txt_vice_captain.setTextColor(Color.parseColor("#9b9b9b"));
                        dbHelper.updateCaptainMyTeam(bean.getId(), false, 0);
                    }
                } else {
                    if(vice_captain_count==0) {
                        vice_captain_count++;
                        txt_vice_captain.setSelected(true);
                        bean.setCheckedViceCaptain(true);
                        captainInterface.vice_captain(true);
                        txt_vice_captain.setTextColor(Color.parseColor("#ffffff"));
                        dbHelper.updateCaptainMyTeam(bean.getId(), false, 1);
                    }
                }
            }
        });
        if(list.get(position).isCheckedCaptain()){
            txt_captain.setSelected(true);
            txt_captain.setTextColor(Color.parseColor("#9b9b9b"));
        }if(list.get(position).isCheckedViceCaptain()){
            txt_vice_captain.setSelected(true);
            txt_vice_captain.setTextColor(Color.parseColor("#9b9b9b"));
        }
        return convertView;
    }
}
