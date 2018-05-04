package com.ljn.buglysimple.calendar;

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
    public static EcgSingleMonthWrapperModel
    transformData(
            List<RealmPatientEcgObject> realmPatientEcgObjects
            , int monthDayCounts
    ) {



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
        for (int i = 0; i < data.size(); i++) {

            try {
                Date dateResult = dateFormat.parse(data.get(i).getMeasure_time());
                int day = dateResult.getDate() - 1;
                RealmPatientEcgObject bean = data.get(i);

                EcgSingleDayCalendarModel model = singleMonthMap.get(day);
                ArrayList<RealmPatientEcgObject> singleDayList = model.data;
                if (singleDayList != null) {
                    singleDayList.add(bean);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }


        }
        return wrapperModel;
    }


}