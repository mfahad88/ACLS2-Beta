package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.model.response.PrizeDistribution.Datum;
import com.psl.fantasy.league.model.ui.WinnerBean;

import java.util.List;

public class WinnerBottomAdapter extends ArrayAdapter {
    Context context;
    int resource;
    List<Datum> list;
    public WinnerBottomAdapter(Context context, int resource, List<Datum> list) {
        super(context, resource,list);
        this.context=context;
        this.resource=resource;
        this.list=list;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        Datum bean=list.get(position);
        TextView txt_rank=convertView.findViewById(R.id.txt_rank);
        TextView txt_amount=convertView.findViewById(R.id.txt_amount);
        txt_rank.setText(bean.getCatDesc());
        txt_amount.setText(bean.getDistValue());
        return convertView;
    }
}
