package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ex_1.Entity.SobutieEntity;
import com.example.ex_1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class KalendarSobSmsGetActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("sobutie");
    private SobutieEntity sobutieEntity;
    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private String idSmsSobutie;
    private String saveIdUser;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendar_sob_sms_get_activity);
        context = this;

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        idSmsSobutie = sharedPreferences.getString("saveSmsIdSobstie", "");
        saveIdUser = sharedPreferences.getString("SaveIdUser", "");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                   if (idSmsSobutie.equals(dataSnapshot.getKey())){
                       String s1 = String.valueOf(dataSnapshot.getValue());
                       sobutieEntity = new Gson().fromJson(s1, SobutieEntity.class);
                       sobutieEntity.setIdBDSobutie(dataSnapshot.getKey());
                       user(context);

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
    }


    private void addJsonYasOrNo(SobutieEntity sobutieEntity) {   //  c EDIT TEXT парсим JSON

        String s = null;

        if (sobutieEntity.getIdUserYas() == null) {
            s = saveIdUser;
        } else {
            String[] arr = sobutieEntity.getIdUserYas().split(",");
            for (String ss : arr) {
                if (ss.equals(saveIdUser)) {
                    s = sobutieEntity.getIdUserYas();
                } else {
                    s = sobutieEntity.getIdUserYas() + "," + saveIdUser;
                }

            }
        }

        String jsonAdd = new Gson().toJson(new SobutieEntity(
                sobutieEntity.getIdBDSobutie(),
                sobutieEntity.getTextSobutie(),
                sobutieEntity.getDataSobutie(),
                s));

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(sobutieEntity.getIdBDSobutie(), jsonAdd);
        databaseReference.updateChildren(childUpdates);
    }

    private void deleteJsonYasOrNo(SobutieEntity sobutieEntity) {   //  c EDIT TEXT парсим JSON

        String s = "";

        String[] arr = sobutieEntity.getIdUserYas().split(",");

        for (String ss : arr) {
            if (ss.equals(saveIdUser)) {

            } else {
                s = s + ss;
            }

        }

        String jsonAdd = new Gson().toJson(new SobutieEntity(
                sobutieEntity.getIdBDSobutie(),
                sobutieEntity.getTextSobutie(),
                sobutieEntity.getDataSobutie(),
                s));

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put(sobutieEntity.getIdBDSobutie(), jsonAdd);
        databaseReference.updateChildren(childUpdates);
    }

    void user(Context context1) {

        boolean isSob = false;
        if (sobutieEntity.getIdUserYas() == null) {
            isSob = false;
        } else {
            String[] arr = sobutieEntity.getIdUserYas().split(",");
            for (String ss : arr) {
                if (ss.equals(saveIdUser)) {
                    isSob = true;
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context1);
        builder
                .setTitle(Html.fromHtml("<font color='#91BFE9'>" + sobutieEntity.getDataSobutie() + "</font>"))
                .setMessage(Html.fromHtml("<font color='#D7E4E9'>" + sobutieEntity.getTextSobutie() + "</font>"))
                .setPositiveButton("выход", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intent = new Intent(context1, KalendarSobActivity.class);
                        finish();
                        startActivity(intent);   // запуск активити добавления тренера
                        dialog.cancel();
                    }
                })
                .setNegativeButton("участвовать", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addJsonYasOrNo(sobutieEntity);

                        Intent intent = new Intent(context1, KalendarSobActivity.class);
                        finish();
                        startActivity(intent);   // запуск активити добавления тренера
                        Toast.makeText(context1, "заявка принята", Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }
                })
                .setNeutralButton("отменить участие", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        deleteJsonYasOrNo(sobutieEntity);

                        Intent intent = new Intent(context1, KalendarSobActivity.class);
                        finish();
                        startActivity(intent);   // запуск активити добавления тренера
                        Toast.makeText(context1, "заявка отменена", Toast.LENGTH_LONG).show();
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
        alert.getWindow().setBackgroundDrawableResource(R.drawable.error_user_id);

        Button nbuttonYAS = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbuttonYAS.setTextColor(Color.BLUE);

        Button nbuttonEXIT = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        nbuttonEXIT.setTextColor(Color.RED);

        Button nbuttonOTMENA = alert.getButton(DialogInterface.BUTTON_NEUTRAL);
        nbuttonOTMENA.setTextColor(Color.MAGENTA);

        if (isSob) {
            nbuttonYAS.setVisibility(View.GONE);

        } else {
            nbuttonOTMENA.setVisibility(View.GONE);
        }

    }
}
