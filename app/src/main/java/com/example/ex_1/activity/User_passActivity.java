package com.example.ex_1.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.example.ex_1.java.UtilZaprosov;

public class User_passActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText editText;
    private UtilZaprosov utilZaprosov;
    private SharedPreferences sharedPreferences;
    private Intent intent;
    private ZaprosF zaprosF;
    private ActionBar bar;

    // TODO: 16.07.19 проверка подключения интернета

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_pass);



        button = findViewById(R.id.buttonID);
        button.setOnClickListener(this);
        editText = findViewById(R.id.editText);

        bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));    // задаем цвет бара

        editText.setOnClickListener(this);

        utilZaprosov = new UtilZaprosov();

        zaprosF = ZaprosF.getInstance();

        intent = new Intent(this, HomeActivity.class);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);

        if (sharedPreferences.getBoolean("passSave", false)) {
            startActivity(intent);
            finish();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonID: {

                if (utilZaprosov.hasConnection(this)) {

                    zaprosF.setUserIDEnter(String.valueOf(editText.getText()));
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            tt();
                            return null;
                        }
                    }.execute();

                }

                break;
            }

            case R.id.editText: {
                editText.setText("");
                break;
            }
        }
    }

    void tt() {
        if (utilZaprosov.checkUser(zaprosF.getUserIDEnter())) {

            sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor1 = sharedPreferences.edit();
            editor1.putString("userId", zaprosF.getUserIDEnter());
            editor1.apply();


            sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("passSave", true);

            utilZaprosov.adminOrUser(zaprosF.getUserIDEnter());
            editor.putString("userOrAdmin", zaprosF.getAdminOrUser());

            editor.apply();

            startActivity(intent);
//        finish();  подумать может не надо
        } else {
            Intent intentError = new Intent(this, ErrorUserActivity.class);
            sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("passSave", false);
            editor.apply();
            startActivity(intentError);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
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
