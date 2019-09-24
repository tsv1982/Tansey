package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class KalendarSobRefaktorActivity extends AppCompatActivity implements View.OnClickListener {

        private SobutieEntity sobutieEntity;

        private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        private DatabaseReference databaseReference = firebaseDatabase.getReference("sobutie");

        private EditText editTextTextSobutiy;
        private EditText editTextDataSobutiy;

        private Button btnRefactorSobutie;

        private String idSobutie;

    private SharedPreferences sharedPreferences;   // для сохранения позиции

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.kalendar_sob_refaktor_activity);

            sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            idSobutie = sharedPreferences.getString("saveIdRefactorSobutie", "");


            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

            editTextTextSobutiy = findViewById(R.id.ET_text_sobutie_refactor);
            editTextDataSobutiy = findViewById(R.id.ET_data_sobutie_refactor);

            btnRefactorSobutie = findViewById(R.id.btn_ADD_Sobutie_refactor);
            btnRefactorSobutie.setOnClickListener(this);

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                    if (idSobutie.equals(dataSnapshot.getKey())) {

                        String s1 = String.valueOf(dataSnapshot.getValue());
                        sobutieEntity = new Gson().fromJson(s1, SobutieEntity.class);
                        sobutieEntity.setIdBDSobutie(dataSnapshot.getKey());
                        editTextDataSobutiy.setText(sobutieEntity.getDataSobutie());
                        editTextTextSobutiy.setText(sobutieEntity.getTextSobutie());

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

        private void addJson(SobutieEntity sobutieEntity) {   //  c EDIT TEXT парсим JSON
            String jsonAdd = new Gson().toJson(new SobutieEntity(
                    sobutieEntity.getIdBDSobutie(),
                    sobutieEntity.getTextSobutie(),
                    sobutieEntity.getDataSobutie(),
                    sobutieEntity.getIdUserYas()));

            Map<String, Object> childUpdates = new HashMap<>();
            childUpdates.put(idSobutie, jsonAdd);
            databaseReference.updateChildren(childUpdates);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btn_ADD_Sobutie_refactor: {

                    sobutieEntity.setIdBDSobutie("255");
                    sobutieEntity.setTextSobutie(String.valueOf(editTextTextSobutiy.getText()));
                    sobutieEntity.setDataSobutie(String.valueOf(editTextDataSobutiy.getText()));

                    addJson(sobutieEntity);

                    Toast.makeText(view.getContext(), "событие созданно", Toast.LENGTH_LONG).show();
                    android.content.Intent intent = new Intent(this, KalendarSobActivity.class);
                    finish();
                    startActivity(intent);

                }
                break;
            }
        }

        @Override
        public boolean dispatchTouchEvent(MotionEvent ev) {   // для удаления фокуса при клике на пустое место
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