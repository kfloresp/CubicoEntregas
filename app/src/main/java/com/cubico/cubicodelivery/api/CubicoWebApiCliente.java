package com.cubico.cubicodelivery.api;

import android.util.Log;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CubicoWebApiCliente  {

   // private static String IP="172.16.32.15:8085";
    private  String BASE_URL;//"http://172.16.32.3/webApiCubicoWS/api/";
    private static CubicoWebApiCliente mInstanceApi;
    private Retrofit retrofit;

    public void setBASE_URL(String BASE_URL) {
        this.BASE_URL = BASE_URL;
    }

    private CubicoWebApiCliente(String url) {
       setBASE_URL(url);
        Log.i(" Cons:Url WEB API",BASE_URL);
        retrofit= new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }


    public static synchronized CubicoWebApiCliente getInstance(String url) {
        Log.i("getInstance",url);
       // if(mInstanceApi==null){
            mInstanceApi= new CubicoWebApiCliente(url);
       // }
        return mInstanceApi;
    }

    public ApiCubico getApiCubicoWebApi(){
      //  Log.i("Urlweb API config",getURL());
        Log.i("Url WEB API",BASE_URL);
        return retrofit.create(ApiCubico.class);
    }
}
