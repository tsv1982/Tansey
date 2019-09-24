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


public class StudentReitingAdapter extends ArrayAdapter<StudentСardEntity> {

    private LayoutInflater inflater;
    private int layout;
    private List<StudentСardEntity> studentList;

    public StudentReitingAdapter(Context context, int resource, List<StudentСardEntity> studentList) {
        super(context, resource, studentList);
        this.studentList = studentList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView imageShop = view.findViewById(R.id.IV_Student_Reiting);
        TextView nameShop = view.findViewById(R.id.TV_Name_student_Reiting);
        TextView nomer = view.findViewById(R.id.TV_Reiting_Nomer_Porjadok);
        TextView ochki = view.findViewById(R.id.TV_SumOchki_student_Reiting);

        StudentСardEntity studentСardEntity = studentList.get(position);

        nameShop.setText(studentСardEntity.getNameStudent());
        nomer.setText(String.valueOf(position+1));

        int summOchki = Integer.parseInt(studentСardEntity.getPressFact()) +
        Integer.parseInt(studentСardEntity.getOtgimaniyFact()) +
        Integer.parseInt(studentСardEntity.getPodtjagFact()) +
        Integer.parseInt(studentСardEntity.getRoznogkaFact()) +
        Integer.parseInt(studentСardEntity.getPrugkiFact());

        ochki.setText(String.valueOf(summOchki));

        Picasso.with(view.getContext())
                .load(studentСardEntity.getUrlStudentFotoCard())
                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                .error(R.drawable.loading_error)  // если еррор
                .into(imageShop);

        return view;
    }
}

