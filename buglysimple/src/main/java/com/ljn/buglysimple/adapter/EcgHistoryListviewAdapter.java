package com.ljn.buglysimple.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ljn.buglysimple.R;
import com.ljn.buglysimple.bean.RealmPatientEcgObject;
import com.ljn.buglysimple.calendar.EcgConstant;
import com.ljn.buglysimple.calendar.ImageUrlManager;

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
        randerState(parent, bean, holder.bpm, holder.state, holder.tvReadState, holder.llBg, holder.tvIdentity);

        randerDate(bean, convertView);
        getEcgThumbnail(bean, parent, holder.ivThumb);
        final Activity context = (Activity) parent.getContext();
        initListener(bean, convertView, context);
        return convertView;

    }

    private void getEcgThumbnail(RealmPatientEcgObject bean, ViewGroup parent, ImageView ivThumb) {
        String url = ImageUrlManager.getInstance().getEcgThumbUrl(mPatientHuid, bean.getHuid());
        Glide.with(parent.getContext()).load(url)
                .error(R.drawable.doctor_patient_particulars_calendar_default2x)
                .placeholder(R.drawable.doctor_patient_particulars_calendar_default2x)
                .into(ivThumb);
    }

    private void randerState(ViewGroup parent, RealmPatientEcgObject bean
            , TextView bpm, TextView state, TextView tvReadState
            , LinearLayout llBg, TextView tvIdentity) {

        if(TextUtils.equals(bean.getResult_type(), EcgConstant.RESULT_NORMAL)){
            llBg.setBackgroundResource(R.drawable.bg_ll_label_nomal);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_doctor);
            tvIdentity.setText("医生");
            state.setText("正常");
        }else if(TextUtils.equals(bean.getResult_type(),EcgConstant.RESULT_AUTO_NORMAL)){

            llBg.setBackgroundResource(R.drawable.bg_ll_label_nomal);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_auto);
            tvIdentity.setText("自动");
            state.setText("未见房颤");

        }else if(TextUtils.equals(bean.getResult_type(), EcgConstant.RESULT_ABNORMAL)){

            llBg.setBackgroundResource(R.drawable.bg_ll_label_abnormal);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_doctor);
            tvIdentity.setText("医生");
            state.setText("异常");
        }else if(TextUtils.equals(bean.getResult_type(), EcgConstant.RESULT_AUTO_ABNORMAL)){

            llBg.setBackgroundResource(R.drawable.bg_ll_label_visit);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_auto);
            tvIdentity.setText("自动");
            state.setText("异常");

        }else if(TextUtils.equals(bean.getResult_type(), EcgConstant.RESULT_SERIOUS)){
            llBg.setBackgroundResource(R.drawable.bg_ll_label_visit);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_doctor);
            tvIdentity.setText("医生");
            state.setText("需就诊");
        }else if(TextUtils.equals(bean.getResult_type(), EcgConstant.RESULT_AUTO_SERIOUS)){
            llBg.setBackgroundResource(R.drawable.bg_ll_label_visit);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_auto);
            tvIdentity.setText("自动");
            state.setText("疑似房颤");
        }
        else if(TextUtils.equals(bean.getResult_type(), EcgConstant.RESULT_INVALID)) {
            llBg.setBackgroundResource(R.drawable.bg_ll_label_unknow);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_doctor);
            tvIdentity.setText("医生");
            state.setText("无效");
        }else if(TextUtils.equals(bean.getResult_type(), EcgConstant.RESULT_AUTO_INVALID)){
            llBg.setBackgroundResource(R.drawable.bg_ll_label_unknow);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_auto);
            tvIdentity.setText("自动");
            state.setText("无效");
        }else {
            llBg.setBackgroundResource(R.drawable.bg_ll_label_unknow);
            tvIdentity.setBackgroundResource(R.drawable.bg_tv_identity_auto);
            tvIdentity.setText("自动");
            state.setText("未知");
        }


        try{

            if(TextUtils.equals(bean.getRead_type(), EcgConstant.READ_UNREAD)){
                tvReadState.setText("未读");
                tvReadState.setTextColor(Color.parseColor("#666666"));
            }else {
                tvReadState.setText("读图中");
                tvReadState.setTextColor(Color.BLACK);
                tvReadState.setTextColor(Color.parseColor("#666666"));
            }
            switch (bean.getResult_type()) {
                case EcgConstant.RESULT_INVALID :
                case EcgConstant.RESULT_NORMAL :
                case EcgConstant.RESULT_ABNORMAL :
                case EcgConstant.RESULT_SERIOUS :
                    tvReadState.setText("已读");
                    tvReadState.setTextColor(Color.parseColor("#666666"));
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        bpm.setText(bean.getBpm()+"");
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
        ImageView ivThumb;
        @InjectView(R.id.ic_icon_ecg_report)
        ImageView mIcIconEcgReport;
        @InjectView(R.id.tv_item_ecghistory_read_state)
        TextView tvReadState;
        @InjectView(R.id.rl_ecg_item_container)
        RelativeLayout mRlEcgItemContainer;
        @InjectView(R.id.tv_item_identity)
        TextView tvIdentity;
        @InjectView(R.id.tv_item_ecghistory_state)
        TextView state;
        @InjectView(R.id.ll_lable_bg)
        LinearLayout llBg;
        @InjectView(R.id.tv_ecgitem_date)
        TextView mTvEcgitemDate;
        @InjectView(R.id.tv_item_ecghistory_bpm)
        TextView bpm;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
