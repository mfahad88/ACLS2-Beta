package com.psl.fantasy.league.revamp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.model.ui.TelcoBean;

import java.util.List;

public class TelcoAdapter extends BaseAdapter {
    Context context;
    int resource;
    int[] icon;
    String[] telcoName;

    public TelcoAdapter(Context context, int resource, int[] icon,String[] telcoName) {
        this.context = context;
        this.resource = resource;
        this.icon = icon;
        this.telcoName=telcoName;
    }

    @Override
    public int getCount() {
        return telcoName.length;
    }

    @Override
    public Object getItem(int position) {
        return telcoName[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("NewApi")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);

        ImageView image_view_telco=convertView.findViewById(R.id.image_view_telco);
        TextView txt_telco=convertView.findViewById(R.id.txt_telco);
        image_view_telco.setBackground(context.getDrawable(icon[position]));
        txt_telco.setText(telcoName[position]);
        return convertView;
    }
}
