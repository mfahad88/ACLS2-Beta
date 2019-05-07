package com.psl.fantasy.league.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.LoggingBehavior;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.psl.fantasy.league.BuildConfig;
import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.activity.StartActivity;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Insert.InsertResponse;
import com.psl.fantasy.league.model.response.Login.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // private static final int RC_SIGN_IN =100 ;
    private View mView;

    private EditText edt_mobile_no,edt_password,edt_mobile_number,edt_pass_word,edt_referral;
    private TextView txt_register;
    private Button btn_next,btn_sign_up;
    private SharedPreferences sharedpreferences;
    private int contestId;
    private double credit;
    private LinearLayout linear_sign_up;
    private LinearLayout linear_sign_in;
    private String signupType="form";
    private SharedPreferences preferences;
    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_login, container, false);
        init();
        if(getArguments()!=null){
            credit=getArguments().getDouble("credit");
            contestId=getArguments().getInt("contestId");
        }

        btn_next.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_sign_in.setVisibility(View.GONE);
                linear_sign_up.setVisibility(View.VISIBLE);
            }
        });
        return mView;
    }

    private void init(){

        btn_next=mView.findViewById(R.id.btn_next);
        edt_mobile_no=mView.findViewById(R.id.edt_mobile_no);
        edt_password=mView.findViewById(R.id.edt_password);
        edt_mobile_number=mView.findViewById(R.id.edt_mobile_number);
        edt_pass_word=mView.findViewById(R.id.edt_pass_word);
        edt_referral=mView.findViewById(R.id.edt_referral);
        sharedpreferences = mView.getContext().getSharedPreferences(Helper.SHARED_PREF, Context.MODE_PRIVATE);
        linear_sign_in=mView.findViewById(R.id.linear_sign_in);
        linear_sign_up=mView.findViewById(R.id.linear_sign_up);
        btn_sign_up=mView.findViewById(R.id.btn_sign_up);
        txt_register=mView.findViewById(R.id.txt_register);
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);

    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*if(RC_SIGN_IN==100) {
         *//*Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);*//*
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }*/
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onClick(View v) {
       if(v.getId()==R.id.btn_next){
           try{
               String mobileNo=edt_mobile_no.getText().toString();
               String password=edt_password.getText().toString();
               if((!TextUtils.isEmpty(mobileNo)) &&(!TextUtils.isEmpty(password))){
                   JSONObject obj=new JSONObject();
                   obj.put("mobile_no",mobileNo);
                   obj.put("pws",password);
                    login(obj);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
       }else{
            try{
                String mobileNo=edt_mobile_number.getText().toString();
                String password=edt_pass_word.getText().toString();
                String referral=edt_referral.getText().toString();
                if((!TextUtils.isEmpty(mobileNo)) && (!TextUtils.isEmpty(password))){
                    JSONObject object=new JSONObject();
                    object.put("pws",password);
                    object.put("mobile_no",mobileNo);
                    object.put("app_version",BuildConfig.VERSION_NAME);
                    object.put("os","Android");
                    object.put("referal_code",referral);
                    object.put("source",signupType);
                    object.put("sts",1);
                    object.put("is_updated","0");
                    object.put("method_Name",this.getClass().getSimpleName()+".btn_sign_up.onClick");

                    ApiClient.getInstance().insertUser(Helper.encrypt(object.toString()))
                            .enqueue(new Callback<InsertResponse>() {
                                @Override
                                public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getResponseCode().equals("1001")){
                                            //Helper.showAlertNetural(mView.getContext(),"Success","Done");
                                            JSONObject obj=new JSONObject();
                                            try {
                                                obj.put("mobile_no",mobileNo);
                                                obj.put("pws",password);
                                                login(obj);
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }


                                        }else{
                                            Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                        }
                                    }else{
                                        if (response.errorBody() != null) {
                                            try {

                                                Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
                                            } catch (IOException e) {
                                                Crashlytics.logException(e.getCause());
                                                e.printStackTrace();

                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<InsertResponse> call, Throwable t) {
                                    t.printStackTrace();
                                    Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                                }
                            });
                }
            }catch (Exception e){
                e.printStackTrace();
            }
       }
    }

    private void login(JSONObject obj) {
        ApiClient.getInstance().login(Helper.encrypt(obj.toString()))
                .enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                Helper.putUserSession(sharedpreferences,Helper.MY_USER,response.body().getData().getMyUser());
                                Helper.createDirectory();

                                if(Helper.getUserSession(preferences,"MyUser")!=null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(Helper.getUserSession(preferences, "MyUser").toString());
                                        Helper.saveText(String.valueOf(jsonObject.getInt("user_id")));
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }

                                Fragment fragment=new PaymentFragment();
                                Bundle bundle=new Bundle();
                                bundle.putInt("conId",contestId);
                                bundle.putDouble("credit",credit);
                                fragment.setArguments(bundle);
                                FragmentTransaction ft=getFragmentManager().beginTransaction();
                                ft.replace(R.id.main_content,fragment);
                                ft.commit();

                            }else{
                                Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                        Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                    }
                });
    }
}
