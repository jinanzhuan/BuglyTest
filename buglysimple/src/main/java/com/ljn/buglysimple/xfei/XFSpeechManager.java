package com.ljn.buglysimple.xfei;

import android.Manifest;
import android.app.Activity;
import android.os.Environment;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.ljn.buglysimple.PermissionUtils;
import com.ljn.buglysimple.SPDtadUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2017/11/14
 *     desc   : 讯飞语音输入管理类
 *     modify :
 * </pre>
 */

public class XFSpeechManager {
    private Activity mContext;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults;
    // 语音听写对象
    private SpeechRecognizer mIat;
    private TextView mResultText;
    // 语音听写UI
    private XFDialog mIatDialog;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    private String mPreContent;

    public XFSpeechManager(Activity context, TextView resultText) {
        mContext = context;
        mResultText = resultText;
        if(requirePermission(20)) {
            init();
        }
    }

    public XFSpeechManager(Activity context, int requestCode, EditText resultText) {
        mContext = context;
        mResultText = resultText;
        if(requirePermission(requestCode)) {
            init();
        }
    }

    private void init() {
        mPreContent = mResultText.getText().toString().trim();
        mResultText.requestFocus();
        mIatResults = new LinkedHashMap<String, String>();
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);

        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
        mIatDialog = new XFDialog(mContext, mInitListener);
        // 设置参数
        setParam();
        // 显示听写对话框
        mIatDialog.setListener(mRecognizerDialogListener);
        mIatDialog.show();
//        mContext.showToastMessage(mContext.getString(R.string.text_begin));
    }

    private void setParam() {
        if(mIat == null) {
            return;
        }
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = SPDtadUtils.getXFString(mContext, "iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
            mIat.setParameter(SpeechConstant.ACCENT, null);
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, SPDtadUtils.getXFString(mContext, "iat_vadbos_preference", "4000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, SPDtadUtils.getXFString(mContext, "iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, SPDtadUtils.getXFString(mContext, "iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/iat.wav");
    }

    /**
     * 听写UI监听器
     */
    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            //mContext.showToastMessage(error.getPlainDescription(true));
        }

    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        String content = mPreContent + resultBuffer.toString();

        mResultText.setText(content);
//        mResultText.setSelection(mResultText.length());
    }



    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            if (code != ErrorCode.SUCCESS) {
                Toast.makeText(mContext, "初始化失败，错误码：" + code, Toast.LENGTH_SHORT).show();
            }
        }
    };

    private boolean requirePermission(int requestCode){
        return PermissionUtils.hasPermission(mContext, requestCode, Manifest.permission.RECORD_AUDIO);
    }
}
