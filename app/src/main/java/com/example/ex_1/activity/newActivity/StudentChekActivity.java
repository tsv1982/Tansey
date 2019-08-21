package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.ex_1.Entity.DateEntity;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

public class StudentChekActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");
    private SharedPreferences sharedPreferences;   // для сохранения позиции

    private List<StudentСardEntity> studentСardArray = new ArrayList();
    private StudentBoxAdapter boxAdapter;

    private Button btnChekAddDate;
    private ListView listViewStudentChek;
    private String nameGroupSave;

    private int monts;
    private int day;
    private int year;

    private Spinner spinnerDay;
    private Spinner spinnerMonts;
    private Spinner spinnerYears;
    private String[] montsName = {"", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
            "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
    private ArrayList<String> yearsName = new ArrayList<>();
    private ArrayList<String> dayName = new ArrayList<>();
    private ArrayAdapter<String> adapterSpinerDay;
    private ArrayAdapter<String> adapterSpinerMonts;
    private ArrayAdapter<String> adapterSpinerYears;


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

        Calendar aDate = new GregorianCalendar(Locale.getDefault());
        monts = (aDate.get(Calendar.MONTH) + 1);
        day = aDate.get(Calendar.DATE);
        year = aDate.get(Calendar.YEAR);


        for (int i = 1; i <= getMondayDay(monts, year); i++) {
            dayName.add(String.valueOf(i));
        }


        spinnerDay = findViewById(R.id.TV_Day_Kalendar_Cheked);
        adapterSpinerDay = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dayName);
        adapterSpinerDay.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDay.getBackground().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        spinnerDay.setAdapter(adapterSpinerDay);
        spinnerDay.setSelection(day);
        spinnerDay.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                day = Integer.parseInt(dayName.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });


        spinnerMonts = findViewById(R.id.TV_Mounts_Kalendar_Cheked);
        adapterSpinerMonts = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, montsName);
        adapterSpinerMonts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMonts.getBackground().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        spinnerMonts.setAdapter(adapterSpinerMonts);
        spinnerMonts.setSelection(monts);
        spinnerMonts.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                monts = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        for (int i = 1; i < 33; i++) {
            int a = 2018 + i;
            yearsName.add(String.valueOf(a));
        }

        spinnerYears = findViewById(R.id.TV_Year_Kalendar_Cheked);
        adapterSpinerYears = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, yearsName);
        adapterSpinerYears.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYears.getBackground().setColorFilter(getResources().getColor(R.color.colorText), PorterDuff.Mode.SRC_ATOP);
        spinnerYears.setAdapter(adapterSpinerYears);
        for (int i = 0; i < 32; i++) {
            if (yearsName.get(i).equals(year)) {
                spinnerYears.setSelection(i);
            }
        }

        spinnerYears.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorText));
                ((TextView) parent.getChildAt(0)).setTextSize(20);
                year = Integer.parseInt(yearsName.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                StudentСardEntity studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);
                studentСardEntity.setIdstudent(dataSnapshot.getKey());

                if (nameGroupSave.equals(studentСardEntity.getGroupName())) {
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
        switch (view.getId()) {
            case R.id.bntChekDateAdd: {
                ArrayList<StudentСardEntity> array = boxAdapter.getReternStudentArray();
                for (int i = 0; i < array.size(); i++) {

                    StudentСardEntity studentСardEntity = boxAdapter.getReternStudentArray().get(i);

                    System.out.println("rrrrrrrrrrrrrrrrrrrr " + studentСardEntity.getNameStudent());

                    databaseReference.child(studentСardEntity.getIdstudent()).removeValue();

                    addJson(studentСardEntity);

                    Intent intent = new Intent(this, StudentActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            }
        }
    }

    private void addJson(StudentСardEntity studentСardEntity) {   //  c EDIT TEXT парсим JSON
        String jsonAddDate = new Gson().toJson(new DateEntity(
                day, monts, year));

        String jsonAdd = new Gson().toJson(new StudentСardEntity(

                studentСardEntity.getIdstudent(),
                studentСardEntity.getIdEnterStudent(),
                studentСardEntity.getUserOrAdmin(),
                studentСardEntity.getNameStudent(),
                studentСardEntity.getGroupName(),
                studentСardEntity.getVes(),
                studentСardEntity.getTitul(),
                studentСardEntity.getDate(),
                studentСardEntity.getSeson(),
                studentСardEntity.getTurnir(),
                studentСardEntity.getBoiov(),
                studentСardEntity.getPobed(),
                studentСardEntity.getPoragenii(),
                studentСardEntity.getOchkov(),
                studentСardEntity.getPressNorma(),
                studentСardEntity.getPressFact(),
                studentСardEntity.getOtgimaniyNorma(),
                studentСardEntity.getOtgimaniyFact(),
                studentСardEntity.getPodtjagNorma(),
                studentСardEntity.getPodtjagFact(),
                studentСardEntity.getRoznogkaNorma(),
                studentСardEntity.getRoznogkaFact(),
                studentСardEntity.getPrugkiNorma(),
                studentСardEntity.getPrugkiFact(),
                studentСardEntity.getUrlStudentFotoCard(),
                studentСardEntity.getReiting(),
                (studentСardEntity.getDataPosicheniy() + "/n" + jsonAddDate)));

        studentСardEntity.setDataPosicheniy(jsonAddDate);

        databaseReference.push().setValue(jsonAdd);
    }

    public static int getMondayDay(int m, int y) {
        Calendar aDate = new GregorianCalendar(y, (m - 1), 1);
        return aDate.getActualMaximum(Calendar.DAY_OF_MONTH);
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


