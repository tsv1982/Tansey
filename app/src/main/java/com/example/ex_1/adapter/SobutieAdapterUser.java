package com.example.ex_1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.ex_1.Entity.SobutieEntity;
import com.example.ex_1.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SobutieAdapterUser extends ArrayAdapter<SobutieEntity> {

    private LayoutInflater inflater;
    private int layout;
    private List<SobutieEntity> SobutieList;
    private SharedPreferences sharedPreferences;   // для сохранения позиции
    private String adminOrUser;
    private Context context;
    private SobutieEntity sobutieEntity;
    private String saveIdUser;


    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference("sobutie");

    public SobutieAdapterUser(Context context, int resource, List<SobutieEntity> sobutieList) {
        super(context, resource, sobutieList);
        this.SobutieList = sobutieList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
        this.context = context;

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        sharedPreferences = context.getSharedPreferences("MyPref", MODE_PRIVATE);
        adminOrUser = sharedPreferences.getString("passSaveUserOrAdmin", "");

        saveIdUser = sharedPreferences.getString("SaveIdUser", "");

        View view = inflater.inflate(this.layout, parent, false);

        TextView textSobutiy = view.findViewById(R.id.TV_text_sob);
        TextView dateSobutiy = view.findViewById(R.id.TV_Date_sob);
        TextView textView = view.findViewById(R.id.TV_IsSobbb);

        sobutieEntity = SobutieList.get(position);

        textSobutiy.setText(sobutieEntity.getTextSobutie());
        dateSobutiy.setText(sobutieEntity.getDataSobutie());

       textView.setVisibility(view.GONE);
        if (sobutieEntity.getIdUserYas() == null) {

        }
        else {
            String[] arr = sobutieEntity.getIdUserYas().split(",");
            for (String ss : arr) {
                if (ss.equals(saveIdUser)) {
                    textView.setVisibility(view.VISIBLE);
                }
            }
        }


        return view;
    }

}

