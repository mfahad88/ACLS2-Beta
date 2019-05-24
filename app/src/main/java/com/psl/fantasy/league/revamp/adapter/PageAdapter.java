package com.psl.fantasy.league.revamp.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.fragment.CreateTeamFragment;
import com.psl.fantasy.league.revamp.interfaces.FragmentInterface;

public class PageAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;
    Fragment fragment;
    FragmentInterface fragmentInterface;
    int teamId1; int teamId2;

    public PageAdapter(FragmentManager fm, int numOfTabs, FragmentInterface fragmentInterface, int teamId1, int teamId2) {
        super(fm);
        this.numOfTabs = numOfTabs;
        this.fragmentInterface=fragmentInterface;
        this.teamId1=teamId1;
        this.teamId2=teamId2;

    }



    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:

                fragment=new CreateTeamFragment(fragmentInterface);
                Bundle bundle_wk=new Bundle();
                bundle_wk.putInt("Player_Type",3);
                bundle_wk.putInt("teamId1",teamId1);
                bundle_wk.putInt("teamId2",teamId2);
                fragment.setArguments(bundle_wk);
                Log.e("Player_Type", String.valueOf(3));
                return fragment;
            case 1:

                fragment=new CreateTeamFragment(fragmentInterface);
                Bundle bundle_bat=new Bundle();
                bundle_bat.putInt("Player_Type",0);
                bundle_bat.putInt("teamId1",teamId1);
                bundle_bat.putInt("teamId2",teamId2);
                fragment.setArguments(bundle_bat);
                Log.e("Player_Type", String.valueOf(0));
                return fragment;
            case 2:

                fragment=new CreateTeamFragment(fragmentInterface);
                Bundle bundle_ar=new Bundle();
                bundle_ar.putInt("Player_Type",2);
                bundle_ar.putInt("teamId1",teamId1);
                bundle_ar.putInt("teamId2",teamId2);
                fragment.setArguments(bundle_ar);
                Log.e("Player_Type", String.valueOf(2));
                return fragment;
            case 3:

                fragment=new CreateTeamFragment(fragmentInterface);
                Bundle bundle_bowl=new Bundle();
                bundle_bowl.putInt("Player_Type",1);
                bundle_bowl.putInt("teamId1",teamId1);
                bundle_bowl.putInt("teamId2",teamId2);
                fragment.setArguments(bundle_bowl);
                Log.e("Player_Type", String.valueOf(1));
                return fragment;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}