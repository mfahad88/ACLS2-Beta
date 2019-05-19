package com.psl.fantasy.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.adapter.PrizesRedeemAdapter;
import com.psl.fantasy.league.model.ui.RedeemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrizesRedeemFragment extends Fragment {


    public PrizesRedeemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_prizes_redeem, container, false);
        ListView list_redeem_prizes=view.findViewById(R.id.list_redeem_prizes);
        List<RedeemBean> list=new ArrayList<>();
        list.add(new RedeemBean(1,"Android","World Cup","10000"));
        list.add(new RedeemBean(2,"MyOwn","T20 Cup","20000"));
        list.add(new RedeemBean(3,"Test","Dubai Series","30000"));
        list.add(new RedeemBean(4,"Gamer","Asia Cup","100000"));
        list.add(new RedeemBean(5,"ACL","World Cup","50000"));
        PrizesRedeemAdapter adapter=new PrizesRedeemAdapter(view.getContext(),R.layout.adapter_redeem_prizes,list);
        list_redeem_prizes.setAdapter(adapter);
        return view;
    }

}
