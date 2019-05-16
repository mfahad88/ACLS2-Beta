package com.psl.fantasy.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.adapter.CaptainAdapter;
import com.psl.fantasy.league.model.ui.PlayerBean;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptainFragment extends Fragment {


    public CaptainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_captain, container, false);
        ListView list_view_captain=mView.findViewById(R.id.list_view_captain);
        Button  btn_save_team=mView.findViewById(R.id.btn_save_team);
        DbHelper dbHelper=new DbHelper(mView.getContext());
        List<PlayerBean> list=dbHelper.getMyTeam();
        CaptainAdapter adapter=new CaptainAdapter(mView.getContext(),R.layout.captain_adapter,list,dbHelper);
        list_view_captain.setAdapter(adapter);
        return mView;
    }

}
