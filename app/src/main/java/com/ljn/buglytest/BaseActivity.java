package com.ljn.buglytest;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.ljn.buglytest.utils.StatusBarCompat;

import butterknife.ButterKnife;


/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/6/5
 *     desc   :
 *     modify :
 * </pre>
 */

public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(getLayoutId());
        ButterKnife.inject(this);
        mContext = this;
        initIntent();
        initView();
        initListener();
        initData();
    }

    /**
     * 初始化intent
     */
    public void initIntent() {}

    /**
     * 初始化布局
     */
    public void initView() { }

    /**
     * 初始化各类监听
     */
    public void initListener() {}

    /**
     * 初始化数据
     */
    public void initData() {}

    /**
     * 获取布局Id
     * @return
     */
    public abstract int getLayoutId();

    public void setFitSystemForTheme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        ViewGroup contentFrameLayout = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
        View parentView = contentFrameLayout.getChildAt(0);
        if (parentView != null && Build.VERSION.SDK_INT >= 14) {
            parentView.setFitsSystemWindows(true);
        }
        //初始设置
        StatusBarCompat.compat(mContext, Color.parseColor("#336699"));
    }

}
