package com.psl.fantasy.league.revamp.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;

public class TermsConditionDialog extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_terms_conditions, container, false);
        TextView txt_terms_conditions=view.findViewById(R.id.txt_terms_conditions);
        txt_terms_conditions.setText(Helper.readFile(view.getContext(),"terms_and_condition.txt"));
        txt_terms_conditions.setMovementMethod(new ScrollingMovementMethod());
        return view;
    }
}
