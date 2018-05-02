package com.ljn.buglysimple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.ljn.buglysimple.calendar.Article;
import com.ljn.buglysimple.calendar.ArticleAdapter;
import com.ljn.buglysimple.group.GroupItemDecoration;
import com.ljn.buglysimple.group.GroupRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/05/02
 *     desc   :
 *     modify :
 * </pre>
 */

public class CalendarActivity extends AppCompatActivity implements CalendarView.OnYearChangeListener, CalendarView.OnDateSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener {
    TextView mTextMonthDay;
    TextView mTextYear;
    CalendarView mCalendarView;
    GroupRecyclerView mRecyclerView;
    ImageView mBtnPast;
    ImageView mBtnFuture;
//    ViewPager vp_content;
    private int mYear;
    CalendarLayout mCalendarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canlendar);
        initView();
        initListener();
        initData();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CalendarActivity.class);
        context.startActivity(intent);
    }

    private void initView() {
        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_year);
        mBtnPast = (ImageView) findViewById(R.id.btn_past);
        mBtnFuture = (ImageView) findViewById(R.id.btn_future);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
//        vp_content = (ViewPager)findViewById(R.id.vp_content);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarView.showYearSelectLayout(mYear);
                    return;
                }
                mCalendarView.showYearSelectLayout(mYear);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(String.valueOf(mYear));
            }
        });

        mCalendarLayout = (CalendarLayout) findViewById(R.id.calendarLayout);

        mTextYear.setText(String.valueOf(mCalendarView.getCurYear()));
        mYear = mCalendarView.getCurYear();
        mTextMonthDay.setText(mCalendarView.getCurMonth() + "月" + mCalendarView.getCurDay() + "日");
        mRecyclerView = (GroupRecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new GroupItemDecoration<String,Article>());
        mRecyclerView.setAdapter(new ArticleAdapter(this));
        mRecyclerView.notifyDataSetChanged();
    }

    private void initListener() {
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnDateSelectedListener(this);
        mBtnPast.setOnClickListener(this);
        mBtnFuture.setOnClickListener(this);
//        vp_content.addOnPageChangeListener(this);
    }

    private void initData() {
        List<Calendar> schemes = new ArrayList<>();
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();

        schemes.add(getSchemeCalendar(year, month, 3, 0, "假"));
        schemes.add(getSchemeCalendar(year, month, 6, 0, "事"));
        schemes.add(getSchemeCalendar(year, month, 9, 0, "议"));
        schemes.add(getSchemeCalendar(year, month, 13, 0, "记"));
        schemes.add(getSchemeCalendar(year, month, 14, 0, "记"));
        schemes.add(getSchemeCalendar(year, month, 15, 0, "假"));
        schemes.add(getSchemeCalendar(year, month, 18, 0, "记"));
        schemes.add(getSchemeCalendar(year, month, 25, 0, "假"));
        mCalendarView.setSchemeDate(schemes);
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mYear = calendar.getYear();
    }

    @SuppressWarnings("all")
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_past :
                mCalendarView.scrollToPre();
                break;
            case R.id.btn_future :
                mCalendarView.scrollToNext();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        int day = mCalendarView.getSelectedCalendar().getDay();
        int month = mCalendarView.getSelectedCalendar().getMonth();
        int year = mCalendarView.getSelectedCalendar().getYear();
        if(positionOffsetPixels >= 0) {
            mCalendarView.scrollToCalendar(year, month, day+1);
        }else {
            mCalendarView.scrollToCalendar(year, month, day+1);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
