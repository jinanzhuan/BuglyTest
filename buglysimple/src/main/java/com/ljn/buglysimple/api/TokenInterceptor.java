package com.ljn.buglysimple.api;

import android.text.TextUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by songyuqiang on 16/11/3.
 */
public class TokenInterceptor implements Interceptor {
    private String token;

    public TokenInterceptor(String token){
        this.token = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String tokenHeader = chain.request().header("Authorization");
        Request.Builder requestBuilder = chain
                .request()
                .newBuilder();
        if(TextUtils.isEmpty(tokenHeader)&&!TextUtils.isEmpty(token)){
            requestBuilder
                    .removeHeader("Authorization")
                    .addHeader("Authorization", token);
        }
        requestBuilder.addHeader("Accept","application/json;version=1.2");
        return chain.proceed(requestBuilder.build());
    }
}
