package com.example.wearosapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;

public class Alarm extends Activity {

    AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    boolean alarmSet = false;
    int alarmHour = 0;
    int alarmMinute = 0;
    private static Alarm inst;

    public static Alarm instance() {
        return inst;
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        //Restore state
        if (savedInstanceState != null){
            Log.d("DEBUG Alarm", "Saved Instance State restored");
            alarmSet = savedInstanceState.getBoolean("AlarmSet", false);
            alarmHour = savedInstanceState.getInt("AlarmHour", 0);
            alarmMinute = savedInstanceState.getInt("AlarmMinute", 0);

            //Restore alarm time
            if (alarmSet){
                TimePicker alarmTimePicker = findViewById(R.id.alarmTimePicker);
                ToggleButton toggleButton = findViewById(R.id.alarmToggle);
                alarmTimePicker.setHour(alarmHour);
                alarmTimePicker.setMinute(alarmMinute);
                toggleButton.setChecked(alarmSet);
            }
        } else {
            Log.d("DEBUG Alarm", "Saved Instance State not restored");
        }

        //Run new activity on swipe event
        View clock = findViewById(R.id.AlarmLayout);
        clock.setOnTouchListener(new OnSwipeTouchListener(this) {
            @Override
            public void onSwipeLeft() {
                //Do stuff
                Log.d("DEBUG Alarm", "Swiped Left");

                Intent intent = new Intent(Alarm.this, MainActivity.class);
                startActivity(intent);
                //finish();

            }

            @Override
            public void onSwipeRight(){
                //Do stuff
                Log.d("DEBUG Alarm", "Swiped Right");
            }
        });

        //Handle button toggle
        ToggleButton toggleButton = findViewById(R.id.alarmToggle);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (toggleButton.isChecked()){
                    Log.d("DEBUG Alarm", "Alarm On");

                    TimePicker alarmTimePicker = findViewById(R.id.alarmTimePicker);

                    //Determine time to set off alarm
                    Calendar calendar = Calendar.getInstance();
                    alarmSet = true;
                    alarmHour = alarmTimePicker.getHour();
                    alarmMinute = alarmTimePicker.getMinute();
                    calendar.set(Calendar.HOUR_OF_DAY, alarmHour);
                    calendar.set(Calendar.MINUTE, alarmMinute);
                    Log.d("DEBUG Alarm", "Setting alarm for " + alarmTimePicker.getHour() + ":" + alarmTimePicker.getMinute() + " " + calendar.getTimeInMillis());
                    Log.d("DEBUG Alarm", "Current time is " + System.currentTimeMillis());

                    //Set alarm to go off at the set time
                    alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                    Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                    pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);
                    alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                } else {
                    Log.d("DEBUG Alarm", "Alarm Off");
                    if (alarmManager != null && pendingIntent != null){
                        alarmManager.cancel(pendingIntent);
                        alarmSet = false;
                    }

                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate if the process is
        // killed and restarted.
        savedInstanceState.putBoolean("AlarmSet", alarmSet);
        savedInstanceState.putInt("AlarmHour", alarmHour);
        savedInstanceState.putInt("AlarmMinute", alarmMinute);

        super.onSaveInstanceState(savedInstanceState);
    }

    public void toggleAlarmButton(boolean toggle){
        ToggleButton toggleButton = findViewById(R.id.alarmToggle);
        toggleButton.setChecked(toggle);
    }
}