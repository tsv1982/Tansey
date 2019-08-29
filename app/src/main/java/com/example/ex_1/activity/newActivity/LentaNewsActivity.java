package com.example.ex_1.activity.newActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ex_1.Entity.LentaNewsEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.LentaNewsAdapter;
import com.example.ex_1.java.UtilZaprosov;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;

public class LentaNewsActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("LentaNews");
    private StorageReference mStorageRef;

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;
    private ArrayList<LentaNewsEntity> lentaNewsEntityList = new ArrayList<>();  // list Entity тренеров
    private ListView listViewLentaNews;  // View для листа тренеров
    private LentaNewsAdapter lentaNewsAdapter;
    private Button btnAddLentaNews;
    private Button btnDeleteLentaNews;
    private Button btnRefactorNews;
    private TextView textViewGetLentaNews;
    private UtilZaprosov utilZaprosov;
    private int position2;                  // позиция
    private String saveIdLentaNews;
    private String adminOrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lenta_news_activity);

        utilZaprosov = new UtilZaprosov();

        btnAddLentaNews = findViewById(R.id.btnAdd_LentaNews);
        btnAddLentaNews.setOnClickListener(this);

        btnDeleteLentaNews = findViewById(R.id.btnDelete_LentaNews);
        btnDeleteLentaNews.setOnClickListener(this);

        btnRefactorNews = findViewById(R.id.btnRefactor_LentaNews);
        btnRefactorNews.setOnClickListener(this);

        textViewGetLentaNews = findViewById(R.id.TV_Get_LentaNews);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");

        if (adminOrUser.equals("admin")) {         // проверяет админ или нет кнопки видны будут или нет
            btnAddLentaNews.setVisibility(View.VISIBLE);
            btnDeleteLentaNews.setVisibility(View.VISIBLE);
            btnRefactorNews.setVisibility(View.VISIBLE);
            textViewGetLentaNews.setVisibility(View.VISIBLE);
        } else {
            btnAddLentaNews.setVisibility(View.GONE);
            btnDeleteLentaNews.setVisibility(View.GONE);
            btnRefactorNews.setVisibility(View.GONE);
            textViewGetLentaNews.setVisibility(View.GONE);
        }

        listViewLentaNews = findViewById(R.id.LV_LentaNews);  // передаем в адаптер лояут и лист объектов тренер
        lentaNewsAdapter = new LentaNewsAdapter(this, R.layout.lenta_list_news, lentaNewsEntityList);
        listViewLentaNews.setAdapter(lentaNewsAdapter);   // сетаем адаптер в листвиев

        listViewLentaNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                position2 = position;
                textViewGetLentaNews.setText(lentaNewsEntityList.get(position).getNameNews());
                saveIdLentaNews = lentaNewsEntityList.get(position).getIdlentaNews();
                textViewGetLentaNews.setTextColor(getResources().getColor(R.color.colorText2));
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                LentaNewsEntity lentaNewsEntity = new Gson().fromJson(s1, LentaNewsEntity.class);
                lentaNewsEntity.setIdlentaNews(dataSnapshot.getKey());

                String[] arr = lentaNewsEntity.getUrlPictureNews1().split(",");
                lentaNewsEntity.setTime(String.valueOf(arr.length - 1));  // количество фото

                if (arr.length > 1) {
                    lentaNewsEntity.setUrlPictureNews1(arr[1]);
                }

                if (arr.length > 2) {
                    lentaNewsEntity.setUrlPictureNews2(arr[2]);
                }

                if (arr.length > 3) {
                    lentaNewsEntity.setUrlPictureNews3(arr[3]);
                }

                if (arr.length > 4) {
                    lentaNewsEntity.setUrlPictureNews4(arr[4]);
                }

                if (arr.length > 5) {
                    lentaNewsEntity.setUrlPictureNews5(arr[5]);
                }

                lentaNewsEntityList.add(lentaNewsEntity);
                lentaNewsAdapter.notifyDataSetChanged();

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
    public void onClick(final View view) {
        switch (view.getId()) {
            case R.id.btnAdd_LentaNews: {    //  менять на активити адд шоп
                if (utilZaprosov.hasConnection(this)) {
                    Intent intent = new Intent(this, LentaAddNewsActivity.class);
                    startActivity(intent);   // запуск активити добавления тренера
                    finish();
                }
                break;
            }
            case R.id.btnDelete_LentaNews: {
                if (textViewGetLentaNews.getText().equals("")) {
                    Toast.makeText(view.getContext(), "выберите тему новости", Toast.LENGTH_LONG).show();
                } else {

                    if (utilZaprosov.hasConnection(this)) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + lentaNewsEntityList.get(position2).getNameNews() + "</font>"))
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
                                                databaseReference.child(lentaNewsEntityList.get(position2).getIdlentaNews()).removeValue();

                                                textViewGetLentaNews.setText("");

                                                if (lentaNewsEntityList.get(position2).getUrlPictureNews1() != null) {
                                                    deleteFoto(lentaNewsEntityList.get(position2).getUrlPictureNews1());
                                                }
                                                if (lentaNewsEntityList.get(position2).getUrlPictureNews2() != null) {
                                                    deleteFoto(lentaNewsEntityList.get(position2).getUrlPictureNews2());
                                                }
                                                if (lentaNewsEntityList.get(position2).getUrlPictureNews3() != null) {
                                                    deleteFoto(lentaNewsEntityList.get(position2).getUrlPictureNews3());
                                                }
                                                if (lentaNewsEntityList.get(position2).getUrlPictureNews4() != null) {
                                                    deleteFoto(lentaNewsEntityList.get(position2).getUrlPictureNews4());
                                                }
                                                if (lentaNewsEntityList.get(position2).getUrlPictureNews5() != null) {
                                                    deleteFoto(lentaNewsEntityList.get(position2).getUrlPictureNews5());
                                                }

                                                Toast.makeText(view.getContext(), "удаленный тренер \n" + lentaNewsEntityList.get(position2).getNameNews(), Toast.LENGTH_LONG).show();
                                                lentaNewsAdapter.remove(lentaNewsEntityList.get(position2));
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
                        break;
                    }
                    break;
                }
            }

            case R.id.btnRefactor_LentaNews: {

                if (textViewGetLentaNews.getText().equals("")) {
                    Toast.makeText(view.getContext(), "выберите тему новости", Toast.LENGTH_LONG).show();
                } else {
                    if (utilZaprosov.hasConnection(this)) {
                        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("saveIdLentaNews", saveIdLentaNews);
                        editor.apply();

                        Intent intent = new Intent(view.getContext(), LentaNewsRefactorActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
                break;
            }
        }
    }

    void deleteFoto(String url) {

        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(url);
        mStorageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                // File deleted successfully
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Uh-oh, an error occurred!
            }
        });

    }

}
