package com.example.ex_1.activity;

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

import com.example.ex_1.R;
import com.example.ex_1.activity.newActivity.StudentGraficPosActivity;
import com.example.ex_1.activity.newActivity.ShopActivity;
import com.example.ex_1.activity.newActivity.StudentActivity;
import com.example.ex_1.activity.newActivity.TrenerActivity;
import com.example.ex_1.java.UtilZaprosov;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

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

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("userOrAdmin", "admin");
        editor.apply();

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

        startService(new Intent(this, MyService.class));

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
//                infoTextView.setText("Вы выбрали кошку!");
                return true;
            case R.id.action_3:
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

                Intent intent = new Intent(this, StudentActivity.class);
                startActivity(intent);

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
