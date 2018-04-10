package com.ljn.buglysimple;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.ljn.buglysimple.xfei.RecognizerResultDialogListener;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/04/10
 *     desc   :
 *     modify :
 * </pre>
 */

public class VoiceBottomDialog extends Dialog {
    private Context mContext;
    private VoiceBottomDialog mDialog;
    private RelativeLayout rl_voice;
    private EditText tv_voice_content;
    private TextView tv_voice_cancel;
    private TextView tv_voice_finish;
    private TextView tv_hint;
    private RoundedImageView view_wave;
    private CompeletedView cv_progress;
    private SpeechRecognizer mSpeechRecognizer;//g
    private RecognizerResultDialogListener mDialogListener;//h
    private long startTime;
    private long endTime;
    private volatile int k;
    private String preContent = "";

    public VoiceBottomDialog(@NonNull Context context, InitListener initListener) {
        this(context, 0, initListener);
    }

    public VoiceBottomDialog(@NonNull Context context, @StyleRes int themeResId, InitListener initListener) {
        super(context, themeResId);
        mContext = context;
        mDialog = this;
        mSpeechRecognizer = SpeechRecognizer.createRecognizer(context.getApplicationContext(), initListener);
        init();
    }

    private void init() {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.voiceinput, null);
        tv_voice_content = (EditText) view.findViewById(R.id.tv_voice_content);
        rl_voice = (RelativeLayout) view.findViewById(R.id.rl_voice);
        tv_voice_cancel = (TextView) view.findViewById(R.id.tv_voice_cancel);
        tv_voice_finish = (TextView) view.findViewById(R.id.tv_voice_finish);
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        view_wave = (RoundedImageView) view.findViewById(R.id.view_wave);
        cv_progress = (CompeletedView) view.findViewById(R.id.cv_progress);
        cv_progress.setProgress(50);
        setMatchWidth(view);
        setListener();
    }

    private void setListener() {
        rl_voice.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN :
                        startTime = SystemClock.currentThreadTimeMillis();
                        mSpeechRecognizer.setParameter("msc.skin", "default");
                        int var3 = mSpeechRecognizer.startListening(recognizerListener);
                        if(var3 != 0) {
                            Toast.makeText(mContext, Html.fromHtml((new SpeechError(var3)).getHtmlDescription(true)), Toast.LENGTH_SHORT).show();
                        }else {
                            k = 1;
                        }
                        tv_voice_content.setVisibility(View.VISIBLE);
                        tv_hint.setVisibility(View.INVISIBLE);
                        view_wave.setVisibility(View.VISIBLE);
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        String result = tv_voice_content.getText().toString().trim();
                        if(TextUtils.isEmpty(result)) {
                            tv_voice_content.setVisibility(View.INVISIBLE);
                            tv_hint.setVisibility(View.VISIBLE);
                        }else {
                            tv_voice_cancel.setVisibility(View.VISIBLE);
                            tv_voice_finish.setVisibility(View.VISIBLE);
                        }
                        view_wave.setVisibility(View.INVISIBLE);
                        endTime = SystemClock.currentThreadTimeMillis();
                        if(endTime - startTime < 500 && tv_voice_content.getVisibility() != View.VISIBLE) {
                            Toast.makeText(mContext, "说话时间太短", Toast.LENGTH_SHORT).show();
                        }
                        mSpeechRecognizer.stopListening();
                        break;
                }
                return false;
            }
        });

        rl_voice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_voice_cancel.setVisibility(View.VISIBLE);
                tv_voice_finish.setVisibility(View.VISIBLE);
                tv_hint.setVisibility(View.INVISIBLE);
                return false;
            }
        });

        tv_voice_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_voice_content.setText("");
                tv_voice_content.setVisibility(View.INVISIBLE);
                tv_voice_cancel.setVisibility(View.INVISIBLE);
                tv_voice_finish.setVisibility(View.INVISIBLE);
                tv_hint.setVisibility(View.VISIBLE);
                preContent = "";
            }
        });

        tv_voice_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.dismiss();
            }
        });
    }

    private void setMatchWidth(View view) {
        Window window = mDialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);

        WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//宽度占满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
    }

    public void setResultListener(RecognizerResultDialogListener var1) {
        mDialogListener = var1;
    }

    public void setVoiceContent(String content, boolean isLast){
        if(!TextUtils.isEmpty(content)) {
            content = preContent + content;
            tv_voice_content.setText(content);
            if(tv_voice_content.getVisibility() != View.VISIBLE) {
                tv_voice_content.setVisibility(View.VISIBLE);
                tv_voice_cancel.setVisibility(View.VISIBLE);
                tv_voice_finish.setVisibility(View.VISIBLE);
                tv_hint.setVisibility(View.INVISIBLE);
            }
            if(isLast) {
                preContent = tv_voice_content.getText().toString().trim();
            }
        }
    }

    private RecognizerListener recognizerListener = new RecognizerListener() {
        public void onBeginOfSpeech() {
        }

        public void onVolumeChanged(int var1, byte[] var2) {
            if(k == 1) {
                var1 = (var1 + 2) / 5;
                setVolume(var1);
                //view_wave.invalidate();
            }

        }

        public void onEndOfSpeech() {
            if(null != mDialogListener) {
                mDialogListener.onEndOfSpeech();
            }
            //j();
            //监听说完话后的网络请求
            Log.e("VoiceBottomDialog", "说完了");
            Toast.makeText(mContext, "已经结束了", Toast.LENGTH_SHORT).show();
        }

        public void onResult(RecognizerResult var1, boolean var2) {

            if(null != mDialogListener) {
                mDialogListener.onResult(var1, var2);
            }

        }

        public void onError(SpeechError var1) {

            if(null != mDialogListener) {
                mDialogListener.onError(var1);
            }

        }

        public void onEvent(int var1, int var2, int var3, Bundle var4) {
        }
    };

    private void setVolume(int var1) {
        if(var1 > 5) {
            var1 = 5;
        }
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_wave.getLayoutParams();
        params.height = dip2px(getContext(), 70) + dip2px(getContext(), var1*2);
        params.width = dip2px(getContext(), 70) + dip2px(getContext(), var1*2);
        view_wave.setLayoutParams(params);
        view_wave.setCornerRadius(params.height/2);
    }

    private int dip2px(Context context,float dipValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dipValue*scale+0.5f);
    }
}
