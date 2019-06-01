package com.psl.fantasy.league.revamp.fragment;



import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.ContestAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;
import com.psl.fantasy.league.revamp.model.response.Contest.ContestResponse;
import com.psl.fantasy.league.revamp.model.response.Contest.Datum;
import com.psl.fantasy.league.revamp.model.ui.ContestBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContestFragment extends Fragment {
    private View mView;
    private int match_Id;

    private ListView list_contest_1,list_contest_2,list_contest_3,list_contest_4;
    String match_id;
    int TeamId1,TeamId2;

    private TextView txt_status,txt_cat1,txt_cat2,txt_cat3,txt_cat4,txt_view_more_mega,txt_view_more_expert,txt_view_more_beginner,txt_view_more_practice,txt_status_progress;
    private LinearLayout linear_progress_bar;
    private FragmentToActivity mCallback;
    private SwipeRefreshLayout pullToRefresh;
    private String teamOne,teamTwo;
    private ContestAdapter adapter;
    private ScrollView scrollView;
    SharedPreferences preferences;
    DbHelper dbHelper;
    public ContestFragment() {

        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }

    /*@Override
    public void onResume() {
        if(adapter!=null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);

                    adapter.clear();
                    populateContest();
                    adapter.notifyDataSetChanged();
                }
            },1000);
        }
        super.onResume();
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView=inflater.inflate(R.layout.fragment_contest, container, false);
        txt_view_more_mega=mView.findViewById(R.id.txt_view_more_mega);
        txt_view_more_expert=mView.findViewById(R.id.txt_view_more_expert);
        txt_view_more_beginner=mView.findViewById(R.id.txt_view_more_beginner);
        txt_view_more_practice=mView.findViewById(R.id.txt_view_more_practice);
        txt_cat1 =mView.findViewById(R.id.txt_cat1);
        txt_cat2 =mView.findViewById(R.id.txt_cat2);
        txt_cat3=mView.findViewById(R.id.txt_cat3);
        txt_cat4=mView.findViewById(R.id.txt_cat4);
        list_contest_1=mView.findViewById(R.id.list_contest_1);
        list_contest_2=mView.findViewById(R.id.list_contest_2);
        list_contest_3=mView.findViewById(R.id.list_contest_3);
        list_contest_4=mView.findViewById(R.id.list_contest_4);
        txt_status=mView.findViewById(R.id.txt_status);
        txt_status_progress=mView.findViewById(R.id.txt_status_progress);
        linear_progress_bar=mView.findViewById(R.id.linear_progress_bar);
        pullToRefresh=mView.findViewById(R.id.pullToRefresh);
        scrollView=mView.findViewById(R.id.scrollView);
        mCallback.communicate("ContestFragment");
        dbHelper=new DbHelper(getContext());
        preferences=getActivity().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        Helper.checkAppVersion(getActivity(),preferences,dbHelper);
        try {
            if(getArguments()!=null) {

                match_id = getArguments().getString("match_id");
                TeamId1=getArguments().getInt("TeamId1");
                TeamId2=getArguments().getInt("TeamId2");
                teamOne=getArguments().getString("TeamOne");
                teamTwo=getArguments().getString("TeamTwo");
            }
            pullToRefresh.setVisibility(View.VISIBLE);
            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
//                    pullToRefresh.setRefreshing(true);
                    if(adapter!=null){
                        Helper.checkAppVersion(getActivity(),preferences,dbHelper);

                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                populateContest();
                                adapter.notifyDataSetChanged();

                                pullToRefresh.setRefreshing(false);
                            }
                        },1000);
                    }

                }
            });


            populateContest();
            txt_view_more_mega.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment=new ContestDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("match_id",match_id);
                    bundle.putInt("TeamId1",TeamId1);
                    bundle.putInt("TeamId2",TeamId2);
                    bundle.putInt("contest_type",0);
                    bundle.putString("TeamOne",teamOne);
                    bundle.putString("TeamTwo",teamTwo);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();

                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });

            txt_view_more_expert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment=new ContestDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("match_id",match_id);
                    bundle.putInt("TeamId1",TeamId1);
                    bundle.putInt("TeamId2",TeamId2);
                    bundle.putInt("contest_type",1);
                    bundle.putString("TeamOne",teamOne);
                    bundle.putString("TeamTwo",teamTwo);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();

                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });
            txt_view_more_beginner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment=new ContestDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("match_id",match_id);
                    bundle.putInt("TeamId1",TeamId1);
                    bundle.putInt("TeamId2",TeamId2);
                    bundle.putInt("contest_type",3);
                    bundle.putString("TeamOne",teamOne);
                    bundle.putString("TeamTwo",teamTwo);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();

                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });

            txt_view_more_practice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment=new ContestDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("match_id",match_id);
                    bundle.putInt("TeamId1",TeamId1);
                    bundle.putInt("TeamId2",TeamId2);
                    bundle.putInt("contest_type",2);
                    bundle.putString("TeamOne",teamOne);
                    bundle.putString("TeamTwo",teamTwo);
                    fragment.setArguments(bundle);
                    FragmentTransaction ft=getFragmentManager().beginTransaction();

                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });






        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }

    public void populateContest(){
        try{
            showProgress(View.VISIBLE);
            JSONObject object=new JSONObject();
            object.put("match_series_id",match_id);
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            Log.e("Fragment",object.toString());
            ApiClient.getInstance().getAllContest(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<ContestResponse>() {
                        @Override
                        public void onResponse(Call<ContestResponse> call, Response<ContestResponse> response) {

                            int counter_mega=0; int counter_expert=0; int counter_beginner=0; int counter_practice=0;
                            if(response.isSuccessful()){
                                showProgress(View.GONE);
//                                adapter.clear();
                                if(response.body().getResponseCode().equals("1001")){
                                    if(response.body().getData().size()>0){
                                        List<ContestBean> list=new ArrayList<>();
                                        List<ContestBean> list_expert=new ArrayList<>();
                                        List<ContestBean> list_beginner=new ArrayList<>();
                                        List<ContestBean> list_pratice=new ArrayList<>();
                                        for(Datum datum:response.body().getData()){
                                            if(datum.getIsVisible().equals("1")){


                                                if(datum.getContestType().equalsIgnoreCase("0")){ //0=mega,1=expert,2=practice,3=beginner
                                                    //if(datum.getPoolConsumed()>0){

                                                        txt_cat1.setText(datum.getContestName());
                                                        txt_cat1.setVisibility(View.VISIBLE);

                                                        if(counter_mega<3) {
                                                            float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                                            int percent = Math.round(perc);

                                                            Log.e("Float-------->",datum.getPoolConsumed().floatValue()+"/"+datum.getPool().floatValue());

                                                            list.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                    , String.valueOf(datum.getPool()),datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));


                                                            list_contest_1.setVisibility(View.VISIBLE);

                                                            adapter = new ContestAdapter(mView.getContext(),R.layout.list_contest,list,TeamId1,TeamId2,teamOne,teamTwo);
                                                            list_contest_1.setAdapter(adapter);

                                                            counter_mega++;
                                                            Log.e("Mega--->",datum.toString());

                                                        }
                                                    ViewGroup.LayoutParams params = list_contest_1.getLayoutParams();
                                                    params.height=Helper.dpToPx(144*counter_mega,mView.getContext());
                                                    list_contest_1.setLayoutParams(params);
                                                    list_contest_1.requestLayout();

                                                    if(counter_mega>=3) {
                                                        txt_view_more_mega.setVisibility(View.VISIBLE);
                                                    }

                                                    //}
                                                }


                                                if(datum.getContestType().equalsIgnoreCase("1")){
//                                                    if(datum.getPoolConsumed()>0){


                                                        txt_cat2.setText(datum.getContestName());
                                                        txt_cat2.setVisibility(View.VISIBLE);
                                                        if(counter_expert<3) {
                                                            float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                                            int percent = Math.round(perc);

                                                            list_expert.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                   ,String.valueOf(datum.getPool()) , datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));
                                                            list_contest_2.setVisibility(View.VISIBLE);
                                                            adapter=new ContestAdapter(mView.getContext(),R.layout.list_contest,list_expert,TeamId1,TeamId2,teamOne,teamTwo);

                                                            list_contest_2.setAdapter(adapter);
                                                            counter_expert++;
                                                            Log.e("Expert--->",datum.toString());


                                                        }

                                                    ViewGroup.LayoutParams params = list_contest_2.getLayoutParams();
                                                    params.height=Helper.dpToPx(144*counter_expert,mView.getContext());
                                                    list_contest_2.setLayoutParams(params);
                                                    list_contest_2.requestLayout();
                                                    if(counter_expert>=3){
                                                        txt_view_more_expert.setVisibility(View.VISIBLE);
                                                    }

//                                                    }
                                                }
                                                if(datum.getContestType().equalsIgnoreCase("3")){
                                                  //  if(datum.getPoolConsumed()>0){


                                                        txt_cat3.setText(datum.getContestName());
                                                        txt_cat3.setVisibility(View.VISIBLE);
                                                        if(counter_beginner<3) {
                                                            float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                                            int percent = Math.round(perc);

                                                            list_beginner.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                    ,String.valueOf(datum.getPool()), datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));
                                                            list_contest_3.setVisibility(View.VISIBLE);
                                                            adapter=new ContestAdapter(mView.getContext(),R.layout.list_contest,list_beginner,TeamId1,TeamId2,teamOne,teamTwo);
                                                            list_contest_3.setAdapter(adapter);
                                                            counter_beginner++;
                                                            Log.e("Beginner--->",datum.toString());
                                                        }
                                                    ViewGroup.LayoutParams params = list_contest_3.getLayoutParams();
                                                    params.height=Helper.dpToPx(144*counter_beginner,mView.getContext());
                                                    list_contest_3.setLayoutParams(params);
                                                    list_contest_3.requestLayout();
                                                    if(counter_beginner>=3){
                                                        txt_view_more_beginner.setVisibility(View.VISIBLE);
                                                    }
                                                    //}
                                                }

                                                if(datum.getContestType().equalsIgnoreCase("2")){
                                                    //  if(datum.getPoolConsumed()>0){


                                                    txt_cat4.setText(datum.getContestName());
                                                    txt_cat4.setVisibility(View.VISIBLE);

                                                    if(counter_practice<3) {
                                                        float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                                        int percent = Math.round(perc);

                                                        list_pratice.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                ,String.valueOf(datum.getPool()), datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));
                                                        list_contest_4.setVisibility(View.VISIBLE);
                                                        adapter=new ContestAdapter(mView.getContext(),R.layout.list_contest,list_pratice,TeamId1,TeamId2,teamOne,teamTwo);
                                                        list_contest_4.setAdapter(adapter);
                                                        counter_practice++;
                                                        Log.e("Practice--->",datum.toString());

                                                    }

                                                    ViewGroup.LayoutParams params = list_contest_4.getLayoutParams();
                                                    params.height=Helper.dpToPx(144*counter_practice,mView.getContext());
                                                    list_contest_4.setLayoutParams(params);
                                                    list_contest_4.requestLayout();
                                                    if(counter_practice>=3){
                                                        txt_view_more_practice.setVisibility(View.VISIBLE);
                                                    }
                                                    //}
                                                }
                                            }
                                        }

                                    }else{
                                        Helper.displayError(txt_status,"No Record found...");
                                    }

                                }else{
                                    Helper.displayError(txt_status,response.body().getMessage());
                                    showProgress(View.GONE);
                                }
                            }else{
                                showProgress(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<ContestResponse> call, Throwable t) {
                            t.printStackTrace();
                            showProgress(View.GONE);
                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");


                        }
                    });
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    private void showProgress(int visibility){


        if(visibility==View.VISIBLE){
            linear_progress_bar.setVisibility(View.VISIBLE);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int i=0;
                    while (true){
                        i++;
                        if(i==1) {
                            txt_status_progress.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt_status_progress.setText("Fetching contest Please Wait.");
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }if(i==2) {
                            txt_status_progress.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt_status_progress.setText("Fetching match fixtures Please Wait..");
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }if(i==3) {
                            txt_status_progress.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt_status_progress.setText("Fetching match fixtures Please Wait...");
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            i=0;
                        }
                    }
                }
            }).start();
        }else{
            linear_progress_bar.setVisibility(View.GONE);
        }

    }
}
