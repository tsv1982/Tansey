package com.example.ex_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.squareup.picasso.Picasso;

import java.util.List;


public class StudentListAdapter extends ArrayAdapter<StudentСardEntity>  {

    private LayoutInflater inflater;
    private int layout;
    private List<StudentСardEntity> studentEntityList;

    public StudentListAdapter(Context context, int resource, List<StudentСardEntity> StudentEntityList) {
        super(context, resource, StudentEntityList);
        this.studentEntityList = StudentEntityList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, final View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        ImageView imageStudent = view.findViewById(R.id.IV_Student_Adapter);
        TextView nameStudent = view.findViewById(R.id.TV_Name_student_Adapter);
        TextView groupStudent = view.findViewById(R.id.TV_Group_student_Adapter);

        StudentСardEntity studentСardEntity = studentEntityList.get(position);

       nameStudent.setText(studentСardEntity.getNameStudent());
       groupStudent.setText(studentСardEntity.getGroupName());

        Picasso.with(view.getContext())
                .load(studentСardEntity.getUrlStudentFotoCard())
                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                .error(R.drawable.loading_error)  // если еррор
                .into(imageStudent);
        return view;
    }

}