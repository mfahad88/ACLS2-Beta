package com.psl.fantasy.league.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.psl.fantasy.league.model.response.Player.Datum;
import com.psl.fantasy.league.model.ui.PlayerBean;

import java.util.ArrayList;
import java.util.List;


public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper DB_HELPER=null;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "acl.db";
    public static final String TBL_CONFIG = "tbl_config";
    public static final String PARAM_CODE = "param_code";
    public static final String PARAM_TYPE = "param_type";
    public static final String DESCRIPTION = "desc";
    public static final String CONFIG_VAL = "config_val";
    public static final String USERID = "userId";
    public static final String CD = "cd";
    public static final String MD = "md";

    public static final String TBL_PLAYERS = "tbl_players";
    public static final String	team_id="team_id";
    public static final String	team_name="team_name";
    public static final String	player_id="player_id";
    public static final String	name="name";
    public static final String	game="game";
    public static final String	pic_url="pic_url";
    public static final String	plays_for="plays_for";
    public static final String	skill="skill";
    public static final String	style="style";
    public static final String	runs="runs";
    public static final String	avg="avg";
    public static final String	hundreds="hundreds";
    public static final String	fifties="fifties";
    public static final String	sr="sr";
    public static final String	wkt="wkt";
    public static final String	price="price";
    public static final String	cd="cd";
    public static final String	md="md";


    public static final String CREATE_CONFIG = "CREATE TABLE " +TBL_CONFIG + "(" + PARAM_CODE + " INTEGER PRIMARY KEY " +
            "," + PARAM_TYPE + " TEXT " +
            "," + DESCRIPTION + " TEXT " +
            "," + CONFIG_VAL + " TEXT " +
            "," + USERID + " TEXT " +
            "," + CD + " TEXT " +
            "," + MD + " TEXT)";

    public static final String CREATE_PLAYERS = "CREATE TABLE "+TBL_PLAYERS + "("+ team_id + " INTEGER " +
            ", "+team_name+" TEXT" +
            ", "+player_id+" TEXT" +
            ", "+name+" TEXT" +
            ", "+game+" TEXT" +
            ", "+pic_url+" TEXT" +
            ", "+plays_for+" TEXT" +
            ", "+skill+" TEXT" +
            ", "+style+" TEXT" +
            ", "+runs+" TEXT" +
            ", "+avg+" TEXT" +
            ", "+hundreds+" TEXT" +
            ", "+fifties+" TEXT" +
            ", "+sr+" TEXT" +
            ", "+wkt+" TEXT" +
            ", "+price+" TEXT" +
            ", "+cd+" TEXT" +
            ", "+md+" TEXT)";

    private static String ID = "Id";
    private static String NAME = "Name";
    private static String PRICE = "Price";
    private static String SKILLS = "Skills";
    private static String ISCAPTAIN = "IsCaptain";
    private static String ISWCAPTAIN = "isWCaption" ;
    private static String ISCHECKED = "ISCHECKED" ;
    private static String TBL_MY_TEAM = "tbl_my_team";
    public static final String CREATE_TBL_MY_TEAM="CREATE TABLE "+TBL_MY_TEAM+ "("+ID+ " INTEGER " +
            ", "+NAME+" TEXT " +
            ", "+PRICE+" TEXT" +
            ", "+SKILLS+" TEXT" +
            ", "+ISCAPTAIN+" INTEGER" +
            ", "+ISWCAPTAIN+" INTEGER" +
            ", "+ISCHECKED+" INTEGER)";
    Context cntxt;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cntxt=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.e("SQLiteDatabase",CREATE_CONFIG);
        Log.e("SQLiteDatabase",CREATE_PLAYERS);
        db.execSQL(CREATE_CONFIG);
        db.execSQL(CREATE_PLAYERS);
        db.execSQL(CREATE_TBL_MY_TEAM);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_CONFIG);
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_PLAYERS);
        db.execSQL("DROP TABLE IF EXISTS "+ TBL_MY_TEAM);
        onCreate(db);
    }



    public List<com.psl.fantasy.league.model.response.Player.Datum> getPlayers(String team_id){
        List<com.psl.fantasy.league.model.response.Player.Datum> list=new ArrayList<>();
        Cursor c = null ;
        try {

            String query = "SELECT * FROM " + TBL_PLAYERS ;

            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    com.psl.fantasy.league.model.response.Player.Datum datum=new com.psl.fantasy.league.model.response.Player.Datum();
                    datum.setTeamId(c.getInt(c.getColumnIndex(team_id)));
                    datum.setTeamName(c.getString(c.getColumnIndex(team_name)));
                    datum.setPlayerId(c.getInt(c.getColumnIndex(player_id)));
                    datum.setName(c.getString(c.getColumnIndex(name)));
                    datum.setGame(c.getString(c.getColumnIndex(game)));
                    datum.setPicUrl(c.getString(c.getColumnIndex(pic_url)));
                    datum.setPlaysFor(c.getString(c.getColumnIndex(plays_for)));
                    datum.setSkill(c.getString(c.getColumnIndex(skill)));
                    datum.setStyle(c.getString(c.getColumnIndex(style)));
                    datum.setRuns(c.getString(c.getColumnIndex(runs)));
                    datum.setAvg(c.getString(c.getColumnIndex(avg)));
                    datum.setHundreds(c.getString(c.getColumnIndex(hundreds)));
                    datum.setFifties(c.getString(c.getColumnIndex(fifties)));
                    datum.setSr(c.getString(c.getColumnIndex(sr)));
                    datum.setWkt(c.getString(c.getColumnIndex(wkt)));
                    datum.setPrice(c.getString(c.getColumnIndex(price)));
                    datum.setCd(c.getString(c.getColumnIndex(cd)));
                    datum.setMd(c.getString(c.getColumnIndex(md)));

                    list.add(datum);
                    Log.e("Value--->",list.toString());
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return list;
    }


    public List<Datum> getPlayersById(String teamId1, String teamId2){
        List<Datum> list=new ArrayList<>();
        Cursor c = null ;
        try {

            String query = "SELECT * FROM " + TBL_PLAYERS +" WHERE "+team_id+" IN ('"+teamId1+"' , '"+teamId2+"')";

            SQLiteDatabase db = this.getReadableDatabase();
            Log.e("SQLiteDatabase",query);
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Datum datum=new Datum();
                    datum.setTeamId(c.getInt(c.getColumnIndex(team_id)));
                    datum.setTeamName(c.getString(c.getColumnIndex(team_name)));
                    datum.setPlayerId(c.getInt(c.getColumnIndex(player_id)));
                    datum.setName(c.getString(c.getColumnIndex(name)));
                    datum.setGame(c.getString(c.getColumnIndex(game)));
                    datum.setPicUrl(c.getString(c.getColumnIndex(pic_url)));
                    datum.setPlaysFor(c.getString(c.getColumnIndex(plays_for)));
                    datum.setSkill(c.getString(c.getColumnIndex(skill)));
                    datum.setStyle(c.getString(c.getColumnIndex(style)));
                    datum.setRuns(c.getString(c.getColumnIndex(runs)));
                    datum.setAvg(c.getString(c.getColumnIndex(avg)));
                    datum.setHundreds(c.getString(c.getColumnIndex(hundreds)));
                    datum.setFifties(c.getString(c.getColumnIndex(fifties)));
                    datum.setSr(c.getString(c.getColumnIndex(sr)));
                    datum.setWkt(c.getString(c.getColumnIndex(wkt)));
                    datum.setPrice(c.getString(c.getColumnIndex(price)));
                    datum.setCd(c.getString(c.getColumnIndex(cd)));
                    datum.setMd(c.getString(c.getColumnIndex(md)));

                    list.add(datum);

                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return list;
    }


    public List<Datum> getPlayersByIdSkills(String teamId1, String teamId2,String skills){
        List<Datum> list=new ArrayList<>();
        Cursor c = null ;
        try {

            String query = "SELECT * FROM " + TBL_PLAYERS +" WHERE "+team_id+" IN ('"+teamId1+"' , '"+teamId2+"') AND "+skill+" = '"+skills+"'";

            SQLiteDatabase db = this.getReadableDatabase();
            Log.e("SQLiteDatabase",query);
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    Datum datum=new Datum();
                    datum.setTeamId(c.getInt(c.getColumnIndex(team_id)));
                    datum.setTeamName(c.getString(c.getColumnIndex(team_name)));
                    datum.setPlayerId(c.getInt(c.getColumnIndex(player_id)));
                    datum.setName(c.getString(c.getColumnIndex(name)));
                    datum.setGame(c.getString(c.getColumnIndex(game)));
                    datum.setPicUrl(c.getString(c.getColumnIndex(pic_url)));
                    datum.setPlaysFor(c.getString(c.getColumnIndex(plays_for)));
                    datum.setSkill(c.getString(c.getColumnIndex(skill)));
                    datum.setStyle(c.getString(c.getColumnIndex(style)));
                    datum.setRuns(c.getString(c.getColumnIndex(runs)));
                    datum.setAvg(c.getString(c.getColumnIndex(avg)));
                    datum.setHundreds(c.getString(c.getColumnIndex(hundreds)));
                    datum.setFifties(c.getString(c.getColumnIndex(fifties)));
                    datum.setSr(c.getString(c.getColumnIndex(sr)));
                    datum.setWkt(c.getString(c.getColumnIndex(wkt)));
                    datum.setPrice(c.getString(c.getColumnIndex(price)));
                    datum.setCd(c.getString(c.getColumnIndex(cd)));
                    datum.setMd(c.getString(c.getColumnIndex(md)));

                    list.add(datum);

                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return list;
    }

    public List<PlayerBean> getMyTeam(){
        List<PlayerBean> list = new ArrayList<>();
        Cursor c = null ;
        try {

            String query = "SELECT * FROM " + TBL_MY_TEAM ;

            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    PlayerBean bean=new PlayerBean();
                    bean.setId(c.getInt(c.getColumnIndex(ID)));
                    bean.setName(c.getString(c.getColumnIndex(NAME)));
                    bean.setPoints(Double.parseDouble(c.getString(c.getColumnIndex(PRICE))));
                    bean.setSkill(c.getString(c.getColumnIndex(SKILLS)));
                   /* if(c.getInt(c.getColumnIndex(ISCAPTAIN))==1) {
                        bean.setCaptain(true);
                    }else{
                        bean.setCaptain(false);
                    }
                    if(c.getInt(c.getColumnIndex(ISWCAPTAIN))==1){
                        bean.setViceCaptain(true);
                    }else{
                        bean.setViceCaptain(false);
                    }
                    if(c.getInt(c.getColumnIndex(ISCHECKED))==1){
                        bean.setChecked(true);
                    }else{
                        bean.setChecked(false);
                    }*/

                    list.add(bean);
                    Log.e("Value--->",list.toString());
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return list;
    }


    public int getIsCaptainMyTeamCount(boolean isCaptain){
        
        Cursor c = null ;
        String query;
        int count = 0;
        try {
            if(isCaptain) {
                
                query = "SELECT COUNT(*) FROM " + TBL_MY_TEAM +" WHERE "+ISCAPTAIN+"=1";
            }else{
                query = "SELECT COUNT(*) FROM " + TBL_MY_TEAM +" WHERE "+ISWCAPTAIN+"=1";
            }

            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    count=c.getInt(0);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return count;
    }


    public int getMyTeamCountBySkills(String skills){
        int count=0;
        Cursor c = null ;
        try {

            String query = "SELECT COUNT(*) FROM " + TBL_MY_TEAM + "WHERE "+SKILLS+"='"+skills+"'";

            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    count=c.getInt(0);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return count;
    }


    public int getMyTeamCount(){
        int count=0;
        Cursor c = null ;
        try {

            String query = "SELECT COUNT(*) FROM " + TBL_MY_TEAM ;

            SQLiteDatabase db = this.getReadableDatabase();
            c = db.rawQuery(query, null);

            if (c.moveToFirst()) {
                do {
                    count=c.getInt(0);
                } while (c.moveToNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            if(c!=null)
                c.close();

        }
        return count;
    }

    public long saveMyTeam(PlayerBean bean) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

                values.put(ID,bean.getId());
                values.put(NAME,bean.getName());
                values.put(PRICE,bean.getPoints());
                values.put(SKILLS,bean.getSkill());
                if(bean.isCaptain()) {
                    values.put(ISCAPTAIN, 1);
                }else{
                    values.put(ISCAPTAIN, 0);
                }
                if(bean.isViceCaptain()) {
                    values.put(ISWCAPTAIN, 1);
                }else{
                    values.put(ISWCAPTAIN,0);
                }
                if(bean.isChecked()) {
                    values.put(ISCHECKED, 1);
                }else {
                    values.put(ISCHECKED,0);
                }
                processId = db.insert(TBL_MY_TEAM, null, values);
                Log.e("SQLiteDatabase",bean.toString());



        }catch (SQLException e){
            e.printStackTrace();
        }	finally
        {
            if(db!=null)
                if(db.isOpen())
                    db.close();
        }

        return processId;
    }

    public long saveListMyTeam(List<PlayerBean> list) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for(PlayerBean bean:list){
                values.put(ID,bean.getId());
                values.put(NAME,bean.getName());
                values.put(PRICE,bean.getPoints());
                values.put(SKILLS,bean.getSkill());
                if(bean.isCaptain()) {
                    values.put(ISCAPTAIN, 1);
                }else{
                    values.put(ISCAPTAIN, 0);
                }
                if(bean.isViceCaptain()) {
                    values.put(ISWCAPTAIN, 1);
                }else{
                    values.put(ISWCAPTAIN,0);
                }
                if(bean.isChecked()) {
                    values.put(ISCHECKED, 1);
                }else {
                    values.put(ISCHECKED,0);
                }
                processId = db.insert(TBL_MY_TEAM, null, values);
                Log.e("SQLiteDatabase Insert",bean.toString());
            }


        }catch (SQLException e){
            e.printStackTrace();
        }	finally
        {
            if(db!=null)
                if(db.isOpen())
                    db.close();
        }

        return processId;
    }



    public long saveConfig(List<com.psl.fantasy.league.model.response.Config.Datum> list) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for(com.psl.fantasy.league.model.response.Config.Datum datum:list){
                values.put(PARAM_CODE,Integer.parseInt(datum.getParamCode()));
                values.put(PARAM_TYPE,datum.getParamType());
                values.put(DESCRIPTION,datum.getDesc());
                values.put(CONFIG_VAL,datum.getConfigVal());
                values.put(USERID,datum.getUserId());
                values.put(CD,datum.getCd());
                values.put(MD,datum.getMd());
                processId = db.insert(TBL_CONFIG, null, values);
                Log.e("SQLiteDatabase",datum.toString());
            }


        }catch (Exception e){
            e.printStackTrace();
        }	finally
        {
            if(db!=null)
                if(db.isOpen())
                    db.close();
        }

        return processId;
    }

    public long savePlayers(List<com.psl.fantasy.league.model.response.Player.Datum> list) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            for(com.psl.fantasy.league.model.response.Player.Datum datum:list){
                values.put(team_id,datum.getTeamId().intValue());
                values.put(team_name,datum.getTeamName());
                values.put(player_id,String.valueOf(datum.getPlayerId()));
                values.put(name,datum.getName());
                values.put(game,datum.getGame());
                values.put(pic_url,datum.getPicUrl());
                values.put(plays_for,datum.getPlaysFor());
                values.put(skill,datum.getSkill());
                values.put(style,datum.getStyle());
                values.put(runs,datum.getRuns());
                values.put(avg,datum.getAvg());
                values.put(hundreds,datum.getHundreds());
                values.put(fifties,datum.getFifties());
                values.put(sr,datum.getSr());
                values.put(wkt,datum.getWkt());
                values.put(price,datum.getPrice());
                values.put(cd,datum.getCd());
                values.put(md,datum.getMd());

                processId = db.insert(TBL_PLAYERS, null, values);
                Log.e("SQLiteDatabase",datum.toString());
            }


        }catch (SQLException e){
            e.printStackTrace();
        }	finally
        {
            if(db!=null)
                if(db.isOpen())
                    db.close();
        }

        return processId;
    }



    public void deleteConfig(){
        try {
            String query="delete from "+ TBL_CONFIG;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            Log.e("SQLiteDatabase",query);
            db.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deletePlayer(){
        try {
            String query="delete from "+ TBL_PLAYERS;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            Log.e("SQLiteDatabase",query);
            db.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteMyTeam(){
        try {
            String query="delete from "+ TBL_MY_TEAM;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            Log.e("SQLiteDatabase",query);
            db.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void deleteMyTeamById(int Id){
        try {
            String query="delete from "+ TBL_MY_TEAM+" where Id="+Id;
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(query);
            Log.e("SQLiteDatabase",query);
            db.close();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    public long updateCaptainMyTeam(int Id,boolean isCaptain,int value) {
        // Gets the data repository in write mode
        long processId = 0;
        SQLiteDatabase db=null;

        try{
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ID,Id);
            if(isCaptain) {
                values.put(ISCAPTAIN,value);
            }else{
                values.put(ISWCAPTAIN,value);
            }
            processId=db.update(TBL_MY_TEAM,values,ID+"="+Id,null);
        }catch (SQLException e){
            e.printStackTrace();
        }	finally
        {
            if(db!=null)
                if(db.isOpen())
                    db.close();
        }

        return processId;
    }
}
