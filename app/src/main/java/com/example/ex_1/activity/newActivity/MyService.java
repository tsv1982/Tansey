package com.example.ex_1.activity.newActivity;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.IBinder;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.example.ex_1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MyService extends Service {
    Context context;

    public MyService() {
        context = this;
    }

    private NotificationCompat.Builder notBuilder;
    private static final int MY_NOTIFICATION_ID = 12345;
    private static final int MY_REQUEST_CODE = 100;
    private String adminOrUser;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("messages");

    private FirebaseDatabase firebaseDatabaseSms = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceSms = firebaseDatabaseSms.getReference("messagesSob");

    public void onCreate() {
        super.onCreate();
        this.notBuilder = new NotificationCompat.Builder(this);
        this.notBuilder.setAutoCancel(true);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");

//        Toast.makeText(context, "onCreate Servis", Toast.LENGTH_LONG).show();

//        aBoolean = false;

        pauseMessage();


    }

    void pauseMessage() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(context, "on Start Servis", Toast.LENGTH_LONG).show();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {


                String textReplace = String.valueOf(dataSnapshot.getValue());
                if (textReplace.length() > 30) {
                    textReplace = textReplace.substring(0, 30) + " ..........";
                }
                sendNotif(textReplace, dataSnapshot.getKey());
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

        if (adminOrUser.equals("admin")) {
        } else {
            databaseReferenceSms.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    String textReplace = String.valueOf(dataSnapshot.getValue());
                    sendNotifSobutie(textReplace , dataSnapshot.getKey());


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
        }
//        return super.onStartCommand(intent, START_REDELIVER_INTENT, startId);
        return Service.START_REDELIVER_INTENT;
    }


    void sendNotif(String s, String key) {

        String ss1 = sharedPreferences.getString("saveIdMesage", "");
        String[] arr = ss1.split(",");
        boolean bol = true;

        for (int i = 0; i < arr.length; i++) {
            if (key.equals(arr[i])) {
                bol = false;
            }
        }

        if (bol) {
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


    }

    void sendNotifSobutie(String s, String key) {

        String ss1 = sharedPreferences.getString("saveIdMesageSmsSob", "");
        String[] arr = ss1.split(",");
        boolean bol = true;

        for (int i = 0; i < arr.length; i++) {
            if (key.equals(arr[i])) {
                bol = false;
            }
        }

        if (bol) {

            this.notBuilder.setSmallIcon(R.drawable.logo);
            this.notBuilder.setTicker("TANSAY MESSAGE");
            this.notBuilder.setWhen(System.currentTimeMillis() + 10 * 1000);
            this.notBuilder.setContentTitle("TANSAY MESSAGE");
            this.notBuilder.setContentText("примите участие в опросе");

            Intent intent = new Intent(this, KalendarSobSmsGetActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, MY_REQUEST_CODE,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationManager notificationService =
                    (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
            this.notBuilder.setContentIntent(pendingIntent);
            Notification notification = notBuilder.build();
            notificationService.notify(MY_NOTIFICATION_ID, notification);

            sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("saveSmsIdSobstie", s);
            editor.apply();

            long mills = 1000L;
            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator.hasVibrator()) {
                vibrator.vibrate(mills);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
//        Toast.makeText(context, "onBind Servis", Toast.LENGTH_LONG).show();
        return null;
    }

    @Override
    public void onDestroy() {
//        Toast.makeText(context, "onDestroy Servis", Toast.LENGTH_LONG).show();
        super.onDestroy();
    }
}