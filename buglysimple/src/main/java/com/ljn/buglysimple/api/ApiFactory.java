package com.ljn.buglysimple.api;

import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/05/03
 *     desc   :
 *     modify :
 * </pre>
 */

public class ApiFactory {
    public final static int TIME_OUT_SECONDS = 15;
    private static final String TAG = "TAG";
    private static final boolean SHOWRESPONSE = true;
    private static final boolean SHOWREQUEST = true;

    public static IApiService create(String token) {
        TokenInterceptor tokenInterceptor = new TokenInterceptor(token);
        OkHttpClient client = new OkHttpClient().newBuilder()
                .addInterceptor(tokenInterceptor)
                .addInterceptor(new ErrorInterceptor(new Gson()))
                .readTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .writeTimeout(TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();
//设置缓存
//            int cacheSize = 10 * 1024 * 1024; // 10 MiB
//            Cache cache = new Cache(context.getCacheDir(), cacheSize);
//            builder.cache(cache);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.eheartcare.net/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        return retrofit.create(IApiService.class);
    }
}
