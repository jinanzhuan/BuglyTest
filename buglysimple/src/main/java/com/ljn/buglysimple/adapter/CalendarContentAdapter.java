package com.ljn.buglysimple.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ljn.buglysimple.R;
import com.ljn.buglysimple.bean.EcgSingleDayCalendarModel;
import com.ljn.buglysimple.view.MyListView;

import java.util.HashMap;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/05/03
 *     desc   :
 *     modify :
 * </pre>
 */

public class CalendarContentAdapter extends PagerAdapter {
    private HashMap<Integer, EcgSingleDayCalendarModel> mData;
    private String mPatientHuid;

    public CalendarContentAdapter(HashMap<Integer, EcgSingleDayCalendarModel> data, String patientHuid) {
        this.mData = data;
        this.mPatientHuid = patientHuid;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager, container, false);

        MyListView lv = (MyListView) view.findViewById(R.id.lv);
        LinearLayout ll_no_ecg = (LinearLayout) view.findViewById(R.id.ll_no_ecg);
        View line = view.findViewById(R.id.blue_line);

        if (mData.get(position).data == null || mData.get(position).data.size() == 0)
        {
            line.setVisibility(View.GONE);
            ll_no_ecg.setVisibility(View.VISIBLE);
        }else {
            ll_no_ecg.setVisibility(View.GONE);
        }

        lv.setDivider(null);

        EcgHistoryListviewAdapter adapter = new EcgHistoryListviewAdapter
                (mData.get(position).data,0, mPatientHuid);

        lv.setAdapter(adapter);
        container.addView(view);

        return view;
    }

}
