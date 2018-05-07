package com.ljn.buglysimple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;
import com.ljn.buglysimple.adapter.CalendarContentAdapter;
import com.ljn.buglysimple.api.ApiFactory;
import com.ljn.buglysimple.api.BaseSubscriber;
import com.ljn.buglysimple.api.IApiService;
import com.ljn.buglysimple.bean.RealmPatientEcgObject;
import com.ljn.buglysimple.calendar.EcgHistoryDataWrapper;
import com.ljn.buglysimple.calendar.EcgSingleMonthWrapperModel;
import com.ljn.buglysimple.view.DirectionViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.ljn.buglysimple.R.id.vp_content;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/05/02
 *     desc   : 分两种情况：1、滑动月份下面的viewpager,
 *              分为四种情况：
 *              （1）上一天
 *              （2）下一天
 *              （3）上一个月 调用scrollToPre()函数
 *              （4）下一个月 调用scrollToNext()函数
 *              第三四中情况，会引发onMonthChange，接着调用网络请求，获取相关月份的数据，最后会调用onDateSelected函数
 *              2、滑动月份，会引发onMonthChange，接着调用网络请求，获取相关月份的数据，最后会调用onDateSelected函数
 *              根据以上分析，在滑动月份下面的viewpager后，优先处理月份跳转，然后在onDateSelected函数中处理viewpager的相关移动
 *     modify :
 * </pre>
 */

public class CalendarActivity extends AppCompatActivity implements CalendarView.OnYearChangeListener, CalendarView.OnDateSelectedListener, View.OnClickListener, ViewPager.OnPageChangeListener, CalendarView.OnMonthChangeListener {
    //    GroupRecyclerView mRecyclerView;
    @InjectView(R.id.btn_past)
    ImageView mBtnPast;
    @InjectView(R.id.tv_month_day)
    TextView mTextMonthDay;
    @InjectView(R.id.tv_year)
    TextView mTextYear;
    @InjectView(R.id.btn_future)
    ImageView mBtnFuture;
    @InjectView(R.id.calendarView)
    CalendarView mCalendarView;
    @InjectView(vp_content)
    DirectionViewPager mVpContent;
    @InjectView(R.id.calendarLayout)
    CalendarLayout mCalendarLayout;
    private int mYear;
    private int mPrePosition;
    private IApiService mIApiService;
    String token = "bearer Y5DUvWYYGYmFqyxVmPA8zqTW9XDhYx";
    String patientHuid = "5P3HZV";
    private int mMonthCount;//月份天数
    private int mMonthStartDiff;//月份start偏移量
    private int mMonthEndDiff;//月份end偏移量
    private boolean isScroll;
    private boolean mIsCalendarClick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canlendar);
        ButterKnife.inject(this);
        initApi();
        initView();
        initListener();
        initData();
    }

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CalendarActivity.class);
        context.startActivity(intent);
    }

    private void initApi() {
        mIApiService = ApiFactory.create(token);
    }

    private void initView() {
        mTextMonthDay = (TextView) findViewById(R.id.tv_month_day);
        mTextYear = (TextView) findViewById(R.id.tv_year);
        mBtnPast = (ImageView) findViewById(R.id.btn_past);
        mBtnFuture = (ImageView) findViewById(R.id.btn_future);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mVpContent = (DirectionViewPager) findViewById(vp_content);
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
    }

    private void initListener() {
        mCalendarView.setOnYearChangeListener(this);
        mCalendarView.setOnDateSelectedListener(this);
        mCalendarView.setOnMonthChangeListener(this);
        mBtnPast.setOnClickListener(this);
        mBtnFuture.setOnClickListener(this);
        mVpContent.addOnPageChangeListener(this);
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
        getDataFormServer(year, month, true);

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
        mIsCalendarClick = isClick;
        //移动viewpager均在此进行处理
        if(isClick) {
            int position = calendar.getDay()-1 + mMonthStartDiff;
            isScroll = false;
            mVpContent.setCurrentItem(position);//定位viewpager到相关日历下
            mPrePosition = position;
            mIsCalendarClick = false;
        }
        Log.e("TAG", "calendar.getDay()="+calendar.getDay());
        Log.e("TAG", "mMonthStartDiff="+mMonthStartDiff);
        Log.e("TAG", "mPrePosition 11="+mPrePosition);
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
            case R.id.btn_past:
                mCalendarView.scrollToPre();
                break;
            case R.id.btn_future:
                mCalendarView.scrollToNext();
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.e("TAG", "isScroll ="+isScroll);

        if(isScroll && !mIsCalendarClick) {
            Log.e("TAG", "mPrePosition 22="+mPrePosition);
            Log.e("TAG", "position 22="+position);
            int day = mCalendarView.getSelectedCalendar().getDay();
            int month = mCalendarView.getSelectedCalendar().getMonth();
            int year = mCalendarView.getSelectedCalendar().getYear();
            //滑动到下一个月
            if(position - mMonthStartDiff > mMonthCount) {
                Log.e("TAG", "滑动到下一个月");
                mCalendarView.scrollToNext();
                return;
            }
            //滑动到上一个月
            if(position < mMonthStartDiff) {
                Log.e("TAG", "滑动到上一个月");
                mCalendarView.scrollToPre();
                return;
            }
            if (position > mPrePosition) {
                mCalendarView.scrollToCalendar(year, month, day + 1);
            } else {
                mCalendarView.scrollToCalendar(year, month, day - 1);
            }
            mPrePosition = position;
        }else {
            isScroll = true;
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onMonthChange(int year, int month) {
        getDataFormServer(year, month, false);
    }

    private void getDataFormServer(int year, int month, final boolean isFirst) {
        String startDate = mCalendarView.getStartDate(year, month);
        String endDate = mCalendarView.getEndDate(year, month);
        mMonthCount = mCalendarView.getMonthCount(year, month);
        mMonthStartDiff = mCalendarView.getMonthStartDiff(year, month);
        mMonthEndDiff = mCalendarView.getMonthEndDiff(year, month);
        mIApiService.fetchEcgHistory(patientHuid, null, null, startDate, endDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<Response<List<RealmPatientEcgObject>>>(this, true, true) {
                    @Override
                    public void onNext(Response<List<RealmPatientEcgObject>> listResponse) {
                        if(listResponse.isSuccessful()) {
                            sortMonthData(listResponse.body(), isFirst);
                        }
                    }

                });
    }

    private void sortMonthData(List<RealmPatientEcgObject> body, boolean isFirst) {
        EcgSingleMonthWrapperModel wrapperModel = EcgHistoryDataWrapper.transformData(body, mMonthCount + mMonthStartDiff + mMonthEndDiff);
        CalendarContentAdapter adapter = new CalendarContentAdapter(wrapperModel.ecgData, patientHuid);
        mVpContent.setAdapter(adapter);
        if(isFirst) {
            int position = mCalendarView.getCurDay() - 1 + mMonthStartDiff;
            isScroll = false;
            mVpContent.setCurrentItem(position);//定位viewpager到相关日历下
            mPrePosition = position;
            Log.e("TAG", "isFisrt mPrePosition="+mPrePosition);
        }
    }
}
