package com.psl.fantasy.league.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psl.fantasy.league.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {

    private View mView;
    private TabLayout allTabs;
    private Fragment fragment;
    public BalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        allTabs = mView.findViewById(R.id.tabs);

        bindWidgetsWithAnEvent();
        setupTabLayout();
        return mView;
    }

    private void setupTabLayout() {

        allTabs.addTab(allTabs.newTab().setText("Points"),true);
        allTabs.addTab(allTabs.newTab().setText("Coins"));
    }

    private void bindWidgetsWithAnEvent()
    {
        allTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                setCurrentTabFragment(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    private void setCurrentTabFragment(int tabPosition)
    {
        switch (tabPosition)
        {
            case 0 :

                fragment = new PointsFragment();

                replaceFragment(fragment);
                break;
            case 1 :
                fragment = new CoinsFragment();
                replaceFragment(fragment);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {
        AppCompatActivity activity=(AppCompatActivity)mView.getContext();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
        FragmentTransaction ft=activity.getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();
    }
}
