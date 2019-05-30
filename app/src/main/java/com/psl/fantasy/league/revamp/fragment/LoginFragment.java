package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.psl.fantasy.league.revamp.BuildConfig;
import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.dialog.TermsConditionDialog;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;
import com.psl.fantasy.league.revamp.model.response.Insert.InsertResponse;
import com.psl.fantasy.league.revamp.model.response.JoinContest.JoinContenstResponse;
import com.psl.fantasy.league.revamp.model.response.Login.LoginResponse;
import com.psl.fantasy.league.revamp.model.ui.PlayerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {
    // private static final int RC_SIGN_IN =100 ;
    private View mView;
    private static final String EMAIL = "email";
    private EditText edt_mobile_no,edt_password,edt_mobile_number,edt_pass_word,edt_referral,edt_confirm_password;
    private TextView txt_register;
    private Button btn_next,btn_sign_up;
    private SharedPreferences sharedpreferences;
    private int contestId,contestAmt;
    private double credit;
    private CardView card_view_sign_up;
    private LinearLayout linear_sign_in;
    private String signupType="form";
    private SharedPreferences preferences;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private FragmentToActivity mCallback;
    private String screen;
    private StringBuilder user_consent;
    ProgressBar progressBar;
    TextView txt_terms_conditions;
    private CheckBox checkbox_terms_condition,checkbox_contact,checkbox_partner;
    private DbHelper dbHelper;
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
    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_login, container, false);
        user_consent=new StringBuilder();
        init();
        mCallback.communicate("disable");
        if(getArguments()!=null){
            credit=getArguments().getDouble("credit");
            contestId=getArguments().getInt("contestId");
            screen=getArguments().getString("screen");
            contestAmt=getArguments().getInt("contestAmt");
        }
//        loginButton = mView.findViewById(R.id.login_button);

//        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        btn_next.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
        callbackManager = CallbackManager.Factory.create();
        txt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linear_sign_in.setVisibility(View.GONE);
                card_view_sign_up.setVisibility(View.VISIBLE);
            }
        });
        txt_terms_conditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment=new TermsConditionDialog();
                dialogFragment.show(getFragmentManager(),"Terms");
            }
        });
     /*   loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
               *//* boolean loggedIn = AccessToken.getCurrentAccessToken() == null;
                Log.d("API123", loggedIn + " ??");
                Log.e("Facebook",loginResult.toString());*//*
                getUserProfile(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                error.printStackTrace();
                Helper.showAlertNetural(mView.getContext(),"Error",error.getMessage());
            }
        });*/
        return mView;
    }


    private void getUserProfile(AccessToken currentAccessToken) {
        GraphRequest request = GraphRequest.newMeRequest(
                currentAccessToken, new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.d("TAG", object.toString());
                        try {
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String email = object.getString("email");
                            String id = object.getString("id");
                            String image_url = "https://graph.facebook.com/" + id + "/picture?type=normal";

                            Log.e("Facebook","First Name: " + first_name + "\nLast Name: " + last_name);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void init(){
        LoginManager.getInstance().logOut();
        btn_next=mView.findViewById(R.id.btn_next);
        edt_mobile_no=mView.findViewById(R.id.edt_mobile_no);
        edt_password=mView.findViewById(R.id.edt_password);
        edt_mobile_number=mView.findViewById(R.id.edt_mobile_number);
        edt_pass_word=mView.findViewById(R.id.edt_pass_word);
        edt_referral=mView.findViewById(R.id.edt_referral);
        sharedpreferences = mView.getContext().getSharedPreferences(Helper.SHARED_PREF, Context.MODE_PRIVATE);
        linear_sign_in=mView.findViewById(R.id.linear_sign_in);

        btn_sign_up=mView.findViewById(R.id.btn_sign_up);
        txt_register=mView.findViewById(R.id.txt_register);
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        progressBar=mView.findViewById(R.id.progressBar);
        card_view_sign_up=mView.findViewById(R.id.card_view_sign_up);
        edt_confirm_password=mView.findViewById(R.id.edt_confirm_password);
        checkbox_terms_condition=mView.findViewById(R.id.checkbox_terms_condition);
        checkbox_partner=mView.findViewById(R.id.checkbox_partner);
        checkbox_contact=mView.findViewById(R.id.checkbox_contact);
        txt_terms_conditions=mView.findViewById(R.id.txt_terms_conditions);
        dbHelper=new DbHelper(getContext());
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
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
                   obj.put("guid",FirebaseInstanceId.getInstance().getToken());
                   btn_next.setEnabled(false);
                   progressBar.setVisibility(View.VISIBLE);
                   login(obj);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
       }else{
            try{
                String mobileNo=edt_mobile_number.getText().toString();
                String password=edt_pass_word.getText().toString();
                String confirmPassword=edt_confirm_password.getText().toString();
                String referral=edt_referral.getText().toString();
                if(!checkbox_terms_condition.isChecked()){
                    Helper.showAlertNetural(mView.getContext(),"Info","Please agree T&C's to proceed");
                }
                if((!TextUtils.isEmpty(mobileNo)) && (!TextUtils.isEmpty(password)) && (!TextUtils.isEmpty(confirmPassword)) && checkbox_terms_condition.isChecked()){
                   if(password.equalsIgnoreCase(confirmPassword)){
                       if(checkbox_terms_condition.isChecked()){
                           user_consent.append(checkbox_terms_condition.getText());
                       }if(checkbox_contact.isChecked()){
                           user_consent.append(";"+checkbox_contact.getText());
                       }if(checkbox_partner.isChecked()){
                           user_consent.append(";"+checkbox_partner.getText());
                       }
                       progressBar.setVisibility(View.VISIBLE);
                       JSONObject object=new JSONObject();
                       object.put("pws",password);
                       object.put("mobile_no",mobileNo);
                       object.put("app_version",BuildConfig.VERSION_NAME);
                       object.put("os","Android");
                       object.put("referal_code",referral);
                       object.put("source",signupType);
                       object.put("sts",1);
                       object.put("is_updated","0");
                       object.put("user_consent",user_consent);
                       object.put("method_Name",this.getClass().getSimpleName()+".btn_sign_up.onClick");
                       btn_sign_up.setEnabled(false);
                       ApiClient.getInstance().insertUser(Helper.encrypt(object.toString()))
                               .enqueue(new Callback<InsertResponse>() {
                                   @Override
                                   public void onResponse(Call<InsertResponse> call, Response<InsertResponse> response) {
                                       if(response.isSuccessful()){
                                           btn_sign_up.setEnabled(true);
                                           progressBar.setVisibility(View.GONE);
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
                                                   btn_sign_up.setEnabled(true);
                                                   Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
                                                   progressBar.setVisibility(View.GONE);
                                               } catch (IOException e) {

                                                   e.printStackTrace();

                                               }
                                           }
                                       }
                                   }

                                   @Override
                                   public void onFailure(Call<InsertResponse> call, Throwable t) {
                                       t.printStackTrace();
                                       btn_sign_up.setEnabled(true);
                                       Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                                   }
                               });
                   }else{
                       Helper.showAlertNetural(mView.getContext(),"Error","Please check password and confirm password");
                       btn_sign_up.setEnabled(true);
                   }
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
                            progressBar.setVisibility(View.GONE);
                            btn_next.setEnabled(true);
                            if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                try {
                                    JSONObject object=new JSONObject();
                                    object.put(Helper.MY_USER,response.body().getData().getMyUser());
                                    object.put(Helper.MY_USER_MSC,response.body().getData().getMyUsermsc());
                                    Helper.putUserSession(sharedpreferences,Helper.MY_USER,object);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Helper.createDirectory();

                                if(Helper.getUserSession(preferences,"MyUser")!=null) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(String.valueOf(Helper.getUserSession(preferences, Helper.MY_USER)));
                                        jsonObject.put(Helper.MY_USER_MSC,String.valueOf(Helper.getUserSession(preferences, Helper.MY_USER_MSC)));
                                        Helper.saveText(String.valueOf(jsonObject));;
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }




                                if(screen.equalsIgnoreCase("payment")){
                                    Fragment fragment=new PaymentFragment();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("conId",contestId);
                                    bundle.putDouble("credit",credit);
                                    bundle.putInt("contestAmt",contestAmt);
                                    fragment.setArguments(bundle);
                                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                                    ft.replace(R.id.main_content,fragment);
                                    ft.commit();
                                }if(screen.equalsIgnoreCase("prizesclaim")){
                                    Fragment fragment=new FragmentClaimPrizes();
                                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                                    ft.replace(R.id.main_content,fragment);
                                    ft.commit();
                                }if(screen.equalsIgnoreCase("mymatches")){
                                    Fragment fragment=new MyMatchesFragment();
                                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                                    ft.replace(R.id.main_content,fragment);
                                    ft.commit();
                                }if(screen.equalsIgnoreCase("accountlinking")){
                                    Fragment fragment=new AccountLinkFragment();
                                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                                    ft.replace(R.id.main_content,fragment);
                                    ft.commit();
                                }if(screen.equalsIgnoreCase("prizes")){
                                    Fragment fragment=new PrizesFragment();
                                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                                    ft.replace(R.id.main_content,fragment);
                                    ft.commit();
                                }if(screen.equalsIgnoreCase("detailfragment")){
                                    Fragment fragment=new MyDetailFragment();
                                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                                    ft.replace(R.id.frame_container,fragment);
                                    ft.commit();
                                }if(screen.equalsIgnoreCase("captain")){
                                    saveTeam(response.body().getData().getMyUser().getUserId().intValue());
                                    Fragment fragment=new DashboardFragment();
                                    Bundle bundle=new Bundle();
                                    bundle.putInt("contestId",contestId);
                                    fragment.setArguments(bundle);
                                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                                    ft.replace(R.id.main_content,fragment);
                                    ft.commit();
                                }

                            }else{
                                progressBar.setVisibility(View.GONE);
                                Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                btn_next.setEnabled(true);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        t.printStackTrace();
                        Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                        btn_next.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }


    public void saveTeam(int user_id){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("yyssddmm");
            List<PlayerBean> list= dbHelper.getMyTeam();
            JSONArray jsonArray = new JSONArray();
            for(PlayerBean bean:list) {

                JSONArray array = new JSONArray();
                array.put(bean.getId());
                if(bean.isCaptain()) {
                    array.put(1);
                }else{
                    array.put(0);
                }
                if(bean.isViceCaptain()) {
                    array.put(1);
                }else {
                    array.put(0);
                }
                jsonArray.put(array);
            }
            Log.e("beanList",jsonArray.toString());

            JSONObject object=new JSONObject();

            object.put("user_id",user_id);
            object.put("contest_id",contestId);
            object.put("name",user_id+"-"+sdf.format(new Date()));
            object.put("method_Name",this.getClass().getSimpleName()+".btn_done.onClick");
            object.put("playersInfo",jsonArray);
            object.put("coins",0);
            object.put("rem_budget",credit);

            ApiClient.getInstance().JoinContest(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<JoinContenstResponse>() {
                        @Override
                        public void onResponse(Call<JoinContenstResponse> call, Response<JoinContenstResponse> response) {
                            try{
                                if(response.isSuccessful()){
                                  //  pd.dismiss();
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                    if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                        dbHelper.deleteMyTeam();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                //Helper.showAlertNetural(mView.getContext(), "Success", response.body().getMessage());
                                            }
                                        },1000);
                                        Fragment fragment=new DashboardFragment();
                                        FragmentTransaction ft=getFragmentManager().beginTransaction();
                                        ft.replace(R.id.main_content,fragment);
                                        ft.commit();

                                    }else{
                                        Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                        Log.e("Pay",response.body().getMessage());
                                    }

                                }
                            }catch (Exception e){
                                e.printStackTrace();
                            }


                        }

                        @Override
                        public void onFailure(Call<JoinContenstResponse> call, Throwable t) {
                            t.printStackTrace();
                           // pd.dismiss();
                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error"+t.getMessage());

                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
