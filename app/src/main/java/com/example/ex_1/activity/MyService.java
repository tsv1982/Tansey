package com.example.ex_1.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.ex_1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyService extends Service {
    public MyService() {
    }

    private NotificationCompat.Builder notBuilder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;
    private boolean aBoolean;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("messages");

    public void onCreate() {
        super.onCreate();
        this.notBuilder = new NotificationCompat.Builder(this);
        this.notBuilder.setAutoCancel(true);

        aBoolean = false;

        pauseMessage();
    }

    void pauseMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(10000);
                    aBoolean = true;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String textReplace = String.valueOf(dataSnapshot.getValue());
                if (textReplace.length() > 30) {
                    textReplace = textReplace.substring(0, 30) + " ..........";
                }

                if (aBoolean) {
                    sendNotif(textReplace);
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return super.onStartCommand(intent, flags, startId);
    }


    void sendNotif(String s) {
        this.notBuilder.setSmallIcon(R.drawable.logo);
        this.notBuilder.setTicker("TANSAY MESSAGE");
        this.notBuilder.setWhen(System.currentTimeMillis() + 10 * 1000);
        this.notBuilder.setContentTitle("TANSAY MESSAGE");
        this.notBuilder.setContentText(s);

        Intent intent = new Intent(this, MessageActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationService =
                (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        this.notBuilder.setContentIntent(pendingIntent);
        Notification notification = notBuilder.build();
        notificationService.notify(MY_NOTIFICATION_ID, notification);
        long mills = 1000L;
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator.hasVibrator()) {
            vibrator.vibrate(mills);
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onDestroy() {
        Toast.makeText(this, "destroy", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}