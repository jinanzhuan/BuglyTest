package com.ljn.buglysimple.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Administrator on 2016/3/31.
 */
public class DirectionViewPager extends ViewPager {



    private boolean left = false;
    private boolean right = false;
    private boolean isScrolling = false;
    private int lastValue = -1;
    private ChangeViewCallback changeViewCallback = null;
    private int position;

    public DirectionViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DirectionViewPager(Context context) {
        super(context);
        init();
    }

    /**
     * init method .
     */
    private void init() {
        setOnPageChangeListener(listener);
    }

    /**
     * listener ,to get move direction .
     */
    public OnPageChangeListener listener = new OnPageChangeListener() {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 1) {
                isScrolling = true;
            } else {
                isScrolling = false;
            }


            if (arg0 == 2) {

                //notify ....
                if(changeViewCallback!=null){
                    changeViewCallback.changeView(left, right);
                }
                right = left = false;
            }

//            if(arg0 == 0) {
//                if(changeViewCallback!=null){
//                    if(position == 0) {
//                        left = false;
//                        right = true;
//                    }
//                    if(position == getAdapter().getCount()) {
//                        right = false;
//                        left = true;
//                    }
//                    changeViewCallback.changeView(true, false);
//                    right = left = false;
//                }
//            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            if (isScrolling) {
                if (lastValue > arg2) {
                    // 递减，向右侧滑动
                    right = true;
                    left = false;
                } else if (lastValue < arg2) {
                    // 递减，向右侧滑动
                    right = false;
                    left = true;
                } else if (lastValue == arg2) {
                    right = left = false;
                }
            }
            lastValue = arg2;
        }

        @Override
        public void onPageSelected(int arg0) {
            position = arg0;
            if(changeViewCallback!=null){
                changeViewCallback.getCurrentPageIndex(arg0);
            }
        }
    };

    /**
     * 得到是否向右侧滑动
     * @return true 为右滑动
     */
    public boolean getMoveRight(){
        return right;
    }

    /**
     * 得到是否向左侧滑动
     * @return true 为左做滑动
     */
    public boolean getMoveLeft(){
        return left;
    }

    /**
     *  滑动状态改变回调
     * @author zxy
     *
     */
    public interface ChangeViewCallback{
        /**
         * 切换视图 ？决定于left和right 。
         * @param left
         * @param right
         */
        public  void changeView(boolean left, boolean right);
        public  void  getCurrentPageIndex(int index);
    }

    /**
     * set ...
     * @param callback
     */
    public void  setChangeViewCallback(ChangeViewCallback callback){
        changeViewCallback = callback;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
