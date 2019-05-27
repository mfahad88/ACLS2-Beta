package com.psl.fantasy.league.revamp.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.fragment.FragmentClaimPrizes;
import com.psl.fantasy.league.revamp.fragment.PrizesRedeemFragment;

public class PrizesAdapter extends FragmentStatePagerAdapter {
    Fragment fragment;
    public PrizesAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                fragment = new FragmentClaimPrizes();
                return fragment;

            case 1:

                fragment = new PrizesRedeemFragment();
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
