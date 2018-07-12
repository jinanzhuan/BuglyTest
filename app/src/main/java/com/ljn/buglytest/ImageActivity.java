package com.ljn.buglytest;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ljn.buglytest.adapter.MainPagerAdapter;
import com.ljn.buglytest.view.NoScrollViewPager;

import butterknife.InjectView;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/7/11
 *     desc   :
 *     modify :
 * </pre>
 */

public class ImageActivity extends BaseActivity {

    @InjectView(R.id.vp_fragment)
    NoScrollViewPager mVpFragment;
    @InjectView(R.id.tl_title)
    TabLayout mTlTitle;

    private String[] titles = {"首页", "消息", "我"};
    private int[] tabIcons = {
            R.drawable.doctor_homemenuhomegray,
            R.drawable.doctor_homemenumessagegray,
            R.drawable.doctor_homemenumygray
    };
    private int[] tabSelectedIcons = {
            R.drawable.doctor_homemenuhomeblue,
            R.drawable.doctor_homemenumessageblue,
            R.drawable.doctor_homemenumyblue
    };
    private MainPagerAdapter mPageAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.activity_image;
    }

    public static void actionStart(Context context) {
        Intent starter = new Intent(context, ImageActivity.class);
        context.startActivity(starter);
    }

    @Override
    public void initView() {
        super.initView();
        mVpFragment.setOffscreenPageLimit(2);
        mPageAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mVpFragment.setAdapter(mPageAdapter);
        mVpFragment.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTlTitle));
        mTlTitle.setupWithViewPager(mVpFragment);
        mTlTitle.getTabAt(0).setCustomView(getTabView(0));
        mTlTitle.getTabAt(1).setCustomView(getTabView(1));
        mTlTitle.getTabAt(2).setCustomView(getTabView(2));
    }

    @Override
    public void initListener() {
        super.initListener();
        mTlTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                changeTabStatus(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                changeTabStatus(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mVpFragment.setCurrentItem(0);
        changeTabStatus(mTlTitle.getTabAt(0), true);
    }

    private View getTabView(final int position) {
        View view = View.inflate(mContext, R.layout.tab_item_main, null);
        TextView mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        ImageView mIvTitle = (ImageView) view.findViewById(R.id.iv_title);
        mIvTitle.setImageResource(tabIcons[position]);
        mTvTitle.setText(titles[position]);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVpFragment.setCurrentItem(position);
            }
        });
        return view;
    }
    private void changeTabStatus(TabLayout.Tab tab, boolean selected) {
        int position = tab.getPosition();
        View view = tab.getCustomView();
        if(view == null) {
            return;
        }
        final ImageView ivTitle = (ImageView) view.findViewById(R.id.iv_title);
        TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
        if (selected) {
            tvTitle.setTextColor(Color.parseColor("#4395bb"));
            ivTitle.setImageResource(tabSelectedIcons[position]);
        } else {
            tvTitle.setTextColor(Color.parseColor("#808080"));
            ivTitle.setImageResource(tabIcons[position]);
        }
    }

}
