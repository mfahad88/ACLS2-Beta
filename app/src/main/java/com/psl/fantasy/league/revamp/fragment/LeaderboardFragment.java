package com.psl.fantasy.league.revamp.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psl.fantasy.league.revamp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class LeaderboardFragment extends Fragment {
    private TabLayout allTabs;
    private View mView;
    private int userId; private int contestId;
    Fragment fragment;
    Bundle bundle;
    public LeaderboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        allTabs = mView.findViewById(R.id.tabs);
        if(getArguments()!=null){
            userId=getArguments().getInt("userId");
            contestId=getArguments().getInt("contestId");
        }
        bindWidgetsWithAnEvent();
        setupTabLayout();
        return mView;
    }

    private void setupTabLayout() {

        allTabs.addTab(allTabs.newTab().setText("My Teams"),true);
        allTabs.addTab(allTabs.newTab().setText("My Leaderboard"));
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

                fragment = new MyMatchesTabFragment();
                bundle = new Bundle();
                bundle.putInt("userId",userId);
                bundle.putInt("contestId",contestId);
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
            case 1 :
                fragment = new MyLeaderboardTabFragment();
                bundle = new Bundle();
                bundle.putInt("userId",userId);
                bundle.putInt("contestId",contestId);
                fragment.setArguments(bundle);
                replaceFragment(fragment);
                break;
        }
    }
    public void replaceFragment(Fragment fragment) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frame_container, fragment);
        //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.addToBackStack(null);
        ft.commit();
    }
}
