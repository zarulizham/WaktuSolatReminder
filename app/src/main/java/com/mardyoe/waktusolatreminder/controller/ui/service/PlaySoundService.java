package com.mardyoe.waktusolatreminder.controller.ui.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.mardyoe.waktusolatreminder.R;

import java.util.Iterator;
import java.util.Set;

public class PlaySoundService extends Service implements MediaPlayer.OnCompletionListener {

    MediaPlayer mPlayer;
    String waktu;
    public PlaySoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();



        showNotification();
        mPlayer = MediaPlayer.create(this, R.raw.azan);
        mPlayer.start();

        mPlayer.setOnCompletionListener(this);

    }

    @Override
    public void onDestroy() {

        mPlayer.stop();
        mPlayer = null;
        super.onDestroy();
    }

    @Override
    public int onStartCommand (Intent intent, int flags, int startId) {
        waktu = (String) intent.getExtras().get("waktu");
        //waktu = intent.getStringExtra("waktu");
        //Log.e("Intent", intent.getExtras().());
        dumpIntent(intent);
        Log.e("Flag", String.valueOf(flags));
        Log.e("SartId", String.valueOf(startId));
        return START_NOT_STICKY;
    }

    public void showNotification() {
        Intent intent = new Intent(this, StopSoundService.class);
        // use System.currentTimeMillis() to have a unique ID for the pending intent
        PendingIntent pIntent = PendingIntent.getService(this, (int) System.currentTimeMillis(), intent, 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Azan");
        mBuilder.setContentText("Telah masuk waktu solat "+waktu);
        mBuilder.addAction(R.drawable.ic_stop_black_24dp, "Stop", pIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        stopSelf();
    }

//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        return START_NOT_STICKY;
//    }

    public static void dumpIntent(Intent i){

        Log.e("A", i.getExtras().getString("waktu"));
        Log.e("B", i.getStringExtra("waktu"));
        Log.e("C", i.getExtras().get("waktu").toString());

        Bundle bundle = i.getExtras();
        if (bundle != null) {
            Set<String> keys = bundle.keySet();
            Iterator<String> it = keys.iterator();
            Log.e("ASD","Dumping Intent start");
            while (it.hasNext()) {
                String key = it.next();
                Log.e("ASD","[" + key + "=" + bundle.get(key)+"]");
            }
            Log.e("ASD","Dumping Intent end");
        }
    }
}
