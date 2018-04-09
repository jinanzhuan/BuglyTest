package com.ljn.buglysimple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_icon;
    private TextView tv_text;
    private Button btn_button;
    private Button btn_button2;

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
                Toast.makeText(MainActivity.this, MyApplication.NAME, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
