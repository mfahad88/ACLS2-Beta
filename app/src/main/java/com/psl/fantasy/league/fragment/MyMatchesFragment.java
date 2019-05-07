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
import com.psl.fantasy.league.model.ui.MyMatchesBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
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
    public MyMatchesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_my_matches, container, false);
        SharedPreferences preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        ListView list_matches=mView.findViewById(R.id.list_matches);
        ProgressBar progressBar=mView.findViewById(R.id.progressBar);
        list=new ArrayList<>();
        /*list.add(new MyMatchesBean(1,1001,2001,"Karachi","Sirilanka","Completed",""));
        list.add(new MyMatchesBean(2,1002,2002,"West indies","Sirilanka","Upcoming",""));
        list.add(new MyMatchesBean(3,1003,2003,"West indies","Sirilanka","live",""));*/
        if(Helper.getUserSession(preferences,"MyUser")==null) {
            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"ACL","user.txt");
            if(file.exists()) {
                if (Helper.getUserIdFromText() != null) {

                    try {
                        userId= Integer.parseInt(Helper.getUserIdFromText());
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
            ApiClient.getInstance().getAllMatchByUserId(Helper.encrypt(obj.toString())).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        progressBar.setVisibility(View.GONE);

                        list_matches.setVisibility(View.VISIBLE);

                        MyMatchesAdapter adapter=new MyMatchesAdapter(mView.getContext(),R.layout.my_matches_adapter,list);
                        list_matches.setAdapter(adapter);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return mView;
    }

}
