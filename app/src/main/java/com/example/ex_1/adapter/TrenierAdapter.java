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
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.example.ex_1.Entity.TrenierEntity;
import com.example.ex_1.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrenierAdapter extends ArrayAdapter<TrenierEntity> {

    private LayoutInflater inflater;
    private int layout;
    private List<TrenierEntity> trenierList;

    public TrenierAdapter(Context context, int resource, List<TrenierEntity> trenierList) {
        super(context, resource, trenierList);
        this.trenierList = trenierList;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView imageTrener = view.findViewById(R.id.IV_trainer_image);
        TextView nameTrener = view.findViewById(R.id.TV_Name_trener);
        TextView dateTrener = view.findViewById(R.id.TV_Date_B);
        TextView abautTrener = view.findViewById(R.id.TV_abaut_trener);
        TextView graficZanatiyTrener = view.findViewById(R.id.TV_grafic_zanjatiy);

        final TrenierEntity trenierEntity = trenierList.get(position);

        nameTrener.setText(trenierEntity.getNameTener());
        dateTrener.setText(trenierEntity.getDateObirth());

        String s = trenierEntity.getAboutTrener();

        if (s.length() > 65){
            s = s.substring(0, 65) + " ..........";
        }

        abautTrener.setText(s);
        graficZanatiyTrener.setText(trenierEntity.getGrafikZanjatiy());

        abautTrener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder
                        .setTitle(Html.fromHtml("<font color='#91BFE9'>" + trenierEntity.getNameTener() + "</font>"))
                        .setMessage(Html.fromHtml("<font color='#D7E4E9'>" + trenierEntity.getAboutTrener() + "</font>"))
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

        Picasso.with(view.getContext())
                .load(trenierEntity.getUrlFotoTrener())
                .placeholder(R.drawable.loading)   // заглушка во время загрузки
                .error(R.drawable.loading_error)  // если еррор
                .into(imageTrener);

        return view;
    }
}
