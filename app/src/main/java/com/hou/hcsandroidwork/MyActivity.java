package com.hou.hcsandroidwork;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hou.hcsandroidwork.finderr.SearchActivity;

import java.util.LinkedList;
import java.util.List;

public class MyActivity extends AppCompatActivity {
    private TextView myInfor;
    private TextView myComm;
    private TextView changeUser;
    private Button exit;
    private String Exit="exit";
    private long lastClickTime;//用于记录上次的点击时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        myInfor=(TextView)findViewById(R.id.myInformation);
        changeUser=(TextView)findViewById(R.id.changeUser);
        exit=(Button) findViewById(R.id.exit);

        myInfor.setOnClickListener(onClickListener);
        changeUser.setOnClickListener(onClickListener);
        exit.setOnClickListener(onClickListener);
    }
    private View.OnClickListener onClickListener=new View.OnClickListener() {
        Intent intent=new Intent();
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.myInformation:
                    intent.setClass(MyActivity.this,AlterActivity.class);
                    startActivity(intent);
                    break;
                case R.id.changeUser:
                    finish();
                    intent.setClass(MyActivity.this,LoginActivity.class);
                    startActivity(intent);
                    break;
                case R.id.exit:
                    intent.setClass(MyActivity.this,LoginActivity.class);
                    intent.putExtra(Exit, true);
                    startActivity(intent);
                    //System.exit(0);
                    break;
            }
        }
    };
    public void click1(View view) {
        Intent intent=new Intent(MyActivity.this,ListView_Activity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    public void click2(View view) {
        Intent intent=new Intent(MyActivity.this,SearchActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
    }
    public void click3(View view) {

    }
}