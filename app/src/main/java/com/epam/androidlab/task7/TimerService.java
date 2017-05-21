package com.epam.androidlab.task7;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class TimerService extends Service {

    private static final int NOTIFY_ID = 1;
   // public Thread thread;

    @Override
    public void onCreate() {
        super.onCreate();
        /*Intent notificationIntent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        Notification notification1 = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.some_work))
                .setContentIntent(pendingIntent).build();

        startForeground(1337, notification1);*/
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int minutes = intent.getIntExtra(getString(R.string.minutes_extra),0);
        int seconds = intent.getIntExtra(getString(R.string.seconds_extra), 0);
        startTimer(minutes, seconds);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        //thread.interrupt();

        Log.e("StopTimer","STOP");
        //SendNotification();
        //stopForeground(false);
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        return null;
    }
    public void startTimer(int minutes, int seconds){
        Log.e("StartTimer","start");
        Log.e("minutes", String.valueOf(minutes));
        Log.e("seconds", String.valueOf(seconds));
        final long milliseconds = seconds * 1000 + minutes * 60000;

       /* thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("THREAD","start");
                    Thread.sleep(milliseconds);
                    Log.e("THREAD","stop");
                   // SendNotification();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    SendNotification();
                    //stopSelf();
                }
            }
        });
        thread.start();*/
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            SendNotification();
        }
    }

    private void SendNotification() {
        Log.e("NOTIFICATION","start");
        Notification.Builder builder = new Notification.Builder(this);

        Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_FROM_BACKGROUND);
        PendingIntent contentIntent = PendingIntent.getActivity(getApplicationContext(),
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_text));

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
        //notification.priority = Notification.PRIORITY_MAX;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFY_ID, notification);
        Log.e("NOTIFICATION","stop");
    }
}
