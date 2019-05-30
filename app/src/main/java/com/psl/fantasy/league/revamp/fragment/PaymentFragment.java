package com.psl.fantasy.league.revamp.fragment;


import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.TelcoAdapter;
import com.psl.fantasy.league.revamp.model.response.JoinContest.JoinContenstResponse;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.SimPaisa.SimPaisaResponse;
import com.psl.fantasy.league.revamp.model.response.SimPaisaOTP.SimPaisaOTPResponse;
import com.psl.fantasy.league.revamp.model.ui.PlayerBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment implements View.OnClickListener {
    private View mView;
    private Button btn_pay;
    private EditText edt_amount;
    private DbHelper dbHelper;
    int ContestId; int userId;
    int contestAmt;
    TextView edt_mobile_no;
    String mobileNo;
    double credit;
    SharedPreferences preferences;
    int[] icon={R.drawable.mobilink_logo, R.drawable.warid_logo, R.drawable.telenor_logo, R.drawable.zong_logo};
    String[] telcoName={"Mobilink","Warid","Telenor","Zong"};
    Spinner spinner_telco;
    TelephonyManager telephonyManager;
    RelativeLayout relativePay,relativeOTP;
    EditText edt_otp;
    Button btn_submit;
    int operatorID;
    ProgressBar progressBar;
    String provider="";
    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_payment, container, false);
        btn_pay=mView.findViewById(R.id.btn_pay);
        edt_amount=mView.findViewById(R.id.edt_amount);
        edt_mobile_no=mView.findViewById(R.id.edt_mobile_no);
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        dbHelper=new DbHelper(mView.getContext());
        spinner_telco=mView.findViewById(R.id.spinner_telco);
        relativePay=mView.findViewById(R.id.relativePay);
        relativeOTP=mView.findViewById(R.id.relativeOTP);


        edt_otp=mView.findViewById(R.id.edt_otp);
        btn_submit=mView.findViewById(R.id.btn_submit);
        progressBar=mView.findViewById(R.id.progressBar);
        requestMultiplePermissions();
        if(getArguments()!=null){
            ContestId=getArguments().getInt("conId");
            credit=getArguments().getDouble("credit");
            contestAmt=getArguments().getInt("contestAmt");
        }
        try {
            edt_amount.setText(String.valueOf(contestAmt));
            JSONObject object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
            JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");

            mobileNo=String.format("%.0f",nameValuePairs.getJSONObject(Helper.MY_USER).getDouble("mobile_no"));
            userId = nameValuePairs.getJSONObject(Helper.MY_USER).getInt("user_id");
            if(!TextUtils.isEmpty(mobileNo)) {
                if(!mobileNo.startsWith("0")){
                    edt_mobile_no.setText("0"+mobileNo);
                }else{
                    edt_mobile_no.setText(mobileNo);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_pay.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
        TelcoAdapter adapter=new TelcoAdapter(mView.getContext(),R.layout.telco_adapter,icon,telcoName);
        spinner_telco.setAdapter(adapter);

        for(int i=0;i<telcoName.length;i++){
            if(telcoName[i].equalsIgnoreCase(provider)){
                spinner_telco.setSelection(i);
            }
        }
        return mView;
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        if(v.getId()==R.id.btn_pay){
            if(!TextUtils.isEmpty(edt_mobile_no.getText().toString())) {
                if(edt_mobile_no.getText().toString().startsWith("0")){
                    mobileNo =  edt_mobile_no.getText().toString().substring(1,edt_mobile_no.getText().length()) ;
                }else{
                    mobileNo =  edt_mobile_no.getText().toString();
                }
                if(spinner_telco.getSelectedItem().toString().equals("Mobilink")){
                    operatorID=100001;
                }if(spinner_telco.getSelectedItem().toString().equals("Telenor")){
                    operatorID=100002;
                }if(spinner_telco.getSelectedItem().toString().equals("Zong")){
                    operatorID=100003;
                }if(spinner_telco.getSelectedItem().toString().equals("Warid")){
                    operatorID=100004;
                }
                paySimPaisa();
            }
        }if(v.getId()==R.id.btn_submit){
            if(spinner_telco.getSelectedItem().toString().equals("Mobilink")){
                operatorID=100001;
            }if(spinner_telco.getSelectedItem().toString().equals("Telenor")){
                operatorID=100002;
            }if(spinner_telco.getSelectedItem().toString().equals("Zong")){
                operatorID=100003;
            }if(spinner_telco.getSelectedItem().toString().equals("Warid")){
                operatorID=100004;
            }
            paySimPaisaOTP();

        }
    }

    public void paySimPaisaOTP(){
        try{
            btn_submit.setEnabled(false);
            JSONObject object=new JSONObject();
            if(operatorID==100002) {
                object.put("productID", 1193);
            }else{
                object.put("productID", 1195);
            }
            object.put("mobileNo",mobileNo);
            object.put("operatorID",operatorID);
            object.put("codeOTP",Integer.parseInt(edt_otp.getText().toString()));
            object.put("amt",contestAmt);
            if(Helper.getUserSession(preferences,Helper.MY_USER)!=null) {
                JSONObject jsonObject = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=jsonObject.getJSONObject("nameValuePairs");
                object.put("user_id", nameValuePairs.getJSONObject("MyUser").getInt("user_id"));
            }

            ApiClient.getInstance().verifyPaymentSimPaisa(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<SimPaisaOTPResponse>() {
                        @Override
                        public void onResponse(Call<SimPaisaOTPResponse> call, Response<SimPaisaOTPResponse> response) {
                            if(response.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                btn_submit.setEnabled(true);
                                if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                    saveTeam();
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
                                }else{
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                }
                            }else{
                                progressBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onFailure(Call<SimPaisaOTPResponse> call, Throwable t) {
                            t.printStackTrace();
                            btn_submit.setEnabled(true);
                            progressBar.setVisibility(View.GONE);
                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void paySimPaisa(){
        try {
            btn_pay.setEnabled(false);
            JSONObject object=new JSONObject();
            object.put("productID",1195);
            object.put("mobileNo",mobileNo);
            object.put("operatorID",operatorID);
            ApiClient.getInstance().makePaymentSimPaisa(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<SimPaisaResponse>() {
                        @Override
                        public void onResponse(Call<SimPaisaResponse> call, Response<SimPaisaResponse> response) {
                            if(response.isSuccessful()){
                                progressBar.setVisibility(View.GONE);
                                btn_pay.setEnabled(true);
                                if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                    relativePay.setVisibility(View.GONE);
                                    relativeOTP.setVisibility(View.VISIBLE);
                                }
                                else{
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                    btn_pay.setEnabled(true);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<SimPaisaResponse> call, Throwable t) {
                            t.printStackTrace();
                            btn_pay.setEnabled(true);
                            Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");
                            progressBar.setVisibility(View.GONE);
                        }
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void saveTeam(){
       try{
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

           object.put("user_id",userId);
           object.put("contest_id",ContestId);
           object.put("name","Android");
           object.put("method_Name",this.getClass().getSimpleName()+".btn_done.onClick");
           object.put("playersInfo",jsonArray);
           object.put("coins",0);
           object.put("rem_budget",credit);

           ApiClient.getInstance().JoinContest(Helper.encrypt(object.toString()))
                   .enqueue(new Callback<JoinContenstResponse>() {
                       @Override
                       public void onResponse(Call<JoinContenstResponse> call, Response<JoinContenstResponse> response) {
                           if(response.isSuccessful()){
                               progressBar.setVisibility(View.GONE);
                               if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                   dbHelper.deleteMyTeam();
//                                Helper.showAlertNetural(mView.getContext(), "Success", response.body().getMessage());
                               }else{
                                   Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                   Log.e("Pay",response.body().getMessage());
                               }

                           }else{
                               try {
                                   Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
                                   Log.e("Error",response.errorBody().string());
                                   progressBar.setVisibility(View.GONE);
                               } catch (IOException e) {
                                   e.printStackTrace();
                               }
                           }


                       }

                       @Override
                       public void onFailure(Call<JoinContenstResponse> call, Throwable t) {
                           t.printStackTrace();
                           progressBar.setVisibility(View.GONE);
                           Helper.showAlertNetural(mView.getContext(),"Error","Communication Error");

                       }
                   });
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    private void requestMultiplePermissions(){

        Dexter.withActivity(getActivity())
                .withPermissions(
                        Manifest.permission.READ_PHONE_STATE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            telephonyManager = (TelephonyManager) mView.getContext().getSystemService(Context.TELEPHONY_SERVICE);
                             provider = telephonyManager.getSimOperatorName();
                        }
                        /*if(!report.areAllPermissionsGranted()){
                            Toast toast=Toast.makeText(mView.getContext(),"To continue allow permission",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                            finish();
                        }*/
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
//                            openSettingsDialog();
                        }
                    }


                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(mView.getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }
}
