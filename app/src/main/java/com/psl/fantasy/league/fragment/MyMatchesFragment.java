package com.psl.fantasy.league.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.MyMatchesAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.interfaces.FragmentToActivity;
import com.psl.fantasy.league.model.response.MyMatches.Datum;
import com.psl.fantasy.league.model.response.MyMatches.MyMatchesResponse;
import com.psl.fantasy.league.model.ui.MyMatchesBean;

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
    private int userId;
    private FragmentToActivity mCallback;
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
        View mView=inflater.inflate(R.layout.fragment_my_matches, container, false);
        SharedPreferences preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        ListView list_matches=mView.findViewById(R.id.list_matches);
        TextView txt_error=mView.findViewById(R.id.txt_error);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        list=new ArrayList<>();
        mCallback.communicate("disable");
        if(Helper.getUserSession(preferences,"MyUser")==null) {
            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"ACL","user.txt");
            if(file.exists()) {
                if (Helper.getUserIdFromText() != null) {

                    try {
                        JSONObject jsonObject=new JSONObject(Helper.getUserIdFromText());
                        userId= jsonObject.getInt("user_id");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }else{
            try{
                JSONObject jsonObject = new JSONObject(Helper.getUserSession(preferences, "MyUser").toString());
                userId=jsonObject.getInt("user_id");
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        JSONObject obj=new JSONObject();
        try {
            obj.put("user_id",userId);
            ApiClient.getInstance().getAllMatchByUserId(Helper.encrypt(obj.toString())).enqueue(new Callback<MyMatchesResponse>() {
                @Override
                public void onResponse(Call<MyMatchesResponse> call, Response<MyMatchesResponse> response) {
                    if(response.isSuccessful()){
                        if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                            for (Datum datum : response.body().getData())
                                list.add(new MyMatchesBean(userId,datum.getMatchId(), datum.getTeamId1().intValue(), datum.getTeamId2().intValue()
                                        , datum.getTeamName1(), datum.getTeamName2(), datum.getMatchSts(), ""));
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

        return mView;
    }

}
