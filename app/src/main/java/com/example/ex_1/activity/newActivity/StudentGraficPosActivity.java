package com.example.ex_1.activity.newActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ex_1.Entity.DateEntity;
import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;

public class StudentGraficPosActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");

    private SharedPreferences sharedPreferences;
    private String saveIdStudent;

    private LinearLayout linearLayout;
    private TableLayout tableLayout;
    private String[] dayWekName = {"пн", "вт", "ср", "чт", "пт", "сб", "вс"};
    private String[] montsName = {"", "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
            "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

    private ArrayList<String> yearsName = new ArrayList<>();
    private int[] dayWekEU = {0, 7, 1, 2, 3, 4, 5, 6};

    private ArrayList<DateEntity> arrayListDataEntity = new ArrayList<>();
    private ArrayList<DateEntity> arrayListNawDataEntity = new ArrayList<>();

    private Spinner spinnerMonts;
    private Spinner spinnerYears;

    private int monts;
    private int day;
    private int year;

    private ArrayAdapter<String> adapterSpinerMonts;
    private ArrayAdapter<String> adapterSpinerYears;

    private StudentСardEntity studentСardEntity;
    private DateEntity dateEntity;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_grafic_pos_activity);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
        saveIdStudent = sharedPreferences.getString("saveIdStudent", "");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String sKey = String.valueOf(dataSnapshot.getKey());

                if (saveIdStudent.equals(sKey)) {
                    String s1 = String.valueOf(dataSnapshot.getValue());

                    studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);

                    if (studentСardEntity.getDataPosicheniy() == null) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "еще не было посещений", Toast.LENGTH_SHORT);
                        toast.show();

                    } else {
                        String sDate = studentСardEntity.getDataPosicheniy();
                        String[] arr = sDate.split("/n");

                        for (int i = 0; i < arr.length; i++) {
                            if (arr[i].equals("null")) {
                            } else {
                                dateEntity = new Gson().fromJson(arr[i], DateEntity.class);
                                arrayListDataEntity.add(dateEntity);
                            }

                        }
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

        Calendar aDate = new GregorianCalendar(Locale.getDefault());
        monts = (aDate.get(Calendar.MONTH) + 1);
        day = aDate.get(Calendar.DATE);
        year = aDate.get(Calendar.YEAR);

        spinnerMonts = findViewById(R.id.TV_Mounts_Kalendar);
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
                tt();
                linearLayout.removeAllViews();
                tt();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        for (int i = 1; i < 33; i++) {
            int a = 2018 + i;
            yearsName.add(String.valueOf(a));
        }

        spinnerYears = findViewById(R.id.TV_Year_Kalendar);
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
                tt();
                linearLayout.removeAllViews();
                tt();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

    }

    private void tt() {
        arrayListNawDataEntity = new ArrayList<>();
        for (int i = 0; i <arrayListDataEntity.size() ; i++) {
            if (arrayListDataEntity.get(i).getMonts() == monts && arrayListDataEntity.get(i).getYear() == year ){
                arrayListNawDataEntity.add(arrayListDataEntity.get(i));
            }
        }

        linearLayout = findViewById(R.id.LinearLayout1_Gragik_Pos);

        tableLayout = new TableLayout(this);

        tableLayout.setLayoutParams(new TableLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        ));
        tableLayout.setStretchAllColumns(true);

        TableRow tableRowD = new TableRow(this);
        tableRowD.setBackgroundColor(0xffcccccc);
        for (int i = 0; i < 7; i++) {
            TextView textViewDayD = new TextView(this);
            textViewDayD.setText(dayWekName[i]);
            tableRowD.addView(textViewDayD);
        }
        tableLayout.addView(tableRowD);

        int startDay = dayWekEU[getweekday(1, monts, year)];
        int startCounter = 1;
        int countDay = 0;
        for (int i = 0; i < 6; i++) {
            TableRow tableRowi = new TableRow(this);
            for (int j = 0; j < 7; j++) {

                TextView textViewi = new TextView(this);

                if (startDay > startCounter) {
                    startCounter++;
                } else {
                    countDay++;
                    if (countDay <= getMondayDay(1, monts, year)) {
                        textViewi.setText(String.valueOf(countDay));
                        textViewi.setTextColor(Color.WHITE);
                        for (int k = 0; k <arrayListNawDataEntity.size() ; k++) {
                            if (countDay == arrayListNawDataEntity.get(k).getDate()){
                                textViewi.setTextColor(Color.BLUE);
                            }
                        }
                    }
                }

                tableRowi.addView(textViewi);

            }
            tableLayout.addView(tableRowi);
        }

        linearLayout.addView(tableLayout);
    }

    public static int getweekday(int d, int m, int y) {
        Calendar aDate = new GregorianCalendar(y, (m - 1), d);
        return aDate.get(Calendar.DAY_OF_WEEK);
    }

    public static int getMondayDay(int d, int m, int y) {
        Calendar aDate = new GregorianCalendar(y, (m - 1), d);
        return aDate.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

}