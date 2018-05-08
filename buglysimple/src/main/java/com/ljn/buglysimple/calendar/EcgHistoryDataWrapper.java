package com.ljn.buglysimple.calendar;

import android.graphics.Color;
import android.util.Log;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;
import com.ljn.buglysimple.R;
import com.ljn.buglysimple.bean.EcgSingleDayCalendarModel;
import com.ljn.buglysimple.bean.RealmPatientEcgObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by songyuqiang on 16/12/10.
 */
public class EcgHistoryDataWrapper {
    public static EcgSingleMonthWrapperModel transformData(List<RealmPatientEcgObject> realmPatientEcgObjects,
                                                           int year, int month, CalendarView calendarView) {
        int monthCount = calendarView.getMonthCount(year, month);
        int monthStartDiff = calendarView.getMonthStartDiff(year, month);
        int monthEndDiff = calendarView.getMonthEndDiff(year, month);
        int monthDayCounts = monthCount + monthStartDiff + monthEndDiff;
        EcgSingleMonthWrapperModel wrapperModel = new EcgSingleMonthWrapperModel();
        final HashMap<Integer, EcgSingleDayCalendarModel> singleMonthMap = new HashMap<>();
        wrapperModel.ecgData =singleMonthMap;

        for (int i = 0; i < monthDayCounts; i++) {
            EcgSingleDayCalendarModel calendarModel = new EcgSingleDayCalendarModel();
            ArrayList<RealmPatientEcgObject> list = new ArrayList<>();
            calendarModel.data = list;
            singleMonthMap.put(i, calendarModel);
        }
        List<RealmPatientEcgObject> data = realmPatientEcgObjects;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Calendar> schemes = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {

            try {
                Date dateResult = dateFormat.parse(data.get(i).getMeasure_time());
                int day = dateResult.getDate();
                RealmPatientEcgObject bean = data.get(i);

                EcgSingleDayCalendarModel model = singleMonthMap.get(monthStartDiff + day-1);
                ArrayList<RealmPatientEcgObject> singleDayList = model.data;
                if (singleDayList != null) {
                    singleDayList.add(bean);
                }
                Log.e("TAG", "i="+i);
                Log.e("TAG", "day="+day);
                Log.e("TAG", "measure_time="+data.get(i).getMeasure_time());
                String result = bean.getResult_type();
                switch (result){
                    case EcgConstant.RESULT_NORMAL:
                        schemes.add(getSchemeCalendar(year, month, day, R.color.line_color, "假"));
                        break;
                    case EcgConstant.RESULT_ABNORMAL:
                    case EcgConstant.RESULT_SERIOUS:
                    case EcgConstant.RESULT_AUTO_ABNORMAL:
                    case EcgConstant.RESULT_AUTO_SERIOUS:
                        schemes.add(getSchemeCalendar(year, month, day, Color.parseColor("#ff0000"), "假"));
                        break;
                    default:
                        schemes.add(getSchemeCalendar(year, month, day, R.color.solar_background, "假"));
                        break;
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        calendarView.setSchemeDate(schemes);
        return wrapperModel;
    }

    @SuppressWarnings("all")
    private static Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }


}