package com.psl.fantasy.league.revamp.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.CaptainAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.interfaces.CaptainInterface;
import com.psl.fantasy.league.revamp.model.response.JoinContest.JoinContenstResponse;
import com.psl.fantasy.league.revamp.model.ui.PlayerBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CaptainFragment extends Fragment {
    CaptainInterface captainInterface;
    boolean Captain;
    boolean ViceCaptain;
    private int contestId;
    int teamId1,teamId2;
    int user_id;
    String teamOne,teamTwo;
    Double credit;
    SharedPreferences preferences;
    private int contestAmt;
    DbHelper dbHelper;
    View mView;
    ProgressDialog pd;
    Date date;
    SimpleDateFormat sdf;
    public CaptainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_captain, container, false);
        ListView list_view_captain=mView.findViewById(R.id.list_view_captain);
        Button  btn_save_team=mView.findViewById(R.id.btn_save_team);
        sdf=new SimpleDateFormat("yyssddmm");
        date=new Date();
        pd = new ProgressDialog(mView.getContext());
        pd.setTitle("Status");
        pd.setMessage("Please wait saving team...");
        dbHelper = new DbHelper(mView.getContext());
        List<PlayerBean> list=dbHelper.getMyTeam();
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        if(getArguments()!=null){
            contestAmt=getArguments().getInt("contestAmt");
            contestId=getArguments().getInt("contestId");
            teamId1=getArguments().getInt("teamId1");
            teamId2=getArguments().getInt("teamId2");
            teamOne=getArguments().getString("TeamOne");
            teamTwo=getArguments().getString("TeamTwo");
            credit=getArguments().getDouble("credit");
        }

        if(Helper.getUserSession(preferences,Helper.MY_USER)==null) {

            Fragment fragment = new LoginFragment();
            Bundle bundle = new Bundle();
            bundle.putDouble("credit", credit);
            bundle.putInt("contestId", contestId);
            bundle.putInt("contestAmt",contestAmt);
            bundle.putString("screen","captain");
            fragment.setArguments(bundle);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.main_content, fragment);
            ft.commit();
        }else{
            try{
                JSONObject object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");
            }catch (Exception e){
                e.printStackTrace();
            }
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
                try {
                    if(contestAmt>0) {
                        fragment = new PaymentFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("contestAmt", contestAmt);
                        bundle.putDouble("credit", credit);
                        bundle.putInt("conId", contestId);
                        fragment.setArguments(bundle);
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        ft.replace(R.id.main_content, fragment);
                        ft.commit();
                    }else{
                        pd.show();
                        saveTeam();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        return mView;
    }

    public void saveTeam(){
        try{
            List<PlayerBean> list= dbHelper.getMyTeam();
            JSONArray jsonArray = new JSONArray();
            for(PlayerBean bean:list) {

                JSONArray array = new JSONArray();
                array.put(bean.getId());
                if(bean.isCaptain()) {
                    array.put(1);
                }else{
                    array.put(0);
                }
                if(bean.isViceCaptain()) {
                    array.put(1);
                }else {
                    array.put(0);
                }
                jsonArray.put(array);
            }
            Log.e("beanList",jsonArray.toString());

            JSONObject object=new JSONObject();

            object.put("user_id",user_id);
            object.put("contest_id",contestId);
            object.put("name",user_id+"-"+sdf.format(date));
            object.put("method_Name",this.getClass().getSimpleName()+".btn_done.onClick");
            object.put("playersInfo",jsonArray);
            object.put("coins",0);
            object.put("rem_budget",credit);

            ApiClient.getInstance().JoinContest(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<JoinContenstResponse>() {
                        @Override
                        public void onResponse(Call<JoinContenstResponse> call, Response<JoinContenstResponse> response) {
                            try{
                                if(response.isSuccessful()){
                                    pd.dismiss();
                                    if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                        dbHelper.deleteMyTeam();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Helper.showAlertNetural(mView.getContext(), "Success", response.body().getMessage());
                                            }
                                        },1000);
                                        Fragment fragment=new DashboardFragment();
                                        FragmentTransaction ft=getFragmentManager().beginTransaction();
                                        ft.replace(R.id.main_content,fragment);
                                        ft.commit();

                                    }else{
                                        Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                        Log.e("Pay",response.body().getMessage());
                                    }

                                }else{
                                    try {
                                        pd.dismiss();
                                        Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
                                        Log.e("Error",response.errorBody().string());

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<JoinContenstResponse> call, Throwable t) {
                            t.printStackTrace();
                            pd.dismiss();
                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
