package com.ljn.buglytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView iv_icon;
    private Button tv_toast;
    private Button btn_icon;
    private Button btn_new;
    private Button btn_new_two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_icon = (ImageView)findViewById(R.id.iv_icon);
        tv_toast = (Button)findViewById(R.id.tv_toast);
        btn_icon = (Button)findViewById(R.id.btn_icon);
        btn_new = (Button)findViewById(R.id.btn_new);
        btn_new_two = (Button)findViewById(R.id.btn_new_two);

        tv_toast.setOnClickListener(this);
        btn_icon.setOnClickListener(this);
        btn_new.setOnClickListener(this);
        btn_new_two.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_toast :
                Toast.makeText(MainActivity.this, "我是土司", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_icon :
                iv_icon.setVisibility(View.GONE);
                break;
            case R.id.btn_new :
                startActivity(new Intent(MainActivity.this, NewActivity.class));
                break;
            case R.id.btn_new_two :
                startActivity(new Intent(MainActivity.this, NewerActivity.class));
                break;
        }
    }
}
