package com.example.wearosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Alarm extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //Run new activity on swipe event
        View clock = findViewById(R.id.AlarmLayout);
        clock.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                //Do stuff
                Log.d("DEBUG Alarm", "Swiped Left");

                Intent intent = new Intent(Alarm.this, MainActivity.class);
                startActivity(intent);
                finish();

            }

            @Override
            public void onSwipeRight(){
                //Do stuff
                Log.d("DEBUG Alarm", "Swiped Right");
            }
        });
    }
}