package com.ljn.buglysimple.calendar;

import com.ljn.buglysimple.bean.EcgSingleDayCalendarModel;

import java.util.HashMap;

/**
 * Created by songyuqiang on 16/12/11.
 */
public class EcgSingleMonthWrapperModel {
   public HashMap<Integer, EcgSingleDayCalendarModel> ecgData;
/*
    Ecg get(int day);
    HashMap<day_index, EcgSingleDayCalendarModel> ecgData;
    int count
    HashMap<day_index, RESULT_TYPE> point;
    get_points();
    set_point(day_index, result_type)
    get_day_count();*/

    //在set point里，要判断danger nomral的优先级

    //view pager 不需要显示的页面直接让ecgData返回null

    //第一次的时候把所有数据都取下来，以后只需要updatetime更新diff



}
