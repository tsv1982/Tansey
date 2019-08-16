package com.example.ex_1.activity.newActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ex_1.Entity.ShopEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.ShopAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("shop");

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;

    private StorageReference mStorageRef;

    private ListView listViewShop1;  // View для листа тренеров
    private Button btnAddShop1;
    private Button btnDeleteShop1;
    private Button btnRefactorShop1;
    private ShopAdapter shopAdapter;
    private List<ShopEntity> shopArray = new ArrayList();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop_activity);

        btnAddShop1 = findViewById(R.id.btnAddShop1);
        btnAddShop1.setOnClickListener(this);
        btnDeleteShop1 = findViewById(R.id.btnDeleteShop1);
        btnDeleteShop1.setOnClickListener(this);
        btnRefactorShop1 = findViewById(R.id.btnRefactorShop1);
        btnRefactorShop1.setOnClickListener(this);

        listViewShop1 = findViewById(R.id.LV_Shop1);
        shopAdapter = new ShopAdapter(this, R.layout.trainer_list_activity, shopArray);
        listViewShop1.setAdapter(shopAdapter);   // сетаем адаптер в листвиев

        listViewShop1.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
                position = i;
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                ShopEntity shopEntity = new Gson().fromJson(s1, ShopEntity.class);
                shopEntity.setIdshop(dataSnapshot.getKey());

                shopArray.add(shopEntity);
                shopAdapter.notifyDataSetChanged();

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
            case R.id.btnAddShop1: {
                Intent intent = new Intent(this, ShopAddActivity.class);
                startActivity(intent);   // запуск активити добавления тренера
                finish();
                break;
            }
            case R.id.btnRefactorShop1: {
                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("saveIdRefactorShop", shopArray.get(position).getIdshop());
                editor.apply();

//                Intent intent = new Intent(this, ShopRefactorActivity.class);
//                startActivity(intent);   // запуск активити добавления тренера
//                finish();
                break;
            }
            case R.id.btnDeleteShop1: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + shopArray.get(position).getNameShop() + "</font>"))
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
                                        databaseReference.child(shopArray.get(position).getIdshop()).removeValue();

                                        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(shopArray.get(position).getUrlFotoShop());
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

                                        Toast.makeText(view.getContext(), "удаленный тренер \n" + shopArray.get(position).getNameShop(), Toast.LENGTH_LONG).show();
                                        shopAdapter.remove(shopArray.get(position));
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
        }
    }
}