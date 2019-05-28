package com.psl.fantasy.league.revamp.fragment;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.WinnerBottomAdapter;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.response.PrizeDistribution.Datum;
import com.psl.fantasy.league.revamp.model.response.PrizeDistribution.PrizeDistributionBean;
import com.psl.fantasy.league.revamp.model.ui.WinnerBean;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WinnerBottomFragment extends BottomSheetDialogFragment {


    public WinnerBottomFragment() {
        // Required empty public constructor
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_winner_bottom, null);
        TextView txt_close = view.findViewById(R.id.txt_close);
        TextView txt_more= view.findViewById(R.id.txt_more);
        ListView list_winner = view.findViewById(R.id.list_winner);
        /*List<WinnerBean> list = new ArrayList<>();
        list.add(new WinnerBean("Rank1", "10000"));
        list.add(new WinnerBean("Rank2", "10000"));
        list.add(new WinnerBean("Rank3", "10000"));
        list.add(new WinnerBean("Rank4", "10000"));
        list.add(new WinnerBean("Rank5", "10000"));
        list.add(new WinnerBean("Rank6", "10000"));
        list.add(new WinnerBean("Rank7", "10000"));
        list.add(new WinnerBean("Rank8", "10000"));
        list.add(new WinnerBean("Rank9", "10000"));
        list.add(new WinnerBean("Rank10", "10000"));
        list.add(new WinnerBean("Rank11", "10000"));
        list.add(new WinnerBean("Rank11", "10000"));
        list.add(new WinnerBean("Rank12", "10000"));*/


        ApiClient.getInstance().getAllContestWinningDist()
                .enqueue(new Callback<PrizeDistributionBean>() {
                    @Override
                    public void onResponse(Call<PrizeDistributionBean> call, Response<PrizeDistributionBean> response) {
                        if(response.isSuccessful()){
                            if(response.body().getResponseCode().equalsIgnoreCase("1001")){

                                WinnerBottomAdapter adapter = new WinnerBottomAdapter(view.getContext(), R.layout.winner_bottom_adapter, response.body().getData());
                                list_winner.setAdapter(adapter);


                            }else{
                                Helper.showAlertNetural(view.getContext(),"Error",response.body().getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<PrizeDistributionBean> call, Throwable t) {
                        t.printStackTrace();
                        Helper.showAlertNetural(view.getContext(),"Error","Communication Error");
                    }
                });


        dialog.setContentView(view);

        txt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) ((View) view.getParent()).getLayoutParams();
        CoordinatorLayout.Behavior behavior = params.getBehavior();

        if (behavior != null && behavior instanceof BottomSheetBehavior) {
            ((BottomSheetBehavior) behavior).setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
                @Override
                public void onStateChanged(@NonNull View bottomSheet, int newState) {
                    String state = "";

                    switch (newState) {
                        case BottomSheetBehavior.STATE_DRAGGING: {
                            state = "DRAGGING";
                            RelativeLayout.LayoutParams params1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                            params1.addRule(RelativeLayout.BELOW,R.id.txt_prize_pool);
                            list_winner.setLayoutParams(params1);
                            break;
                        }
                        case BottomSheetBehavior.STATE_SETTLING: {
                            state = "SETTLING";
                            break;
                        }
                        case BottomSheetBehavior.STATE_EXPANDED: {
                            state = "EXPANDED";

                            break;
                        }
                        case BottomSheetBehavior.STATE_COLLAPSED: {
                            state = "COLLAPSED";
                            break;
                        }
                        case BottomSheetBehavior.STATE_HIDDEN: {
                            dismiss();
                            state = "HIDDEN";
                            break;
                        }
                    }

                   // Toast.makeText(getContext(), "Bottom Sheet State Changed to: " + state, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                }
            });
        }
        txt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_more.setVisibility(GONE);
                DisplayMetrics metrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);

                BottomSheetDialog d = (BottomSheetDialog) dialog;
                d.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, (int) (metrics.heightPixels * 1));

                FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);

                RelativeLayout.LayoutParams params1=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
                params1.addRule(RelativeLayout.BELOW,R.id.txt_prize_pool);
                list_winner.setLayoutParams(params1);
                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);


            }
        });
    }
}
