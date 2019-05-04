package com.psl.fantasy.league.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;

import com.psl.fantasy.league.model.request.TestBeanRequest;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
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


public class Helper {
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final String publicKeyString="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCVMxpYZO46njcjm/iizphuiSJlL5P2kj16WJRT\n" +
            "OmD+rJ/DG6IsOqhEZWHOu2SUpGp+OFbNzYdRGkuDl7oWoe95v5QOMA7+8qBgwCr1/OZWp+aHkxxM\n" +
            "/to87hLEmFzWgiC8zzyHvWDzjvNJEGfPa9J0RDYjxEES7kuyhRY4KLxDowIDAQAB";
    public static final String SHARED_PREF = "PSL_FANTSY";
    public static final String MY_USER = "MyUser";
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

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        }
        catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
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

    public static Object getUserSession(SharedPreferences sharedpreferences, String key){
        Gson gson=new Gson();
       String json=sharedpreferences.getString(key,"");
       return  gson.fromJson(json,Object.class);
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
}
