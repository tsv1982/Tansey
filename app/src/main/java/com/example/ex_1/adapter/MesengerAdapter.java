package com.example.ex_1.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ex_1.R;
import com.example.ex_1.Entity.Mesage;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class MesengerAdapter extends ArrayAdapter<Mesage> {

    private LayoutInflater inflater;
    private int layout;
    private List<Mesage> mesages;
    private SharedPreferences sharedPreferences;


    public MesengerAdapter(Context context, int resource, List<Mesage> mesages) {
        super(context, resource, mesages);
        this.mesages = mesages;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView textMessageView = view.findViewById(R.id.text_Mesage);
        TextView dateMessageView = view.findViewById(R.id.dateMesage);

        Mesage mesage = mesages.get(position);

        String textReplace = mesage.getTextMesage();
        if (textReplace.length() > 20) {
            textReplace = textReplace.substring(0, 20) + " ..........";
        }
        textMessageView.setText(textReplace);

        dateMessageView.setText(mesage.getDateMesage());

        sharedPreferences = view.getContext().getSharedPreferences("MyPref", MODE_PRIVATE);   // вытаскиваем переменную
        String ss = sharedPreferences.getString("saveMassageView", "");

        String[] arr = ss.split("\n");
        for (int i = 0; i <arr.length ; i++) {
            if (mesage.getName().equals(arr[i])){
                textMessageView.setTextColor(Color.GRAY);
                dateMessageView.setTextColor(Color.GRAY);
            }
        }



        return view;
    }

}