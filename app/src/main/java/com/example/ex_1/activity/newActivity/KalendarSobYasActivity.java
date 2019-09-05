package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.SobutieYasStudentAdapter;
import com.example.ex_1.adapter.StudentListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class KalendarSobYasActivity extends AppCompatActivity   {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;

    private List<StudentСardEntity> studentСardArray = new ArrayList();

    private ListView listViewStudentSobutie;
    private SobutieYasStudentAdapter studentListAdapterStudent;
    private TextView textViewTVCountStudentSobutiy;

    private String listIdSobutieYasUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kalendar_sob_yas_activity);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        listIdSobutieYasUser = sharedPreferences.getString("saveIdListUser", "");

        textViewTVCountStudentSobutiy = findViewById(R.id.TV_Count_Student_Sobutiy);

        listViewStudentSobutie = findViewById(R.id.LV_Student_Sobutiy);
        studentListAdapterStudent = new SobutieYasStudentAdapter(this, R.layout.kalendar_sob_yas_list, studentСardArray);
        listViewStudentSobutie.setAdapter(studentListAdapterStudent);   // сетаем адаптер в листвиев

        String[] arr = listIdSobutieYasUser.split(",");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String ss1 = String.valueOf(dataSnapshot.getKey());

                for (String ss : arr) {
                    if (ss.equals(ss1)) {

                        String s1 = String.valueOf(dataSnapshot.getValue());

                        StudentСardEntity studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);
                        studentСardEntity.setIdstudent(dataSnapshot.getKey());
                        studentСardArray.add(studentСardEntity);

                        textViewTVCountStudentSobutiy.setText("количество учеников = " + studentСardArray.size());

                        studentListAdapterStudent.notifyDataSetChanged();
                    }
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


}
