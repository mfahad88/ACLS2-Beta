package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.BuildConfig;
import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.MyMatchesAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;
import com.psl.fantasy.league.revamp.model.response.AppVersion.AppVersionBean;
import com.psl.fantasy.league.revamp.model.response.MyMatches.Datum;
import com.psl.fantasy.league.revamp.model.response.MyMatches.MyMatchesResponse;
import com.psl.fantasy.league.revamp.model.response.SelectUser.SelectUserBean;
import com.psl.fantasy.league.revamp.model.ui.MyMatchesBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyMatchesFragment extends Fragment {
    private List<MyMatchesBean> list;
    private int user_id;
    private FragmentToActivity mCallback;
    SharedPreferences preferences;
    View mView;
    public MyMatchesFragment() {
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

        mView = inflater.inflate(R.layout.fragment_my_matches, container, false);

        preferences = mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        ListView list_matches=mView.findViewById(R.id.list_matches);
        TextView txt_error=mView.findViewById(R.id.txt_error);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        list=new ArrayList<>();
        mCallback.communicate("disable");
        if(Helper.getUserSession(preferences,Helper.MY_USER)!=null) {
            try {
                JSONObject object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");
                JSONObject obj=new JSONObject();
                try {
                    obj.put("user_id",user_id);
                    ApiClient.getInstance().getAllMatchByUserId(Helper.encrypt(obj.toString())).enqueue(new Callback<MyMatchesResponse>() {
                        @Override
                        public void onResponse(Call<MyMatchesResponse> call, Response<MyMatchesResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                    if(response.body().getData()!=null) {
                                        for (Datum datum : response.body().getData())
                                            list.add(new MyMatchesBean(user_id, datum.getMatchId(), datum.getTeamId1().intValue(), datum.getTeamId2().intValue()
                                                    , datum.getTeamName1(), datum.getTeamName2(), datum.getMatchSts(), "",datum.getTeam_name1_short(),datum.getTeam_name2_short(),datum.getSeries_name()));
                                        progressBar.setVisibility(View.GONE);

                                        list_matches.setVisibility(View.VISIBLE);

                                        MyMatchesAdapter adapter = new MyMatchesAdapter(mView.getContext(), R.layout.my_matches_adapter, list);
                                        list_matches.setAdapter(adapter);
                                    }else{
                                        progressBar.setVisibility(View.GONE);
                                        txt_error.setVisibility(View.VISIBLE);
                                        txt_error.setText(response.message());
                                    }
                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    txt_error.setVisibility(View.VISIBLE);
                                    txt_error.setText(response.message());
                                }
                            }else{
                                try {
                                    progressBar.setVisibility(View.GONE);
                                    txt_error.setVisibility(View.VISIBLE);
                                    txt_error.setText(response.errorBody().string());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MyMatchesResponse> call, Throwable t) {
                            t.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            txt_error.setVisibility(View.VISIBLE);
                            txt_error.setText(t.getMessage());
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Fragment fragment=new LoginFragment();
            Bundle bundle=new Bundle();
            bundle.putString("screen","mymatches");
            fragment.setArguments(bundle);
            AppCompatActivity activity=(AppCompatActivity)getActivity();
            FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_content,fragment);
            ft.commit();
        }


        return mView;
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

                                            if(Integer.parseInt(response.body().getData().getMyUser().getApp_version())>BuildConfig.VERSION_CODE){
                                                if(response.body().getData().getMyUser().getSts().intValue()==0){
                                                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://1drv.ms/u/s!AtJGoRk9R0bQhAVuq-dk8qsAbXxY"));
                                                    browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(browserIntent);
                                                    getActivity().finish();
                                                    System.exit(0);

                                                }
                                            }else if(response.body().getData().getMyUser().getSts().intValue()==0){
                                                if(Integer.parseInt(response.body().getData().getMyUser().getApp_version())==BuildConfig.VERSION_CODE){

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
