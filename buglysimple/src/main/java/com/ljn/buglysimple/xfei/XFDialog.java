package com.ljn.buglysimple.xfei;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2017/11/14
 *     desc   :
 *     modify :
 * </pre>
 */

public class XFDialog extends RecognizerDialog {

    public XFDialog(Context context){
        super(context, null);
    }

    public XFDialog(Context context, InitListener initListener) {
        super(context, initListener);
        this.a = new XFView(context, initListener);
    }

    @Override
    protected void onCreate(Bundle bundle) {
        this.requestWindowFeature(1);
        this.setContentView(this.a);
        this.getWindow().setBackgroundDrawable(new ColorDrawable(0));
    }

    @Override
    public void setListener(RecognizerDialogListener recognizerDialogListener) {
        ((XFView)this.a).setResultListener(recognizerDialogListener);
    }

}
