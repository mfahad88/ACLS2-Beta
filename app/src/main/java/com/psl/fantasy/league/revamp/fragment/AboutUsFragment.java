package com.psl.fantasy.league.revamp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.BuildConfig;
import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutUsFragment extends Fragment {


    public AboutUsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_about_us, container, false);
        TextView txt_about_us_doc=view.findViewById(R.id.txt_about_us_doc);
        TextView txt_version=view.findViewById(R.id.txt_version);
        txt_version.setText("version: "+String.valueOf(BuildConfig.VERSION_NAME));
        txt_about_us_doc.setText(Helper.readFile(view.getContext(),"about_us.txt"));
        txt_about_us_doc.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

}
