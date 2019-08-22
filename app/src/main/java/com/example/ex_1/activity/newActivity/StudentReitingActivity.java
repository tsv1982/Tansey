package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.StudentReitingAdapter;
import com.example.ex_1.adapter.StudentTrenierListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentReitingActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");

    private ArrayList<StudentСardEntity> studentEntityArrayList = new ArrayList();  // list Entity тренеров
    private ListView listViewReitingStudent;  // View для листа тренеров
    private StudentReitingAdapter studentTrenierAdapter;

    private Button btnRegactor;

    String adminOrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_reiting_activity);

                btnRegactor = findViewById(R.id.btn_Reiting_Student_Refactor);
                btnRegactor.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");

        if (adminOrUser.equals("admin")){
            btnRegactor.setVisibility(View.VISIBLE);
        }else {
            btnRegactor.setVisibility(View.GONE);
        }



                listViewReitingStudent = findViewById(R.id.LV_Student_Reiting);  // передаем в адаптер лояут и лист объектов тренер
                studentTrenierAdapter = new StudentReitingAdapter(this, R.layout.student_reiting_list, studentEntityArrayList);
                listViewReitingStudent.setAdapter(studentTrenierAdapter);   // сетаем адаптер в листвиев

                listViewReitingStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
//                position2 = position;
//                Toast.makeText(v.getContext(), "группа добавлена \n" + trenerEntityArrayList.get(position).getNameTener(), Toast.LENGTH_LONG).show();


                    }
                });

                databaseReference.addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                        String s1 = String.valueOf(dataSnapshot.getValue());

                        StudentСardEntity studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);
//                        studentСardEntity.setIdtrainers(dataSnapshot.getKey());  // присваиваем id c базы
                        studentEntityArrayList.add(studentСardEntity);
                        studentTrenierAdapter.notifyDataSetChanged();

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




            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.btn_Reiting_Student_Refactor:{


                        break;
                    }
                                   }
            }
        }