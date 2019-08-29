package com.example.ex_1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ex_1.Entity.StudentСardEntity;
import com.example.ex_1.R;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class StudentBoxAdapter extends BaseAdapter {

    private Context ctx;
    private LayoutInflater lInflater;
    private ArrayList<StudentСardEntity> studentCardEntityArray;
    private ArrayList<StudentСardEntity> reternStudentArray = new ArrayList<>();

    private StudentСardEntity studentСardEntity;

    public StudentBoxAdapter(Context context, ArrayList<StudentСardEntity> studentСardEntityArrayList) {
        ctx = context;
        studentCardEntityArray = studentСardEntityArrayList;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return studentCardEntityArray.size();
    }

    @Override
    public Object getItem(int position) {
        return studentCardEntityArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.student_check_list_adapter, parent, false);
        }

        studentСardEntity = studentCardEntityArray.get(position);

        TextView textViewStudentName = view.findViewById(R.id.tvDescr);
        textViewStudentName.setText(studentСardEntity.getNameStudent());
        ImageView imageViewStudent = view.findViewById(R.id.ivImage);

        Picasso.with(view.getContext())
                .load(studentСardEntity.getUrlStudentFotoCard())
                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                .error(R.drawable.loading_error)  // если еррор
                .into(imageViewStudent);

        CheckBox cbBuy = view.findViewById(R.id.cbBox);
        // присваиваем чекбоксу обработчик
        cbBuy.setOnCheckedChangeListener(myCheckChangeList);
        // пишем позицию
        cbBuy.setTag(position);
        // заполняем данными из товаров: в корзине или нет
        cbBuy.setChecked(studentCardEntityArray.get(position).getIsChecket());
        return view;
    }

    public ArrayList<StudentСardEntity> getReternStudentArray() {
        return reternStudentArray;
    }

    // обработчик для чекбоксов
    OnCheckedChangeListener myCheckChangeList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            studentCardEntityArray.get((Integer) buttonView.getTag()).setChecket(isChecked);
            reternStudentArray.add(studentCardEntityArray.get((Integer) buttonView.getTag()));

        }
    };
}