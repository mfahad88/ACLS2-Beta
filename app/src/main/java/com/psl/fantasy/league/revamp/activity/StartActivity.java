package com.psl.fantasy.league.revamp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.fragment.AccountLinkFragment;
import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.fragment.DashboardFragment;

import com.psl.fantasy.league.revamp.fragment.MoreFragment;
import com.psl.fantasy.league.revamp.fragment.MyMatchesFragment;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;
import com.psl.fantasy.league.revamp.model.response.UserNotification.Datum;
import com.psl.fantasy.league.revamp.model.response.UserNotification.GetUserNotificationBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity implements FragmentToActivity {

    private TextView mTextMessage;
    private Fragment fragment;
    private ViewPager viewPage;
    private TextView txt_bullet_1,txt_bullet_2,txt_bullet_3,txtNotifCount;
    private LinearLayout linear_pointer;
    SharedPreferences preferences;
    private int user_id;
    private DbHelper dbHelper;
    private RelativeLayout relative_notification;
    PopupMenu popupMenu;
    List<Datum> list_subject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        preferences=getSharedPreferences(Helper.SHARED_PREF,MODE_PRIVATE);
        dbHelper=new DbHelper(this);
        txtNotifCount=findViewById(R.id.txtNotifCount);
        list_subject = new ArrayList<Datum>();

        txt_bullet_1 = findViewById(R.id.txt_bullet_1);
        txt_bullet_2 = findViewById(R.id.txt_bullet_2);
        txt_bullet_3 = findViewById(R.id.txt_bullet_3);
        linear_pointer= findViewById(R.id.linear_pointer);
        Helper.checkAppVersion(this, preferences, dbHelper);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        setBottomNavigationLabelsTextSize(navigation,0.7f);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                    fragment=new DashboardFragment();
                        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.main_content,fragment);
                        ft.commit();
                        return true;
                    case R.id.my_details:
                        fragment=new MyMatchesFragment();
                        FragmentTransaction ft_dash=getSupportFragmentManager().beginTransaction();
                        ft_dash.replace(R.id.main_content,fragment);
                        ft_dash.commit();
                        return true;
                    /*case R.id.prizes:
                        fragment=new FragmentPrizes();
                        FragmentTransaction ft_prizes=getSupportFragmentManager().beginTransaction();
                        ft_prizes.replace(R.id.main_content,fragment);
                        ft_prizes.commit();
                        return true;*/

                    case R.id.balance:
//                        fragment=new BalanceFragment();
                        fragment=new AccountLinkFragment();
                        FragmentTransaction ft_bal=getSupportFragmentManager().beginTransaction();
                        ft_bal.replace(R.id.main_content,fragment);
                        ft_bal.commit();
                        return true;

                    case R.id.more:
                        fragment=new MoreFragment();
                        FragmentTransaction ft_more=getSupportFragmentManager().beginTransaction();
                        ft_more.replace(R.id.main_content,fragment);
                        ft_more.commit();
                        return true;
                }
                return false;
            }
        });

        navigation.setSelectedItemId(R.id.navigation_home);
        if(Helper.getUserSession(preferences,Helper.MY_USER)!=null){
            JSONObject object= null;
            try {
                object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");
                getNotification();
                txtNotifCount.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Integer.parseInt(txtNotifCount.getText().toString())>0){
                            popupMenu=new PopupMenu(getApplicationContext(),v);
                            for(Datum datum:list_subject){
                                popupMenu.getMenu().add(0,datum.getUserNotifId(),0,datum.getSubj());
                            }
                            popupMenu.show();
                            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    for(Datum datum:list_subject) {
                                        if(datum.getUserNotifId()==item.getItemId()) {
                                            Helper.showAlertNetural(StartActivity.this,datum.getSubj(),datum.getMsg());
                                        }
                                    }
                                    return false;
                                }
                            });
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void communicate(String comm) {
        if(comm.equalsIgnoreCase("DashboardFragment")){
            linear_pointer.setVisibility(View.VISIBLE);
            txt_bullet_1.setEnabled(false);
        }if(comm.equalsIgnoreCase("ContestFragment")){
            txt_bullet_2.setEnabled(false);
        }if(comm.equalsIgnoreCase("TeamFragment")){
            txt_bullet_3.setEnabled(false);
        }if(comm.equalsIgnoreCase("disable")){
            txt_bullet_1.setEnabled(true);
            txt_bullet_2.setEnabled(true);
            txt_bullet_3.setEnabled(true);
            linear_pointer.setVisibility(View.GONE);
        }

    }

    private void setBottomNavigationLabelsTextSize(BottomNavigationView bottomNavigationView, float ratio) {
        for (int i = 0; i < bottomNavigationView.getChildCount(); i++) {
            View item = bottomNavigationView.getChildAt(i);

            if (item instanceof BottomNavigationMenuView) {
                BottomNavigationMenuView menu = (BottomNavigationMenuView) item;

                for (int j = 0; j < menu.getChildCount(); j++) {
                    View menuItem = menu.getChildAt(j);

                    View small = menuItem.findViewById(android.support.design.R.id.smallLabel);
                    if (small instanceof TextView) {
                        float size = ((TextView) small).getTextSize();
                        ((TextView) small).setTextSize(TypedValue.COMPLEX_UNIT_PX, ratio * size);
                    }
                    View large = menuItem.findViewById(android.support.design.R.id.largeLabel);
                    if (large instanceof TextView) {
                        float size = ((TextView) large).getTextSize();
                        ((TextView) large).setTextSize(TypedValue.COMPLEX_UNIT_PX, ratio * size);
                    }
                }
            }
        }
    }

    public void getNotification(){
        if(Helper.isConnectedToNetwork(this)){
            try{
                JSONObject object=new JSONObject();
                object.put("user_id",user_id);
                ApiClient.getInstance().getUserNotif(Helper.encrypt(object.toString()))
                        .enqueue(new Callback<GetUserNotificationBean>() {
                            @Override
                            public void onResponse(Call<GetUserNotificationBean> call, Response<GetUserNotificationBean> response) {
                                if(response.isSuccessful()){
                                    if(response.body().getResponseCode().equalsIgnoreCase("1001")){


                                        txtNotifCount.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                txtNotifCount.setText(String.valueOf(response.body().getData().size()));
                                                for(Datum datum:response.body().getData()){
                                                    list_subject.add(datum);

                                                }

                                            }
                                        });
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<GetUserNotificationBean> call, Throwable t) {
                                call.cancel();
                                Helper.showAlertNetural(StartActivity.this,"Error","Communication Error");
                                t.printStackTrace();

                            }
                        });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.deleteConfig();
        dbHelper.deleteMyTeam();
        //dbHelper.deletePlayer();

        super.onDestroy();
    }


}
