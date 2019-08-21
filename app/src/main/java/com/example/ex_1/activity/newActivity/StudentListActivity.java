package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.StudentGroupEntity;
import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.StudentListAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class StudentListActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");

    private StorageReference mStorageRef;

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;
    private String saveIdStudent;

    private List<StudentСardEntity> studentСardArray = new ArrayList();
    private int position2;

    private ListView listViewStudent;
    private StudentListAdapter studentListAdapter;

    private TextView textViewCountStudent;
    private TextView textViewGetStudent;

    private Button btnAddStudent;
    private Button btnDeleteStudent;
    private Button btnRefactorStudent;
    private Button btnProsmotrStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_list_activity);

        textViewCountStudent = findViewById(R.id.TV_Count_Student_01);
        textViewGetStudent = findViewById(R.id.TV_Get_Student_01);

        btnAddStudent = findViewById(R.id.btn_ADD_Student_Add_01);
        btnAddStudent.setOnClickListener(this);
        btnDeleteStudent = findViewById(R.id.btn_Delete_Student01);
        btnDeleteStudent.setOnClickListener(this);
        btnRefactorStudent = findViewById(R.id.btn_Student_Refactor01);
        btnRefactorStudent.setOnClickListener(this);
        btnProsmotrStudent = findViewById(R.id.btn_ADD_Prosmotr_Student01);
        btnProsmotrStudent.setOnClickListener(this);

        listViewStudent = findViewById(R.id.LV_Student01);
        studentListAdapter = new StudentListAdapter(this, R.layout.student_list_student, studentСardArray);
        listViewStudent.setAdapter(studentListAdapter);   // сетаем адаптер в листвиев

        listViewStudent.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                position2 = position;
                textViewGetStudent.setText(studentСardArray.get(position).getNameStudent());
                saveIdStudent = studentСardArray.get(position).getIdstudent();
                textViewGetStudent.setTextColor(getResources().getColor(R.color.colorText2));
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                StudentСardEntity studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);
                studentСardEntity.setIdstudent(dataSnapshot.getKey());
                studentСardArray.add(studentСardEntity);

                textViewCountStudent.setText("количество студентов = " + studentСardArray.size());

                studentListAdapter.notifyDataSetChanged();

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
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btn_ADD_Student_Add_01: {
                Intent intent = new Intent(view.getContext(), StudentAddActivity.class);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.btn_Delete_Student01: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + studentСardArray.get(position2).getNameStudent() + "</font>"))
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
                                        databaseReference.child(studentСardArray.get(position2).getIdstudent()).removeValue();

                                        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(studentСardArray.get(position2).getUrlStudentFotoCard());
                                        mStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                // File deleted successfully
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception exception) {
                                                // Uh-oh, an error occurred!
                                            }
                                        });

                                        Toast.makeText(view.getContext(), "удаленный тренер \n" + studentСardArray.get(position2).getNameStudent(), Toast.LENGTH_LONG).show();
                                        studentListAdapter.remove(studentСardArray.get(position2));
                                        textViewCountStudent.setText("количество студентов = " + studentСardArray.size());
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
                break;
            }

            case R.id.btn_Student_Refactor01: {
                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("saveIdStudent", saveIdStudent);
                editor.apply();

                Intent intent = new Intent(this, StudentRefactorActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btn_ADD_Prosmotr_Student01: {
                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("saveIdStudent", saveIdStudent);
                editor.apply();

                Intent intent = new Intent(view.getContext(), StudentKartaPeopleActivity.class);
                startActivity(intent);
                break;
            }
        }
    }
}
