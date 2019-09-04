package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
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
    private TextView textViewGroupName;
    private TextView textViewVes;
    private TextView textViewTitul;
    private TextView textViewDate;
    private TextView textViewSeson;
    private TextView textViewTurnir;
    private TextView textViewBoiov;
    private TextView textViewPobed;
    private TextView textViewPoragenii;
    private TextView textViewOchkov;
    private TextView textViewPressNorma;
    private TextView textViewPressFact;
    private TextView textViewOtgimaniyNorma;
    private TextView textViewOtgimaniyFact;
    private TextView textViewPodtjagNorma;
    private TextView textViewPodtjagFact;
    private TextView textViewRoznogkaNorma;
    private TextView textViewRoznogkaFact;
    private TextView textViewPrugkiNorma;
    private TextView textViewPrugkiFact;
    private TextView textViewReiting;

    private Button btnGrafic;
    private Button btnPeoplePay;

    private StudentСardEntity studentСardEntity;
    private String saveIdStudent;
    private Context context;

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
        textViewReiting = findViewById(R.id.tv_student_reiting);
        textViewVes = findViewById(R.id.tv_student_ves);
        textViewGroupName = findViewById(R.id.tv_student_Group_Name);
        textViewTitul = findViewById(R.id.TV_Titul_student);
        textViewSeson = findViewById(R.id.TV_seson_student);
        textViewTurnir = findViewById(R.id.TV_turnir_Student);
        textViewBoiov = findViewById(R.id.TV_Boiov_Student);
        textViewPobed = findViewById(R.id.TV_pobed_Student);
        textViewPoragenii = findViewById(R.id.TV_porageniy_Student);
        textViewOchkov = findViewById(R.id.TV_ochkov_Student);
        textViewPressNorma = findViewById(R.id.TV_Press_Norma_Student);
        textViewOtgimaniyNorma = findViewById(R.id.TV_Otgimaniy_Norma_Student);
        textViewPodtjagNorma = findViewById(R.id.TV_Podtjag_Norma_Student);
        textViewRoznogkaNorma = findViewById(R.id.TV_Roznogka_Norma_Student);
        textViewPrugkiNorma = findViewById(R.id.TV_Prugki_Norma_Student);
        textViewPressFact = findViewById(R.id.TV_Press_Fact_Student);
        textViewOtgimaniyFact = findViewById(R.id.TV_Otgimaniy_Fact_Student);
        textViewPodtjagFact = findViewById(R.id.TV_Podtjag_Fact_Student);
        textViewRoznogkaFact = findViewById(R.id.TV_Roznogka_Fact_Student);
        textViewPrugkiFact = findViewById(R.id.TV_Prugki_Fact_Student);

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
                    textViewGroupName.setText(studentСardEntity.getGroupName());
                    textViewVes.setText(studentСardEntity.getVes() + " " + "кг");
                    textViewTitul.setText(studentСardEntity.getTitul());
                    textViewDate.setText(studentСardEntity.getDate());
                    textViewSeson.setText(studentСardEntity.getSeson());
                    textViewTurnir.setText(studentСardEntity.getTurnir());
                    textViewBoiov.setText(studentСardEntity.getBoiov());
                    textViewPobed.setText(studentСardEntity.getPobed());
                    textViewPoragenii.setText(studentСardEntity.getPoragenii());
                    textViewOchkov.setText(studentСardEntity.getOchkov());

                    textViewPressNorma.setText(studentСardEntity.getPressNorma());
                    textViewPressFact.setText(studentСardEntity.getPressFact());
                    if (Integer.parseInt(studentСardEntity.getPressNorma()) >
                            Integer.parseInt(studentСardEntity.getPressFact())) {
                        textViewPressFact.setTextColor(Color.RED);
                    }

                    textViewOtgimaniyNorma.setText(studentСardEntity.getOtgimaniyNorma());
                    textViewOtgimaniyFact.setText(studentСardEntity.getOtgimaniyFact());
                    if (Integer.parseInt(studentСardEntity.getOtgimaniyNorma()) >
                            Integer.parseInt(studentСardEntity.getOtgimaniyFact())) {
                        textViewOtgimaniyFact.setTextColor(Color.RED);
                    }

                    textViewPodtjagNorma.setText(studentСardEntity.getPodtjagNorma());
                    textViewPodtjagFact.setText(studentСardEntity.getPodtjagFact());
                    if (Integer.parseInt(studentСardEntity.getPodtjagNorma()) >
                            Integer.parseInt(studentСardEntity.getPodtjagFact())) {
                        textViewPodtjagFact.setTextColor(Color.RED);
                    }

                    textViewRoznogkaNorma.setText(studentСardEntity.getRoznogkaNorma());
                    textViewRoznogkaFact.setText(studentСardEntity.getRoznogkaFact());
                    if (Integer.parseInt(studentСardEntity.getRoznogkaNorma()) >
                            Integer.parseInt(studentСardEntity.getRoznogkaFact())) {
                        textViewRoznogkaFact.setTextColor(Color.RED);
                    }

                    textViewPrugkiNorma.setText(studentСardEntity.getPrugkiNorma());
                    textViewPrugkiFact.setText(studentСardEntity.getPrugkiFact());
                    if (Integer.parseInt(studentСardEntity.getPrugkiNorma()) >
                            Integer.parseInt(studentСardEntity.getPrugkiFact())) {
                        textViewPrugkiFact.setTextColor(Color.RED);
                    }

                    textViewReiting.setText("рейтинг по клубу - " + studentСardEntity.getReiting());

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

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnPeoplePay_student: {
//                Intent intent = new Intent(this, PaymentActivity.class);
//                startActivity(intent);
                finish();
                break;
            }
            case R.id.btnPeopleGrofik_student: {
                Intent intent = new Intent(this, StudentGraficPosActivity.class);
                startActivity(intent);
                finish();
                break;
            }

        }

    }
}
