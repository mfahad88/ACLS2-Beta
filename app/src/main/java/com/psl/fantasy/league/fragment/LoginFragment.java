package com.psl.fantasy.league.fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.activity.StartActivity;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Login.LoginResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // private static final int RC_SIGN_IN =100 ;
    private View mView;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private EditText edt_mobile_no,edt_password;
    private Button btn_next;
    private SharedPreferences sharedpreferences;
    private int contestId;
    private double credit;
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
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("Facebook: ",loginResult.getAccessToken().getUserId());
                AccessToken accessToken = loginResult.getAccessToken();
                useLoginInformation(accessToken);
            }

            @Override
            public void onCancel() {
                Toast.makeText(mView.getContext(), "Cancelled...", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                Toast.makeText(mView.getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        btn_next.setOnClickListener(this);
        return mView;
    }

    private void init(){
        LoginManager.getInstance().logOut();
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(mView.getContext());
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        loginButton=mView.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("email", "public_profile"));
        callbackManager=CallbackManager.Factory.create();
        btn_next=mView.findViewById(R.id.btn_next);
        edt_mobile_no=mView.findViewById(R.id.edt_mobile_no);
        edt_password=mView.findViewById(R.id.edt_password);
        sharedpreferences = mView.getContext().getSharedPreferences(Helper.SHARED_PREF, Context.MODE_PRIVATE);


    }

    private void useLoginInformation(AccessToken accessToken) {
        /**
         Creating the GraphRequest to fetch user details
         1st Param - AccessToken
         2nd Param - Callback (which will be invoked once the request is successful)
         **/
        GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
            //OnCompleted is invoked once the GraphRequest is successful
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String image = object.getJSONObject("picture").getJSONObject("data").getString("url");
                    /*edt_first_name.setText(object.getString("first_name"));
                    edt_last_name.setText(object.getString("last_name"));
                    edt_email.setText(object.getString("email"));
                    signupType="Facebook";*/
                    Log.e("Facebook",object.getString("email"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        // We set parameters to the GraphRequest using a Bundle.
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email,picture.width(200),phone");
        request.setParameters(parameters);
        // Initiate the GraphRequest
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        try{
            String mobileNo=edt_mobile_no.getText().toString();
            String password=edt_password.getText().toString();
            if((!TextUtils.isEmpty(mobileNo)) &&(!TextUtils.isEmpty(password))){
                JSONObject obj=new JSONObject();
                obj.put("mobile_no",mobileNo);
                obj.put("pws",password);
                ApiClient.getInstance().login(Helper.encrypt(obj.toString()))
                        .enqueue(new Callback<LoginResponse>() {
                            @Override
                            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                        Helper.putUserSession(sharedpreferences,Helper.MY_USER,response.body().getData().getMyUser());
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
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
