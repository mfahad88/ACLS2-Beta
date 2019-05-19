package com.psl.fantasy.league.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.ImageAdapter;
import com.psl.fantasy.league.fragment.BalanceFragment;
import com.psl.fantasy.league.fragment.DashboardFragment;

import com.psl.fantasy.league.fragment.MoreFragment;
import com.psl.fantasy.league.fragment.MyMatchesFragment;
import com.psl.fantasy.league.fragment.TeamFragment;
import com.psl.fantasy.league.interfaces.FragmentToActivity;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class StartActivity extends AppCompatActivity implements FragmentToActivity {

    private TextView mTextMessage;
    private Fragment fragment;
    private ViewPager viewPage;
    private TextView txt_bullet_1,txt_bullet_2,txt_bullet_3;
    private LinearLayout linear_pointer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        mTextMessage = (TextView) findViewById(R.id.message);
        txt_bullet_1 = findViewById(R.id.txt_bullet_1);
        txt_bullet_2 = findViewById(R.id.txt_bullet_2);
        txt_bullet_3 = findViewById(R.id.txt_bullet_3);
        linear_pointer= findViewById(R.id.linear_pointer);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        setBottomNavigationLabelsTextSize(navigation,0.9f);
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
                        fragment=new BalanceFragment();
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
}
