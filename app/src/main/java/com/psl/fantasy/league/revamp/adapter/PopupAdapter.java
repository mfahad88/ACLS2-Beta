package com.psl.fantasy.league.revamp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.model.response.UserNotification.Datum;

import java.util.List;

public class PopupAdapter extends ArrayAdapter<Datum> {
    Context context;
    int resource;
    List<Datum> list;
    public PopupAdapter(Context context, int resource, List<Datum> objects) {
        super(context, resource, objects);
        this.context=context;
        this.resource=resource;
        this.list=objects;
    }

    @Override
    public Datum getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=LayoutInflater.from(context).inflate(resource,parent,false);
        ImageView image_view_icon=convertView.findViewById(R.id.image_view_icon);
        TextView txt_subject=convertView.findViewById(R.id.txt_subject);
        Datum datum=list.get(position);
        if(datum.getUserNotifSts().equalsIgnoreCase("i")){
            image_view_icon.setBackground(context.getResources().getDrawable(R.drawable.ic_email_black_24dp));
            txt_subject.setText(datum.getSubj());
            txt_subject.setTypeface(txt_subject.getTypeface(),Typeface.BOLD);
        }if(datum.getUserNotifSts().equalsIgnoreCase("v")){
            image_view_icon.setBackground(context.getResources().getDrawable(R.drawable.ic_drafts_black_24dp));
            txt_subject.setText(datum.getSubj());
        }


        return convertView;
    }
}
