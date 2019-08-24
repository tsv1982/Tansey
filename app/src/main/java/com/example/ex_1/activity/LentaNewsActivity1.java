package com.example.ex_1.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ex_1.Entity.LentaNewsEntity;
import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.example.ex_1.activity.newActivity.AddLentaNewsActivity;
import com.example.ex_1.adapter.LentaNewsAdapter1;
import com.example.ex_1.java.UtilZaprosov;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LentaNewsActivity1 extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;
    private ArrayList<LentaNewsEntity> lentaNewsEntityList;  // list Entity тренеров
    private ListView listViewLentaNews;  // View для листа тренеров
    private LentaNewsAdapter1 lentaNewsAdapter1;
    private Button btnAddLentaNews;
    private Button btnDeleteLentaNews;
    private UtilZaprosov utilZaprosov;
    private int position;                  // позиция

    private int waitStart = 1;         // 1 ожидает загрузку conneckToServer() возвращает 2 не ожидает
    private int waitDelete = 1;         // 1 ожидает загрузку conneckToServer() возвращает 2 не ожидает

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lenta_news1);

        utilZaprosov = new UtilZaprosov();

        loadAsyncTask();   // запускае asynhron задачу там запускае conneckToServer() и возвращаем json

        while (waitStart == 1) {  // ожидаем загрузку
        }

        btnAddLentaNews = findViewById(R.id.btnAddLentaNews1);
        btnAddLentaNews.setOnClickListener(this);

        btnDeleteLentaNews = findViewById(R.id.btnDeleteLentaNews1);
        btnDeleteLentaNews.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
        String ss = sharedPreferences.getString("userOrAdmin", ZaprosF.getInstance().getAdminOrUser());

        if (ss.equals("admin")) {          // проверяет админ или нет кнопки видны будут или нет
            btnAddLentaNews.setVisibility(View.VISIBLE);
            btnDeleteLentaNews.setVisibility(View.VISIBLE);
        } else {
            btnAddLentaNews.setVisibility(View.GONE);
            btnDeleteLentaNews.setVisibility(View.GONE);
        }


        listViewLentaNews = findViewById(R.id.LV_LentaNews_List1);  // передаем в адаптер лояут и лист объектов тренер
        lentaNewsAdapter1 = new LentaNewsAdapter1(this, R.layout.list_news1, lentaNewsEntityList);
        listViewLentaNews.setAdapter(lentaNewsAdapter1);   // сетаем адаптер в листвиев

        listViewLentaNews.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                position = i;
            }
        });
    }

    private ArrayList<LentaNewsEntity> jsonToEntity(String s) {    // метод для обработки строки в json и json в объект трейнер
        String[] arr = s.split("\\}");  // розбиваем строку на json строки
        ArrayList<LentaNewsEntity> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {     // обрабатываем json в объекты и добавляем } так ка не хватае хнака после разбиения строк
            LentaNewsEntity lentaNewsEntity = new Gson().fromJson(arr[i] + "}", LentaNewsEntity.class);
            list.add(lentaNewsEntity);
        }
        return list;   // возвращаем в conneckToServer
    }

    private ArrayList<LentaNewsEntity> conneckToServer() {   // делает запрос на сервер и возвращает json
        waitStart = 1;   // 1 ожидаем 2 нет
        ArrayList<LentaNewsEntity> arrayList = null;
        ZaprosF zaprosF = ZaprosF.getInstance();      // вызываем инстанс констант запросов
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("allLentaNews", "getAllLentaNews")    // отправляет запрос с именем nameId
                .build();
        Request request = new Request.Builder()
                .url(zaprosF.getURL_Lenta_News())
                .post(formBody)
                .build();
        try {
            Response response = client.newCall(request).execute();
            arrayList = jsonToEntity(response.body().string());  // вызываем метод обработки json
        } catch (Exception e) {                                          // в объекты Trener
        }
        waitStart = 2;    //  возвращаем 2 загрузка с сервера закончена отпускае поток
        return arrayList;             // возвразщаем лист с объектами Trainer в loadAsyncTask
    }

    private void loadAsyncTask() {              // assyngron адача
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                lentaNewsEntityList = conneckToServer();   // вызываем метод который делает запрос
                return null;                                   // и сохраняем возвращенный лист
            }
        }.execute();
    }

    private void conneckToServerDelete(String idLentaNews) {   // делает запрос на сервер для удаления по id
        waitDelete = 1; // 1 ожидаем 2 нет
        ZaprosF zaprosF = ZaprosF.getInstance();      // вызываем инстанс констант запросов
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("deleteLentaNews", idLentaNews)    // отправляет запрос с именем deleteTrener
                .build();
        Request request = new Request.Builder()
                .url(zaprosF.getURL_Lenta_News())
                .post(formBody)
                .build();
        try {
            client.newCall(request).execute();
        } catch (Exception e) {
        }
        waitDelete = 2;    //  возвращаем 2 загрузка с сервера закончена отпускае поток
    }

    private void loadAsyncTaskDelete() {              // assyngron адача для удаления
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                conneckToServerDelete(String.valueOf(lentaNewsEntityList.get(position).getIdlentaNews()));
                return null;                                   // передаем id тренера для удаления
            }
        }.execute();
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btnAddLentaNews1: {    //  менять на активити адд шоп
                if (utilZaprosov.hasConnection(this)) {
                    Intent intent = new Intent(this, AddLentaNewsActivity.class);
                    startActivity(intent);   // запуск активити добавления тренера
                    finish();
                }
                break;
            }
            case R.id.btnDeleteLentaNews1: {
                if (utilZaprosov.hasConnection(this)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + lentaNewsEntityList.get(position).getNameNews() + "</font>"))
                            .setIcon(R.drawable.logo)
                            .setCancelable(true)
                            .setPositiveButton("отмена", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {
                                    dialog.cancel();
                                }
                            })
                            .setNegativeButton("удалить",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            loadAsyncTaskDelete();
                                            while (waitDelete == 1) {  // ожидаем загрузку
                                            }
                                            Toast.makeText(view.getContext(), "удаленный товар \n" + lentaNewsEntityList.get(position).getNameNews(), Toast.LENGTH_LONG).show();
                                            lentaNewsAdapter1.remove(lentaNewsEntityList.get(position));
                                            dialog.cancel();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                    alert.getWindow().setBackgroundDrawableResource(R.drawable.error_user_id);

                    Button nbuttonN = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                    nbuttonN.setTextColor(Color.RED);

                    Button nbuttonP = alert.getButton(DialogInterface.BUTTON_POSITIVE);
                    nbuttonP.setTextColor(Color.BLUE);
                }
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);  // извлекаем перемунную позицию
        int ss = sharedPreferences.getInt("savePositionListNews", 2);
        listViewLentaNews.setSelectionFromTop(ss, 2);    // переходим в позицию
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("savePositionListNews", position);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();          // сохраняем позицию
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("savePositionListNews", position);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();            // востанавливаем позицию
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        int ss = sharedPreferences.getInt("savePositionListNews", 2);
        listViewLentaNews.setSelectionFromTop(ss, 2);
    }

}
