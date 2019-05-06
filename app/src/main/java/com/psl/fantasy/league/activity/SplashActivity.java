package com.psl.fantasy.league.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Config.ConfigBeanResponse;
import com.psl.fantasy.league.model.response.Player.PlayerResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        dbHelper=new DbHelper(this);
        JSONObject obj=new JSONObject();
        Helper.printHashKey(this);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        FacebookSdk.addLoggingBehavior(LoggingBehavior.APP_EVENTS);
        FacebookSdk.setAutoLogAppEventsEnabled(true);
        getMatches();
        getConfig();
    }

    private void getMatches(){
        ApiClient.getInstance().getPlayersMatches().enqueue(new Callback<PlayerResponse>() {
            @Override
            public void onResponse(Call<PlayerResponse> call, Response<PlayerResponse> response) {
                if(response.isSuccessful()){
                    if(response.body().getResponseCode().equals("1001")){

                        dbHelper.savePlayers(response.body().getData());
                    }
                }else{
                    try {
                        Helper.showAlertNetural(SplashActivity.this,"Error",response.errorBody().string());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<PlayerResponse> call, Throwable t) {
                t.fillInStackTrace();
                Helper.showAlertNetural(SplashActivity.this,"Error",t.getMessage());
            }
        });
    }

    private void getConfig(){
        try{
            JSONObject obj=new JSONObject();
            obj.put("param_type","GF");
            obj.put("userId","1001");
            obj.put("method_Name",this.getClass().getSimpleName()+".onCreate");
            System.out.println(obj.toString());
            dbHelper.deletePlayer();
            dbHelper.deleteConfig();


            ApiClient.getInstance().getConfig(Helper.encrypt(obj.toString()))
                    .enqueue(new Callback<ConfigBeanResponse>() {
                        @Override
                        public void onResponse(Call<ConfigBeanResponse> call, Response<ConfigBeanResponse> response) {
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equals("1001")){

                                    dbHelper.saveConfig(response.body().getData());

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent intent=new Intent(SplashActivity.this,StartActivity.class);

                                            startActivity(intent);
                                            finish();
                                        }
                                    },500);
                                }
                            }else{
                                try {
                                    Helper.showAlertNetural(SplashActivity.this,"Error",response.errorBody().string());

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ConfigBeanResponse> call, Throwable t) {
                            t.printStackTrace();
                            Helper.showAlertNetural(SplashActivity.this,"Error",t.getMessage());

                        }
                    });


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
