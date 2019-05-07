package com.psl.fantasy.league.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.LoggingBehavior;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Config.ConfigBeanResponse;
import com.psl.fantasy.league.model.response.Player.PlayerResponse;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

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
        requestMultiplePermissions();

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

    private void requestMultiplePermissions(){

        Dexter.withActivity(this)
                .withPermissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {

                            getMatches();
                            getConfig();
                        }
                         if(!report.areAllPermissionsGranted()){
                            Toast toast=Toast.makeText(getApplicationContext(),"To continue allow permission",Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER,0,0);
                            toast.show();
                             finish();
                         }
                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings
                            openSettingsDialog();
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
                        Toast.makeText(getApplicationContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();

    }

    private void openSettingsDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Required Permissions");
        builder.setMessage("This app require permission to use awesome feature. Grant them in app settings.");
        builder.setPositiveButton("Take Me To SETTINGS", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 101);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
}
