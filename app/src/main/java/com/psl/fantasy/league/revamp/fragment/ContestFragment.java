package com.psl.fantasy.league.revamp.fragment;



import android.content.Context;
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
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.R;
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

    private ListView list_contest_1,list_contest_2,list_contest_3;
    String match_id;
    int TeamId1,TeamId2;
    private ProgressBar progressBar;
    private TextView txt_status,txt_cat1,txt_cat2,txt_cat3,txt_view_more_mega,txt_view_more_expert,txt_view_more_beginner;
    private FragmentToActivity mCallback;
    private SwipeRefreshLayout pullToRefresh;
    private String teamOne,teamTwo;
    private ContestAdapter adapter;
    private ScrollView scrollView;
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView=inflater.inflate(R.layout.fragment_contest, container, false);
        progressBar=mView.findViewById(R.id.progressBar);
        txt_view_more_mega=mView.findViewById(R.id.txt_view_more_mega);
        txt_view_more_expert=mView.findViewById(R.id.txt_view_more_expert);
        txt_view_more_beginner=mView.findViewById(R.id.txt_view_more_beginner);
        txt_cat1 =mView.findViewById(R.id.txt_cat1);
        txt_cat2 =mView.findViewById(R.id.txt_cat2);
        txt_cat3=mView.findViewById(R.id.txt_cat3);
        list_contest_1=mView.findViewById(R.id.list_contest_1);
        list_contest_2=mView.findViewById(R.id.list_contest_2);
        list_contest_3=mView.findViewById(R.id.list_contest_3);
        txt_status=mView.findViewById(R.id.txt_status);
        pullToRefresh=mView.findViewById(R.id.pullToRefresh);
        scrollView=mView.findViewById(R.id.scrollView);
        mCallback.communicate("ContestFragment");
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
                    adapter.clear();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            populateContest();
                            adapter.notifyDataSetChanged();

                            pullToRefresh.setRefreshing(false);
                        }
                    },1000);

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
            JSONObject object=new JSONObject();
            object.put("match_series_id",match_id);
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            Log.e("Fragment",object.toString());
            ApiClient.getInstance().getAllContest(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<ContestResponse>() {
                        @Override
                        public void onResponse(Call<ContestResponse> call, Response<ContestResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            int counter_mega=0; int counter_expert=0; int counter_beginner=0;
                            if(response.isSuccessful()){
//                                adapter.clear();
                                if(response.body().getResponseCode().equals("1001")){
                                    if(response.body().getData().size()>0){
                                        List<ContestBean> list=new ArrayList<>();
                                        List<ContestBean> list_expert=new ArrayList<>();
                                        List<ContestBean> list_beginner=new ArrayList<>();
                                        for(Datum datum:response.body().getData()){
                                            if(datum.getIsVisible().equals("1")){


                                                if(datum.getContestType().equalsIgnoreCase("0")){
                                                    //if(datum.getPoolConsumed()>0){

                                                        txt_cat1.setText("Mega Contest");

                                                        if(counter_mega<3) {
                                                            float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                                            int percent = Math.round(perc);
                                                            txt_cat1.setVisibility(View.VISIBLE);
                                                            Log.e("Float-------->",datum.getPoolConsumed().floatValue()+"/"+datum.getPool().floatValue());

                                                            list.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                    , String.valueOf(datum.getPool()),datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));


                                                            list_contest_1.setVisibility(View.VISIBLE);

                                                            adapter = new ContestAdapter(mView.getContext(),R.layout.list_contest,list,TeamId1,TeamId2,teamOne,teamTwo);
                                                            list_contest_1.setAdapter(adapter);

                                                            counter_mega++;
                                                            Log.e("Mega--->",datum.toString());
                                                            if(counter_mega==3) {
                                                                txt_view_more_mega.setVisibility(View.VISIBLE);
                                                            }
                                                            ViewGroup.LayoutParams params = list_contest_1.getLayoutParams();
                                                            params.height=Helper.dpToPx(144*counter_mega,mView.getContext());
                                                            list_contest_1.setLayoutParams(params);
                                                            list_contest_1.requestLayout();
                                                        }


                                                    //}
                                                }


                                                if(datum.getContestType().equalsIgnoreCase("1")){
//                                                    if(datum.getPoolConsumed()>0){
                                                        txt_view_more_expert.setVisibility(View.VISIBLE);

                                                        txt_cat2.setText("Expert Contest");

                                                        if(counter_expert<3) {
                                                            float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                                            int percent = Math.round(perc);
                                                            txt_cat2.setVisibility(View.VISIBLE);
                                                            list_expert.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                   ,String.valueOf(datum.getPool()) , datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));
                                                            list_contest_2.setVisibility(View.VISIBLE);
                                                            adapter=new ContestAdapter(mView.getContext(),R.layout.list_contest,list_expert,TeamId1,TeamId2,teamOne,teamTwo);

                                                            list_contest_2.setAdapter(adapter);
                                                            counter_expert++;
                                                            Log.e("Expert--->",datum.toString());
                                                            if(counter_expert==3){
                                                                txt_view_more_expert.setVisibility(View.VISIBLE);
                                                            }
                                                            ViewGroup.LayoutParams params = list_contest_2.getLayoutParams();
                                                            params.height=Helper.dpToPx(144*counter_expert,mView.getContext());
                                                            list_contest_2.setLayoutParams(params);
                                                            list_contest_2.requestLayout();
                                                        }


//                                                    }
                                                }
                                                if(datum.getContestType().equalsIgnoreCase("2")){
                                                  //  if(datum.getPoolConsumed()>0){

                                                        txt_view_more_beginner.setVisibility(View.VISIBLE);
                                                        txt_cat3.setText("Beginner Contest");

                                                        if(counter_beginner<3) {
                                                            float perc= ((datum.getPoolConsumed().floatValue() / datum.getPool().floatValue()) * 100);
                                                            int percent = Math.round(perc);
                                                            txt_cat3.setVisibility(View.VISIBLE);
                                                            list_beginner.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                    ,String.valueOf(datum.getPool()), datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));
                                                            list_contest_3.setVisibility(View.VISIBLE);
                                                            adapter=new ContestAdapter(mView.getContext(),R.layout.list_contest,list_beginner,TeamId1,TeamId2,teamOne,teamTwo);
                                                            list_contest_3.setAdapter(adapter);
                                                            counter_beginner++;
                                                            Log.e("Beginner--->",datum.toString());
                                                            if(counter_beginner==3){
                                                                txt_view_more_beginner.setVisibility(View.VISIBLE);
                                                            }

                                                            ViewGroup.LayoutParams params = list_contest_3.getLayoutParams();
                                                            params.height=Helper.dpToPx(144*counter_beginner,mView.getContext());
                                                            list_contest_3.setLayoutParams(params);
                                                            list_contest_3.requestLayout();
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
                                }
                            }else{
                                Helper.showAlertNetural(mView.getContext(),"Error",response.raw().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<ContestResponse> call, Throwable t) {
                            t.printStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                            progressBar.setVisibility(View.GONE);

                        }
                    });
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}