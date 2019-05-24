package com.psl.fantasy.league.revamp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;

/**
 * A simple {@link Fragment} subclass.
 */
public class TermsConditionsFragment extends Fragment {


    public TermsConditionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_terms_conditions, container, false);
        TextView txt_terms_conditions=view.findViewById(R.id.txt_terms_conditions);
        txt_terms_conditions.setText(Helper.readFile(view.getContext(),"terms_and_condition.txt"));
        txt_terms_conditions.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }

}
