package com.psl.fantasy.league.fragment;


import android.app.Fragment;
import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.ContestAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Contest.ContestResponse;
import com.psl.fantasy.league.model.response.Contest.Datum;
import com.psl.fantasy.league.model.ui.ContestBean;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContestFragment extends Fragment {
    private View mView;
    private int match_Id;
    private List<ContestBean> list;
    private ListView list_contest_1,list_contest_2,list_contest_3;
    int match_id;
    int TeamId1,TeamId2;
    private ProgressBar progressBar;
    private TextView txt_status,txt_cat1,txt_cat2,txt_cat3,txt_view_more;
    int counter=0;
    public ContestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mView=inflater.inflate(R.layout.fragment_contest, container, false);
        progressBar=mView.findViewById(R.id.progressBar);
        txt_view_more=mView.findViewById(R.id.txt_view_more);
        txt_cat1 =mView.findViewById(R.id.txt_cat1);
        txt_cat2 =mView.findViewById(R.id.txt_cat2);
        txt_cat3=mView.findViewById(R.id.txt_cat3);
        list_contest_1=mView.findViewById(R.id.list_contest_1);
        list_contest_2=mView.findViewById(R.id.list_contest_2);
        list_contest_3=mView.findViewById(R.id.list_contest_3);
        txt_status=mView.findViewById(R.id.txt_status);
        try {
            if(getArguments()!=null) {

                match_id = getArguments().getInt("match_id");
                TeamId1=getArguments().getInt("TeamId1");
                TeamId2=getArguments().getInt("TeamId2");
            }

            txt_view_more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Fragment fragment=new ContestDetailFragment();
                    Bundle bundle=new Bundle();
                    bundle.putInt("match_id",match_id);
                    bundle.putInt("TeamId1",TeamId1);
                    bundle.putInt("TeamId2",TeamId2);
                    bundle.putInt("contest_type",1);
                    fragment.setArguments(bundle);
                    android.app.FragmentTransaction ft=getFragmentManager().beginTransaction();

                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });
            list=new ArrayList<>();
            JSONObject object=new JSONObject();
            object.put("match_id",match_id);
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            Log.e("Fragment",object.toString());
            ApiClient.getInstance().getAllContest(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<ContestResponse>() {
                        @Override
                        public void onResponse(Call<ContestResponse> call, Response<ContestResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equals("1001")){
                                    if(response.body().getData().size()>0){
                                        for(Datum datum:response.body().getData()){
                                            if(datum.getIsVisible().equals("1")){
                                                if(datum.getContestType().equalsIgnoreCase("1")){
                                                    if(datum.getPoolConsumed()>0){

                                                        txt_cat1.setText("Mega Contest");
                                                        if(counter<3) {
                                                            int percent=((datum.getPool()-datum.getPoolConsumed())/datum.getPoolConsumed())*100;
                                                            txt_cat1.setVisibility(View.VISIBLE);
                                                            list.add(new ContestBean(datum.getContestId(), datum.getWinningPoints(), percent, String.valueOf(datum.getPool() - datum.getPoolConsumed())
                                                                    , datum.getWinners(), datum.getDiscount().toString(), datum.getEnteryFee(), datum.getMultipleAllowed(), datum.getConfirmedWinning(), datum.getContestType()));
                                                            counter++;
                                                        }
                                                        list_contest_1.setVisibility(View.VISIBLE);
                                                        ContestAdapter adapter=new ContestAdapter(mView.getContext(),R.layout.list_contest,list,TeamId1,TeamId2);
                                                        list_contest_1.setAdapter(adapter);

                                                    }
                                                }
                                            }
                                        }
                                    }else{
                                        Helper.displayError(txt_status,"No Record found...");
                                    }

                                }else{
                                    Helper.displayError(txt_status,response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ContestResponse> call, Throwable t) {
                            t.printStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                        }
                    });

          //if hardcoded data
            /*list.add(new ContestBean(1,"20",20,"10,000 spots left","40 winners","4000","5000","S","C","A"));
            list.add(new ContestBean(2,"40",40,"10,000 spots left","40 winners","0","5000","M","C","B"));
            list.add(new ContestBean(3,"60",60,"10,000 spots left","40 winners","0","5000","M","N","B"));
            list.add(new ContestBean(4,"100",80,"10,000 spots left","40 winners","0","5000","M","N","B"));
            list.add(new ContestBean(5 ,"120",100,"10,000 spots left","40 winners","0","5000","M","N","B"));
            ContestAdapter adapter=new ContestAdapter(mView.getContext(),R.layout.list_contest,list);
            list_contest.setAdapter(adapter);*/

        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }

}
