package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
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
    private static final String TAG = "FacebookLogin";
     private static final int RC_SIGN_IN =100 ;
    private View mView;
    private static final String EMAIL = "email";
    private EditText edt_mobile_no,edt_password,edt_mobile_number,edt_pass_word,edt_referral,edt_confirm_password,edt_team_name;
    private TextView txt_register;
    private Button btn_next,btn_sign_up;
    private SharedPreferences sharedpreferences;
    private int contestId,contestAmt;
    private double credit;
    private CardView card_view_sign_up;
    private LinearLayout linear_sign_in;
    private String signupType="form";
    private SharedPreferences preferences;

    private FragmentToActivity mCallback;
    private String screen;
    
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
        
        init();
        mCallback.communicate("disable");
        if(getArguments()!=null){
            credit=getArguments().getDouble("credit");
            contestId=getArguments().getInt("contestId");
            screen=getArguments().getString("screen");
            contestAmt=getArguments().getInt("contestAmt");
        }



        btn_next.setOnClickListener(this);
        btn_sign_up.setOnClickListener(this);
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

        btn_sign_up=mView.findViewById(R.id.btn_sign_up);
        txt_register=mView.findViewById(R.id.txt_register);
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        progressBar=mView.findViewById(R.id.progressBar);
        card_view_sign_up=mView.findViewById(R.id.card_view_sign_up);
        edt_confirm_password=mView.findViewById(R.id.edt_confirm_password);
        edt_team_name=mView.findViewById(R.id.edt_team_name);
        checkbox_terms_condition=mView.findViewById(R.id.checkbox_terms_condition);
        checkbox_partner=mView.findViewById(R.id.checkbox_partner);
        checkbox_contact=mView.findViewById(R.id.checkbox_contact);
        txt_terms_conditions=mView.findViewById(R.id.txt_terms_conditions);
        dbHelper=new DbHelper(getContext());


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

       /* if(RC_SIGN_IN==100) {
         Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = null;
            try {
                account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                e.printStackTrace();
            }

        }*//*else{
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
*/
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
                   obj.put("guid",FirebaseInstanceId.getInstance().getToken());
                   btn_next.setEnabled(false);
                   progressBar.setVisibility(View.VISIBLE);
                   login(obj);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
       }/*else if(v.getId()==R.id.sign_in_button){
           signIn();
       }if(v.getId()==R.id.login_button){
            signOut();
        }*/

       if(v.getId()==R.id.btn_sign_up){
           if(Helper.isConnectedToNetwork(getActivity())){
               try{
                   String mobileNo=edt_mobile_number.getText().toString();
                   String password=edt_pass_word.getText().toString();
                   String confirmPassword=edt_confirm_password.getText().toString();
                   String referral=edt_referral.getText().toString();
                   String teamName=edt_team_name.getText().toString();
                   StringBuilder user_consent = new StringBuilder();
                   if(checkbox_terms_condition.isChecked()){
                       user_consent.append(checkbox_terms_condition.getText());
                   }if(checkbox_contact.isChecked()){
                       user_consent.append(";"+checkbox_contact.getText());
                   }if(checkbox_partner.isChecked()){
                       user_consent.append(";"+checkbox_partner.getText());
                   }
                   if(checkbox_terms_condition.getVisibility()==View.VISIBLE && !checkbox_terms_condition.isChecked()){
                       Helper.showAlertNetural(mView.getContext(),"Info","Please agree T&C's to proceed");
                   }
                   if(TextUtils.isEmpty(edt_referral.getText().toString())){
                       referral=null;
                   }
                   if((!TextUtils.isEmpty(mobileNo)) && (!TextUtils.isEmpty(password)) && (!TextUtils.isEmpty(confirmPassword)) && checkbox_terms_condition.isChecked() &&(!TextUtils.isEmpty(teamName))){
                       if(password.equalsIgnoreCase(confirmPassword)){
                           
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
                           object.put("team_name",teamName);
                           object.put("method_Name",this.getClass().getSimpleName()+".btn_sign_up.onClick");
                           if(mobileNo.length()==11){
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
                                               call.cancel();
                                               t.printStackTrace();
                                               btn_sign_up.setEnabled(true);
                                               Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                                           }
                                       });
                           }else{
                               Helper.showAlertNetural(mView.getContext(),"Error","Please provide valid mobile number");
                           }
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
    }

    private void login(JSONObject obj) {
        if(Helper.isConnectedToNetwork(getActivity())){
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
//                                    saveTeam(response.body().getData().getMyUser().getUserId().intValue());
                                        Fragment fragment = null;
                                        if(contestAmt>0) {

                                            fragment = new PaymentFragment();
                                            Bundle bundle = new Bundle();
                                            bundle.putInt("contestAmt",contestAmt);
                                            fragment.setArguments(bundle);
                                        }else{
                                            fragment = new DashboardFragment();
                                            saveTeam(response.body().getData().getMyUser().getUserId().intValue());
                                        }
//                                    bundle.putInt("contestId",contestId);


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
                            call.cancel();
                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                            btn_next.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }
    }


    public void saveTeam(int user_id){
       if(Helper.isConnectedToNetwork(getActivity())){
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
//                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                       if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                           dbHelper.deleteMyTeam();
                                           new Handler().postDelayed(new Runnable() {
                                               @Override
                                               public void run() {
                                                   Helper.showAlertNetural(mView.getContext(), "Success", response.body().getMessage());
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

}
