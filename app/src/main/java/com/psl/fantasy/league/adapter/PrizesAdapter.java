package com.psl.fantasy.league.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.fragment.FragmentClaimPrizes;
import com.psl.fantasy.league.fragment.PrizesRedeemFragment;

public class PrizesAdapter extends FragmentStatePagerAdapter {
    Fragment fragment;
    public PrizesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                fragment = new PrizesRedeemFragment();
                return fragment;

            case 1:
                fragment = new FragmentClaimPrizes();
                return fragment;

            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
