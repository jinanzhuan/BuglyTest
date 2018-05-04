package com.ljn.buglysimple.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ljn.buglysimple.R;
import com.ljn.buglysimple.bean.RealmPatientEcgObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by Administrator on 2016/5/6.
 */
public class EcgHistoryListviewAdapter extends BaseAdapter {
    private int mFrom;
    private HashMap<Integer, ArrayList<RealmPatientEcgObject>> mData;
    private int mDisplayMode;
    private String mPatientHuid;

    private ArrayList<RealmPatientEcgObject> realmPatientEcgObjects;

    public EcgHistoryListviewAdapter(ArrayList<RealmPatientEcgObject> realmPatientEcgObjects) {
        this.realmPatientEcgObjects = realmPatientEcgObjects;
    }

    public EcgHistoryListviewAdapter(ArrayList<RealmPatientEcgObject> realmPatientEcgObjects, int from, String patientHuid) {
        this.realmPatientEcgObjects = realmPatientEcgObjects;
        mFrom = from;
        this.mPatientHuid = patientHuid;
    }

    @Override
    public int getCount() {
        return realmPatientEcgObjects.size();
    }

    @Override
    public RealmPatientEcgObject getItem(int position) {
        return realmPatientEcgObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ecghisttory_ecg_header, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        final RealmPatientEcgObject bean = realmPatientEcgObjects.get(position);
        String huid = bean.getHuid();
        if (TextUtils.isEmpty(huid)) {
            return new TextView(parent.getContext());
        }

        randerDate(bean, convertView);
        getEcgThumbnail(bean, parent, holder.mIvEcghistoryThumb);
        final Activity context = (Activity) parent.getContext();
        initListener(bean, convertView, context);
        return convertView;

    }

    private void getEcgThumbnail(RealmPatientEcgObject bean, ViewGroup parent, ImageView ivThumb) {
    }

    private void randerState(ViewGroup parent, RealmPatientEcgObject bean
            , TextView bpm, TextView state, TextView tvReadState
            , LinearLayout llBg, TextView tvIdentity) {

        bpm.setText(bean.getBpm() + "");
    }

    private void initListener(RealmPatientEcgObject bean, View view, Activity context) {
    }

    private void randerDate(RealmPatientEcgObject bean, View view) {
        String measureTime = bean.getMeasure_time();

        //// TODO: 2016/5/20 显示异常
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat myFmt = new SimpleDateFormat("HH:mm");
        try {
            Date parse = dateFormat.parse(measureTime);
            String format = myFmt.format(parse);
            TextView tvDate = (TextView) view.findViewById(R.id.tv_ecgitem_date);
            if (!TextUtils.isEmpty(measureTime)) {
                tvDate.setText(format);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public class ViewHolder {
        @InjectView(R.id.iv_ecg_historyheader)
        LinearLayout mIvEcgHistoryheader;
        @InjectView(R.id.iv_ecghistory_thumb)
        ImageView mIvEcghistoryThumb;
        @InjectView(R.id.ic_icon_ecg_report)
        ImageView mIcIconEcgReport;
        @InjectView(R.id.tv_item_ecghistory_read_state)
        TextView mTvItemEcghistoryReadState;
        @InjectView(R.id.rl_ecg_item_container)
        RelativeLayout mRlEcgItemContainer;
        @InjectView(R.id.tv_item_identity)
        TextView mTvItemIdentity;
        @InjectView(R.id.tv_item_ecghistory_state)
        TextView mTvItemEcghistoryState;
        @InjectView(R.id.ll_lable_bg)
        LinearLayout mLlLableBg;
        @InjectView(R.id.tv_ecgitem_date)
        TextView mTvEcgitemDate;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
