package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.StudentGroupEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.StudentGroupAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.gson.Gson;

import java.util.ArrayList;

public class StudentGroupActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceGroup = firebaseDatabase.getReference("group");

    private ArrayList<StudentGroupEntity> studentGroupEntityArrayList = new ArrayList();
    private StudentGroupAdapter adapter;

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;

    private Button btnAddGroup;
    private Button btnRefactorGroup;
    private Button btnDeleteGroup;
    private ListView listViewGroup;
    private TextView textViewGetGroup;

    private int position2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_group_activity);

        btnAddGroup = findViewById(R.id.btnAddGroup);
        btnAddGroup.setOnClickListener(this);
        btnRefactorGroup = findViewById(R.id.btnRefactorGroup);
        btnRefactorGroup.setOnClickListener(this);
        btnDeleteGroup = findViewById(R.id.btnDeleteGroup);
        btnDeleteGroup.setOnClickListener(this);

        textViewGetGroup = findViewById(R.id.TV_Get_Group);

        listViewGroup = findViewById(R.id.LV_Group);

        adapter = new StudentGroupAdapter(this, R.layout.student_group_list, studentGroupEntityArrayList);
        listViewGroup.setAdapter(adapter);

        listViewGroup.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                position2 = position;
                textViewGetGroup.setText(studentGroupEntityArrayList.get(position).getNameGroup());
                textViewGetGroup.setTextColor(getResources().getColor(R.color.colorText2));
            }
        });

        databaseReferenceGroup.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                StudentGroupEntity studentGroupEntity = new Gson().fromJson(s1, StudentGroupEntity.class);
                studentGroupEntity.setIdGroupBD(dataSnapshot.getKey());
                studentGroupEntityArrayList.add(studentGroupEntity);
                adapter.notifyDataSetChanged();
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
            case R.id.btnAddGroup: {
                Intent intent = new Intent(this, StudentGroupAddActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.btnRefactorGroup: {

                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("saveIdGroupp", studentGroupEntityArrayList.get(position2).getIdGroupBD());
                editor.apply();

                Intent intent = new Intent(this, StudentGroupRefactorActivity.class);
                startActivity(intent);
                finish();
                break;
            }
            case R.id.btnDeleteGroup: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + studentGroupEntityArrayList.get(position2).getNameGroup() + "</font>"))
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
                                        databaseReferenceGroup.child(studentGroupEntityArrayList.get(position2).getIdGroupBD()).removeValue();

                                        textViewGetGroup.setText("");

                                        Toast.makeText(view.getContext(), "группа удалена \n" + studentGroupEntityArrayList.get(position2).getNameGroup(), Toast.LENGTH_LONG).show();
                                        adapter.remove(studentGroupEntityArrayList.get(position2));
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
        }

    }
}
