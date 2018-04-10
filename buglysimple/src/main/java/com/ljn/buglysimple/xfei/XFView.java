package com.ljn.buglysimple.xfei;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.resource.Resource;
import com.iflytek.cloud.thirdparty.cv;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.cloud.thirdparty.bw;
import com.iflytek.cloud.thirdparty.cb;
import com.iflytek.cloud.thirdparty.cs;
import com.iflytek.cloud.thirdparty.cw;
/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2017/11/14
 *     desc   :
 *     modify :
 * </pre>
 */

public class XFView extends cv implements View.OnClickListener {
    private LinearLayout d;
    private cw e = null;
    private RotateAnimation f = null;
    private SpeechRecognizer g;
    private RecognizerDialogListener h;
    private long i = 0L;
    public static int a = 9;
    private volatile int k;

    public XFView(Context context, InitListener var2) {
        super(context.getApplicationContext());
        this.g = SpeechRecognizer.createRecognizer(context.getApplicationContext(), var2);
        this.a();
    }

    public void a() {
        try {
            final Context var1 = this.getContext().getApplicationContext();
            View var2 = cs.a(var1, "recognize", this);
            var2.setBackgroundDrawable(cs.a(var1.getApplicationContext(), "voice_bg.9"));
//            TextView var3 = (TextView)var2.findViewWithTag("textlink");
//            var3.getPaint().setFlags(8);
//            var3.setText("语音识别能力由讯飞输入法提供");
//            var3.setLinksClickable(true);
//            var3.setOnClickListener(new OnClickListener() {
//                public void onClick(View var1x) {
//                    try {
//                        String var2 = bu.b(cg.a());
//                        Uri var3 = Uri.parse("http://www.xunfei.cn/?appid=" + var2);
//                        cb.a(var3.toString());
//                        Intent var4 = new Intent("android.intent.action.VIEW", var3);
//                        var4.addFlags(268435456);
//                        k = 1;
//                        k();
//                        g.stopListening();
//                        i();
//                        var1.getApplicationContext().startActivity(var4);
//                    } catch (Exception var5) {
//                        cb.e("failed");
//                    }
//
//                }
//            });
            this.d = (LinearLayout)var2.findViewWithTag("container");
            bw.a(this);
            this.e = new cw(var1.getApplicationContext());
            LinearLayout.LayoutParams var4 = new LinearLayout.LayoutParams(-1, 0, 1.0F);
            var4.bottomMargin = 20;
            this.d.addView(this.e, 1, var4);
            FrameLayout var5 = (FrameLayout)this.d.findViewWithTag("waiting");
            View var6 = var5.findViewWithTag("control");
            var6.setBackgroundDrawable(cs.a(this.getContext(), "waiting"));
            this.f = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
            this.f.setRepeatCount(-1);
            this.f.setInterpolator(new LinearInterpolator());
            this.f.setDuration(700L);
        } catch (Exception var7) {
            cb.a(var7);
        }

    }

    public void setResultListener(RecognizerDialogListener var1) {
        this.h = var1;
        this.setOnClickListener(this);
    }

    public void a(String var1, String var2) {
        this.g.setParameter(var1, var2);
    }

    private RecognizerListener j = new RecognizerListener() {
        public void onBeginOfSpeech() {
        }

        public void onVolumeChanged(int var1, byte[] var2) {
            if(k == 1 && null != e) {
                var1 = (var1 + 2) / 5;
                e.setVolume(var1);
                e.invalidate();
            }

        }

        public void onEndOfSpeech() {
            j();
        }

        public void onResult(RecognizerResult var1, boolean var2) {
            if(var2) {
                f();
            }

            if(null != h) {
                h.onResult(var1, var2);
            }

        }

        public void onError(SpeechError var1) {
            if(var1 != null && b) {
                a(var1);
            } else {
                f();
            }

            if(null != h) {
                h.onError(var1);
            }

        }

        public void onEvent(int var1, int var2, int var3, Bundle var4) {
        }
    };

    private void a(SpeechError var1) {
        try {
            LinearLayout var2 = (LinearLayout)this.d.findViewWithTag("error");
            TextView var3 = (TextView)var2.findViewWithTag("errtxt");
            this.a(var3, var1);
            View var4 = var2.findViewWithTag("errview");
            var4.setBackgroundDrawable(cs.a(this.getContext(), "warning"));
            this.setTag(var1);
            this.k = 3;
            this.k();
        } catch (Exception var5) {
            cb.a(var5);
        }

    }

    public void a(TextView var1, SpeechError var2) {
        boolean var4 = false;
        String var5 = this.g.getParameter("view_tips_plain");
        if(null != var5 && (var5.equalsIgnoreCase("true") || var5.equalsIgnoreCase("1"))) {
            var4 = true;
        }

        var1.setText(Html.fromHtml(var2.getHtmlDescription(!var4)));
        var1.setMovementMethod(LinkMovementMethod.getInstance());
        var1.bringToFront();
        CharSequence var6 = var1.getText();
        if(var6 instanceof Spannable) {
            int var7 = var6.length();
            Spannable var8 = (Spannable)var1.getText();
            URLSpan[] var9 = (URLSpan[])var8.getSpans(0, var7, URLSpan.class);
            SpannableStringBuilder var10 = new SpannableStringBuilder(var6);
            var10.clearSpans();
            URLSpan[] var11 = var9;
            int var12 = var9.length;

            for(int var13 = 0; var13 < var12; ++var13) {
                URLSpan var14 = var11[var13];
                a var15 = new a(var14.getURL());
                var10.setSpan(var15, var8.getSpanStart(var14), var8.getSpanEnd(var14), 34);
            }

            int var16 = var2.getHtmlDescription(false).length();
            var12 = var2.getHtmlDescription(true).length() - "<br>".length();
            var10.setSpan(new ForegroundColorSpan(cs.a()[0]), 0, var16, 34);
            var10.setSpan(new AbsoluteSizeSpan(cs.b()[0], true), 0, var16, 33);
            if(!var4) {
                var10.setSpan(new ForegroundColorSpan(cs.a()[1]), var16 + 1, var12 + 1, 34);
                var10.setSpan(new AbsoluteSizeSpan(cs.b()[1], true), var16 + 1, var7, 34);
            }

            String errorMsg = var10.toString().trim();
            if(!TextUtils.isEmpty(errorMsg)) {
                String[] split = errorMsg.split("\n");
                var1.setText(split[0]);
            }else {
                var1.setText(var10);
            }
        }

    }

    private void j() {
        try {
            FrameLayout var1 = (FrameLayout)this.d.findViewWithTag("waiting");
            View var2 = var1.findViewWithTag("control");
            var2.startAnimation(this.f);
            this.k = 2;
            this.k();
        } catch (Exception var3) {
            cb.a(var3);
        }

    }

    private void k() {
        FrameLayout var1 = (FrameLayout)this.d.findViewWithTag("waiting");
        TextView var2 = (TextView)this.d.findViewWithTag("title");
        LinearLayout var3 = (LinearLayout)this.d.findViewWithTag("error");
        TextView var4 = (TextView)var1.findViewWithTag("tips");
        if(this.k == 1) {
            var3.setVisibility(8);
            var2.setVisibility(0);
            var1.setVisibility(8);
            var2.setText(Resource.getTitle(2));
            this.e.setVolume(0);
            this.e.invalidate();
            this.e.setVisibility(0);
        } else if(this.k == 2) {
            var2.setVisibility(8);
            this.e.setVisibility(8);
            var1.setVisibility(0);
            var4.setVisibility(0);
            var4.setText(Resource.getTitle(3));
        } else if(this.k == 3) {
            var2.setVisibility(8);
            this.e.setVisibility(8);
            var1.setVisibility(8);
            var3.setVisibility(0);
        }

    }

    @Override
    public void onClick(View var1) {
        switch(this.k) {
            case 1:
                this.g.stopListening();
                this.j();
                break;
            case 3:
                if(var1.getTag() != null && ((SpeechError)var1.getTag()).getErrorCode() == 20001) {
                    this.e();
                } else {
                    this.g();
                }
        }
    }

    private void g() {
        cb.a("startRecognizing");
        long var1 = this.i;
        this.i = SystemClock.elapsedRealtime();
        if(this.i - var1 >= 300L) {
            this.g.setParameter("msc.skin", "default");
            int var3 = this.g.startListening(this.j);
            if(var3 != 0) {
                this.a(new SpeechError(var3));
            } else {
                this.i();
            }

        }
    }

    private void h() {
        if(this.d != null) {
            this.d.destroyDrawingCache();
            this.d = null;
        }

        this.e = null;
        System.gc();
    }

    public void setTitle(CharSequence var1) {
        TextView var2 = (TextView)this.d.findViewWithTag("title");
        var2.setText(var1);
    }

    private void i() {
        if(this.e == null) {
            this.e = new cw(this.getContext().getApplicationContext());
        }

        this.k = 1;
        this.k();
    }

    public void b() {
        super.b();
        this.g();
    }

    public void c() {
        if(this.g.isListening()) {
            this.g.cancel();
        }

        super.c();
    }

    protected boolean d() {
        if(super.d()) {
            this.h();
            return this.g.destroy();
        } else {
            return false;
        }
    }

    public class a extends ClickableSpan {
        private String b;

        public a(String var2) {
            this.b = var2;
        }

        public void onClick(View var1) {
            try {
                try {
                    Context var2 = var1.getContext();
                    Uri var3 = Uri.parse(this.b);
                    Intent var4 = new Intent("android.intent.action.VIEW", var3);
                    var4.addFlags(268435456);
                    var4.putExtra("com.android.browser.application_id", var2.getPackageName());
                    var2.startActivity(var4);
                } catch (Exception var8) {
                    cb.a(var8);
                }

            } finally {
                ;
            }
        }

        public void updateDrawState(TextPaint var1) {
            super.updateDrawState(var1);
        }
    }

}
