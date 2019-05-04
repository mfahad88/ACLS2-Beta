package com.psl.fantasy.league.fragment;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.psl.fantasy.league.R;
import com.psl.fantasy.league.Utils.DbHelper;
import com.psl.fantasy.league.Utils.Helper;
import com.psl.fantasy.league.adapter.PlayerInfoAdapter;
import com.psl.fantasy.league.interfaces.FragmentInterface;
import com.psl.fantasy.league.interfaces.PlayerInterface;
import com.psl.fantasy.league.model.response.Player.Datum;
import com.psl.fantasy.league.model.ui.PlayerBean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        if(Player_Type==3) {
            txt_selection.setText("You can pick only 1 Wicket-keeper");
        }if(Player_Type==0) {
            txt_selection.setText("Pick 3-5 Batsmen");
        }if(Player_Type==2) {
            txt_selection.setText("Pick 1-3 All-Rounders");
        }if(Player_Type==1) {
            txt_selection.setText("Pick 3-5 Bowlers");
        }

        List<Datum> playerData=dbHelper.getPlayersById(String.valueOf(teamId1),String.valueOf(teamId2));
        List<PlayerBean> player = new ArrayList<>();
        for(Datum datum:playerData){
            PlayerBean bean=new PlayerBean();
            bean.setId(datum.getPlayerId());
            bean.setName(datum.getName());
            bean.setShort_country(datum.getTeamName());
            bean.setSkill(String.valueOf(Player_Type));
            bean.setCredits(Double.parseDouble(datum.getPrice()));
            bean.setPoints(Double.parseDouble(datum.getAvg()));
            bean.setChecked(false);
            bean.setCaptain(false);
            bean.setViceCaptain(false);

            player.add(bean);

        }
        /*List<PlayerBean> player=new ArrayList<>();
        player.add(new PlayerBean("Kamran Akmal","PK","0",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","PK","0",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","AU","0",313.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","PK","0",314.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","AU","0",315.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","PK","0",316.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","AU","0",317.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","PK","0",318.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","AU","0",319.5,20.0,false,false,false));

        player.add(new PlayerBean("Kamran Akmal","PK","1",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","PK","1",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","AU","1",313.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","PK","1",314.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","AU","1",315.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","PK","1",316.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","AU","1",317.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","PK","1",318.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","AU","1",319.5,20.0,false,false,false));

        player.add(new PlayerBean("Kamran Akmal","PK","2",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","PK","2",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","AU","2",313.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","PK","2",314.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","AU","2",315.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","PK","2",316.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","AU","2",317.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","PK","2",318.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","AU","2",319.5,20.0,false,false,false));


        player.add(new PlayerBean("Kamran Akmal","PK","3",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","PK","3",312.5,20.0,false,false,false));
        player.add(new PlayerBean("ABC","AU","3",313.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","PK","3",314.5,20.0,false,false,false));
        player.add(new PlayerBean("DEF","AU","3",315.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","PK","3",316.5,20.0,false,false,false));
        player.add(new PlayerBean("XYZ","AU","3",317.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","PK","3",318.5,20.0,false,false,false));
        player.add(new PlayerBean("GHI","AU","3",319.5,20.0,false,false,false));*/
        List<PlayerBean>list=player.stream().filter(p->p.getSkill().equals(String.valueOf(Player_Type))).collect(Collectors.toList());

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
        PlayerInfoAdapter adapter=new PlayerInfoAdapter(mView.getContext(),R.layout.player_info_adapter,list,Player_Type,playerInterface,dbHelper);
        list_player.setAdapter(adapter);


        return mView;
    }

}
