package com.cubico.cubicodelivery.api;

import android.util.Log;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CubicoWSClient  {
    //private static String IP="172.16.32.15:8085";
    private  String BASE_URL;//"http://172.16.32.15:8085/SGAA_WCF/";
    private static CubicoWSClient mInstance;
    private Retrofit retrofit;

    public String getBASE_URL() {
        return BASE_URL;
    }

    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    private CubicoWSClient(String url) {
        //BASE_URL= getURL();
        setBASE_URL(url);
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
//    String getURL(){
//        CubicoGlobal cg= (CubicoGlobal)getApplication();
//        return cg.getWebUrl(); //"http://" + IP + "/SGAA_WCF/";
//
//    }
    public static synchronized CubicoWSClient getInstance(String url) {
        if(mInstance==null){
            mInstance= new CubicoWSClient(url);
            //mInstance.setBASE_URL(url);
        }
        return mInstance;
    }

    public ApiCubico getApiCubico(){
        Log.i("Url CubicoWSClient:",BASE_URL);

        return retrofit.create(ApiCubico.class);
    }
}
