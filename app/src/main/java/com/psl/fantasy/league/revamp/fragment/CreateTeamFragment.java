package com.psl.fantasy.league.revamp.fragment;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.revamp.R;
import com.psl.fantasy.league.revamp.Utils.DbHelper;
import com.psl.fantasy.league.revamp.adapter.PlayerInfoAdapter;
import com.psl.fantasy.league.revamp.interfaces.FragmentInterface;
import com.psl.fantasy.league.revamp.interfaces.PlayerInterface;
import com.psl.fantasy.league.revamp.model.response.Player.Datum;
import com.psl.fantasy.league.revamp.model.ui.PlayerBean;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class CreateTeamFragment extends Fragment {

    ListView list_player;
    View mView;
    int Player_Type;
    PlayerInterface playerInterface;
    FragmentInterface fragmentInterface;
    DbHelper dbHelper;
    int teamId1; int teamId2;
    public CreateTeamFragment(FragmentInterface fragmentInterface) {
        this.fragmentInterface=fragmentInterface;
    }


    @TargetApi(Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView=inflater.inflate(R.layout.fragment_create_team, container, false);
        list_player=mView.findViewById(R.id.list_player);
        TextView txt_selection=mView.findViewById(R.id.txt_selection);
        dbHelper=new DbHelper(mView.getContext());
        if(getArguments()!=null){
            Player_Type=getArguments().getInt("Player_Type"); //0=batting ,1=bowling,2=alrounder,3=keeper
            teamId1=getArguments().getInt("teamId1");
            teamId2=getArguments().getInt("teamId2");
        }
        if(dbHelper.getMyTeamCount()>0){
            dbHelper.deleteMyTeam();
        }
        if(Player_Type==3) {
            txt_selection.setText("Pick only 1 Wicket-keeper");
        }if(Player_Type==0) {
            txt_selection.setText("Pick 4 Batsmen");
        }if(Player_Type==2) {
            txt_selection.setText("Pick 2 All-Rounders");
        }if(Player_Type==1) {
            txt_selection.setText("Pick 4 Bowlers");
        }

        List<Datum> playerData=dbHelper.getPlayersByIdSkills(String.valueOf(teamId1),String.valueOf(teamId2), String.valueOf(Player_Type));
        List<PlayerBean> player = new ArrayList<>();
        for(Datum datum:playerData){

            PlayerBean bean=new PlayerBean();
            bean.setId(datum.getPlayerId());
            bean.setName(datum.getName());
            bean.setShort_country(datum.getTeamName());
            bean.setSkill(String.valueOf(Player_Type));
            if(!TextUtils.isEmpty(datum.getPrice())) {
                bean.setCredits(Double.parseDouble(datum.getPrice()));
            }
            if(!TextUtils.isEmpty(datum.getAvg())) {
                bean.setPoints(Double.parseDouble(datum.getAvg()));
            }
            bean.setChecked(false);
            bean.setCaptain(false);
            bean.setViceCaptain(false);
            player.add(bean);
        }

        //List<PlayerBean>list=player.stream().filter(p->p.getSkill().equals(String.valueOf(Player_Type))).collect(Collectors.toList());

        playerInterface=new PlayerInterface() {
            @Override
            public void playerCount(int type, int count) {
//                Toast.makeText(mView.getContext(), type+","+count, Toast.LENGTH_SHORT).show();
                fragmentInterface.playerCount(type,count);

            }

            @Override
            public void count(char ch) {
                fragmentInterface.count(ch);
            }

            @Override
            public void credit(double count) {
                fragmentInterface.credit(count);
            }


        };
        PlayerInfoAdapter adapter=new PlayerInfoAdapter(mView.getContext(),R.layout.player_info_adapter,player,Player_Type,playerInterface,dbHelper);
        list_player.setAdapter(adapter);


        return mView;
    }

}
