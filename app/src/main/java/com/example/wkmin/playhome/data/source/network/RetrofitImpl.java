package com.example.wkmin.playhome.data.source.network;

import com.example.wkmin.playhome.BuildConfig;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitImpl {

    private static Retrofit INSTANCE;

    public static Retrofit getInstance() {
        if (INSTANCE == null) {
            INSTANCE = invoke();
        }
        return INSTANCE;
    }

    private static Retrofit invoke() {
        return new Retrofit.Builder()
                .client(createClient())
                .baseUrl("https://openapi.naver.com/")
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static OkHttpClient createClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        if (BuildConfig.DEBUG) {
            builder.addInterceptor(loggingInterceptor);
        }

        return builder.build();
    }
}
