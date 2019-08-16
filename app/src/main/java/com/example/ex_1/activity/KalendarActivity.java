package com.example.ex_1.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.ex_1.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class KalendarActivity extends AppCompatActivity {

    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kalendar);


        calendarView = findViewById(R.id.calendarView1);





        String selectedDate = "12/09/2019";
        try {

            calendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate).getTime(), true, true);

        } catch (ParseException e) {
            e.printStackTrace();
        }


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                String selectedDate = new StringBuilder().append(mMonth + 1)
                        .append("-").append(mDay).append("-").append(mYear)
                        .append(" ").toString();
                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_LONG).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(KalendarActivity.this);
                builder.setTitle("Важное сообщение!\n")
                        .setMessage(selectedDate + " \n будет соревнование")
                        .setCancelable(true);
//                        .setNegativeButton("Установить",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        Intent intent = new Intent(Intent.ACTION_VIEW);
//                                        intent.setData(Uri.parse("market://details?id=com.google.android.googlequicksearchbox&hl=ru"));
//                                        startActivity(intent);
//                                    }
//                                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }
}

