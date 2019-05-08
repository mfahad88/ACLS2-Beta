package com.psl.fantasy.league.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.fragment.CreatedTeamFragment;
import com.psl.fantasy.league.model.ui.MyMatchesTabBean;

import java.util.List;
import java.util.zip.Inflater;

public class MyMatchesTabAdapter extends ArrayAdapter<MyMatchesTabBean> {
    Context context;
    int resource;
    List<MyMatchesTabBean> list;
    public MyMatchesTabAdapter(Context context, int resource, List<MyMatchesTabBean> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public MyMatchesTabBean getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public int getPosition(MyMatchesTabBean item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,null);
        TextView txt_team_name = convertView.findViewById(R.id.txt_team_name);
        TextView txt_credit = convertView.findViewById(R.id.txt_credit);
        TextView txt_point = convertView.findViewById(R.id.txt_point);
        MyMatchesTabBean bean=list.get(position);

            Log.e("MyMatchesTabBean",bean.toString());

            txt_team_name.setText(bean.getTeamName());
            txt_credit.setText(bean.getCredit());
            txt_point.setText(bean.getPoint());


            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment=new CreatedTeamFragment();
                    Bundle bundle=new Bundle();
                    bundle.putInt("teamId",bean.getTeamId());
                    fragment.setArguments(bundle);
                    FragmentTransaction ft=((FragmentActivity)context).getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });


        return convertView;
    }
}
