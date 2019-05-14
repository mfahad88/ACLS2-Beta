package com.psl.fantasy.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.adapter.MoreAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    public static final String[] items={"Terms & Conditions","FAQs","Prizes","About","Rules"};

    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_more, container, false);
        ListView list_view_items=mView.findViewById(R.id.list_view_items);
        MoreAdapter adapter=new MoreAdapter(mView.getContext(),R.layout.more_adapter,items);
        list_view_items.setAdapter(adapter);
        return mView;
    }

}
