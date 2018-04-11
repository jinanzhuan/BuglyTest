package com.ljn.buglysimple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/04/11
 *     desc   :
 *     modify :
 * </pre>
 */

public class ProgressActivity extends AppCompatActivity {
    private CompletedView cv_progress;
    private int mTotalProgress = 100;
    private int mCurrentProgress = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);
        initView();
        initListener();
        initData();
    }

    public static void actionStart(Context context){
        Intent intent = new Intent(context, ProgressActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        cv_progress = (CompletedView)findViewById(R.id.cv_progress);
    }

    private void initListener() {

    }

    private void initData() {
        new Thread(new ProgressRunable()).start();
    }

    class ProgressRunable implements Runnable {
        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;
                cv_progress.setProgress(mCurrentProgress);
                try {
                    Thread.sleep(40);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(mCurrentProgress == mTotalProgress) {
                    mCurrentProgress = 0;
                }
            }
        }
    }
}
