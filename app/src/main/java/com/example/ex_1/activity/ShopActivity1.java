package com.example.ex_1.activity;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ex_1.Entity.ShopEntity;
import com.example.ex_1.R;
import com.example.ex_1.ZaprosF;
import com.example.ex_1.activity.newActivity.ShopAddActivity;
import com.example.ex_1.adapter.ShopAdapter;
import com.example.ex_1.java.UtilZaprosov;
import com.google.gson.Gson;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ShopActivity1 extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;
    private ArrayList<ShopEntity> shopEntityArrayList;  // list Entity тренеров
    private ListView listViewShop;  // View для листа тренеров
    private ShopAdapter shopAdapter;
    private Button btnAddShop;
    private Button btnDeleteShop;
    private UtilZaprosov utilZaprosov;
    private int pisition;                  // позиция

    private int waitStart = 1;         // 1 ожидает загрузку conneckToServer() возвращает 2 не ожидает
    private int waitDelete = 1;         // 1 ожидает загрузку conneckToServer() возвращает 2 не ожидает

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop1);

        utilZaprosov = new UtilZaprosov();

        loadAsyncTask();   // запускае asynhron задачу там запускае conneckToServer() и возвращаем json

        while (waitStart == 1) {  // ожидаем загрузку
        }

        btnAddShop = findViewById(R.id.btnAddShop);
        btnAddShop.setOnClickListener(this);

        btnDeleteShop = findViewById(R.id.btnDeleteShop);
        btnDeleteShop.setOnClickListener(this);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
        String ss = sharedPreferences.getString("userOrAdmin", ZaprosF.getInstance().getAdminOrUser());
        if (ss.equals("admin")) {          // проверяет админ или нет кнопки видны будут или нет
            btnAddShop.setVisibility(View.VISIBLE);
            btnDeleteShop.setVisibility(View.VISIBLE);
        } else {
            btnAddShop.setVisibility(View.GONE);
            btnDeleteShop.setVisibility(View.GONE);
        }


        listViewShop = findViewById(R.id.LV_Shop);  // передаем в адаптер лояут и лист объектов тренер
        shopAdapter = new ShopAdapter(this, R.layout.list_shop, shopEntityArrayList);
        listViewShop.setAdapter(shopAdapter);   // сетаем адаптер в листвиев

        listViewShop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                pisition = i;
            }
        });
    }

    private ArrayList<ShopEntity> jsonToEntity(String s) {    // метод для обработки строки в json и json в объект трейнер
        String[] arr = s.split("\\}");  // розбиваем строку на json строки
        ArrayList<ShopEntity> list = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {     // обрабатываем json в объекты и добавляем } так ка не хватае хнака после разбиения строк
            ShopEntity shopEntity = new Gson().fromJson(arr[i] + "}", ShopEntity.class);
            list.add(shopEntity);
        }
        return list;   // возвращаем в conneckToServer
    }

    private ArrayList<ShopEntity> conneckToServer() {   // делает запрос на сервер и возвращает json
        waitStart = 1;   // 1 ожидаем 2 нет
        ArrayList<ShopEntity> arrayList = null;
        ZaprosF zaprosF = ZaprosF.getInstance();      // вызываем инстанс констант запросов
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("allShop", "getAllShop")    // отправляет запрос с именем nameId
                .build();
        Request request = new Request.Builder()
                .url(zaprosF.getURL_SHOP_ALL())
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
                shopEntityArrayList = conneckToServer();   // вызываем метод который делает запрос
                return null;                                   // и сохраняем возвращенный лист
            }
        }.execute();
    }

    private void conneckToServerDelete(String idShop) {   // делает запрос на сервер для удаления по id
        waitDelete = 1; // 1 ожидаем 2 нет
        ZaprosF zaprosF = ZaprosF.getInstance();      // вызываем инстанс констант запросов
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("deleteShop", idShop)    // отправляет запрос с именем deleteTrener
                .build();
        Request request = new Request.Builder()
                .url(zaprosF.getURL_SHOP_ALL())
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
                conneckToServerDelete(String.valueOf(shopEntityArrayList.get(pisition).getIdshop()));
                return null;                                   // передаем id тренера для удаления
            }
        }.execute();
    }

    @Override
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btnAddShop: {    //  менять на активити адд шоп
                if (utilZaprosov.hasConnection(this)) {
                    Intent intent = new Intent(this, ShopAddActivity.class);
                    startActivity(intent);   // запуск активити добавления тренера
                    finish();
                }
                break;
            }
            case R.id.btnDeleteShop: {
                if (utilZaprosov.hasConnection(this)) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + shopEntityArrayList.get(pisition).getNameShop() + "</font>"))
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
                                            Toast.makeText(view.getContext(), "удаленный товар \n" + shopEntityArrayList.get(pisition).getNameShop(), Toast.LENGTH_LONG).show();
                                            shopAdapter.remove(shopEntityArrayList.get(pisition));
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
        int ss = sharedPreferences.getInt("savePositionListShop", 2);
        listViewShop.setSelectionFromTop(ss, 2);    // переходим в позицию
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("savePositionListShop", pisition);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();          // сохраняем позицию
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("savePositionListShop", pisition);
        editor.apply();
    }

    @Override
    protected void onResume() {
        super.onResume();            // востанавливаем позицию
        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        int ss = sharedPreferences.getInt("savePositionListShop", 2);
        listViewShop.setSelectionFromTop(ss, 2);
    }

}
