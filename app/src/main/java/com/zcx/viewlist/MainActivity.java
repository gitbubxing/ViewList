package com.zcx.viewlist;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.zcx.viewlist.view.ChangeColorView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
     ChangeColorView view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         view  = findViewById(R.id.tv);

//        Timer timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                value+=0.1;
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        view.setPercent(value);
//                    }
//                });
//
//            }
//        },500,500);
        //属性动画
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                ObjectAnimator.ofFloat(view,"percent",0f,1f).setDuration(5000).start();
//            }
//        },2000);

    }
}
