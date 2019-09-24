package com.example.ex_1.activity.newActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

public class KalendarSobAddActivity extends AppCompatActivity implements View.OnClickListener {

    private SobutieEntity sobutieEntity;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("sobutie");

    private EditText editTextTextSobutiy;
    private EditText editTextDataSobutiy;

    private Button btnAddSobutie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendar_sob_add_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        editTextTextSobutiy = findViewById(R.id.ET_text_sobutie);
        editTextDataSobutiy = findViewById(R.id.ET_data_sobutie);

        btnAddSobutie = findViewById(R.id.btn_ADD_Sobutie);
        btnAddSobutie.setOnClickListener(this);

    }

    private void addJson(SobutieEntity sobutieEntity) {   //  c EDIT TEXT парсим JSON
        String jsonAdd = new Gson().toJson(new SobutieEntity(
                sobutieEntity.getIdBDSobutie(),
                sobutieEntity.getTextSobutie(),
                sobutieEntity.getDataSobutie()));
        databaseReference.push().setValue(jsonAdd);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ADD_Sobutie: {

                sobutieEntity = new SobutieEntity();
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
