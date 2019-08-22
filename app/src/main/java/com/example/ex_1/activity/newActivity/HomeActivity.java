package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.example.ex_1.activity.KalendarActivity;
import com.example.ex_1.activity.LentaNewsActivity1;
import com.example.ex_1.activity.MessageActivity;
import com.example.ex_1.activity.MyService;
import com.example.ex_1.activity.PaymentActivity;
import com.example.ex_1.java.UtilZaprosov;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReferenceStudent = firebaseDatabase.getReference("student");

    private Button bntLenta;
    private Button btnKartaPeople;
    private Button btnKalendar;
    private Button btnPay;
    private Button btnMessage;
    private Button btnShop;
    private Button btnTrener;
    private Button btnGrafic;
    private ActionBar bar;
    private UtilZaprosov utilZaprosov;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    String adminOrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");

        utilZaprosov = new UtilZaprosov();

        bntLenta = findViewById(R.id.btnLenta);
        bntLenta.setOnClickListener(this);

        btnKartaPeople = findViewById(R.id.btnKartaPeople);
        btnKartaPeople.setOnClickListener(this);

        btnKalendar = findViewById(R.id.btnKalendar);
        btnKalendar.setOnClickListener(this);

        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(this);

        btnMessage = findViewById(R.id.btnMessage);
        btnMessage.setOnClickListener(this);

        btnShop = findViewById(R.id.btn_shop);
        btnShop.setOnClickListener(this);

        btnTrener = findViewById(R.id.btnTrener);
        btnTrener.setOnClickListener(this);

        btnGrafic = findViewById(R.id.btnGrafic);
        btnGrafic.setOnClickListener(this);

        bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));    // задаем цвет бара

        databaseReferenceStudent.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());
                StudentСardEntity studentСardEntity = new Gson().fromJson(s1, StudentСardEntity.class);

                if (studentСardEntity.getIdEnterStudent().equals(sharedPreferences.getString("passSaveIdEnterUser", ""))){

                    editor = sharedPreferences.edit();
                    editor.putString("saveIdStudent", dataSnapshot.getKey());
                    editor.apply();
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


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // получим идентификатор выбранного пункта меню
        int id = item.getItemId();

        // Операции для выбранного пункта меню
        switch (id) {
            case R.id.action_0:
                Toast.makeText(this, "включенно оповищение", Toast.LENGTH_LONG).show();
                startService(new Intent(this, MyService.class));
                return true;
            case R.id.action_1:
                Toast.makeText(this, "выключенно оповищение", Toast.LENGTH_LONG).show();
                stopService(new Intent(this, MyService.class));
                return true;
            case R.id.action_2:
                SharedPreferences sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("passSave", false);
                editor.apply();
                Intent intent = new Intent(this, User_passActivity.class);
                startActivity(intent);
                this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLenta: {
                Intent intent;
                if (utilZaprosov.hasConnection(this)) {
                    intent = new Intent(this, LentaNewsActivity1.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.btnKartaPeople: {

                if (adminOrUser.equals("admin")){
                    Intent intent = new Intent(this, StudentActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(this, StudentKartaPeopleActivity.class);
                    startActivity(intent);
                }



                break;
            }
            case R.id.btnKalendar: {
                Intent intent;
                if (utilZaprosov.hasConnection(this)) {
                    intent = new Intent(this, KalendarActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.btnPay: {
                Intent intent;
                if (utilZaprosov.hasConnection(this)) {
                    intent = new Intent(this, PaymentActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.btnMessage: {
                Intent intent;
                if (utilZaprosov.hasConnection(this)) {
                    intent = new Intent(this, MessageActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.btn_shop: {
                Intent intent;
                if (utilZaprosov.hasConnection(this)) {
                    intent = new Intent(this, ShopActivity.class);
                    startActivity(intent);
                }
                break;
            }
            case R.id.btnTrener: {
                Intent intent;
                if (utilZaprosov.hasConnection(this)) {
                    intent = new Intent(this, TrenerActivity.class);
                    startActivity(intent);
                } else
                    break;
                return;
            }
            case R.id.btnGrafic: {
                Intent intent;
                if (utilZaprosov.hasConnection(this)) {
                    intent = new Intent(this, StudentGraficPosActivity.class);
                    startActivity(intent);
                } else
                    break;
            }

        }
    }
}
