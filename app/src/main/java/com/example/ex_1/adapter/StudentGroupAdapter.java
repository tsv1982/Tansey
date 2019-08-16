package com.example.ex_1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ex_1.Entity.StudentGroupEntity;

import com.example.ex_1.R;

import java.util.List;


public class StudentGroupAdapter extends ArrayAdapter<StudentGroupEntity>  {

    private LayoutInflater inflater;
    private int layout;
    private List<StudentGroupEntity> groupEntityList;

    public StudentGroupAdapter(Context context, int resource, List<StudentGroupEntity> groupEntityList) {
        super(context, resource, groupEntityList);
        this.groupEntityList = groupEntityList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, final View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        TextView nameGroup = view.findViewById(R.id.TV_student_group_list);
        TextView textViewNemeTrener = view.findViewById(R.id.TV_student_group_list_nameTrener);

        StudentGroupEntity studentGroupEntity = groupEntityList.get(position);

        nameGroup.setText(studentGroupEntity.getNameGroup());
        textViewNemeTrener.setText(studentGroupEntity.getNameTrener());

        return view;
    }

}
