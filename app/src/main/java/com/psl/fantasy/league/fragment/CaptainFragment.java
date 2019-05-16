package com.psl.fantasy.league.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.CaptainAdapter;
import com.psl.fantasy.league.interfaces.CaptainInterface;
import com.psl.fantasy.league.model.ui.PlayerBean;

import java.io.File;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptainFragment extends Fragment {
    CaptainInterface captainInterface;
    boolean Captain;
    boolean ViceCaptain;
    private int contestId;
    int teamId1,teamId2;
    String teamOne,teamTwo;
    Double credit;
    SharedPreferences preferences;
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
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        if(getArguments()!=null){
            contestId=getArguments().getInt("contestId");
            teamId1=getArguments().getInt("teamId1");
            teamId2=getArguments().getInt("teamId2");
            teamOne=getArguments().getString("TeamOne");
            teamTwo=getArguments().getString("TeamTwo");
            credit=getArguments().getDouble("credit");
        }
        captainInterface=new CaptainInterface() {
            @Override
            public void captain(boolean isCaptain) {
                Captain=isCaptain;
                if(Captain && ViceCaptain){
                    btn_save_team.setTextColor(Color.WHITE);
                    btn_save_team.setEnabled(true);
                }else{
                    btn_save_team.setTextColor(Color.BLACK);
                    btn_save_team.setEnabled(false);
                }
            }

            @Override
            public void vice_captain(boolean isViceCaptain) {
                ViceCaptain=isViceCaptain;
                if(Captain && ViceCaptain){
                    btn_save_team.setTextColor(Color.WHITE);
                    btn_save_team.setEnabled(true);
                }else{
                    btn_save_team.setTextColor(Color.BLACK);
                    btn_save_team.setEnabled(false);
                }
            }
        };
        CaptainAdapter adapter=new CaptainAdapter(mView.getContext(),R.layout.captain_adapter,list,dbHelper,captainInterface);
        list_view_captain.setAdapter(adapter);
        btn_save_team.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                        if(Helper.getUserSession(preferences,"MyUser")==null) {
                            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"ACL","user.txt");
                            if(file.exists()) {
                                if (Helper.getUserIdFromText() != null) {

                                    try {
                                        fragment=new PaymentFragment();
                                        Bundle bundle = new Bundle();
                                        bundle.putDouble("credit", credit);
                                        bundle.putInt("conId", contestId);
                                        fragment.setArguments(bundle);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }
                            }else{
                                fragment = new LoginFragment();
                                Bundle bundle = new Bundle();
                                bundle.putDouble("credit", credit);
                                bundle.putInt("contestId", contestId);
                                fragment.setArguments(bundle);
                            }
                        } else {
                            try {
                                fragment=new PaymentFragment();
                                Bundle bundle = new Bundle();
                                bundle.putDouble("credit", credit);
                                bundle.putInt("conId", contestId);
                                fragment.setArguments(bundle);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.main_content, fragment);
                        ft.commit();
            }
        });
        return mView;
    }

}
