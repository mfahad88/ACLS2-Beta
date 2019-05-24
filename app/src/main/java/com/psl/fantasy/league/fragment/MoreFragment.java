package com.psl.fantasy.league.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.MoreAdapter;
import com.psl.fantasy.league.interfaces.FragmentToActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {
    public static final String[] items={"Terms & Conditions","FAQs","Prizes","About","Rules","Logout"};
    private FragmentToActivity mCallback;
    private SharedPreferences preferences;

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
        preferences=mView.getContext().getSharedPreferences(Helper.SHARED_PREF,Context.MODE_PRIVATE);
        MoreAdapter adapter=new MoreAdapter(mView.getContext(),R.layout.more_adapter,items,preferences);
        mCallback.communicate("disable");
        list_view_items.setAdapter(adapter);
        return mView;
    }

}
