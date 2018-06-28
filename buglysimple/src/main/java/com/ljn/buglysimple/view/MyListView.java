package com.ljn.buglysimple.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/05/08
 *     desc   :
 *     modify :
 * </pre>
 */

public class MyListView extends ListView {
    private float preX, preY;
    private float currentX, currentY;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN :
                preX = ev.getX();
                preY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                currentX = ev.getX();
                currentY = ev.getY();
                if(currentY - preY > 5 && getFirstVisiblePosition() != 0) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }else {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
