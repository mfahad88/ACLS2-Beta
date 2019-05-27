package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.BuildConfig;
import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.activity.StartActivity;
import com.psl.fantasy.league.revamp.adapter.FixtureAdapter;
import com.psl.fantasy.league.revamp.adapter.ImageAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;
import com.psl.fantasy.league.revamp.model.response.AppVersion.AppVersionBean;
import com.psl.fantasy.league.revamp.model.response.Matches.Datum;
import com.psl.fantasy.league.revamp.model.response.Matches.MatchesResponse;

import com.psl.fantasy.league.revamp.model.response.SelectUser.SelectUserBean;
import com.psl.fantasy.league.revamp.model.ui.MatchesBean;

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
    private FixtureAdapter fixtureAdapter;
    private SharedPreferences preferences;
    private int user_id;
    private DbHelper dbHelper;
    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if(fixtureAdapter!=null) {
            fixtureAdapter.clear();
            populateMatches();
            fixtureAdapter.notifyDataSetChanged();
        }
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

            list_matches.setSmoothScrollbarEnabled(true);
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
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        dbHelper=new DbHelper(mView.getContext());
//        checkAppVersion();
        Helper.checkAppVersion(getActivity(),preferences,dbHelper);
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
                                                list.add(new MatchesBean(bean.getMatch_series_id(),bean.getTeamId1Name(),bean.getTeamId2Name(),bean.getTeam_id1_shortName(),bean.getTeam_id2_shortName(),bean.getStartDate(),bean.getTeamId1().intValue(),bean.getTeamId2().intValue(),bean.getSeries_name()));
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

    public void checkAppVersion(){
        if(Helper.getUserSession(preferences,Helper.MY_USER)!=null){
            JSONObject object= null;
            try {
                object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("user_id",user_id);
                    ApiClient.getInstance().SelectUser(Helper.encrypt(jsonObject.toString()))
                            .enqueue(new Callback<SelectUserBean>() {
                                @Override
                                public void onResponse(Call<SelectUserBean> call, Response<SelectUserBean> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getResponseCode().equalsIgnoreCase("1001")){

                                            if(Float.parseFloat(response.body().getData().getMyUser().getApp_version())>Float.parseFloat(BuildConfig.VERSION_NAME)){
                                                if(response.body().getData().getMyUser().getSts().intValue()==0){
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://1drv.ms/u/s!AtJGoRk9R0bQhAVuq-dk8qsAbXxY"));
                                                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(browserIntent);
                                                    getActivity().finish();
                                                    System.exit(0);

                                                }
                                            }else if(response.body().getData().getMyUser().getSts().intValue()==0){
                                                if(Float.parseFloat(response.body().getData().getMyUser().getApp_version())==Float.parseFloat(BuildConfig.VERSION_NAME)){

                                                    try {
                                                        JSONObject jsonObject=new JSONObject();
                                                        jsonObject.put("user_id",user_id);
                                                        jsonObject.put("sts",1);

                                                        ApiClient.getInstance().updateAppVersion(Helper.encrypt(jsonObject.toString()))
                                                                .enqueue(new Callback<AppVersionBean>() {
                                                                    @Override
                                                                    public void onResponse(Call<AppVersionBean> call, Response<AppVersionBean> response) {
                                                                        if(response.isSuccessful()){
                                                                            if(response.body().getResponseCode().equalsIgnoreCase("1001")){

                                                                            }else{
                                                                                Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                                                            }
                                                                        }
                                                                    }

                                                                    @Override
                                                                    public void onFailure(Call<AppVersionBean> call, Throwable t) {
                                                                        t.printStackTrace();
                                                                        Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                                                                    }
                                                                });
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }

                                        }else{
                                            Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<SelectUserBean> call, Throwable t) {
                                    t.printStackTrace();
                                    Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

}
