package com.example.wearosapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
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
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}