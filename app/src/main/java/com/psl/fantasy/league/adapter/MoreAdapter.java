package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.fragment.FaqFragment;
import com.psl.fantasy.league.fragment.FragmentPrizes;

public class MoreAdapter extends ArrayAdapter {
    Context context;
    int resource;
    Object[] objects;
    public MoreAdapter(Context context, int resource, Object[] objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_item=convertView.findViewById(R.id.txt_item);
        txt_item.setText(objects[position].toString());
        AppCompatActivity activity=(AppCompatActivity)context;
        FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
//        FragmentTransaction ft =((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(objects[position].toString().equalsIgnoreCase("prizes")){
                    ft.replace(R.id.main_content,new FragmentPrizes());
                    ft.commit();
                }if(objects[position].toString().equalsIgnoreCase("FAQs")){
                    ft.replace(R.id.main_content,new FaqFragment());
                    ft.commit();
                }
            }
        });

        return convertView;
    }
}


