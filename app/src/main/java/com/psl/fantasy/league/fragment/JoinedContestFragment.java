package com.psl.fantasy.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.psl.fantasy.league.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class JoinedContestFragment extends Fragment {


    public JoinedContestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_joined_contest, container, false);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        ListView list_matches=mView.findViewById(R.id.list_matches);
        progressBar.setVisibility(View.GONE);
        list_matches.setVisibility(View.VISIBLE);
        return mView;
    }

}
