package com.example.ex_1.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.LentaNewsEntity;
import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class AddLentaNewsActivity extends AppCompatActivity implements View.OnClickListener {

    LentaNewsEntity lentaNewsEntity;

    EditText editTextNameLentaNews;
    EditText editTextDataLentaNews;
    EditText editTextTimeLentaNews;
    EditText editTextTextLentaNews;
    EditText editTextAutorLentaNews;

    Button btnAddLentaNews;
    Button btnOpenGAlery;
    TextView XXXXX;
    String filePut;

    int waitAdd = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lenta_news);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN); // убрать фокус при загрузке

        editTextNameLentaNews = findViewById(R.id.ET_Lenta_News_Name1);
        editTextDataLentaNews = findViewById(R.id.ET_Data_Lenta_News1);
        editTextTimeLentaNews = findViewById(R.id.ET_Time_Lenta_News1);
        editTextTextLentaNews = findViewById(R.id.ET_Text_Lenta_News1);
        editTextAutorLentaNews = findViewById(R.id.ET_Autor_Lenta_News1);

        btnAddLentaNews = findViewById(R.id.btn_ADD_Lenta_News1);
        btnAddLentaNews.setOnClickListener(this);

        XXXXX = findViewById(R.id.XXXXXX_Lenta_News1);

        btnOpenGAlery = findViewById(R.id.btn_xxxxxx_Lenta_News1);
        btnOpenGAlery.setOnClickListener(this);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && null != data) {

            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            XXXXX.setText(picturePath);
            System.out.println("FFFFFFFFFFFFF    " + picturePath);
            filePut = picturePath;
            cursor.close();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btn_ADD_Lenta_News1: {
                lentaNewsEntity = new LentaNewsEntity();

                lentaNewsEntity.setIdlentaNews(255);
                lentaNewsEntity.setNameNews(String.valueOf(editTextNameLentaNews.getText()));
                lentaNewsEntity.setData(String.valueOf(editTextDataLentaNews.getText()));
                lentaNewsEntity.setTime(String.valueOf(editTextTimeLentaNews.getText()));
                lentaNewsEntity.setText(String.valueOf(editTextTextLentaNews.getText()));
                lentaNewsEntity.setUrlPictureNews("url picture");
                lentaNewsEntity.setAuthorNews(String.valueOf(editTextAutorLentaNews.getText()));

                loadAsyncTaskAdd();


                while (waitAdd == 1) {
                }

                Toast.makeText(view.getContext(), "добавлена новость \n" + editTextNameLentaNews.getText(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LentaNewsActivity1.class);
                startActivity(intent);
                finish();
                break;
            }

            case R.id.btn_xxxxxx_Lenta_News1: {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 101);
            }
        }

    }

    private void addJson(LentaNewsEntity lentaNewsEntity) {   //  c EDIT TEXT парсим JSON

        String jsonAdd = new Gson().toJson(new LentaNewsEntity(
                lentaNewsEntity.getIdlentaNews(),
                lentaNewsEntity.getNameNews(),
                lentaNewsEntity.getData(),
                lentaNewsEntity.getTime(),
                lentaNewsEntity.getText(),
                lentaNewsEntity.getUrlPictureNews(),
                lentaNewsEntity.getAuthorNews()));

        conneckToServerADD(jsonAdd);
    }

    private void conneckToServerADD(String jsonAdd) {   // делает запрос на сервер для add trenera
        waitAdd = 1;
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, jsonAdd);
        Request request = new Request.Builder()
                .addHeader("addLentaNews", "dskjdskdfs")
                .url(ZaprosF.getInstance().getURL_Lenta_News())
                .post(body)
                .build();
        try {
            client.newCall(request).execute();
            waitAdd = 2;
        } catch (Exception e) {
        }
    }

    private void loadAsyncTaskAdd() {              // assyngron адача для удаления
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                addJson(lentaNewsEntity);
                return null;                                   // передаем id тренера для удаления
            }
        }.execute();
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
