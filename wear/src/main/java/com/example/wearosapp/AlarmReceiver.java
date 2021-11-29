package com.example.wearosapp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;



public class AlarmReceiver extends BroadcastReceiver {

    /** If the alarm is older than STALE_WINDOW seconds, ignore.  It
     is probably the result of a time or timezone change */
    private final static int STALE_WINDOW = 60 * 30;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("DEBUG AlarmReceiver", "Alarm went off");

        Alarm inst = Alarm.instance();
        inst.toggleAlarmButton(false);


        //this will sound the alarm tone
        //this will sound the alarm once, if you wish to
        //raise alarm in loop continuously then use MediaPlayer and setLooping(true)
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null) {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        Ringtone ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();

        //Stop ringstone after a few sec
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
                ringtone.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();

        Log.d("DEBUG AlarmReceiver", "Alarm turned off");


    }
}
