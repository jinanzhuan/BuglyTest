package com.ljn.buglysimple.api;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import rx.Subscriber;


/**
 * Created by songyuqiang on 16/12/25.
 */
public abstract class BaseSubscriber<T> extends Subscriber<T> {
    private Activity mContext;
    private boolean mShowProgress;
    private boolean mShowErrorMsg;


    public void onCompleted(){
    }

    public BaseSubscriber(){
        this.mShowErrorMsg = true;
    }

    public BaseSubscriber(Activity context){
        this.mContext = context;
        this.mShowErrorMsg = true;
    }

    public BaseSubscriber(Activity context, boolean showProgress, boolean showErrorMsg){
        this.mContext = context;
        this.mShowProgress = showProgress;
        this.mShowErrorMsg = showErrorMsg;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        try{

            if(mContext != null) {
                String message = HandleException.handleException(e);
                if(!TextUtils.isEmpty(message) && mShowErrorMsg) {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public abstract void onNext(T t);
}
