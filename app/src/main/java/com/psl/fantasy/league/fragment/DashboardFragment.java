package com.psl.fantasy.league.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.FixtureAdapter;
import com.psl.fantasy.league.adapter.ImageAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.interfaces.FragmentToActivity;
import com.psl.fantasy.league.model.response.Matches.Datum;
import com.psl.fantasy.league.model.response.Matches.MatchesResponse;

import com.psl.fantasy.league.model.ui.MatchesBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    private ListView list_matches;
    private List<MatchesBean> list;
    private View mView;
    private TextView txt_status;
    private ProgressBar progressBar;
    private FragmentToActivity mCallback;
    private SwipeRefreshLayout pullToRefresh;
    FixtureAdapter fixtureAdapter;

    public DashboardFragment() {
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
        mView=inflater.inflate(R.layout.fragment_dashboard, container, false);
        init();
        try{
            mCallback.communicate("disable");
            mCallback.communicate("DashboardFragment");
            pullToRefresh.setVisibility(View.VISIBLE);

            populateMatches();

            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    try {
                        fixtureAdapter.clear();
                        fixtureAdapter.notifyDataSetChanged();

                        populateMatches();

                        pullToRefresh.setRefreshing(false);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }

    private void init(){
        progressBar=mView.findViewById(R.id.progressBar);
        list_matches=mView.findViewById(R.id.list_matches);
        list=new ArrayList<>();
        txt_status=mView.findViewById(R.id.txt_status);
        ViewPager viewPage=mView.findViewById(R.id.viewPage);
        ImageAdapter adapter=new ImageAdapter(mView.getContext());
        viewPage.setAdapter(adapter);
        pullToRefresh=mView.findViewById(R.id.pullToRefresh);
    }

    public void populateMatches(){
        try{
            JSONObject object=new JSONObject();
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            ApiClient.getInstance().matches(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<MatchesResponse>() {
                        @Override
                        public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equals("1001")){
                                    for(Datum bean:response.body().getData()){
                                        if(response.body().getData().size()>0) {
                                            if(bean.getMatchSts().equalsIgnoreCase("1")){
                                                list.add(new MatchesBean(bean.getMatchId().intValue(),bean.getTeamId1Name(),bean.getTeamId2Name(),bean.getTeam_id1_shortName(),bean.getTeam_id2_shortName(),bean.getStartDate(),bean.getTeamId1().intValue(),bean.getTeamId2().intValue(),bean.getSeries_name()));
                                                list_matches.setVisibility(View.VISIBLE);

                                                fixtureAdapter = new FixtureAdapter(mView.getContext(), R.layout.list_fixture, list);

                                                list_matches.setAdapter(fixtureAdapter);

                                            }
                                        }else{
                                            Helper.displayError(txt_status,"No record found...");
                                        }
                                    }

                                }else{
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MatchesResponse> call, Throwable t) {
                            t.fillInStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                        }
                    });
        }catch (JSONException e){
            e.printStackTrace();
        }
    }


}
