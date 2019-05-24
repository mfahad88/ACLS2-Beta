package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BalanceFragment extends Fragment {

    private View mView;
    private TabLayout allTabs;
    private Fragment fragment;
    private FragmentToActivity mCallback;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement FragmentToActivity");
        }
    }
    public BalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_leaderboard, container, false);
        allTabs = mView.findViewById(R.id.tabs);
        mCallback.communicate("disable");

        bindWidgetsWithAnEvent();
        setupTabLayout();
        return mView;
    }

    private void setupTabLayout() {
        allTabs.addTab(allTabs.newTab().setText("Account Linking"),true);
        allTabs.addTab(allTabs.newTab().setText("My Detail"));
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

                fragment = new AccountLinkFragment();

                replaceFragment(fragment);
                break;
            case 1 :

                fragment = new MyDetailFragment();

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
