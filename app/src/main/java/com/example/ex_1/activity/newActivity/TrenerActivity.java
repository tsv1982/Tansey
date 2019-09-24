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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.example.ex_1.adapter.TrenierAdapter;
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

public class TrenerActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("trener");

    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private SharedPreferences.Editor editor;

    private StorageReference mStorageRef;

    private ListView listViewTrenier1;  // View для листа тренеров
    private Button btnAddTrener1;
    private Button btnDeleteTrener1;
    private Button btnRefactorTrener1;
    private TrenierAdapter trenierAdapter;
    private List<TrenierEntity> trenerArray = new ArrayList();
    private TextView textViewGetTrener;
    private int position;
    private String saveIdTrener;
    private String adminOrUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trener_activity);

        btnAddTrener1 = findViewById(R.id.btnAddTrenet1);
        btnAddTrener1.setOnClickListener(this);
        btnDeleteTrener1 = findViewById(R.id.btnDeleteTrenet1);
        btnDeleteTrener1.setOnClickListener(this);
        btnRefactorTrener1 = findViewById(R.id.btnRefactorTrenet1);
        btnRefactorTrener1.setOnClickListener(this);

        textViewGetTrener = findViewById(R.id.TV_Get_Trener);

        sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");

        if (adminOrUser.equals("admin")){
           btnAddTrener1.setVisibility(View.VISIBLE);
           btnDeleteTrener1.setVisibility(View.VISIBLE);
           btnRefactorTrener1.setVisibility(View.VISIBLE);
           textViewGetTrener.setVisibility(View.VISIBLE);
        }else {
            btnAddTrener1.setVisibility(View.GONE);
            btnDeleteTrener1.setVisibility(View.GONE);
            btnRefactorTrener1.setVisibility(View.GONE);
            textViewGetTrener.setVisibility(View.GONE);
        }

        listViewTrenier1 = findViewById(R.id.LV_Trainer1);
        trenierAdapter = new TrenierAdapter(this, R.layout.trainer_list_activity, trenerArray);
        listViewTrenier1.setAdapter(trenierAdapter);   // сетаем адаптер в листвиев

        listViewTrenier1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                position = position;
                textViewGetTrener.setText(trenerArray.get(position).getNameTener());
                saveIdTrener = trenerArray.get(position).getIdtrainers();
                textViewGetTrener.setTextColor(getResources().getColor(R.color.colorText2));
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {  //вытаскиваем с базы

                String s1 = String.valueOf(dataSnapshot.getValue());

                TrenierEntity trenierEntity = new Gson().fromJson(s1, TrenierEntity.class);
                trenierEntity.setIdtrainers(dataSnapshot.getKey());                         // присваиваем id c базы
                trenerArray.add(trenierEntity);
                trenierAdapter.notifyDataSetChanged();

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
            case R.id.btnAddTrenet1: {
                Intent intent = new Intent(this, TraineAddActivity.class);
                startActivity(intent);   // запуск активити добавления тренера
                finish();
                break;
            }
            case R.id.btnRefactorTrenet1: {
                sharedPreferences = getSharedPreferences("MyPref", MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString("saveIdRefactorTrener", saveIdTrener );
                editor.apply();

                Intent intent = new Intent(this, TrainerRefactorActivity.class);
                startActivity(intent);   // запуск активити добавления тренера
                finish();
                break;
            }
            case R.id.btnDeleteTrenet1: {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(Html.fromHtml("<font color='#91BFE9'>" + trenerArray.get(position).getNameTener() + "</font>"))
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
                                        databaseReference.child(trenerArray.get(position).getIdtrainers()).removeValue();

                                        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl(trenerArray.get(position).getUrlFotoTrener());
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

                                        Toast.makeText(view.getContext(), "удаленный тренер \n" + trenerArray.get(position).getNameTener(), Toast.LENGTH_LONG).show();
                                        trenierAdapter.remove(trenerArray.get(position));
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
