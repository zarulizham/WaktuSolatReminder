package com.mardyoe.waktusolatreminder.controller.ui.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.Calendar;

public class SetAlarmForAzan extends Service {

    private Context context;
    public SetAlarmForAzan() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        int hour = 12, minute = 9;
        String waktu = "Zohor";

        setAzan(hour, minute, waktu);
        setAzan(hour, minute+1, "Asar");
        setAzan(hour, minute+2, "Maghrib");

        Log.e("Start", "TES");

        stopSelf();

    }

    public void setAzan(int hour, int minute, String waktu) {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, hour); // For 1 PM or 2 PM
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Log.e("Calendar", calendar.getTime().toString());
        Intent intent = new Intent(this, PlaySoundService.class);
        intent.putExtra("waktu", waktu);


        PendingIntent pi = PendingIntent.getService(context, (int) calendar.getTimeInMillis(),
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (calendar.after(Calendar.getInstance())) {
            Log.e("Added", waktu);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        } else {
            Log.e("Added", "NO");
        }
        Log.e("Now", Calendar.getInstance().getTime().toString());
    }
}
