package com.example.wearosapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class Timer extends Activity {
    Button btnStart;
    Button btnReset;
    TextView timer;

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        btnStart = findViewById(R.id.startBtn);
        btnReset = findViewById(R.id.resetBtn);
        timer = findViewById(R.id.timerText);
        runTimer();

        //Run new activity on swipe event
        View clock = findViewById(R.id.TimerLayout);
        clock.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                //Do stuff
                Log.d("DEBUG Timer", "Swiped Left");

                Intent intent = new Intent(Timer.this, Alarm.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                finish();

            }

            @Override
            public void onSwipeRight(){
                //Do stuff
                Log.d("DEBUG Timer", "Swiped Right");
            }
        });
    }



    public void onClickStart(View view)
    {
        if (btnStart.getText().equals("Start"))
        {
            running = true;
            btnStart.setText("Stop");
        }
        else{
            running = false;
            btnStart.setText("Start");
        }

    }

    public void onClickReset(View view)
    {
        running = false;
        seconds = 0;
    }

    private void runTimer()
    {
        final int[] interval = {0};

        // Get the text view.
        final TextView timeText = (TextView)findViewById(R.id.timerText);

        // Creates a new Handler
        final Handler handler  = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run()
            {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes, secs);
                // Set the text view text.
                timeText.setText(time);
                // If running is true, increment the
                // seconds variable.
                if (running) {
                    if (interval[0] % 10 == 0){
                        seconds++;
                    }
                    interval[0]++;

                }
                handler.postDelayed(this, 100);
            }
        });
    }
}