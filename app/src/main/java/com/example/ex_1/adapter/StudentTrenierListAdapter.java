package com.example.ex_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class StudentTrenierListAdapter extends ArrayAdapter<TrenierEntity> {

    private LayoutInflater inflater;
    private int layout;
    private List<TrenierEntity> trenerList;

    public StudentTrenierListAdapter(Context context, int resource, List<TrenierEntity> trenerList) {
        super(context, resource, trenerList);
        this.trenerList = trenerList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView imageShop = view.findViewById(R.id.IV_Student_Trener);
        TextView nameShop = view.findViewById(R.id.TV_Name_student_Trener);

        TrenierEntity trenierEntity = trenerList.get(position);

        nameShop.setText(trenierEntity.getNameTener());

        Picasso.with(view.getContext())
                .load(trenierEntity.getUrlFotoTrener())
                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                .error(R.drawable.loading_error)  // если еррор
                .into(imageShop);

        return view;
    }
}
