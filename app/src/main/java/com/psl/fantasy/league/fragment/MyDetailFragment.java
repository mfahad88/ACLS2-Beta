package com.psl.fantasy.league.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.request.TestBeanRequest;
import com.psl.fantasy.league.model.response.SelectUser.SelectUserBean;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyDetailFragment extends Fragment {
    int user_id = 0;

    public MyDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView=inflater.inflate(R.layout.fragment_my_detail, container, false);
        TextView txt_mobile_no=mView.findViewById(R.id.txt_mobile_no);
        TextView txt_coins=mView.findViewById(R.id.txt_coins);
        TextView txt_points=mView.findViewById(R.id.txt_points);
        TextView txt_referral_code=mView.findViewById(R.id.txt_referral_code);
        ImageButton imageButtonShare=mView.findViewById(R.id.imageButtonShare);
        SharedPreferences preferences=getActivity().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);

        if(Helper.getUserSession(preferences,Helper.MY_USER)!=null){
            JSONObject object= null;
            try {
                object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            Fragment fragment=new LoginFragment();
            AppCompatActivity activity = (AppCompatActivity)mView.getContext() ;
            FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
            Bundle bundle=new Bundle();
            bundle.putString("screen","detailfragment");
            fragment.setArguments(bundle);
            ft.replace(R.id.frame_container,fragment);
            ft.commit();
        }

        try {
            JSONObject object=new JSONObject();
            object.put("user_id",user_id);
            ApiClient.getInstance().SelectUser(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<SelectUserBean>() {
                        @Override
                        public void onResponse(Call<SelectUserBean> call, Response<SelectUserBean> response) {
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equalsIgnoreCase("1001")){

                                    String shareId=response.body().getData().getMyUser().getMobileNo().substring(4,7).trim()+""+user_id;
                                    txt_coins.setText(response.body().getData().getMyUsermsc().getCoinsBalance().toString());
                                    txt_points.setText(response.body().getData().getMyUsermsc().getBudgetPoint().toString());
                                    txt_mobile_no.setText(response.body().getData().getMyUser().getMobileNo());
                                    txt_referral_code.setText(shareId);
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
        imageButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShareIntent("Please download this app from our website");
            }
        });
        return mView;
    }

    private void initShareIntent(String _text) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, _text);
        //shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(Environment.getExternalStorageDirectory().toString() + "/acl.png")));  //optional//use this when you want to send an image
        shareIntent.setType("text/plain");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }
}
