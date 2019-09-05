package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.SobutieEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.SobutieAdapterUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KalendarSobActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("sobutie");
    private FirebaseDatabase firebaseDatabaseSMS = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceSMS = firebaseDatabaseSMS.getReference("messagesSob");

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;

    private ListView listViewSobutie;  // View для листа тренеров
    private Button btnAddSobutiy;
    private Button btnDeleteSobutie;
    private Button btnRefactorSobutie;
    private Button btnTVGetSobutiy;
    private Button btnSendSms;
    private SobutieAdapterUser sobutieAdapterUser;
    private List<SobutieEntity> sobutieArray = new ArrayList();
    private TextView textViewGetSobutie;
    private int position1;
    private String saveIdSobutie;
    private String adminOrUser;
    private String saveIdUser;
    private String saveListIdUser;
    private int countSms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendar_sob_activity);

//        if (isMyServiceRunning(MyService.class)) {
//
//        } else {
//            startService(new Intent(this, MyService.class));
//            startService(new Intent(getWindow().getContext(), MyService.class));
//        }

        btnAddSobutiy = findViewById(R.id.btnAddSobutiy);
        btnAddSobutiy.setOnClickListener(this);
        btnDeleteSobutie = findViewById(R.id.btnDeleteSobutiy);
        btnDeleteSobutie.setOnClickListener(this);
        btnRefactorSobutie = findViewById(R.id.btnRefactorSobutiy);
        btnRefactorSobutie.setOnClickListener(this);
        btnTVGetSobutiy = findViewById(R.id.btnProsmotr_Sob);
        btnTVGetSobutiy.setOnClickListener(this);
        btnSendSms = findViewById(R.id.btnSend_sms_Sobutie);
        btnSendSms.setOnClickListener(this);

        textViewGetSobutie = findViewById(R.id.TV_Get_Sobutiy);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");
        saveIdUser = sharedPreferences.getString("SaveIdUser", "");

        if (adminOrUser.equals("admin")) {
            btnAddSobutiy.setVisibility(View.VISIBLE);
            btnDeleteSobutie.setVisibility(View.VISIBLE);
            btnRefactorSobutie.setVisibility(View.VISIBLE);
            textViewGetSobutie.setVisibility(View.VISIBLE);
            btnSendSms.setVisibility(View.VISIBLE);
            btnTVGetSobutiy.setVisibility(View.VISIBLE);


        } else {
            btnAddSobutiy.setVisibility(View.GONE);
            btnDeleteSobutie.setVisibility(View.GONE);
            btnRefactorSobutie.setVisibility(View.GONE);
            textViewGetSobutie.setVisibility(View.GONE);
            btnSendSms.setVisibility(View.GONE);
            btnTVGetSobutiy.setVisibility(View.GONE);
        }

        listViewSobutie = findViewById(R.id.LV_Sobutiy);
        sobutieAdapterUser = new SobutieAdapterUser(this, R.layout.kalendar_sob_list, sobutieArray);
        listViewSobutie.setAdapter(sobutieAdapterUser);   // сетаем адаптер в листвиев



        listViewSobutie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                position1 = position;

                if (adminOrUser.equals("admin")) {

                    String s = sobutieArray.get(position).getTextSobutie();
                    if (s.length() > 30) {
                        s = s.substring(0, 30) + " ..........";
                    }


                    textViewGetSobutie.setText(s);
                    saveIdSobutie = sobutieArray.get(position).getIdBDSobutie();
                    saveListIdUser = sobutieArray.get(position).getIdUserYas();
                    textViewGetSobutie.setTextColor(getResources().getColor(R.color.colorText2));



                } else {
                    user(v);
                }


            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());
                SobutieEntity sobutieEntity = new Gson().fromJson(s1, SobutieEntity.class);
                sobutieEntity.setIdBDSobutie(dataSnapshot.getKey());                         // присваиваем id c базы
                sobutieArray.add(sobutieEntity);

                 sobutieAdapterUser.notifyDataSetChanged();

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

        databaseReferenceSMS.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                countSms++;

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

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
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

    void user(View view) {

        boolean isSob = false;
        if (sobutieArray.get(position1).getIdUserYas() == null) {
            isSob = false;
        } else {
            String[] arr = sobutieArray.get(position1).getIdUserYas().split(",");
            for (String ss : arr) {
                if (ss.equals(saveIdUser)) {
                    isSob = true;
                }
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder
                .setTitle(Html.fromHtml("<font color='#91BFE9'>" + sobutieArray.get(position1).getDataSobutie() + "</font>"))
                .setMessage(Html.fromHtml("<font color='#D7E4E9'>" + sobutieArray.get(position1).getTextSobutie() + "</font>"))
                .setPositiveButton("выход", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("участвовать", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        addJsonYasOrNo(sobutieArray.get(position1));
                        sobutieAdapterUser.notifyDataSetChanged();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        Toast.makeText(view.getContext(), "заявка принята", Toast.LENGTH_LONG).show();
                        dialog.cancel();

                    }
                })
                .setNeutralButton("отменить участие", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        deleteJsonYasOrNo(sobutieArray.get(position1));
                        sobutieAdapterUser.notifyDataSetChanged();
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                        Toast.makeText(view.getContext(), "заявка отменена", Toast.LENGTH_LONG).show();
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
            nbuttonYAS.setVisibility(view.GONE);

        } else {
            nbuttonOTMENA.setVisibility(view.GONE);
        }

    }

    private boolean tt(View view) {
        if (textViewGetSobutie.getText().equals("")) {
            Toast.makeText(view.getContext(), "не выбранно событие", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(final View view) {

        switch (view.getId()) {
            case R.id.btnAddSobutiy: {
                Intent intent = new Intent(this, KalendarSobAddActivity.class);
                finish();
                startActivity(intent);   // запуск активити добавления тренера
                break;
            }

            case R.id.btnRefactorSobutiy: {
                if (tt(view)) {
                    sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString("saveIdRefactorSobutie", saveIdSobutie);
                    editor.apply();
                    Intent intent = new Intent(this, KalendarSobRefaktorActivity.class);
                    finish();
                    startActivity(intent);   // запуск активити добавления тренера
                }
                break;
            }

            case R.id.btnProsmotr_Sob: {
                if (tt(view)) {
                    editor = sharedPreferences.edit();
                    editor.putString("saveIdListUser", saveListIdUser);
                    editor.apply();
                    Intent intent = new Intent(this, KalendarSobYasActivity.class);
                    startActivity(intent);   // запуск активити добавления тренера
                }
                break;
            }

            case R.id.btnDeleteSobutiy: {
                if (tt(view)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + sobutieArray.get(position1).getTextSobutie() + "</font>"))
                            .setIcon(R.drawable.logo)
                            .setCancelable(true)
                            .setPositiveButton("отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("удалить",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            databaseReference.child(sobutieArray.get(position1).getIdBDSobutie()).removeValue();

                                            Toast.makeText(view.getContext(), "удаленно", Toast.LENGTH_LONG).show();
                                            sobutieAdapterUser.remove(sobutieArray.get(position1));
                                            sobutieAdapterUser.notifyDataSetChanged();
                                            textViewGetSobutie.setText("");
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getWindow().setBackgroundDrawableResource(R.drawable.error_user_id);

                    Button nbuttonN = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbuttonN.setTextColor(Color.RED);

                    Button nbuttonP = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    nbuttonP.setTextColor(Color.BLUE);
                }
                break;
            }

            case R.id.btnSend_sms_Sobutie: {
                if (tt(view)) {
                    if (countSms > 50){
                        databaseReferenceSMS.removeValue();
                        countSms = 0;
                    }
                    databaseReferenceSMS.push().setValue(saveIdSobutie);
                    Toast.makeText(view.getContext(), "запрос отравлен", Toast.LENGTH_LONG).show();
                    textViewGetSobutie.setText("");
                }
                break;
            }
        }
    }


}