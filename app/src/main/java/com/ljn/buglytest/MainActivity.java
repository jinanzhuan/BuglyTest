package com.ljn.buglytest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
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
    private int num = 0;

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

                switch (num) {
                    case 0 :
                        iv_icon.setImageResource(R.drawable.mv1);
                        num++;
                        break;
                    case 1 :
                        iv_icon.setImageResource(R.drawable.mv2);
                        num++;
                        break;
                    case 2 :
                        iv_icon.setImageResource(R.drawable.mv3);
                        num++;
                        break;
                    case 3 :
                        iv_icon.setImageResource(R.drawable.mv4);
                        num++;
                        break;
                    case 4 :
                        iv_icon.setImageResource(R.drawable.mv5);
                        num++;
                        break;
                    case 5 :
                        iv_icon.setImageResource(R.drawable.mv6);
                        num++;
                        break;
                    case 6 :
                        iv_icon.setImageResource(R.drawable.timg);
                        num=0;
                        break;
                }
                break;
            case R.id.btn_new :
                startActivity(new Intent(MainActivity.this, NewActivity.class));
                break;
            case R.id.btn_new_two :
                startActivity(new Intent(MainActivity.this, NewerActivity.class));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (keyCode == KeyEvent.KEYCODE_BACK) { //表示按返回键 时的操作
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
