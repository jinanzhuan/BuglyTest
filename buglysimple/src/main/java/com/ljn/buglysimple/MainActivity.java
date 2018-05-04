package com.ljn.buglysimple;

import android.os.Bundle;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @InjectView(R.id.btn_viewpager)
    Button mBtnViewpager;
    private ImageView iv_icon;
    private TextView tv_text;
    private Button btn_button;
    private Button btn_button2;
    private Button btn_progress;
    private Button btn_canlendar;
    private ScaleAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        initView();
        initListener();
        initData();
    }

    private void initView() {
        iv_icon = (ImageView) findViewById(R.id.iv_icon);
        tv_text = (TextView) findViewById(R.id.tv_text);
        btn_button = (Button) findViewById(R.id.btn_button);
        btn_button2 = (Button) findViewById(R.id.btn_button2);
        btn_progress = (Button) findViewById(R.id.btn_progress);
        btn_canlendar = (Button) findViewById(R.id.btn_canlendar);
    }

    private void initListener() {
        btn_button.setOnClickListener(this);
        btn_button2.setOnClickListener(this);
        btn_progress.setOnClickListener(this);
        btn_canlendar.setOnClickListener(this);
        mBtnViewpager.setOnClickListener(this);
    }

    private void initData() {
        ((MyApplication) getApplication()).setBugly("wo shoud do some that we want to do when we are young!");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_button:
//                String bug = null;
                new com.ljn.buglysimple.xfei.XFSpeechManager(this, tv_text);
                break;
            case R.id.btn_button2:
                new XFSpeechManager(this, 100, tv_text);
                break;
            case R.id.btn_progress:
                ProgressActivity.actionStart(this);
                break;
            case R.id.btn_canlendar:
                CalendarActivity.actionStart(this);
                break;
            case R.id.btn_viewpager:
                ViewPagerActivity.actionStart(this);
                break;
        }
    }

    private void showBottomDialog() {
        VoiceBottomDialog dialog = new VoiceBottomDialog(this, R.style.MyBottomDialog, null);
        dialog.show();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Process.killProcess(Process.myPid());
    }
}
