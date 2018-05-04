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
 *     desc   :
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
    String token = "bearer CoQALzN7de6kYlmCkUhlMXtynjBg9d";
    String patientHuid = "5P3HZV";
    private int mMonthCount;//月份天数
    private int mMonthStartDiff;//月份start偏移量
    private int mMonthEndDiff;//月份end偏移量
    private boolean isScroll;
    private int type;//type:0初始化，1上个月，2下个月
    private static final int INIT = 0;
    private static final int PREVIOUS = 1;
    private static final int NEXT = 2;

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

        getDataFormServer(year, month);

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
        if(isScroll) {
            Log.e("TAG", "mPrePosition="+mPrePosition);
            int day = mCalendarView.getSelectedCalendar().getDay();
            int month = mCalendarView.getSelectedCalendar().getMonth();
            int year = mCalendarView.getSelectedCalendar().getYear();
            //滑动到下一个月
            if(position - mMonthStartDiff > mMonthCount) {
                if(month + 1 > 12) {
                    year ++;
                    month = 1;
                }else {
                    month ++;
                }
                type = NEXT;
                mCalendarView.scrollToCalendar(year, month, 1);
                return;
            }
            //滑动到上一个月
            if(position <= mMonthStartDiff) {
                if(month - 1 < 1) {
                    year--;
                    month = 12;
                }else {
                    month--;
                }
                type = PREVIOUS;
                int monthCount = mCalendarView.getMonthCount(year, month);
                mCalendarView.scrollToCalendar(year, month, monthCount);
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
        getDataFormServer(year, month);
    }

    private void getDataFormServer(int year, int month) {
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
                            sortMonthData(listResponse.body());
                        }
                    }

                });
    }

    private void sortMonthData(final List<RealmPatientEcgObject> body) {
        EcgSingleMonthWrapperModel wrapperModel = EcgHistoryDataWrapper.transformData(body, mMonthCount + mMonthStartDiff + mMonthEndDiff);
        CalendarContentAdapter adapter = new CalendarContentAdapter(wrapperModel.ecgData, patientHuid);
        mVpContent.setAdapter(adapter);
        switch (type) {
            case INIT ://0初始化
                int curDay = mCalendarView.getCurDay();
                mPrePosition = curDay + mMonthStartDiff;
                isScroll = false;
                mVpContent.setCurrentItem(mPrePosition);
                break;
            case PREVIOUS ://1上个月
                mPrePosition = mMonthCount + mMonthStartDiff;
                isScroll = false;
                mVpContent.setCurrentItem(mPrePosition);
                break;
            case NEXT ://2下个月
                mPrePosition = mMonthStartDiff;
                isScroll = false;
                mVpContent.setCurrentItem(mPrePosition);
                break;
        }
    }
}
