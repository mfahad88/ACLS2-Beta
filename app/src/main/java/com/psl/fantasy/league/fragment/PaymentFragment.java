package com.psl.fantasy.league.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.activity.StartActivity;
import com.psl.fantasy.league.adapter.TelcoAdapter;
import com.psl.fantasy.league.model.response.JoinContest.JoinContenstResponse;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.ui.PlayerBean;
import com.psl.fantasy.league.model.ui.TelcoBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
    private DbHelper dbHelper;
    int ContestId; int userId;
    TextView edt_mobile_no;
    String mobileNo;
    double credit;
    SharedPreferences preferences;
    int[] icon={R.drawable.mobilink_logo, R.drawable.warid_logo, R.drawable.telenor_logo, R.drawable.zong_logo,R.drawable.ufone_logo};
    String[] telcoName={"Mobilink","Warid","Telenor","Zong","Ufone"};
    Spinner spinner_telco;
    TelephonyManager telephonyManager;
    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_payment, container, false);
        btn_pay=mView.findViewById(R.id.btn_pay);
        edt_mobile_no=mView.findViewById(R.id.edt_mobile_no);
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        dbHelper=new DbHelper(mView.getContext());
        spinner_telco=mView.findViewById(R.id.spinner_telco);

        telephonyManager = (TelephonyManager) mView.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        String provider = telephonyManager.getSimOperatorName();


        if(getArguments()!=null){
            ContestId=getArguments().getInt("conId");
            credit=getArguments().getDouble("credit");
        }
        try {
            /*if(Helper.getUserSession(preferences,"MyUser")!=null) {
                JSONObject jsonObject = new JSONObject(String.valueOf(Helper.getUserSession(preferences, "MyUser")));

                mobileNo=String.format("%.0f",jsonObject.getDouble("mobile_no"));
                userId = jsonObject.getInt("user_id");

            }else{
                if(!TextUtils.isEmpty(Helper.getUserIdFromText())) {
                    JSONObject object=new JSONObject(Helper.getUserIdFromText());
                    mobileNo=object.getString("mobile_no");
                    userId = object.getInt("user_id");

                }
            }*/
            JSONObject jsonObject = new JSONObject(String.valueOf(Helper.getUserSession(preferences, "MyUser")));

            mobileNo=String.format("%.0f",jsonObject.getDouble("mobile_no"));
            userId = jsonObject.getInt("user_id");
            //if(!TextUtils.isEmpty(mobileNo)) {
                edt_mobile_no.setText("0"+mobileNo);
            //}
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btn_pay.setOnClickListener(this);
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
        try {
            object.put("user_id",userId);
            object.put("contest_id",ContestId);
            object.put("name","Android");
            object.put("method_Name",this.getClass().getSimpleName()+".btn_done.onClick");
            object.put("playersInfo",jsonArray);
            object.put("coins",0);
            object.put("rem_budget",credit);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiClient.getInstance().JoinContest(Helper.encrypt(object.toString()))
                .enqueue(new Callback<JoinContenstResponse>() {
                    @Override
                    public void onResponse(Call<JoinContenstResponse> call, Response<JoinContenstResponse> response) {
                        if(response.isSuccessful()){
                            if(response.body().getResponseCode().equalsIgnoreCase("1001")) {
                                dbHelper.deleteMyTeam();
                                Helper.showAlertNetural(mView.getContext(), "Success", response.body().getMessage());
                            }else{
                                Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                Log.e("Pay",response.body().getMessage());
                            }

                        }else{
                            try {
                                Helper.showAlertNetural(mView.getContext(),"Error",response.errorBody().string());
                                Log.e("Error",response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Intent intent=new Intent(mView.getContext(),StartActivity.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onFailure(Call<JoinContenstResponse> call, Throwable t) {
                        t.printStackTrace();

                    }
                });
    }
}
