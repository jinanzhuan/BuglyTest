package com.ljn.buglytest.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;


/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/7/11
 *     desc   :
 *     modify :
 * </pre>
 */

public class MyImageView extends AppCompatImageView implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {
    private GestureDetector mGestureDetector;

    public MyImageView(Context context) {
        this(context, null);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mGestureDetector = new GestureDetector(getContext(), this);
        mGestureDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    /**
     * 用户按下屏幕就会触发；
     * @param e
     * @return
     */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.e("TAG", "onDown");
        return true;
    }

    /**
     * 用户按下按键后100ms（根据Android7.0源码）还没有松开或者移动就会回调，
     * 官方在源码的解释是说一般用于告诉用户已经识别按下事件的回调（我暂时想不出有什么用途，
     * 因为这个回调触发之后还会触发其他的，不像长按）。
     * @param e
     */
    @Override
    public void onShowPress(MotionEvent e) {
        Log.e("TAG", "onShowPress");
    }

    /**
     * 用户手指松开（UP事件）的时候如果没有执行onScroll()和onLongPress()这两个回调的话
     * ，就会回调这个，说明这是一个点击抬起事件，但是不能区分是否双击事件的抬起。
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.e("TAG", "onSingleTapUp");
        return true;
    }

    /**
     * 手指滑动的时候执行的回调（接收到MOVE事件，且位移大于一定距离）
     * ，e1,e2分别是之前DOWN事件和当前的MOVE事件
     * ，distanceX和distanceY就是当前MOVE事件和上一个MOVE事件的位移量。
     * @param e1
     * @param e2
     * @param distanceX
     * @param distanceY
     * @return
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        Log.e("TAG", "onScroll");
        return true;
    }

    /**
     * 用户长按后（好像不同手机的时间不同，源码里默认是100ms+500ms）触发，触发之后不会触发其他回调，直至松开（UP事件）。
     * @param e
     * @return
     */
    @Override
    public void onLongPress(MotionEvent e) {
        Log.e("TAG", "onLongPress");
    }

    /**
     * 用户执行抛操作之后的回调，MOVE事件之后手松开（UP事件）那一瞬间的x或者y方向速度
     * ，如果达到一定数值（源码默认是每秒50px），就是抛操作（也就是快速滑动的时候松手会有这个回调
     * ，因此基本上有onFling必然有onScroll）。
     * @param e1
     * @param e2
     * @param velocityX
     * @param velocityY
     * @return
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.e("TAG", "onFling");
        return true;
    }

    /**
     * 可以确认（通过单击DOWN后300ms没有下一个DOWN事件确认）这不是一个双击事件，而是一个单击事件的时候会回调。
     * @param e
     * @return
     */
    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.e("TAG", "onSingleTapConfirmed");
        return true;
    }

    /**
     * 可以确认这是一个双击事件的时候回调。
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.e("TAG", "onDoubleTap");
        return true;
    }

    /**
     * onDoubleTap()回调之后的输入事件（DOWN、MOVE、UP）都会回调这个方法（这个方法可以实现一些双击后的控制
     * ，如让View双击后变得可拖动等）。
     * @param e
     * @return
     */
    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.e("TAG", "onDoubleTapEvent");
        return true;
    }
}
