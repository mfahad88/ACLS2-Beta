package com.psl.fantasy.league.revamp.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.psl.fantasy.league.revamp.BuildConfig;
import com.psl.fantasy.league.revamp.client.ApiClient;
import com.psl.fantasy.league.revamp.model.request.TestBeanRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.psl.fantasy.league.revamp.model.response.AppVersion.AppVersionBean;
import com.psl.fantasy.league.revamp.model.response.SelectUser.SelectUserBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Helper {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVMxpYZO46njcjm/iizphuiSJlL5P2kj16WJRT\n" +
            "OmD+rJ/DG6IsOqhEZWHOu2SUpGp+OFbNzYdRGkuDl7oWoe95v5QOMA7+8qBgwCr1/OZWp+aHkxxM\n" +
            "/to87hLEmFzWgiC8zzyHvWDzjvNJEGfPa9J0RDYjxEES7kuyhRY4KLxDowIDAQAB";
    public static final String i="Test";
    public static final String SHARED_PREF = "PSL_FANTSY";
    public static final String MY_USER = "MyUser";
    public static final String MY_USER_MSC = "MyUserMsc";
    public static final String CNIC = "cnic";
    public static final String PrizeDistributionBean = "PrizeDistributionBean";
    public static final String isFromLogin="isFromLogin";
    public static void showAlertNetural(Context ctx, String title, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(ctx);
        builder.setTitle(title).setMessage(message);
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public static boolean validateEmail(String emailStr) {

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        Log.e("email", String.valueOf(matcher.find()));
        return matcher.find();
    }

    @SuppressLint("NewApi")
    public static TestBeanRequest encrypt(String messsage){
        String encryptedSecretKey = null;
        TestBeanRequest request =null;
        try{
            request =new TestBeanRequest();
//            Base64.Encoder encoder = Base64.getEncoder();
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128); // AES is currently available in three key sizes: 128, 192 and 256 bits.The design and strength of all key lengths of the AES algorithm are sufficient to protect classified information up to the SECRET level
            SecretKey secretKey = keyGenerator.generateKey();

            System.out.println("SecretKey: "+ Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT));


            //System.out.println("Beta:: "+beta(a));
            // 2. get string which needs to be encrypted
            String text = messsage;

            // 3. encrypt string using secret key
            byte[] raw = secretKey.getEncoded();

            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, new IvParameterSpec(new byte[16]));
            /*StringBuffer cipherTextString = new StringBuffer();
            cipherTextString.append(Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("UTF-8"))),Base64.DEFAULT));*/
            String cipherTextString =Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("UTF-8"))),Base64.DEFAULT) ;
            System.out.println("cipherTextString: [Text with secret key encryption ]"+cipherTextString);



            X509EncodedKeySpec publicSpec = new X509EncodedKeySpec(Base64.decode(publicKeyString,Base64.DEFAULT));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(publicSpec);
            byte[] a= alpha(Base64.encodeToString(secretKey.getEncoded(),Base64.DEFAULT));


          //  System.out.println("Alpha:: "+a);
            // 6. encrypt secret key using public key
            Cipher cipher2 = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
            cipher2.init(Cipher.ENCRYPT_MODE, publicKey);
            encryptedSecretKey =Base64.encodeToString(cipher2.doFinal(secretKey.getEncoded()),Base64.DEFAULT);
            System.out.println("encryptedSecretKey: "+encryptedSecretKey);
            request.setRequest(cipherTextString);
            request.setKey(encryptedSecretKey);
            System.out.println(request.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

    public static byte[] alpha(String value) {
        System.out.println("Alphaaaaaaaaaaaaaaaa");
        String dd=value.substring(0, 3);
        String ee=value.substring(value.length()-3, value.length());
        String rem=value.substring(3,value.length()-3);
        //System.out.println(dd);
        //System.out.println(ee);
        //System.out.println(rem);
        rem=ee+rem+dd;
        System.out.println("Alphaaa:"+rem);

        return rem.getBytes();

    }

    /*public static void updateGoogleAnalytics(Application app,String screenName,Bundle params){
        AnalyticsApplication application = (AnalyticsApplication) app;
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName(screenName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        mTracker.setAppId(BuildConfig.APPLICATION_ID);
        mTracker.setAppVersion(BuildConfig.VERSION_NAME);
        if(!params.isEmpty()){
            FirebaseAnalytics  mFirebaseAnalytics = FirebaseAnalytics.getInstance(app.getApplicationContext());
            mFirebaseAnalytics.logEvent(screenName, params);
        }

    }*/

    /*public static void updateGoogleAnalytics(Tracker mTracker,String screenName){

        mTracker.setScreenName(screenName);
        mTracker.setAppId(BuildConfig.APPLICATION_ID);
        mTracker.setAppVersion(BuildConfig.VERSION_NAME);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


    }

    public  static Tracker getGoogleAnalytics(Application app){
        AnalyticsApplication application = (AnalyticsApplication) app;
        return application.getDefaultTracker();
    }*/

    public static void trackEvent(Tracker t,String category, String action, String label) {


        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }


    public static boolean permissionAlreadyGranted(Context context,String permission) {

        int result = ContextCompat.checkSelfPermission(context, permission);
        if (result == PackageManager.PERMISSION_GRANTED)
            return true;

        return false;
    }

    public static String readFile(Context context, String filename) {
        String buffer_row = "";
        try {
            AssetManager am = context.getAssets();
            java.io.InputStream fs = am.open(filename);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fs));
            String datarow = "";

            while ((datarow = reader.readLine()) != null) {
                buffer_row += datarow + "\n";

            }

            //getAlert(buffer_row);
            reader.close();

        } catch (Exception e) {

        }
        return buffer_row;
    }

    public static void putUserSession(SharedPreferences sharedpreferences,String key,Object object){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        String json=new Gson().toJson(object);
        editor.putString(key,json);
        editor.commit();
    }

    /*public static <T>Object getUserSession(SharedPreferences sharedpreferences, String key){
        Gson gson=new Gson();
       String json=sharedpreferences.getString(key,"");
       return  gson.fromJson(json,Object.class);
    }*/

    public static <T>Object getUserSession(SharedPreferences sharedpreferences, String key){
        Gson gson=new Gson();
        String session = null;
        String json=sharedpreferences.getString(key,"");
        if(gson.fromJson(json,Object.class)==null){
            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"ACL","user.txt");
            if(file.exists()) {
                if (Helper.getUserIdFromText() != null) {

                    try {
                        session=Helper.getUserIdFromText();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }else{
            session=gson.fromJson(json,Object.class).toString();
        }
        return  session;
    }

    public static boolean removeUserSession(SharedPreferences sharedPreferences,String key){
        return sharedPreferences.edit().remove(key).commit();
    }

    @SuppressLint("NewApi")
    public static String convertInputStreamToString(InputStream inputStream)
            throws IOException {

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return result.toString(StandardCharsets.UTF_8.name());
    }

    public static void printHashKey(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("Facebook", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("Facebook", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("Facebook", "printHashKey()", e);
        }
    }


    public static void createDirectory(){
       try{
           File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"ACL");

           if(!direct.exists()) {
               if(direct.mkdir()){
                   Log.e("Helper",direct.getPath()+" created...");
               }
           }
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public static void saveText(String msg){
        File file=null;
        try {
            file=new File(Environment.getExternalStorageDirectory()+File.separator+"ACL"+File.separator+"user.txt");
            if(!file.exists()){
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"ACL"+File.separator+"user.txt");
                try {
                    fos.write(AESUtils.encrypt(msg).getBytes());
//                    fos.write(msg.getBytes());
                    fos.close();
                    Log.e("Helper",file.getPath()+" saved...");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{
                file.delete();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(Environment.getExternalStorageDirectory()+File.separator+"ACL"+File.separator+"user.txt");
                fos.write(msg.getBytes());
                fos.close();
                Log.e("Helper",file.getPath()+" saved...");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static String getUserIdFromText(){
        //Get the text file


        //Read text from file
        StringBuilder text = new StringBuilder();
        String line = null;
        try {


            File file = new File(Environment.getExternalStorageDirectory()+File.separator+"ACL","user.txt");

            BufferedReader br = new BufferedReader(new FileReader(file));


            while ((line = br.readLine()) != null) {
                text.append(AESUtils.decrypt(line));

            }
            br.close();
        }
        catch (Exception e) {
            //You'll need to add proper error handling here
            e.printStackTrace();
        }
        Log.e("Helper",text.toString()+" Line...");
        return text.toString();
    }

    public static void displayError(TextView tv,String msg){
        tv.setVisibility(View.VISIBLE);
        tv.setText(msg);
    }

    /*public static void updateGoogleAnalytics(Tracker mTracker,String screenName){

        mTracker.setScreenName(screenName);
        mTracker.setAppId(BuildConfig.APPLICATION_ID);
        mTracker.setAppVersion(BuildConfig.VERSION_NAME);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


    }

    public  static Tracker getGoogleAnalytics(Application app){
        AnalyticsApplication application = (AnalyticsApplication) app;
        return application.getDefaultTracker();
    }*/

    public static void deleteDirectory(){
        try{
            File direct = new File(Environment.getExternalStorageDirectory()+File.separator+"ACL");
            File file=new File(Environment.getExternalStorageDirectory()+File.separator+"ACL"+File.separator+"user.txt");
            direct.delete();
            file.delete();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static int dpToPx(int dp,Context context) {
        float density = context.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public static void checkAppVersion(Activity activity, SharedPreferences preferences, DbHelper dbHelper){
        int user_id;
        if(Helper.getUserSession(preferences,Helper.MY_USER)!=null){
            JSONObject object= null;
            try {
                object = new JSONObject(Helper.getUserSession(preferences,Helper.MY_USER).toString());
                JSONObject nameValuePairs=object.getJSONObject("nameValuePairs");
                user_id=nameValuePairs.getJSONObject("MyUser").getInt("user_id");
                try {
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("user_id",user_id);
                    ApiClient.getInstance().SelectUser(Helper.encrypt(jsonObject.toString()))
                            .enqueue(new Callback<SelectUserBean>() {
                                @Override
                                public void onResponse(Call<SelectUserBean> call, Response<SelectUserBean> response) {
                                    if(response.isSuccessful()){
                                        if(response.body().getResponseCode().equalsIgnoreCase("1001")){
                                           if(!TextUtils.isEmpty(response.body().getData().getMyUser().getIsUpdated())){
                                               if(response.body().getData().getMyUser().getIsUpdated().equalsIgnoreCase("0")){
                                                   if(Float.parseFloat(dbHelper.getConfig())>Float.parseFloat(BuildConfig.VERSION_NAME)) {
                                                       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://1drv.ms/u/s!AtJGoRk9R0bQhAVuq-dk8qsAbXxY"));
                                                       browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                       activity.startActivity(browserIntent);
                                                       activity.finish();
                                                       System.exit(0);
                                                   }else if(Float.parseFloat(dbHelper.getConfig())<Float.parseFloat(BuildConfig.VERSION_NAME)){
                                                       Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://1drv.ms/u/s!AtJGoRk9R0bQhAVuq-dk8qsAbXxY"));
                                                       browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                                       activity.startActivity(browserIntent);
                                                       activity.finish();
                                                       System.exit(0);
                                                   }else{
                                                       try {
                                                           JSONObject jsonObject=new JSONObject();
                                                           jsonObject.put("user_id",user_id);
                                                           jsonObject.put("isUpdated",1);

                                                           ApiClient.getInstance().updateAppVersion(Helper.encrypt(jsonObject.toString()))
                                                                   .enqueue(new Callback<AppVersionBean>() {
                                                                       @Override
                                                                       public void onResponse(Call<AppVersionBean> call, Response<AppVersionBean> response) {
                                                                           if(response.isSuccessful()){
                                                                               if(response.body().getResponseCode().equalsIgnoreCase("1001")){

                                                                               }else{
                                                                                   Helper.showAlertNetural(activity.getApplicationContext(),"Error",response.body().getMessage());
                                                                               }
                                                                           }
                                                                       }

                                                                       @Override
                                                                       public void onFailure(Call<AppVersionBean> call, Throwable t) {
                                                                           t.printStackTrace();
                                                                           Helper.showAlertNetural(activity.getApplicationContext(),"Error","Communication Error");
                                                                       }
                                                                   });
                                                       } catch (JSONException e) {
                                                           e.printStackTrace();
                                                       }
                                                   }
                                               }
                                           }

                                        }else{
                                            Helper.showAlertNetural(activity.getApplicationContext(),"Error",response.body().getMessage());
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<SelectUserBean> call, Throwable t) {
                                    t.printStackTrace();
                                    Helper.showAlertNetural(activity.getApplicationContext(),"Error","Communication Error");
                                }
                            });
                }catch (Exception e){
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

}
