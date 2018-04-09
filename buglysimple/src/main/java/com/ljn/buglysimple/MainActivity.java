package com.ljn.buglysimple;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_icon;
    private TextView tv_text;
    private Button btn_button;
    private Button btn_button2;
    private RotateAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        iv_icon = (ImageView)findViewById(R.id.iv_icon);
        tv_text = (TextView)findViewById(R.id.tv_text);
        btn_button = (Button)findViewById(R.id.btn_button);
        btn_button2 = (Button)findViewById(R.id.btn_button2);
    }

    private void initListener() {
        btn_button.setOnClickListener(this);
        btn_button2.setOnClickListener(this);
    }

    private void initData() {
        ((MyApplication)getApplication()).setBugly("wo shoud do some that we want to do when we are young!");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_button :
//                String bug = null;
                Toast.makeText(MainActivity.this, ((MyApplication)getApplication()).getBugly(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_button2:
                showBottomDialog();
                break;
        }
    }

    private void showBottomDialog() {
        final Dialog dialog = new Dialog(this, R.style.MyBottomDialog);
        final View view = LayoutInflater.from(this).inflate(R.layout.voiceinput, null);
        final RelativeLayout rl_voice = (RelativeLayout) view.findViewById(R.id.rl_voice);
        final TextView tv_voice_cancel = (TextView) view.findViewById(R.id.tv_voice_cancel);
        final TextView tv_voice_finish = (TextView) view.findViewById(R.id.tv_voice_finish);
        final TextView tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.setContentView(view);

        WindowManager.LayoutParams lp = window.getAttributes(); // 获取对话框当前的参数值
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;//宽度占满屏幕
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);
        dialog.show();

        animation = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        animation.setRepeatCount(-1);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(700L);

        rl_voice.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_voice_cancel.setVisibility(View.VISIBLE);
                tv_voice_finish.setVisibility(View.VISIBLE);
                tv_hint.setVisibility(View.INVISIBLE);
                rl_voice.startAnimation(animation);
                return false;
            }
        });

        tv_voice_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tv_voice_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
