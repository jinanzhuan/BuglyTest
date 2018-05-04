package com.ljn.buglysimple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/05/03
 *     desc   :
 *     modify :
 * </pre>
 */

public class ViewPagerActivity extends AppCompatActivity {
    @InjectView(R.id.dvp_content)
    ViewPager mDvpContent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        ButterKnife.inject(this);
        initView();
        initListener();
        initData();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ViewPagerActivity.class);
        context.startActivity(intent);
    }

    private void initView() {

    }

    private void initListener() {

    }

    private void initData() {
        List<String> data = new ArrayList<>();
        data.add("星期一");
        data.add("星期二");
        data.add("星期三");
        data.add("星期四");
        data.add("星期五");
        data.add("星期六");
        data.add("星期日");
//        CalendarContentAdapter adapter = new CalendarContentAdapter(data);
//        mDvpContent.setAdapter(adapter);
//        mDvpContent.setOffscreenPageLimit(3);
    }
}
