package com.ljn.buglysimple.calendar;

/**
 * Created by Administrator on 2017/3/10.
 */

public class EcgConstant
{
    public static final int MORNING_ECG = 0;
    public static final int SPORT_ECG = 1;
    public static final int FULL_ECG = 2;

    //读图类型
    public static final String READ_UNREAD = "UNREAD";//0
    public static final String READ_NORMAL = "NORMAL";//1
    public static final String READ_QUICK = "QUICK";//2
    public static final String READ_URGENT = "URGENT";//3

    //心电图的测量结果
    public static final String RESULT_UNKNOWN = "UNKNOWN";//0
    public static final String RESULT_INVALID = "INVALID";//1
    public static final String RESULT_NORMAL = "NORMAL";//2
    public static final String RESULT_ABNORMAL = "ABNORMAL";//3
    public static final String RESULT_SERIOUS = "SERIOUS";//4
    public static final String RESULT_AUTO_INVALID = "AUTO_INVALID";//-1
    public static final String RESULT_AUTO_NORMAL = "AUTO_NORMAL";//-2
    public static final String RESULT_AUTO_ABNORMAL = "AUTO_ABNORMAL";//-3
    public static final String RESULT_AUTO_SERIOUS = "AUTO_SERIOUS";//-4
}
