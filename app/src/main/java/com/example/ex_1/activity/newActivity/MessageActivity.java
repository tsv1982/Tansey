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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ex_1.R;
import com.example.ex_1.adapter.MesengerAdapter;
import com.example.ex_1.Entity.Mesage;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// https://console.firebase.google.com/project/ex1firebaseproject-162ac/database/ex1firebaseproject-162ac/data
// tanseytansey19@gmail.com
// zxzxzx1231

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    SharedPreferences.Editor editor;
    private Button btnAddMessage;
    private EditText editTextMessage;
    private MesengerAdapter mesageAdapter;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("messages2");

    private List<Mesage> mesagesArray = new ArrayList();

    private ListView mesageList;

    private String saveIdMesage = "";

    String adminOrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_activity);


        if (isMyServiceRunning(MyService.class)){

        }else {
//            startService(new Intent(this, MyService.class));
            startService(new Intent(getWindow().getContext(), MyService.class));
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        btnAddMessage = findViewById(R.id.btnAddMesagge);
        btnAddMessage.setOnClickListener(this);

        editTextMessage = findViewById(R.id.editAddMesagge);
        editTextMessage.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");

        if (adminOrUser.equals("admin")) {
            btnAddMessage.setVisibility(View.VISIBLE);
            editTextMessage.setVisibility(View.VISIBLE);

        } else {
            btnAddMessage.setVisibility(View.GONE);
            editTextMessage.setVisibility(View.GONE);

        }

        mesageList = findViewById(R.id.countriesListMesage);

        mesageAdapter = new MesengerAdapter(this, R.layout.mesage_list, mesagesArray);

        mesageList.setAdapter(mesageAdapter);
        // слушатель выбора в списке
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                final Mesage selectedState = (Mesage) parent.getItemAtPosition(position);
                final int pos = position;

                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder
                        .setTitle(Html.fromHtml("<font color='#91BFE9'>" + "сообщение" + "</font>"))
                        .setMessage(Html.fromHtml("<font color='#D7E4E9'>" + selectedState.getText() + "</font>"))
                        .setIcon(R.drawable.logo)
                        .setCancelable(true)
                        .setPositiveButton("выйти", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
                                String sss = sharedPreferences.getString("saveMassageView", "");
                                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                                editor = sharedPreferences.edit();
                                editor.putString("saveMassageView", sss + selectedState.getName() + "\n");
                                editor.apply();
                                mesageAdapter.notifyDataSetChanged();
                                dialog.cancel();
                            }
                        })
                        .setNegativeButton("удалить",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                        databaseReference.child(selectedState.getName()).removeValue();
                                        System.out.println(selectedState.getName());
                                        mesagesArray.remove(pos);
                                        mesageAdapter.notifyDataSetChanged();
                                        Toast.makeText(getApplicationContext(), "Сообщение удаленно ", Toast.LENGTH_SHORT).show();

                                        dialog.cancel();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().setBackgroundDrawableResource(R.drawable.error_user_id);

                Button nbuttonN = alert.getButton(DialogInterface.BUTTON_NEGATIVE);

                nbuttonN.setTextColor(Color.RED);


                if (adminOrUser.equals("admin")) {          // проверяет админ или нет кнопки видны будут или нет
                    nbuttonN.setVisibility(View.VISIBLE);

                } else {
                    nbuttonN.setVisibility(View.GONE);
                }

                Button nbuttonP = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                nbuttonP.setTextColor(Color.BLUE);

                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
                String sss = sharedPreferences.getString("saveMassageView", "");
                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("saveMassageView", sss + selectedState.getName() + "\n");
                editor.apply();
                mesageAdapter.notifyDataSetChanged();

            }
        };

        mesageList.setOnItemClickListener(itemListener);


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                String s1 = String.valueOf(dataSnapshot.getValue());
                String[] arr = s1.split("\n");
                mesagesArray.add(new Mesage(dataSnapshot.getKey(), arr[1], arr[0]));
                mesageAdapter.notifyDataSetChanged();

                saveIdMesage = saveIdMesage + dataSnapshot.getKey() + ",";
                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("saveIdMesage", saveIdMesage );
                editor.apply();


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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddMesagge: {
                SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy  HH:mm");
                String currentDateandTime = sdf.format(new Date());

                if (String.valueOf(editTextMessage.getText()).equals("") || String.valueOf(editTextMessage.getText()).equals("текст смс")) {
                    Toast.makeText(getApplicationContext(), "пустое сообщение", Toast.LENGTH_SHORT).show();
                } else {
                    String strEdit = String.valueOf(editTextMessage.getText());
                    databaseReference.push().setValue(strEdit + "\n" + currentDateandTime);
                    Toast.makeText(getApplicationContext(), "смс отправленно ", Toast.LENGTH_SHORT).show();
                    editTextMessage.setText("текст смс");
                }
                break;
            }
            case R.id.editAddMesagge: {
                editTextMessage.setText("");
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (isMyServiceRunning(MyService.class)){

        }else {
            startService(new Intent(this, MyService.class));
//            startService(new Intent(getWindow().getContext(), MyService.class));
        }
        super.onDestroy();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                v.clearFocus();
                InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        }
        return super.dispatchTouchEvent(ev);
    }
}


