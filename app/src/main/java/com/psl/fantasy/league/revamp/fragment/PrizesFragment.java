package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.PrizesAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrizesFragment extends Fragment {
    View mView;
    PrizesAdapter adapter;
    public PrizesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_prizes, container, false);
        ViewPager viewPager=mView.findViewById(R.id.pager);
        TabLayout tabLayout=mView.findViewById(R.id.tab_layout);
        adapter=new PrizesAdapter(getFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager.setOffscreenPageLimit(tabLayout.getTabCount());
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        tabLayout.addTab(tabLayout.newTab().setText("Claim"),true);
        tabLayout.addTab(tabLayout.newTab().setText("Redeem"));
        if(Helper.getUserSession(mView.getContext().getSharedPreferences(Helper.MY_USER,Context.MODE_PRIVATE),Helper.MY_USER)==null){
            Fragment fragment=new LoginFragment();
            Bundle bundle=new Bundle();
            bundle.putString("screen","prizes");
            fragment.setArguments(bundle);
            AppCompatActivity activity=(AppCompatActivity)mView.getContext();
            FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_content,fragment);
            ft.commit();
        }
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                tabLayout.getTabAt(i).select();
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        return mView;
    }

}
