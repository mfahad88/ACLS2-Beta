package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.FixtureAdapter;
import com.psl.fantasy.league.revamp.adapter.ImageAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;
import com.psl.fantasy.league.revamp.model.response.Matches.Datum;
import com.psl.fantasy.league.revamp.model.response.Matches.MatchesResponse;

import com.psl.fantasy.league.revamp.model.ui.MatchesBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

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
    private FixtureAdapter fixtureAdapter;
    private SharedPreferences preferences;
    private int user_id;
    ViewPager viewPage;
    private DbHelper dbHelper;
    private LinearLayout linear_progress_bar;
    boolean isConnected;
    ImageButton leftNav,rightNav;
    public DashboardFragment() {
        // Required empty public constructor
    }

    /*@Override
    public void onResume() {
        super.onResume();
        if(fixtureAdapter!=null) {
            fixtureAdapter.clear();
            progressBar.setVisibility(View.VISIBLE);
            populateMatches();
            fixtureAdapter.notifyDataSetChanged();
        }
    }*/

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

        Helper.checkAppVersion(getActivity(), preferences, dbHelper);
        try{

            list_matches.setSmoothScrollbarEnabled(true);
            mCallback.communicate("disable");
            mCallback.communicate("DashboardFragment");
            pullToRefresh.setVisibility(View.VISIBLE);
            list_matches.setVisibility(View.VISIBLE);
            populateMatches();

            pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    try {
                        Helper.checkAppVersion(getActivity(), preferences, dbHelper);
                        //fixtureAdapter.clear();

//                        fixtureAdapter.notifyDataSetInvalidated();
                            populateMatches();

                        pullToRefresh.setRefreshing(false);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });

           new Thread(new Runnable() {
               @Override
               public void run() {
                   // Images left navigation
                   leftNav.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           int tab = viewPage.getCurrentItem();
                           if (tab > 0) {
                               tab--;
                               viewPage.setCurrentItem(tab);
                           } else if (tab == 0) {
                               viewPage.setCurrentItem(tab);
                           }
                       }
                   });

                   // Images right navigatin
                   rightNav.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           int tab = viewPage.getCurrentItem();
                           tab++;
                           viewPage.setCurrentItem(tab);
                       }
                   });
               }
           }).start();

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
        leftNav = (ImageButton) mView.findViewById(R.id.left_nav);
        rightNav = (ImageButton) mView.findViewById(R.id.right_nav);

        viewPage = mView.findViewById(R.id.viewPage);
        ImageAdapter adapter=new ImageAdapter(mView.getContext());
        viewPage.setAdapter(adapter);
        pullToRefresh=mView.findViewById(R.id.pullToRefresh);
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        dbHelper=new DbHelper(mView.getContext());
//        checkAppVersion();
        if(isConnected) {
            Helper.checkAppVersion(getActivity(), preferences, dbHelper);
        }
        linear_progress_bar=mView.findViewById(R.id.linear_progress_bar);
    }

    public void populateMatches(){


        try{
            if(Helper.isConnectedToNetwork(getActivity())){
                showProgress(View.VISIBLE);
                JSONObject object=new JSONObject();
                object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
                ApiClient.getInstance().matches(Helper.encrypt(object.toString()))
                        .enqueue(new Callback<MatchesResponse>() {
                            @Override
                            public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {
                               try{
                                   showProgress(GONE);
                                   if(response.isSuccessful()){
                                       if(response.body().getResponseCode().equals("1001")){
                                           for(Datum bean:response.body().getData()){
                                               if(response.body().getData().size()>0) {
                                                   if(bean.getMatchSts().equalsIgnoreCase("1")){
                                                       list.add(new MatchesBean(bean.getMatch_series_id(),bean.getTeamId1Name(),bean.getTeamId2Name(),bean.getTeam_id1_shortName(),bean.getTeam_id2_shortName(),bean.getStartDate(),bean.getTeamId1().intValue(),bean.getTeamId2().intValue(),bean.getSeries_name(),false));
                                                   }
                                               }else{
                                                   Helper.displayError(txt_status,"No record found...");
                                               }
                                           }
                                           if(list.size()>0){


                                               fixtureAdapter = new FixtureAdapter(mView.getContext(), R.layout.list_fixture, list);
                                               fixtureAdapter.notifyDataSetChanged();
                                               list_matches.setAdapter(fixtureAdapter);
                                           }

                                       }else{
                                           Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                       }
                                   }
                               }catch (Exception e){
                                   e.printStackTrace();
                               }
                            }

                            @Override
                            public void onFailure(Call<MatchesResponse> call, Throwable t) {

                                Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                                call.cancel();
                                showProgress(GONE);
                                t.printStackTrace();
                            }
                        });

            }
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
                            txt_status.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt_status.setText("Fetching match fixtures Please Wait.");
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }if(i==2) {
                            txt_status.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt_status.setText("Fetching match fixtures Please Wait..");
                                }
                            });
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }if(i==3) {
                            txt_status.post(new Runnable() {
                                @Override
                                public void run() {
                                    txt_status.setText("Fetching match fixtures Please Wait...");
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
            linear_progress_bar.setVisibility(GONE);
        }

    }



}
