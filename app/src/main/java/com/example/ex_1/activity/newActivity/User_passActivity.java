package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.example.ex_1.java.UtilZaprosov;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class User_passActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText editText;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ActionBar bar;
    private ArrayList<StudentСardEntity> studentСardEntityArrayList = new ArrayList<>();
    private ArrayList<TrenierEntity> trenierEntityArrayList = new ArrayList<>();
    private boolean isTrueEnter = true;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceStudent = firebaseDatabase.getReference("student");
    private DatabaseReference databaseReferenceTrener = firebaseDatabase.getReference("trener");
    private UtilZaprosov utilZaprosov;

    // TODO: 16.07.19 проверка подключения интернета

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass);

        // TODO: 23.08.19 проверка на админа при новой установке должно быть  user pass

        bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));    // задаем цвет бара
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        button = findViewById(R.id.buttonID);
        button.setOnClickListener(this);
        editText = findViewById(R.id.editText);
        editText.setOnClickListener(this);

        utilZaprosov = new UtilZaprosov();

        databaseReferenceStudent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                StudentСardEntity studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);
                studentСardEntityArrayList.add(studentСardEntity);

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

        databaseReferenceTrener.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                TrenierEntity trenierEntity = new Gson().fromJson(s1, TrenierEntity.class);
                trenierEntityArrayList.add(trenierEntity);
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

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("passSave", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonID: {

                if (utilZaprosov.hasConnection(this)) {



                for (int i = 0; i < trenierEntityArrayList.size(); i++) {
                    if (trenierEntityArrayList.get(i).getIdEnterUser().equals(String.valueOf(editText.getText()))) {

                        editor = sharedPreferences.edit();
                        editor.putBoolean("passSave", true);
                        editor.apply();

                        editor = sharedPreferences.edit();
                        editor.putString("passSaveIdEnterUser", trenierEntityArrayList.get(i).getIdEnterUser());

                        editor.apply();

                        editor = sharedPreferences.edit();
                        editor.putString("passSaveUserOrAdmin", trenierEntityArrayList.get(i).getAdminOrUser());
                        editor.apply();

                        isTrueEnter = false;


                        if (isMyServiceRunning(MyService.class)) {
                            startService(new Intent(getWindow().getContext(), MyService.class));
                        }

                        Intent intent = new Intent(this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    }
                }


                for (int i = 0; i < studentСardEntityArrayList.size(); i++) {
                    if (studentСardEntityArrayList.get(i).getIdEnterStudent().equals(String.valueOf(editText.getText()))) {

                        editor = sharedPreferences.edit();
                        editor.putBoolean("passSave", true);
                        editor.apply();

                        editor = sharedPreferences.edit();
                        editor.putString("passSaveIdEnterUser", studentСardEntityArrayList.get(i).getIdEnterStudent());
                        editor.apply();

                        editor = sharedPreferences.edit();
                        editor.putString("passSaveUserOrAdmin", studentСardEntityArrayList.get(i).getUserOrAdmin());
                        editor.apply();

                        isTrueEnter = false;

//                        startService(new Intent(this, MyService.class));
                       if (isMyServiceRunning(MyService.class)) {
                           startService(new Intent(getWindow().getContext(), MyService.class));
                       }
                        Intent intent = new Intent(this, HomeActivity.class);

                        startActivity(intent);
                        finish();
                        return;
                    }
                }

                if (isTrueEnter) {
                    Intent intent = new Intent(this, ErrorUserActivity.class);
                    startActivity(intent);
                }
                break;
            }
            }

            case R.id.editText: {
                editText.setText("");
                break;
            }
        }
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
