package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.StudentGroupEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.googlecode.mp4parser.boxes.mp4.samplegrouping.GroupEntry;

import java.util.ArrayList;

public class StudentGroupAddActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("trener");
    private DatabaseReference databaseReferenceGroup = firebaseDatabase.getReference("group");

    private ArrayList<TrenierEntity> trenerEntityArrayList = new ArrayList();
    private ArrayList<String> arrayListNameTrener = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    private Button btnAddGroup;
    private EditText editTextGroupId;
    private EditText editTextGroupName;
    private Spinner spinner;

    private int positionSpiner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_group_add_activity);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        editTextGroupId = findViewById(R.id.ET_Group_Id);
        editTextGroupId.setVisibility(View.GONE);
        editTextGroupName = findViewById(R.id.ET_Group_Name);

        btnAddGroup = findViewById(R.id.btn_ADD_Group);
        btnAddGroup.setOnClickListener(this);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, arrayListNameTrener);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner = findViewById(R.id.SP_Group_Id_Trener_Enter);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                positionSpiner = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                TrenierEntity trenierEntity = new Gson().fromJson(s1, TrenierEntity.class);
                trenierEntity.setIdtrainers(dataSnapshot.getKey());                         // присваиваем id c базы
                trenerEntityArrayList.add(trenierEntity);
                arrayListNameTrener.add(trenierEntity.getNameTener());
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

    private void addJson(StudentGroupEntity studentGroupEntity) {   //  c EDIT TEXT парсим JSON
        String jsonAdd = new Gson().toJson(new StudentGroupEntity(
                studentGroupEntity.getIdGroupBD(),
                studentGroupEntity.getIdGroup(),
                studentGroupEntity.getIdTrenerEnter(),
                studentGroupEntity.getNameGroup(),
                studentGroupEntity.getNameTrener()));

        databaseReferenceGroup.push().setValue(jsonAdd);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_ADD_Group:{

                StudentGroupEntity studentGroupEntity = new StudentGroupEntity();
                studentGroupEntity.setIdGroupBD("255");
                studentGroupEntity.setIdGroup("000");
                studentGroupEntity.setNameGroup(String.valueOf(editTextGroupName.getText()));
                studentGroupEntity.setIdTrenerEnter(trenerEntityArrayList.get(positionSpiner).getIdEnterUser());
                studentGroupEntity.setNameTrener(trenerEntityArrayList.get(positionSpiner).getNameTener());
                addJson(studentGroupEntity);

                Toast.makeText(view.getContext(), "группа добавлена \n" + studentGroupEntity.getNameGroup(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, StudentGroupActivity.class);
                startActivity(intent);
                finish();
            }
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


