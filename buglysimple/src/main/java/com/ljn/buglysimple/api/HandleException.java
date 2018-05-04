package com.ljn.buglysimple.api;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Response;

/**
 * Created by songyuqiang on 16/11/18.
 */
public class HandleException {
    public static Response handle(Response response) {
        return null;
    }

    public static String handleException(Throwable throwable){
        if(throwable instanceof UnknownHostException){
            return "网络连接失败,请检查网络后重试";
        }
        if(throwable instanceof SocketTimeoutException){
            return "网络连接超时,请检查网络后重试";
        }

        if(throwable instanceof EhException) {
            return throwable.getMessage();
        }
        return "";
    }

}
