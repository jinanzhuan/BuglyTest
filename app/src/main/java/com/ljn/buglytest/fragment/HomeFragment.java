package com.ljn.buglytest.fragment;

import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ljn.buglytest.BaseFragment;
import com.ljn.buglytest.R;
import com.ljn.buglytest.utils.StatusBarCompat;

import butterknife.InjectView;


/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/7/12
 *     desc   :
 *     modify :
 * </pre>
 */

public class HomeFragment extends BaseFragment {
    @InjectView(R.id.vp_home_adv)
    ViewPager vpHomeAdv;
    private boolean isResume;

    private int draw[] = {R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4, R.drawable.banner5};
    int mPosition = 0;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // 轮播图，自定义的消息传递，有可能内存泄漏，待完善
            vpHomeAdv.setCurrentItem(mPosition);
            if (mPosition < 5) {
                mPosition++;
            }
            if (mPosition == 5) {
                mPosition = 0;
            }
            sendEmptyMessageDelayed(0, 5000);
        }
    };
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        super.initView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        StatusBarCompat.compat(getActivity(), Color.parseColor("#336699"));
        vpHomeAdv.setAdapter(new VpHomeAdvAdapter());
        vpHomeAdv.setOffscreenPageLimit(5);
    }

    @Override
    public void onResume() {
        super.onResume();
        isResume = true;
        StatusBarCompat.compat(getActivity(), Color.TRANSPARENT);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isResume) {
            if(isVisibleToUser) {
                StatusBarCompat.compat(getActivity(), Color.TRANSPARENT);
            }else {
                StatusBarCompat.compat(getActivity(), Color.parseColor("#336699"));
            }

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacksAndMessages(null);
    }

    public class VpHomeAdvAdapter extends PagerAdapter implements View.OnTouchListener {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {

            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(container.getContext(), R.layout.item_homevp, null);

            ImageView iv = (ImageView) view;
            Glide.with(container.getContext()).load(draw[position]).into(iv);
            iv.setOnTouchListener(this);

            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);

        }

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.removeCallbacksAndMessages(null);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.sendMessageDelayed(Message.obtain(), 3000);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    handler.sendMessageDelayed(Message.obtain(), 3000);
                    break;

                default:
                    break;
            }
            return true;
        }
    }
}
