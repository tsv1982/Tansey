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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ex_1.Entity.StudentGroupEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.StudentTrenierListAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("trener");
    private DatabaseReference databaseReferenceGroup = firebaseDatabase.getReference("group");

    private ArrayList<TrenierEntity> trenerEntityArrayList = new ArrayList();  // list Entity тренеров
    private ArrayList<StudentGroupEntity> studentGroupEntityArrayList = new ArrayList();
    private ArrayList<String> arrayListNameGroup;
    private ListView listViewStudentTrenier;  // View для листа тренеров
    private StudentTrenierListAdapter studentTrenierAdapter;

    private Button btnListGrooup;
    private Button btnListStudent;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_activity);

        context = this;

        btnListGrooup = findViewById(R.id.btnListGroup);
        btnListGrooup.setOnClickListener(this);
        btnListStudent = findViewById(R.id.btnListStudent_List);
        btnListStudent.setOnClickListener(this);

        listViewStudentTrenier = findViewById(R.id.LV_Student_Trener);  // передаем в адаптер лояут и лист объектов тренер
        studentTrenierAdapter = new StudentTrenierListAdapter(this, R.layout.student_list_trener, trenerEntityArrayList);
        listViewStudentTrenier.setAdapter(studentTrenierAdapter);   // сетаем адаптер в листвиев

        listViewStudentTrenier.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                arrayListNameGroup = new ArrayList<>();
                for (int i = 0; i <studentGroupEntityArrayList.size() ; i++) {
                    if (trenerEntityArrayList.get(position).getIdEnterUser()
                            .equals(studentGroupEntityArrayList.get(i).getIdTrenerEnter())){
                        arrayListNameGroup.add(studentGroupEntityArrayList.get(i).getNameGroup());
                    }
                }
                final String[] groupName = arrayListNameGroup.toArray(new String[0]);
                TextView textView = new TextView(v.getContext());
                textView.setText("Выбрать группу");
                textView.setPadding(20, 30, 20, 30);
                textView.setTextSize(20F);
                textView.setBackgroundColor(getResources().getColor(R.color.colorTrenerName));
//                textView.setTextColor(Color.WHITE);
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setCustomTitle(textView)
                .setPositiveButton("отмена", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
                    }
                });

                builder.setItems(groupName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        // TODO Auto-generated method stub
                        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("saveIdGroup", groupName[item]);
                        editor.apply();

                        Intent intent = new Intent(context, StudentChekActivity.class);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(true);
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().setBackgroundDrawableResource(R.color.colorTrenerName);
                Button nbuttonN = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbuttonN.setTextColor(Color.RED);
                Button nbuttonP = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                nbuttonP.setTextColor(Color.BLUE);
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                TrenierEntity trenierEntity = new Gson().fromJson(s1, TrenierEntity.class);
                trenierEntity.setIdtrainers(dataSnapshot.getKey());                         // присваиваем id c базы
                trenerEntityArrayList.add(trenierEntity);
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

        databaseReferenceGroup.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                StudentGroupEntity studentGroupEntity = new Gson().fromJson(s1, StudentGroupEntity.class);
                studentGroupEntity.setIdGroupBD(dataSnapshot.getKey());
                studentGroupEntityArrayList.add(studentGroupEntity);

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
           case R.id.btnListGroup:{
               Intent intent = new Intent(this, StudentGroupActivity.class);
               startActivity(intent);
               finish();
               break;
           }
           case R.id.btnListStudent_List:{
               Intent intent = new Intent(this, StudentListActivity.class);
               startActivity(intent);
               finish();
               break;
           }
       }
    }
}
