package com.psl.fantasy.league.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.FixtureAdapter;
import com.psl.fantasy.league.adapter.ImageAdapter;
import com.psl.fantasy.league.client.ApiClient;
import com.psl.fantasy.league.model.response.Matches.Datum;
import com.psl.fantasy.league.model.response.Matches.MatchesResponse;

import com.psl.fantasy.league.model.ui.MatchesBean;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    private ListView list_matches;
    private List<MatchesBean> list;
    private View mView;
    private TextView txt_status;
    private ProgressBar progressBar;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_dashboard, container, false);
        init();
        try{

            JSONObject object=new JSONObject();
            object.put("method_Name",this.getClass().getSimpleName()+".onCreateView");
            ApiClient.getInstance().matches(Helper.encrypt(object.toString()))
                    .enqueue(new Callback<MatchesResponse>() {
                        @Override
                        public void onResponse(Call<MatchesResponse> call, Response<MatchesResponse> response) {
                            progressBar.setVisibility(View.GONE);
                            if(response.isSuccessful()){
                                if(response.body().getResponseCode().equals("1001")){
                                    for(Datum bean:response.body().getData()){
                                        if(response.body().getData().size()>0) {
                                            list.add(new MatchesBean(bean.getMatchId().intValue(),bean.getTeamId1Name(),bean.getTeamId2Name(),bean.getStartDate(),bean.getTeamId1().intValue(),bean.getTeamId2().intValue()));
                                            list_matches.setVisibility(View.VISIBLE);
                                            FixtureAdapter fixtureAdapter = new FixtureAdapter(mView.getContext(), R.layout.list_fixture, list);
                                            list_matches.setAdapter(fixtureAdapter);
                                        }else{
                                            Helper.displayError(txt_status,"No record found...");
                                        }
                                    }

                                }else{
                                    Helper.showAlertNetural(mView.getContext(),"Error",response.body().getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<MatchesResponse> call, Throwable t) {
                            t.fillInStackTrace();
                            Helper.showAlertNetural(mView.getContext(),"Error",t.getMessage());
                        }
                    });


            /*list_matches.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Fragment fragment=new ContestFragment();
                    FragmentTransaction ft=getFragmentManager().beginTransaction();
                    ft.replace(R.id.main_content,fragment);
                    ft.commit();
                }
            });*/
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }

    private void init(){
        progressBar=mView.findViewById(R.id.progressBar);
        list_matches=mView.findViewById(R.id.list_matches);
        list=new ArrayList<>();
        txt_status=mView.findViewById(R.id.txt_status);
        ViewPager viewPage=mView.findViewById(R.id.viewPage);
        ImageAdapter adapter=new ImageAdapter(mView.getContext());
        viewPage.setAdapter(adapter);
    }
}
