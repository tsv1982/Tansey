package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.StudentBoxAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StudentChekActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");
    private SharedPreferences sharedPreferences;   // для сохранения позиции

    private List<StudentСardEntity> studentСardArray = new ArrayList();
    private StudentBoxAdapter boxAdapter;

    private Button btnChekAddDate;
    private ListView listViewStudentChek;
    private String nameGroupSave;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_chek_activity);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);  // извлекаем перемунную позицию
        nameGroupSave = sharedPreferences.getString("saveIdGroup", "");

        boxAdapter = new StudentBoxAdapter(this, (ArrayList<StudentСardEntity>) studentСardArray);

        btnChekAddDate = findViewById(R.id.bntChekDateAdd);
        btnChekAddDate.setOnClickListener(this);

        // настраиваем список
        listViewStudentChek = findViewById(R.id.lvMain222);
        listViewStudentChek.setAdapter(boxAdapter);

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                StudentСardEntity studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);
                studentСardEntity.setIdstudent(dataSnapshot.getKey());

                if (nameGroupSave.equals(studentСardEntity.getGroupName())){
                    studentСardArray.add(studentСardEntity);
                }
                boxAdapter.notifyDataSetChanged();
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
           case R.id.bntChekDateAdd:{
              ArrayList<StudentСardEntity> array = boxAdapter.getReternStudentArray();
               for (int i = 0; i <array.size() ; i++) {
                   System.out.println("@@@@@@@@@@@@  " + array.get(i).getDate());
                   System.out.println("@@@@@@@@@@@@  " + array.get(i).getNameStudent());
                   System.out.println("@@@@@@@@@@@@  " + array.get(i).getIdstudent())
                   ;
               }

               break;
           }
       }
    }


    // выводим информацию о корзине
//    public void showResult(View v) {
//        String result = "Товары в корзине:";
//        for (Product p : boxAdapter.getReternStudentArray()) {
//            if (p.box)
//                result += "\n" + p.name;
//        }
//        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
//    }
}


