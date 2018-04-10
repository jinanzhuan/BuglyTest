package com.ljn.buglysimple.xfei;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * <pre>
 *     author : created by ljn
 *     e-mail : liujinan@edreamtree.com
 *     time   : 2018/04/10
 *     desc   :
 *     modify :
 * </pre>
 */

public class VoiceBackgroundView extends View {
    private PaintFlagsDrawFilter d = new PaintFlagsDrawFilter(1, 2);
    private Path path;
    public VoiceBackgroundView(Context context) {
        this(context, null);
    }

    public VoiceBackgroundView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VoiceBackgroundView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        try {
            this.path = new Path();
            setVolume(0);
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(int volume) {
        this.path.reset();
        this.path.addCircle(0.0F, 0.0F, (float)(dip2px(getContext(), 60) * volume / 12), Path.Direction.CCW);
    }

    @Override
    public void onDraw(Canvas var1) {
        super.onDraw(var1);
        var1.save();
        var1.setDrawFilter(this.d);
        var1.translate((float)(this.getWidth() / 2), (float)(this.getHeight() / 2));
        var1.clipPath(this.path);
        var1.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int var3 = MeasureSpec.getSize(widthMeasureSpec);
        int var4 = MeasureSpec.getSize(heightMeasureSpec);
        Drawable var5 = this.getBackground();
        if(var5 != null) {
            var3 = var5.getMinimumWidth();
            var4 = var5.getMinimumHeight();
        }
        this.setMeasuredDimension(resolveSize(var3, widthMeasureSpec), resolveSize(var4, heightMeasureSpec));
    }

    public int dip2px(Context context,float dipValue){
        final float scale=context.getResources().getDisplayMetrics().density;
        return (int)(dipValue*scale+0.5f);
    }
}
