package com.psl.fantasy.league.revamp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.Helper;
import com.psl.fantasy.league.revamp.adapter.MoreAdapter;
import com.psl.fantasy.league.revamp.interfaces.FragmentToActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    public static final String[] items={"Terms & Conditions","FAQs","Prizes","About","Rules","Invite a friend","Logout"};
    public static final String[] items1={"Terms & Conditions","FAQs","Prizes","About","Rules"};
    private FragmentToActivity mCallback;
    private SharedPreferences preferences;
    MoreAdapter adapter;
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
    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_more, container, false);

        ListView list_view_items=mView.findViewById(R.id.list_view_items);
        try{
            preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
            if(Helper.getUserSession(preferences,Helper.MY_USER)!=null) {


                adapter = new MoreAdapter(mView.getContext(),R.layout.more_adapter,items,preferences);

            }else{
                adapter = new MoreAdapter(mView.getContext(),R.layout.more_adapter,items1,preferences);
            }


            mCallback.communicate("disable");
            list_view_items.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return mView;
    }

}
