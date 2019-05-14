package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.model.ui.WinnerBean;

import java.util.List;

public class WinnerBottomAdapter extends ArrayAdapter<WinnerBean> {
    Context context;
    int resource;
    List<WinnerBean> list;
    public WinnerBottomAdapter(Context context, int resource, List<WinnerBean> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }


    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        WinnerBean bean=list.get(position);
        TextView txt_rank=convertView.findViewById(R.id.txt_rank);
        TextView txt_amount=convertView.findViewById(R.id.txt_amount);
        txt_rank.setText(bean.getRank());
        txt_amount.setText(bean.getPrice());
        return convertView;
    }
}
