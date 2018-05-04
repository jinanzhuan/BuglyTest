package com.ljn.buglysimple.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by songyuqiang on 16/11/7.
 */
public class RealmPatientEcgObject implements Serializable{
    private OnUploadStatusChangeListener mStatusChangeListener;
    private String huid;
    /**
     * bpm : 138
     * note :
     * page_count : 1
     */
    private int id;
    private int bpm;
    private String note;
    private int page_count;
    private String ownHuid;//用来标识ecg是哪个人的？
    private String measure_time;
    private int duration;
    private String file_name;
    private String read_type;
    private String result_type;
    private String comment;
    private String update_time;
    private Date date;
    private long timestamp;
    private boolean isLocal;
    // just local ecg
    private String measure_name;
    private String fileLocalUrl;
    private boolean collect;//是否添加收藏
    private String collect_title;//收藏的标题
    private String create_time;//收藏的时间
    @SerializedName("favor_id")
    private int collect_id;//收藏的id
    private int upload;//用来标识上传的状态，只针对本地的ecg,0代表未上传，1代表上传中，2代表上传完成
    private String memo;//用户对心电图的备注，用于本地存储
    private int userbean_id;//用于维护user的主键
    private int ecgmembermodel_id;//用于维护member的主键

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOwnHuid() {
        return ownHuid;
    }

    public void setOwnHuid(String ownHuid) {
        this.ownHuid = ownHuid;
    }

    public String getHuid() {
        return huid;
    }

    public void setHuid(String huid) {
        this.huid = huid;
    }

    public String getMeasure_time() {
        return measure_time;
    }

    public void setMeasure_time(String measure_time) {
        this.measure_time = measure_time;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getRead_type() {
        return read_type;
    }

    public void setRead_type(String read_type) {
        this.read_type = read_type;
    }

    public String getResult_type() {
        return result_type;
    }

    public void setResult_type(String result_type) {
        this.result_type = result_type;
    }

    public Object getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public int getBpm() {
        return bpm;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getMeasure_name() {
        return measure_name;
    }

    public void setMeasure_name(String measure_name) {
        this.measure_name = measure_name;
    }

    public String getFileLocalUrl() {
        return fileLocalUrl;
    }

    public void setFileLocalUrl(String fileLocalUrl) {
        this.fileLocalUrl = fileLocalUrl;
    }

    public boolean isCollect() {
        return collect;
    }

    public void setCollect(boolean collect) {
        this.collect = collect;
        if(mStatusChangeListener != null) {
            mStatusChangeListener.onCollectionStatusChange(collect);
        }
    }

    public int getUpload() {
        return upload;
    }

    public void setUpload(int upload) {
        this.upload = upload;
        if(mStatusChangeListener != null) {
            mStatusChangeListener.onUploadStatusChange(upload);
        }
    }

    public String getCollect_title() {
        return collect_title;
    }

    public void setCollect_title(String collect_title) {
        this.collect_title = collect_title;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public int getCollect_id() {
        return collect_id;
    }

    public void setCollect_id(int collect_id) {
        this.collect_id = collect_id;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }


    public OnUploadStatusChangeListener getStatusChangeListener() {
        return mStatusChangeListener;
    }

    public void setStatusChangeListener(OnUploadStatusChangeListener statusChangeListener) {
        mStatusChangeListener = statusChangeListener;
    }

    public int getUserbean_id() {
        return userbean_id;
    }

    public void setUserbean_id(int userbean_id) {
        this.userbean_id = userbean_id;
    }

    public int getEcgmembermodel_id() {
        return ecgmembermodel_id;
    }

    public void setEcgmembermodel_id(int ecgmembermodel_id) {
        this.ecgmembermodel_id = ecgmembermodel_id;
    }


    /**
     * 设置更新状态的监听
     * @param listener
     */
    public void setOnUpdateStatusChangeListener(OnUploadStatusChangeListener listener){
        this.mStatusChangeListener = listener;
    }

    public interface OnUploadStatusChangeListener{
        /**
         * 上传状态变化
         * @param upload
         */
        void onUploadStatusChange(int upload);
        void onCollectionStatusChange(boolean isCollected);
    }

}
