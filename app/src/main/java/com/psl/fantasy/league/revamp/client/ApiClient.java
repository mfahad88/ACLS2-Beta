package com.psl.fantasy.league.revamp.client;


import com.psl.fantasy.league.revamp.interfaces.ApiInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiClient {
 

    public static final String BASE_URL = "http://59ca1ae3.ngrok.io/api/";
//    private static final String BASE_URL = "http://apnicricketleague.eastus.cloudapp.azure.com:8754/JsApniCricketLeague/api/";
//      public static final String BASE_URL = "http://172.28.28.51:8080/api/";
//      public static final String BASE_URL = "http://192.168.1.7:8080/";
    private static Retrofit retrofit = null;

 
    public static ApiInterface getInstance() {

          if (retrofit==null) {
              Gson gson = new GsonBuilder()
                      .setLenient()
                      .create();
              retrofit = new Retrofit.Builder()
                      .baseUrl(BASE_URL)
                      .addConverterFactory(GsonConverterFactory.create(gson))
                      .client(getRequestHeader())
                      .build();
          }

        return retrofit.create(ApiInterface.class);
    }

    private static OkHttpClient getRequestHeader() {
        HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient.Builder()
        .addInterceptor(interceptor)
//        .retryOnConnectionFailure(true)
        .connectTimeout(10,TimeUnit.SECONDS)
        .readTimeout(5,TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .build();
        return httpClient;
    }

}