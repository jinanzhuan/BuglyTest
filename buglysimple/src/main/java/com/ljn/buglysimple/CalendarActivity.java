package com.ljn.buglysimple;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class CalendarActivity extends AppCompatActivity implements CalendarView.OnYearChangeListener, CalendarView.OnDateSelectedListener, View.OnClickListener, CalendarView.OnMonthChangeListener, DirectionViewPager.ChangeViewCallback {
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
    private IApiService mIApiService;
    String token = "bearer QHCXoxrNQ1uawL1JT0CSlYFU8W29hQ";
    String patientHuid = "5P3HZV";
    private int mMonthCount;//月份天数
    private int mMonthStartDiff;//月份start偏移量
    private int mMonthEndDiff;//月份end偏移量
    private Calendar calendar;

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
        mVpContent.setChangeViewCallback(this);
    }

    private void initData() {
        int year = mCalendarView.getCurYear();
        int month = mCalendarView.getCurMonth();
        getDataFormServer(year, month);

    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }

    @Override
    public void onDateSelected(Calendar calendar, boolean isClick) {
        this.calendar = calendar;
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mYear = calendar.getYear();
        //移动viewpager均在此进行处理
        int position = calendar.getDay()-1 + mMonthStartDiff;
        mVpContent.setCurrentItem(position, false);//定位viewpager到相关日历下
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
    public void onMonthChange(int year, int month) {
        getDataFormServer(year, month);
    }

    private void getDataFormServer(final int year, final int month) {
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
                            sortMonthData(listResponse.body(), year, month);
                        }
                    }

                });
    }

    private void sortMonthData(List<RealmPatientEcgObject> body, int year, int month) {
        EcgSingleMonthWrapperModel wrapperModel = EcgHistoryDataWrapper.transformData(body, year, month, mCalendarView);
        CalendarContentAdapter adapter = new CalendarContentAdapter(wrapperModel.ecgData, patientHuid);
        mVpContent.setAdapter(adapter);
        if(calendar != null) {
            int position = calendar.getDay() - 1 + mMonthStartDiff;
            mVpContent.setCurrentItem(position, false);//定位viewpager到相关日历下
        }
    }

    @Override
    public void changeView(boolean left, boolean right) {
        int day = mCalendarView.getSelectedCalendar().getDay();
        int month = mCalendarView.getSelectedCalendar().getMonth();
        int year = mCalendarView.getSelectedCalendar().getYear();
        if(left) {
            if(day >= mMonthCount) {
                Log.e("TAG", "滑动到下一个月");
                mCalendarView.scrollToNext();
            }else {
                mCalendarView.scrollToCalendar(year, month, day + 1);
            }
        }
        if(right) {
            //滑动到上一个月
            if(day <= 1) {
                Log.e("TAG", "滑动到上一个月");
                mCalendarView.scrollToPre();
            }else {
                mCalendarView.scrollToCalendar(year, month, day - 1);
            }
        }
    }

    @Override
    public void getCurrentPageIndex(int position) {
        Log.e("TAG", "postion="+position);
    }
}
