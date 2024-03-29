package com.example.ex_1.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.ex_1.Entity.ImageNewsEntity;
import com.example.ex_1.Entity.LentaNewsEntity;
import com.example.ex_1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class LentaNewsAdapter extends ArrayAdapter<LentaNewsEntity> {
    private LayoutInflater inflater;
    private int layout;
    private List<LentaNewsEntity> listLentaNewsEntities;
    ArrayList<ImageNewsEntity> listImage = new ArrayList<>();
    ImageAdapter imageAdapter;


    public LentaNewsAdapter(Context context, int resource, List<LentaNewsEntity> listLentaNewsEntities) {
        super(context, resource, listLentaNewsEntities);
        this.listLentaNewsEntities = listLentaNewsEntities;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = inflater.inflate(this.layout, parent, false);

        final ImageView imageNewsVie = view.findViewById(R.id.IV_news_image1);
        TextView nameNewsView = view.findViewById(R.id.name_news1);
        TextView textNewsView = view.findViewById(R.id.text_news1);
        TextView dateNewsView = view.findViewById(R.id.data_news1);
        TextView autorNewsView = view.findViewById(R.id.autor_news1);

        TextView textViewPlusFoto = view.findViewById(R.id.TV_Lenta_News_Plus_foto);

        final LentaNewsEntity lentaNewsEntity = listLentaNewsEntities.get(position);


        nameNewsView.setText(lentaNewsEntity.getNameNews());

        String textReplace = lentaNewsEntity.getText();

        if (textReplace.length() > 65) {
            textReplace = textReplace.substring(0, 65) + " ..........";
        }

        textNewsView.setText(textReplace);
        dateNewsView.setText(lentaNewsEntity.getData());
        autorNewsView.setText(lentaNewsEntity.getAuthorNews());

        textNewsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder
                        .setTitle(Html.fromHtml("<font color='#91BFE9'>" + lentaNewsEntity.getNameNews() + "</font>"))
                        .setMessage(Html.fromHtml("<font color='#D7E4E9'>" + lentaNewsEntity.getText() + "</font>"))
                        .setNegativeButton("выход",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();

                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().setBackgroundDrawableResource(R.drawable.error_user_id);

                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.BLUE);
            }
        });

        imageNewsVie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ListView listView = new ListView(view.getContext());

                if (lentaNewsEntity.getUrlPictureNews1() != null) {
                    listImage.add(new ImageNewsEntity(lentaNewsEntity.getUrlPictureNews1()));
                }
                if (lentaNewsEntity.getUrlPictureNews2() != null) {
                    listImage.add(new ImageNewsEntity(lentaNewsEntity.getUrlPictureNews2()));
                }
                if (lentaNewsEntity.getUrlPictureNews3() != null) {
                    listImage.add(new ImageNewsEntity(lentaNewsEntity.getUrlPictureNews3()));
                }
                if (lentaNewsEntity.getUrlPictureNews4() != null) {
                    listImage.add(new ImageNewsEntity(lentaNewsEntity.getUrlPictureNews4()));
                }
                if (lentaNewsEntity.getUrlPictureNews5() != null) {
                    listImage.add(new ImageNewsEntity(lentaNewsEntity.getUrlPictureNews5()));
                }

                imageAdapter = new ImageAdapter(view.getContext(), R.layout.lenta_news_list_image_image_view, listImage);
                listView.setAdapter(imageAdapter);
                imageAdapter.notifyDataSetChanged();

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setView(listView)
                        .setNegativeButton("выход",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        listImage.clear();
                                    }
                                });
                AlertDialog alert = builder.create();
                alert.show();
                alert.getWindow().setBackgroundDrawableResource(R.drawable.error_user_id);

                Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
                nbutton.setTextColor(Color.BLUE);
            }
        });

        textViewPlusFoto.setText("+ " + (lentaNewsEntity.getTime())); // количество фото

        ImageNewsEntity imageNewsEntity = new ImageNewsEntity(lentaNewsEntity.getUrlPictureNews1());

        Picasso.with(view.getContext())
                .load(imageNewsEntity.getURL())
                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                .error(R.drawable.loading_error)  // если еррор
                .into(imageNewsVie);

        return view;
    }
}