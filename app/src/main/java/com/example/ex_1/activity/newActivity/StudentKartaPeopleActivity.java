package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.example.ex_1.activity.PaymentActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

public class StudentKartaPeopleActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("student");

    private SharedPreferences sharedPreferences;

    private ImageView imageViewFotoStudent;

    private TextView textViewNameStudent;
    private TextView textViewDate;
    private TextView textViewNameGroup;
    private TextView textViewRang;

    private TextView textViewD1;
    private TextView textViewD2;
    private TextView textViewD3;

    private Button btnGrafic;
    private Button btnPeoplePay;

    private StudentСardEntity studentСardEntity;
    private String saveIdStudent;
    private Context context;

    TextView textView11111;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_karta_people_activity);
        context = this;

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
        saveIdStudent = sharedPreferences.getString("saveIdStudent", "");

        imageViewFotoStudent = findViewById(R.id.Iv_picture_student);
        textViewNameStudent = findViewById(R.id.tv_student_name);
        textViewDate = findViewById(R.id.tv_student_date);
        textViewNameGroup = findViewById(R.id.tv_student_Group_Name);
        textViewRang = findViewById(R.id.TV_Rang_student);
        textViewD1 = findViewById(R.id.TV_D1_student);
        textViewD2 = findViewById(R.id.TV_D2_student);
        textViewD3 = findViewById(R.id.TV_D3_student);

        textView11111 = findViewById(R.id.iiiiiiiiiiiiiiiiiiiii);
        textView11111.setText("75");
        textView11111.setTextColor(Color.RED);

         databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String sKey = String.valueOf(dataSnapshot.getKey());

                if (saveIdStudent.equals(sKey)) {
                    String s1 = String.valueOf(dataSnapshot.getValue());
                    studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);

                    Picasso.with(context)
                            .load(studentСardEntity.getUrlStudentFotoCard())
                            .placeholder(R.drawable.loading)   // заглушка во время загрузки
                            .error(R.drawable.loading_error)  // если еррор
                            .into(imageViewFotoStudent);

                    textViewNameStudent.setText(studentСardEntity.getNameStudent());
                    textViewDate.setText(studentСardEntity.getDate());
                    textViewNameGroup.setText(studentСardEntity.getGroupName());
                    textViewRang.setText(studentСardEntity.getRang());
                    textViewD1.setText(studentСardEntity.getDost1());
                    textViewD2.setText(studentСardEntity.getDost2());
                    textViewD3.setText(studentСardEntity.getDost3());
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

        btnPeoplePay = findViewById(R.id.btnPeoplePay_student);
        btnPeoplePay.setOnClickListener(this);
        btnGrafic = findViewById(R.id.btnPeopleGrofik_student);
        btnGrafic.setOnClickListener(this);

        textViewD1.setOnClickListener(this);
        textViewD2.setOnClickListener(this);
        textViewD3.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPeoplePay_student: {
                Intent intent = new Intent(this, PaymentActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.btnPeopleGrofik_student:{
                Intent intent = new Intent(this, StudentGraficPosActivity.class);
                startActivity(intent);
                break;
            }
//            case R.id.TV_D1_student: {
//
//                GraphViewSeries exampleSeries = new GraphViewSeries(
//                        new GraphView.GraphViewData[]{
//
//                                new GraphView.GraphViewData(1, 20),
//                                new GraphView.GraphViewData(2, 1),
//                                new GraphView.GraphViewData(3, 2),
//                                new GraphView.GraphViewData(4, 4),
//                                new GraphView.GraphViewData(5, 5)});
//
//
//                GraphView graphView = new LineGraphView(this, "График каких-то данных");
//                graphView.addSeries(exampleSeries);
//
//                graphView.getGraphViewStyle().setHorizontalLabelsColor(Color.WHITE);
//                graphView.getGraphViewStyle().setVerticalLabelsColor(Color.WHITE);
//
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
//                builder.setView(graphView)
//                        .setNegativeButton("выход",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        dialog.cancel();
////                                        listImage.clear();
//                                    }
//                                });
//                AlertDialog alert = builder.create();
//                alert.show();
//                alert.getWindow().setBackgroundDrawableResource(R.drawable.error_user_id);
//
//                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
//                nbutton.setTextColor(Color.BLUE);
//
//                break;
//
//            }

        }

    }
}
