package com.walkud.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.walkud.mine.bean.MineInfo;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MineInfo mineInfo = new MineInfo();
    }
}
