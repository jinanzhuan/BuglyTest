package com.ljn.buglytest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/6/5
 *     desc   :
 *     modify :
 * </pre>
 */

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(container.getContext(), getLayoutId(), null);
        ButterKnife.inject(this, view);
        initArgument();
        initView();
        initListener();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化数据bundle
     */
    public void initArgument() {}

    /**
     * 初始化布局
     */
    public void initView() {}

    /**
     * 初始化监听
     */
    public void initListener() {}

    /**
     * 初始化数据
     */
    public void initData() {}

    /**
     * 获取布局id
     * @return
     */
    public abstract int getLayoutId();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
