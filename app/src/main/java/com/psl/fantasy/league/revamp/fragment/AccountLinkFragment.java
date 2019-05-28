package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.AccountLinking.BankInfo;
import com.psl.fantasy.league.revamp.model.response.AccountLinking.LinkingBean;
import com.psl.fantasy.league.revamp.model.response.AccountLinking.OTPBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class AccountLinkFragment extends Fragment implements View.OnClickListener {
    RadioGroup radio_group_linking;
    View mView;
    RelativeLayout relative_linking,relative_OTP,relative_other;
    EditText edt_cnic,edt_mobile_no,edt_otp,edt_acc,edt_toa;
    SharedPreferences preferences;
    String mobileNo;
    TextView txt_expires;
    Button btn_ok,btn_submit,btn_other;
    Spinner spinner_bank;
    ProgressBar progressBar;
    int user_id;
    public AccountLinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_account_link, container, false);
        radio_group_linking=mView.findViewById(R.id.radio_group_linking);
        relative_linking=mView.findViewById(R.id.relative_linking);
        relative_OTP=mView.findViewById(R.id.relative_OTP);
        relative_other=mView.findViewById(R.id.relative_other);
        edt_cnic=mView.findViewById(R.id.edt_cnic);
        edt_mobile_no=mView.findViewById(R.id.edt_mobile_no);
        edt_otp=mView.findViewById(R.id.edt_otp);
        edt_acc=mView.findViewById(R.id.edt_acc);
        edt_toa=mView.findViewById(R.id.edt_toa);
        btn_ok=mView.findViewById(R.id.btn_ok);
        btn_submit=mView.findViewById(R.id.btn_submit);
        btn_other=mView.findViewById(R.id.btn_other);
        txt_expires=mView.findViewById(R.id.txt_expires);
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        progressBar=mView.findViewById(R.id.progressBar);
        spinner_bank=mView.findViewById(R.id.spinner_bank);
        if(Helper.getUserSession(preferences,Helper.MY_USER)!=null){
            JSONObject object= null;
            try {
                object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");
                populateItems();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else{
            Fragment fragment=new LoginFragment();
            Bundle bundle=new Bundle();
            bundle.putString("screen","accountlinking");
            fragment.setArguments(bundle);
            AppCompatActivity activity=(AppCompatActivity)getActivity();
            FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frame_container,fragment);
            ft.commit();

        }

        (mView.findViewById(R.id.radio_js_wallet)).setSelected(true);
        radio_group_linking.check(R.id.radio_js_wallet);

        relative_linking.setVisibility(View.VISIBLE);

        radio_group_linking.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selected=group.getCheckedRadioButtonId();
                if(selected==R.id.radio_js_wallet){
                    relative_linking.setVisibility(View.VISIBLE);
                    relative_OTP.setVisibility(View.GONE);
                    relative_other.setVisibility(View.GONE);
                    populateItems();
                }else{
                    relative_linking.setVisibility(View.GONE);
                    relative_OTP.setVisibility(View.GONE);
                    relative_other.setVisibility(View.VISIBLE);
                    populateSpinner();
                    edt_acc.setText("");
                    edt_toa.setText("");
                }
            }
        });
        btn_ok.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        btn_other.setOnClickListener(this);
        return mView;
    }
    private void populateSpinner(){
        List<String> list=new ArrayList<>();
        list.add("HBL");
        list.add("UBL");
        list.add("Soneri");
        ArrayAdapter adapter=new ArrayAdapter(mView.getContext(),android.R.layout.simple_spinner_item, list);
        spinner_bank.setAdapter(adapter);
    }
    private void populateItems(){
        try {

            JSONObject object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
            JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");

            //JSONObject jsonObject = new JSONObject(String.valueOf(Helper.getUserSession(preferences, "MyUser")));

            mobileNo=String.format("%.0f",nameValuePairs.getJSONObject("MyUser").getDouble("mobile_no"));
            if(!TextUtils.isEmpty(mobileNo)) {
                if(!mobileNo.startsWith("0")){
                    edt_mobile_no.setText("0"+mobileNo);
                }else{
                    edt_mobile_no.setText(mobileNo);
                }
            }
            JSONObject jsonObject=new JSONObject(Helper.getUserSession(preferences,Helper.CNIC).toString());
            JSONObject nameValuePairs2=jsonObject.getJSONObject("nameValuePairs");
            if(!TextUtils.isEmpty(nameValuePairs2.getString(Helper.CNIC))){
                edt_cnic.setText(nameValuePairs2.getString(Helper.CNIC));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_ok){
            progressBar.setVisibility(View.VISIBLE);
            btn_ok.setEnabled(false);
            if(TextUtils.isEmpty(edt_cnic.getText()) || TextUtils.isEmpty(edt_mobile_no.getText())){
                Helper.showAlertNetural(mView.getContext(),"Error","Please check cnic and mobile number");
                btn_ok.setEnabled(true);
                progressBar.setVisibility(View.GONE);
            }else{
                if(edt_cnic.getText().length()==13 && edt_mobile_no.getText().length()==11){
                    try {
                        JSONObject object=new JSONObject();
                        object.put("cnic",edt_cnic.getText().toString());
                        object.put("mobileNumber",edt_mobile_no.getText().toString());
                        object.put("method_name",this.getClass().getSimpleName()+"btn_ok.onClick");
                        ApiClient.getInstance().verifyAccount(Helper.encrypt(object.toString()))
                                .enqueue(new Callback<LinkingBean>() {
                                    @Override
                                    public void onResponse(Call<LinkingBean> call, Response<LinkingBean> response) {
                                        btn_ok.setEnabled(true);
                                        progressBar.setVisibility(View.GONE);
                                        if(response.isSuccessful()){
                                            if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                                relative_linking.setVisibility(View.GONE);
                                                relative_OTP.setVisibility(View.VISIBLE);
                                                new CountDownTimer(60000,1000){

                                                    @Override
                                                    public void onTick(long millisUntilFinished) {
                                                        txt_expires.setText(""+String.format("%d min, %d sec",
                                                                TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                                        Log.e("Counter---->",""+String.format("%d min, %d sec",
                                                                TimeUnit.MILLISECONDS.toMinutes( millisUntilFinished),
                                                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                                                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))));
                                                    }

                                                    @Override
                                                    public void onFinish() {
                                                        Fragment fragment=new AccountLinkFragment();
                                                        FragmentTransaction ft=getFragmentManager().beginTransaction();
                                                        ft.replace(R.id.frame_container,fragment);
                                                        ft.commit();
                                                    }
                                                }.start();
                                                //Helper.showAlertNetural(mView.getContext(),"Success",response.body().getMessage());
                                            }else{
                                                Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<LinkingBean> call, Throwable t) {
                                        t.printStackTrace();
                                        btn_ok.setEnabled(true);
                                        progressBar.setVisibility(View.GONE);
                                        Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                                    }
                                });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    Helper.showAlertNetural(mView.getContext(),"Error","Please check cnic and mobile number");
                    btn_ok.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }
            }
        }if(v.getId()==R.id.btn_submit){
            try{
                btn_submit.setEnabled(false);
                progressBar.setVisibility(View.VISIBLE);
                if(TextUtils.isEmpty(edt_otp.getText())){
                    Helper.showAlertNetural(mView.getContext(),"Error","Enter valid OTP");
                    btn_submit.setEnabled(true);
                    progressBar.setVisibility(View.GONE);
                }else{
                    if(edt_otp.getText().length()==5){
                        try {
                            JSONObject object=new JSONObject();
                            object.put("cnic",edt_cnic.getText());
                            object.put("mobileNumber",edt_mobile_no.getText());
                            object.put("otp",edt_otp.getText());
                            ApiClient.getInstance().verifyOtp(Helper.encrypt(object.toString()))
                                    .enqueue(new Callback<OTPBean>() {
                                        @Override
                                        public void onResponse(Call<OTPBean> call, Response<OTPBean> response) {
                                            progressBar.setVisibility(View.GONE);
                                            if(response.isSuccessful()){
                                                btn_submit.setEnabled(true);
                                                if(response.body().getResponseCode().equalsIgnoreCase("1001")){

                                                    try {
                                                        JSONObject object=new JSONObject();
                                                        object.put(Helper.CNIC,edt_cnic.getText().toString());
                                                        Helper.putUserSession(preferences,Helper.CNIC,object);
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }


                                                    new Handler().postDelayed(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            Helper.showAlertNetural(mView.getContext(),"Success",response.body().getMessage());
                                                        }
                                                    },1000);
                                                    AppCompatActivity activity=(AppCompatActivity)getActivity();
                                                    Fragment fragment=new DashboardFragment();
                                                    FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
                                                    ft.replace(R.id.main_content,fragment);
                                                    ft.commit();
                                                }else {
                                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                                }
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<OTPBean> call, Throwable t) {
                                            t.printStackTrace();
                                            btn_submit.setEnabled(true);
                                            progressBar.setVisibility(View.GONE);
                                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                                        }
                                    });
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        Helper.showAlertNetural(mView.getContext(),"Error","Invalid OTP length");
                        btn_submit.setEnabled(true);
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }if(v.getId()==R.id.btn_other){
            if(!TextUtils.isEmpty(edt_acc.getText().toString())){
                try{
                    btn_other.setEnabled(false);
                    JSONObject object=new JSONObject();
                    object.put("user_id",user_id);
                    object.put("bank_IMD",036);
                    object.put("bank_Name",spinner_bank.getSelectedItem().toString());
                    object.put("core_account",edt_acc.getText());
                    object.put("title_of_account",edt_toa.getText());
                    ApiClient.getInstance().updateBankTitle(Helper.encrypt(object.toString()))
                            .enqueue(new Callback<BankInfo>() {
                                @Override
                                public void onResponse(Call<BankInfo> call, Response<BankInfo> response) {

                                    if(response.isSuccessful()){
                                        if(response.body().getResponseCode().equalsIgnoreCase("1001")){

                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Helper.showAlertNetural(mView.getContext(),"Success",response.body().getMessage());
                                                }
                                            },1000);
                                            btn_other.setEnabled(true);
                                            /*AppCompatActivity revamp.activity=(AppCompatActivity)mView.getContext();
                                            FragmentTransaction ft=revamp.activity.getSupportFragmentManager().beginTransaction();
                                            ft.replace(R.id.frame_container,new BalanceFragment());
                                            ft.commit();*/

                                        }else{
                                            Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                            btn_other.setEnabled(true);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<BankInfo> call, Throwable t) {
                                    t.printStackTrace();
                                    Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                                }
                            });
                    //edt_toa.setText("Rizwan Nasir");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
