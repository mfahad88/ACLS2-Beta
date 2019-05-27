package com.psl.fantasy.league.revamp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.activity.StartActivity;
import com.psl.fantasy.league.revamp.fragment.AboutUsFragment;
import com.psl.fantasy.league.revamp.fragment.FaqFragment;
import com.psl.fantasy.league.revamp.fragment.PrizesFragment;
import com.psl.fantasy.league.revamp.fragment.RulesFragment;
import com.psl.fantasy.league.revamp.fragment.TermsConditionsFragment;

public class MoreAdapter extends ArrayAdapter {
    Context context;
    int resource;
    Object[] objects;
    SharedPreferences preferences;
    public MoreAdapter(Context context, int resource, Object[] objects, SharedPreferences preferences) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.objects=objects;
        this.preferences=preferences;
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
                    ft.replace(R.id.main_content,new PrizesFragment());
                    ft.commit();
                }if(objects[position].toString().equalsIgnoreCase("FAQs")){
                    ft.replace(R.id.main_content,new FaqFragment());
                    ft.commit();
                }if(objects[position].toString().equalsIgnoreCase("Rules")){
                    ft.replace(R.id.main_content,new RulesFragment());
                    ft.commit();
                }if(objects[position].toString().equalsIgnoreCase("Terms & Conditions")){
                    ft.replace(R.id.main_content,new TermsConditionsFragment());
                    ft.commit();
                }if(objects[position].toString().equalsIgnoreCase("About")){
                    ft.replace(R.id.main_content,new AboutUsFragment());
                    ft.commit();
                }
                if(Helper.getUserSession(preferences,Helper.MY_USER)!=null){
                    if(objects[position].toString().equalsIgnoreCase("Logout")){
                            preferences.edit().clear().commit();
                            Helper.deleteDirectory();
                            Intent intent=new Intent(context,StartActivity.class);
                            context.startActivity(intent);
                            ((AppCompatActivity) context).finish();
                    }
                }
            }
        });

        return convertView;
    }
}

